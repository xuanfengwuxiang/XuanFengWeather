package com.xuanfeng.testcomponent.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.transition.Explode
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.xuanfeng.testcomponent.R
import com.xuanfeng.testcomponent.databinding.ActivityTestBinding
import com.xuanfeng.testcomponent.service.ForegroundService
import com.xuanfeng.xflibrary.component.ComponentUtil
import com.xuanfeng.xflibrary.http.httpmgr.HttpManager.Companion.instance
import com.xuanfeng.xflibrary.mvp.BaseActivity
import com.xuanfeng.xflibrary.mvp.BasePresenter
import com.xuanfeng.xflibrary.utils.ImageUtil

class TestActivity : BaseActivity<BasePresenter<*>?, ActivityTestBinding?>(), View.OnClickListener {

    override fun onClick(view: View) {
        val i = view.id //测试自定义EditText
        if (i == R.id.tv_test_for_edittext) {
            val intent = Intent(this, TestForEditTextActivity::class.java)
            startActivity(intent)
        } else if (i == R.id.tv_test_for_popupmenu) {

        } else if (i == R.id.tv_test_for_touch_dispatch) {
            val intent: Intent
            intent = Intent(this, TestTouchEventActivity::class.java)
            startActivity(intent)
        } else if (i == R.id.tv_test_for_flag) {

        } else if (i == R.id.tv_test_for_download_https_image) { //下载https图片
            val url = "http://imgservice.suning.cn/uimg1/snsawp/snsawp_common/d59b9eb8-c2de-4885-a826-ce0679d2bb3c.png"
            Thread {
                val path = Environment.getExternalStorageDirectory().absolutePath
                val bitmap = ImageUtil.getBitmap(url)
                ImageUtil.saveBitmapToSD(bitmap, path, "ceshi.jpg")
            }.start()
        } else if (i == R.id.tv_test_for_gallery) {
            val intent: Intent
            intent = Intent(this, TestForGalleryActivity::class.java)
            startActivity(intent)
        } else if (i == R.id.tv_aidl) {

        } else if (i == R.id.ll_test_share_anim) {

        } else if (i == R.id.tv_contact) {
            ComponentUtil.toRouterPage(this@TestActivity, "667")
        } else if (i == R.id.iv_left) {
            finishAfterTransition()
        } else if (i == R.id.tv_hot_fix) {

        } else if (i == R.id.tv_foreground_service) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(Intent(this, ForegroundService::class.java))
            } else {
                startService(Intent(this, ForegroundService::class.java))
            }
        } else if (i == R.id.tv_upload) {
            ImageUtil.selectFromGallery(this, 22)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val selectedImage = data!!.data //获取系统返回的照片的Uri
        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(selectedImage,
                filePathColumn, null, null, null) //从系统表中查询指定Uri对应的照片
        cursor.moveToFirst()
        val columnIndex = cursor.getColumnIndex(filePathColumn[0])
        val path = cursor.getString(columnIndex) //获取照片路径
        Log.i("!@#", "path =$path")
        val uu = "http://139.196.88.41:9025/functionMenu/image/smallUploadImage"
        instance.uploadImage(uu, path) {
            onSuccess {

            }
            onError {

            }
        }

    }


    override fun onDestroy() {
        super.onDestroy()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_test
    }

    override fun initPresenter(): BasePresenter<*>? {
        mBinding!!.listener = this
        return null
    }

    override fun initData(bundle: Bundle?) {
        mBinding!!.tvTittle.text = "测试界面"
        mBinding!!.listener = this
        Glide.with(this).load("https://111.229.132.124/image/20200615/35274f02bf37454b86cac85059eaa73f.jpg").apply(RequestOptions.bitmapTransform(CircleCrop())).into(mBinding!!.ivShareAnim)
    }


    override fun getStatusBarColorResId(): Int {
        return R.color.baseThemeColor
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window?.enterTransition = Explode()
        super.onCreate(savedInstanceState)
    }


}