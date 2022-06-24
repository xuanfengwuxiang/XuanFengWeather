package com.xuanfeng.testcomponent.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.MediaStore;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.JsonObject;
import com.xuanfeng.server.IMyAidlInterface;
import com.xuanfeng.server.Person;
import com.xuanfeng.testcomponent.R;
import com.xuanfeng.testcomponent.databinding.ActivityTestBinding;
import com.xuanfeng.testcomponent.hotfix.ISay;
import com.xuanfeng.testcomponent.hotfix.SayException;
import com.xuanfeng.testcomponent.service.ForegroundService;
import com.xuanfeng.xflibrary.component.ComponentUtil;
import com.xuanfeng.xflibrary.http.HttpResponse;
import com.xuanfeng.xflibrary.http.httpmgr.HttpManager;
import com.xuanfeng.xflibrary.mvp.BaseActivity;
import com.xuanfeng.xflibrary.mvp.BasePresenter;
import com.xuanfeng.xflibrary.utils.ImageUtil;
import com.xuanfeng.xflibrary.utils.SoftKeyBoardUtil;
import com.xuanfeng.xflibrary.widget.popupmenu.PopupMenu;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import dalvik.system.DexClassLoader;


public class TestActivity extends BaseActivity<BasePresenter, ActivityTestBinding> implements View.OnClickListener {


    private PopupMenu mPopupMenu;
    private SoftKeyBoardUtil mSoftKeyBoardUtil;

    int GALLERY_CODE = 666;
    int CROP_CODE = 777;
    int TAKE_CODE = 888;


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
            try {
                iTest.addLog(new Person("够烦"));
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        } else if (i == R.id.ll_test_share_anim) {
            Intent intent;
            intent = new Intent(this, TestShareAnimActivity.class);
            Pair pair = Pair.create(mBinding.tvShareAnim, "fab");
            Pair pair1 = Pair.create(mBinding.ivShareAnim, "pic");
            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(TestActivity.this, pair, pair1);
            startActivity(intent, activityOptionsCompat.toBundle());
        } else if (i == R.id.tv_contact) {
            ComponentUtil.toRouterPage(TestActivity.this, "667");
        } else if (i == R.id.iv_left) {
            finishAfterTransition();
        } else if (i == R.id.tv_hot_fix) {
            hotFix();
        } else if (i == R.id.tv_foreground_service) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(new Intent(this, ForegroundService.class));
            } else {
                startService(new Intent(this, ForegroundService.class));
            }
        }else if(i == R.id.tv_upload){

            ImageUtil.selectFromGallery(this,22);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri selectedImage = data.getData(); //获取系统返回的照片的Uri
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);//从系统表中查询指定Uri对应的照片
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String path = cursor.getString(columnIndex); //获取照片路径
        Log.i("!@#","path ="+path);
        String uu = "http://139.196.88.41:9025/functionMenu/image/smallUploadImage";

        HttpManager.getInstance().uploadImage(uu, path, new HttpResponse<JsonObject>() {
            @Override
            public void onSuccess(JsonObject jsonObject) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void hotFix() {

        ISay say;


        // 获取hotfix的jar包文件
        final File jarFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "outfix.dex");

        if (!jarFile.exists()) {
            say = new SayException();
            Toast.makeText(this, say.saySomething(), Toast.LENGTH_SHORT).show();
        } else {
            // 只要有读写权限的路径均可
            DexClassLoader dexClassLoader = new DexClassLoader(jarFile.getAbsolutePath(),
                    getExternalCacheDir().getAbsolutePath(), null, getClassLoader());
            try {
                // 加载 SayHotFix 类
                Class clazz = dexClassLoader.loadClass("com.xuanfeng.testcomponent.hotfix.SayHotFix");
                // 强转成 ISay, 注意 ISay 的包名需要和hotfix jar包中的一致
                ISay iSay = (ISay) clazz.newInstance();
                Toast.makeText(this, iSay.saySomething(), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
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
        mBinding.setListener(this);
        mSoftKeyBoardUtil = SoftKeyBoardUtil.setListener(this, mKeyBoardListener);
        Glide.with(this).load("https://111.229.132.124/image/20200615/35274f02bf37454b86cac85059eaa73f.jpg").

                apply(RequestOptions.bitmapTransform(new CircleCrop())).into(mBinding.ivShareAnim);

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
        return R.color.baseThemeColor;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setEnterTransition(new Explode());
        super.onCreate(savedInstanceState);

        //软件一启动就绑定服务
        bindService();
    }

    private void bindService() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.xuanfeng.server", "com.xuanfeng.server.MyService"));//第一个参数包名，第二个参数文件名
        bindService(intent, connection, BIND_AUTO_CREATE);
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

    IMyAidlInterface iTest;

    private ServiceConnection connection = new ServiceConnection() {
        //绑定上服务的时候
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //拿到远程服务
            iTest = IMyAidlInterface.Stub.asInterface(service);
        }

        //断开服务的时候
        @Override
        public void onServiceDisconnected(ComponentName name) {
            //回收资源
            iTest = null;
        }
    };

}
