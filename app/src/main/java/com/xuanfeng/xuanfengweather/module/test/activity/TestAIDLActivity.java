package com.xuanfeng.xuanfengweather.module.test.activity;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.xuanfeng.mylibrary.mvp.BaseActivity;
import com.xuanfeng.server.IDemandManager;
import com.xuanfeng.server.MessageBean;
import com.xuanfeng.xuanfengweather.R;
import com.xuanfeng.xuanfengweather.module.test.utils.AidlUtil;

import butterknife.BindView;
import butterknife.OnClick;

//AIDL测试界面
public class TestAIDLActivity extends BaseActivity {


    @BindView(R.id.tv_result)
    TextView mTvResult;

    @BindView(R.id.tv_tittle)
    TextView mTvTittle;

    @BindView(R.id.et_input)
    EditText mEtInput;

    private IDemandManager demandManager;//交互接口


    @Override
    public int getLayoutId() {
        return R.layout.activity_test_aidl;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initData(Bundle bundle) {
        mTvTittle.setText("AIDL客户端");
        AidlUtil.startRemoteService(this, connection);
    }

    @Override
    public int getStatusBarColorResId() {
        return 0;
    }


    @OnClick({R.id.iv_left, R.id.tv_get_msg, R.id.tv_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.tv_send://发送消息到服务端
                AidlUtil.sendToServer(mEtInput, this, demandManager);
                break;
            case R.id.tv_get_msg:
                getRemoteMsg();
                break;
        }
    }

    //获取远程信息
    private void getRemoteMsg() {
        try {
            if (demandManager != null) {
                MessageBean messageBean = demandManager.getDemand();
                if (messageBean != null) {
                    mTvResult.setText("服务端数据:  " + messageBean.getContent());
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    //service的链接
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            demandManager = IDemandManager.Stub.asInterface(service);//连接后拿到 Binder，转换成 AIDL，可以进行进程间的方法调用和传输啦。
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }
}
