package com.xuanfeng.testcomponent.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.transition.Explode;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xuanfeng.mylibrary.mvp.BaseActivity;
import com.xuanfeng.mylibrary.mvp.BasePresenter;
import com.xuanfeng.mylibrary.utils.ImageUtil;
import com.xuanfeng.mylibrary.utils.SoftKeyBoardUtil;
import com.xuanfeng.mylibrary.widget.popupmenu.PopupMenu;
import com.xuanfeng.testcomponent.R;
import com.xuanfeng.testcomponent.databinding.ActivityTestBinding;

import java.util.ArrayList;
import java.util.List;


@Route(path = "/testcomponent/TestActivity")
public class TestActivity extends BaseActivity<BasePresenter, ActivityTestBinding> {


    private PopupMenu mPopupMenu;
    private SoftKeyBoardUtil mSoftKeyBoardUtil;

    public void onClick(View view) {
        int i = view.getId();//测试自定义EditText
        if (i == R.id.tv_test_for_edittext) {
            Intent intent = new Intent(this, TestForEditTextActivity.class);
            startActivity(intent);
        } else if (i == R.id.tv_test_for_popupmenu) {
            if (mPopupMenu == null) {
                initPopupMenu();
            }
            if (!mPopupMenu.isShowing()) {
                mPopupMenu.showAsDropDown(mBinding.tvTestForPopupmenu);
            }
        } else if (i == R.id.tv_test_for_touch_dispatch) {
            Intent intent;
            intent = new Intent(this, TestTouchEventActivity.class);
            startActivity(intent);
        } else if (i == R.id.tv_test_for_flag) {
            addFlags((int) Math.pow(2, abs++));
            mBinding.tvTestForFlag.setText(Integer.toBinaryString(mFlags) + "");
        } else if (i == R.id.tv_test_for_download_https_image) {//下载https图片
            String url = "http://imgservice.suning.cn/uimg1/snsawp/snsawp_common/d59b9eb8-c2de-4885-a826-ce0679d2bb3c.png";
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String path = Environment.getExternalStorageDirectory().getAbsolutePath();
                    Bitmap bitmap = ImageUtil.getBitmap(url);
                    ImageUtil.saveBitmapToSD(bitmap, path, "ceshi.jpg");

                }
            }).start();

        } else if (i == R.id.tv_test_for_gallery) {
            Intent intent;
            intent = new Intent(this, TestForGalleryActivity.class);
            startActivity(intent);
        } else if (i == R.id.tv_aidl) {
            Intent intent;
            intent = new Intent(this, TestAIDLActivity.class);
            startActivity(intent);
        } else if (i == R.id.ll_test_share_anim) {
            Intent intent;
            intent = new Intent(this, TestShareAnimActivity.class);
            Pair pair = Pair.create(mBinding.tvShareAnim, "fab");
            Pair pair1 = Pair.create(mBinding.ivShareAnim, "pic");
            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(TestActivity.this, pair, pair1);
            startActivity(intent, activityOptionsCompat.toBundle());
        } else if (i == R.id.iv_left) {
            finishAfterTransition();
        }
    }


    private void initPopupMenu() {
        final List<String> list = new ArrayList<>();
        list.add("周杰伦");
        list.add("王力宏");
        list.add("陶喆");
        list.add("周传雄");
        list.add("胡彦斌");
        list.add("刘德华");
        list.add("简弘亦");
        list.add("张学友");
        list.add("杨坤");
        mPopupMenu = new PopupMenu(this, mBinding.tvTestForPopupmenu.getWidth(), 500, list);
        mPopupMenu.setOnItemClickListener((view, position) -> {
            mPopupMenu.dismiss();
            Toast.makeText(TestActivity.this, list.get(position), Toast.LENGTH_SHORT).show();
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mSoftKeyBoardUtil.removeListener();
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    public BasePresenter initPresenter() {
        mBinding.setListener(this);
        return null;
    }


    @Override
    public void initData(Bundle bundle) {
        mBinding.tvTittle.setText("测试界面");
        mSoftKeyBoardUtil = SoftKeyBoardUtil.setListener(this, mKeyBoardListener);
    }

    SoftKeyBoardUtil.KeyBoardListener mKeyBoardListener = new SoftKeyBoardUtil.KeyBoardListener() {
        @Override
        public void keyBoardShow(int height) {
            //do nothing
        }

        @Override
        public void keyBoardHide(int height) {
            //do nothing
        }
    };

    @Override
    public int getStatusBarColorResId() {
        return R.color.white;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setEnterTransition(new Explode());
        super.onCreate(savedInstanceState);
    }

    private int mFlags = 0b000;
    int abs = 0;

    //mFlags是否true
    public boolean isFlags(int mFlags) {
        return (this.mFlags & mFlags) == mFlags;
    }

    //设置mFlags 为true
    public void addFlags(int mFlags) {
        this.mFlags |= mFlags;
    }

    //设置mFlags为false
    public void removeFlags(int mFlags) {
        this.mFlags &= ~mFlags;
    }

    //设置mFlags属性value
    public void setFlags(int mFlags, boolean value) {
        if (value) {
            addFlags(mFlags);
        } else {
            removeFlags(mFlags);
        }
    }
}
