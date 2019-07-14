package com.xuanfeng.testcomponent.activity;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

import com.xuanfeng.mylibrary.mvp.BaseActivity;
import com.xuanfeng.mylibrary.mvp.BasePresenter;
import com.xuanfeng.testcomponent.R;
import com.xuanfeng.testcomponent.databinding.ActivityTestAidlBinding;


//AIDL测试界面
public class TestAIDLActivity extends BaseActivity<BasePresenter, ActivityTestAidlBinding> {


//    private IDemandManager demandManager;//交互接口


    @Override
    public int getLayoutId() {
        return R.layout.activity_test_aidl;
    }

    @Override
    public void initPresenter() {
        mBinding.setListener(this);
    }

    @Override
    public void initData(Bundle bundle) {
        mBinding.tvTittle.setText("AIDL客户端");
//        AidlUtil.startRemoteService(this, connection);
    }

    @Override
    public int getStatusBarColorResId() {
        return 0;
    }

    public void onClick(View view) {
        int i = view.getId();//发送消息到服务端
        if (i == R.id.iv_left) {
            finish();
        } else if (i == R.id.tv_send) {
//            AidlUtil.sendToServer(mBinding.etInput, this, demandManager);
        } else if (i == R.id.tv_get_msg) {
            getRemoteMsg();
        }
    }

    //获取远程信息
    private void getRemoteMsg() {
//        try {
//            if (demandManager != null) {
//                MessageBean messageBean = demandManager.getDemand();
//                if (messageBean != null) {
//                    mBinding.tvResult.setText("服务端数据:  " + messageBean.getContent());
//                }
//            }
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
    }

    //service的链接
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
//            demandManager = IDemandManager.Stub.asInterface(service);//连接后拿到 Binder，转换成 AIDL，可以进行进程间的方法调用和传输啦。
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
