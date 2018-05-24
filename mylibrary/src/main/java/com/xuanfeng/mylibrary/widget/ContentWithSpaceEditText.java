package com.xuanfeng.mylibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

import com.xuanfeng.mylibrary.R;

/**
 * Created by Zhujh on 2017/11/21.
 * 这个EditText支持：电话号码、身份证、银行卡号输入的时候，自动加入空格
 */

public class ContentWithSpaceEditText extends EditText {

    public static final int TYPE_PHONE = 0;//电话
    public static final int TYPE_CARD = 1;//银行卡
    public static final int TYPE_IDCARD = 2;//身份证
    private int maxLength = 100;
    private int contentType;
    private int start, count, before;
    private String digits;

    public ContentWithSpaceEditText(Context context) {
        this(context, null);
    }

    public ContentWithSpaceEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttributeSet(context, attrs);
    }

    public ContentWithSpaceEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttributeSet(context, attrs);
    }

    //获取属性type的类型、以及初始化
    private void parseAttributeSet(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ContentWithSpaceEditText, 0, 0);
        contentType = ta.getInt(R.styleable.ContentWithSpaceEditText_type, 0);
        ta.recycle();
        initType();
        setSingleLine();
        addTextChangedListener(watcher);
    }

    //根据type限定输入的长度、类型
    private void initType() {
        if (contentType == TYPE_PHONE) {//电话11位+2空位
            maxLength = 13;
            digits = "0123456789 ";
            setInputType(InputType.TYPE_CLASS_NUMBER);
        } else if (contentType == TYPE_CARD) {//借记卡19位+4空位
            maxLength = 23;
            digits = "0123456789 ";
            setInputType(InputType.TYPE_CLASS_NUMBER);
        } else if (contentType == TYPE_IDCARD) {//身份证18位+3空位
            maxLength = 21;
            digits = "0123456789xX ";
            setInputType(InputType.TYPE_CLASS_TEXT);
        }
        setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
    }

    @Override//设置输入类型
    public void setInputType(int type) {
        super.setInputType(type);
        // setKeyListener要在setInputType后面调用，否则无效
        if (!TextUtils.isEmpty(digits)) {
            setKeyListener(DigitsKeyListener.getInstance(digits));
        }
    }

    //显示格式化
    private TextWatcher watcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override//在文本变化时调用,此时s的内容已发生改变
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            ContentWithSpaceEditText.this.start = start;//start代表变化的起始位置
            ContentWithSpaceEditText.this.before = before;//before代表变化前，该位置开始字符数量
            ContentWithSpaceEditText.this.count = count;//count代表新增加的字符长度
            Log.d("onTextChanged", "start==" + start + "before==" + before + "count==" + count);
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s == null) {
                return;
            }
            //判断是否是在中间输入，需要重新计算空格位置
            boolean isMiddle = (start + count) < (s.length());
            //在末尾输入时，是否需要加入空格，需要重新计算空格位置
            boolean isNeedSpace = false;
            if (!isMiddle && isSpace(s.length())) {
                isNeedSpace = true;
            }
            //1、在中间输入  2、在末尾输入需要加空格的时候  3、有内容输入的时候    这3中情况都需要重新计算
            if (isMiddle || isNeedSpace || count > 1) {
                //重新调整内容，在需要的位置加上空格
                String newStr = s.toString();
                newStr = newStr.replace(" ", "");
                StringBuilder sb = new StringBuilder();
                int spaceCount = 0;
                for (int i = 0; i < newStr.length(); i++) {
                    sb.append(newStr.substring(i, i + 1));
                    //如果当前输入的字符下一位为空格(i+1+1+spaceCount)，因为i是从0开始计算的，所以一开始的时候需要先加1
                    if (isSpace(i + 2 + spaceCount)) {
                        sb.append(" ");
                        spaceCount += 1;
                    }
                }
                removeTextChangedListener(watcher);
                s.replace(0, s.length(), sb);
                //内容调整完成后，设置光标的位置
                //如果是在末尾的话,或者加入的字符个数大于零的话（输入或者粘贴）
                if (!isMiddle || count > 1) {
                    setSelection(s.length() <= maxLength ? s.length() : maxLength);
                } else if (isMiddle) {
                    //如果是删除
                    if (count == 0) {
                        //如果删除时，光标停留在空格的前面，光标则要往前移一位
                        /*if (isSpace(start - before + 1)) {
                            setSelection((start - before) > 0 ? start - before : 0);
                        } else {
                            setSelection((start - before + 1) > s.length() ? s.length() : (start - before + 1));
                        }*/
                        if (isSpace(start)) {
                            setSelection(start - 1);
                        } else {
                            setSelection(start);
                        }
                    }
                    //如果是增加
                    else {
                        /*if (isSpace(start - before + count)) {
                            setSelection((start + count - before + 1) < s.length() ? (start + count - before + 1) : s.length());
                        } else {
                            setSelection(start + count - before);
                        }*/

                        if (isSpace(start + count)) {
                           setSelection(start + count + 1);
                        } else {
                            setSelection(start + count);
                        }
                    }
                }
                addTextChangedListener(watcher);
            }
            if (count == 0) {//在“末尾”删除的时候，删除后末尾是空格的话，连同空格一起删除
                removeTextChangedListener(watcher);
                deleteWhenLastIsSpace(s);
                addTextChangedListener(watcher);
            }
        }
    };

    //在“末尾”删除的时候，删除后末尾是空格的话，连同空格一起删除
    private void deleteWhenLastIsSpace(Editable s) {
        if (contentType == TYPE_PHONE) {
            if (s.length() == 4) {
                s.delete(3, 4);
            }
            if (s.length() == 9) {
                s.delete(8, 9);
            }
        } else if (contentType == TYPE_CARD) {
            if (isSpaceCard(s.length())) {
                s.delete(s.length() - 1, s.length());
            }
        } else if (contentType == TYPE_IDCARD) {
            if (isSpaceIDCard(s.length())) {
                s.delete(s.length() - 1, s.length());
            }
        }

    }

    //是否需要加空格
    private boolean isSpace(int length) {
        if (contentType == TYPE_PHONE) {
            return isSpacePhone(length);
        } else if (contentType == TYPE_CARD) {
            return isSpaceCard(length);
        } else if (contentType == TYPE_IDCARD) {
            return isSpaceIDCard(length);
        }
        return false;
    }

    //此长度末尾是否是空格，电话
    private boolean isSpacePhone(int length) {
        return length >= 4 && (length == 4 || (length + 1) % 5 == 0);
    }

    //此长度末尾是否是空格，银行卡
    private boolean isSpaceCard(int length) {
        return length % 5 == 0;
    }

    //此长度末尾是否是空格，身份证
    private boolean isSpaceIDCard(int length) {
        return length > 6 && (length == 7 || (length - 2) % 5 == 0);
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
        initType();
    }

    public String getTextWithoutSpace() {
        return super.getText().toString().replace(" ", "");
    }

    /**
     * @Description 内容校验
     */
    public boolean isContentCheck() {
        String text = getTextWithoutSpace();
        if (contentType == TYPE_PHONE) {
            if (TextUtils.isEmpty(text)) {
                //ToastUtil.showText(UIUtils.getString(R.string.phone_number_not_empty));
            } else if (text.length() < 11) {
                // ToastUtil.showText(UIUtils.getString(R.string.phone_number_incorrect_length));
            } else {
                return true;
            }
        } else if (contentType == TYPE_CARD) {
            if (TextUtils.isEmpty(text)) {
                //ToastUtil.showText(UIUtils.getString(R.string.bank_card_not_empty));
            } else if (text.length() < 16) {
                // ToastUtil.showText(UIUtils.getString(R.string.bank_card_incorrect_length));
            } else {
                return true;
            }
        } else if (contentType == TYPE_IDCARD) {
            if (TextUtils.isEmpty(text)) {
                //ToastUtil.showText(UIUtils.getString(R.string.identity_number_not_empty));
            } else if (text.length() < 18) {
                //ToastUtil.showText(UIUtils.getString(R.string.identity_number_incorrect_length));
            } else {
                return true;
            }
        }
        return false;
    }
}
