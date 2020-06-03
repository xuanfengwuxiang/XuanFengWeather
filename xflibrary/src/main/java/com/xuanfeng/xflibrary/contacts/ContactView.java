package com.xuanfeng.xflibrary.contacts;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.xuanfeng.xflibrary.R;
import com.xuanfeng.xflibrary.contacts.ContactRecyclerView.ContactBean;
import com.xuanfeng.xflibrary.utils.SystemUtils;

import java.util.ArrayList;
import java.util.List;

public class ContactView extends LinearLayout {

    private Context mContext;

    public ContactView(Context context) {
        this(context, null);
    }

    public ContactView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ContactView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        View view = View.inflate(mContext, R.layout.layout_contact_view, this);
        ContactRecyclerView rv = view.findViewById(R.id.recyclerView);
        rv.setData(getContacts());

    }

    private List<ContactBean> getContacts() {
        List<ContactBean> result = new ArrayList<>();
        List<SystemUtils.ContactsBean> list = SystemUtils.getAllContacts(mContext);
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        for (SystemUtils.ContactsBean bean : list) {
            ContactBean temp = new ContactBean();
            temp.setFirst(!isContain(result, bean.getFirstChar()));
            temp.setFirstChar(bean.getFirstChar());
            temp.setName(bean.getName());
            temp.setPhoneNum(bean.getPhoneNum());
            result.add(temp);
        }

        return result;
    }

    private boolean isContain(List<ContactBean> list, String ss) {

        if (list == null || list.isEmpty()) {
            return false;
        }
        for (ContactBean bean : list) {
            if (TextUtils.equals(bean.getFirstChar(), ss)) {
                return true;
            }
        }
        return false;
    }
}
