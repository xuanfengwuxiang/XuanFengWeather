package com.xuanfeng.xuanfengweather;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Process;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xuanfeng.mylibrary.rxbus.RxBean;
import com.xuanfeng.mylibrary.rxbus.RxBus;
import com.xuanfeng.mylibrary.utils.ImageUtil;
import com.xuanfeng.mylibrary.utils.StatusBarUtil;
import com.xuanfeng.mylibrary.widget.NoScrollViewPager;
import com.xuanfeng.xuanfengweather.base.BaseActivity;
import com.xuanfeng.xuanfengweather.constant.RxConstant;
import com.xuanfeng.xuanfengweather.module.gallery.fragment.EntertainmentFragment;
import com.xuanfeng.xuanfengweather.module.news.NewsFragment;
import com.xuanfeng.xuanfengweather.module.weather.activity.SelectCityActivity;
import com.xuanfeng.xuanfengweather.module.weather.fragment.WeatherFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

public class MainActivity extends BaseActivity {


    @BindView(R.id.iv_left_1)
    ImageView mIvLeft1;
    @BindView(R.id.iv_left_2)
    ImageView mIvLeft2;
    @BindView(R.id.tv_left_3)
   public TextView mTvLeft3;
    @BindView(R.id.ly_left)
    LinearLayout mLyLeft;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_right_1)
    TextView mTvRight1;
    @BindView(R.id.iv_right_2)
    ImageView mIvRight2;
    @BindView(R.id.ly_right)
    LinearLayout mLyRight;
    @BindView(R.id.rl_header)
    RelativeLayout mRlHeader;
    @BindView(R.id.vp_main)
    NoScrollViewPager mVpMain;
    @BindView(R.id.rb_weather)
    RadioButton mRbWeather;
    @BindView(R.id.rb_shijing)
    RadioButton mRbShijing;
    @BindView(R.id.rb_me)
    RadioButton mRbMe;
    @BindView(R.id.rg_parent)
    RadioGroup mRgParent;
    @BindView(R.id.activity_main)
    LinearLayout mActivityMain;

    private List<Fragment> mFragmentList;
    private EntertainmentFragment mEntertainmentFragment;
    private long mExitTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViews();
        setListeners();
        initData();
    }

    @Override
    protected void initViews() {

        mIvLeft1.setVisibility(View.GONE);
        mVpMain.setCurrentItem(0);
        mActivityMain.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_sunny_day));
        mRbWeather.setChecked(true);
        mTvLeft3.setVisibility(View.VISIBLE);
        mTvLeft3.setTextColor(Color.WHITE);
        mTvLeft3.setText("南京市");
        Drawable img = getResources().getDrawable(R.drawable.ic_add);
        img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
        mTvLeft3.setCompoundDrawables(img, null, null, null);

    }

    @Override
    protected void setListeners() {
        mVpMain.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setLayoutWhenOnPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //当viewpager切换的时候修改布局
    private void setLayoutWhenOnPageSelected(int position) {
        switch (position) {
            case 0:
                mTvLeft3.setVisibility(View.VISIBLE);
                mRlHeader.setBackgroundColor(getResources().getColor(R.color.color_transparent));
                mActivityMain.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_sunny_day));
                mRlHeader.setBackgroundColor(getResources().getColor(R.color.color_transparent));
                mRbWeather.setChecked(true);
                mTvTitle.setText("");
                mIvLeft1.setVisibility(View.GONE);
                break;
            case 1:
                mTvLeft3.setVisibility(View.GONE);
                mActivityMain.setBackgroundColor(getResources().getColor(R.color.baseThemeColor));
                mRlHeader.setBackgroundColor(getResources().getColor(R.color.baseThemeColor));
                mRbShijing.setChecked(true);
                mTvTitle.setText("多媒体");
                mIvLeft1.setVisibility(View.GONE);
                break;
            case 2:
                mTvLeft3.setVisibility(View.GONE);
                mActivityMain.setBackgroundColor(getResources().getColor(R.color.baseThemeColor));
                mRlHeader.setBackgroundColor(getResources().getColor(R.color.baseThemeColor));
                mRbMe.setChecked(true);
                mTvTitle.setText("新闻");
                mIvLeft1.setVisibility(View.VISIBLE);
                mIvLeft1.setImageBitmap(ImageUtil.getColorBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_back), 128f, 128f, 128f, 128f));
                break;
        }
    }

    @Override
    protected void initData() {
        setViewPagerAdapter();
        registerRxBus();
    }

    private void registerRxBus() {
        registRxBus(new Action1<RxBean>() {
            @Override
            public void call(RxBean rxBean) {
                if (rxBean == null) {
                    return;
                }
                switch (rxBean.getKey()) {
                    case RxConstant.EXIT_APP:
                        exitApp();
                        break;
                }
            }
        });
    }

    //设置viewpager的适配器
    private void setViewPagerAdapter() {
        mFragmentList = new ArrayList<>();
        mEntertainmentFragment = new EntertainmentFragment();
        mFragmentList.add(new WeatherFragment());
        mFragmentList.add(mEntertainmentFragment);
        mFragmentList.add(new NewsFragment());
        FragmentPagerAdapter fragmentPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mFragmentList);
        mVpMain.setAdapter(fragmentPagerAdapter);
        mVpMain.setOffscreenPageLimit(2);
    }


    @OnClick({R.id.rb_weather, R.id.rb_shijing, R.id.rb_me, R.id.tv_left_3, R.id.iv_left_1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_weather:
                mVpMain.setCurrentItem(0, false);
                break;
            case R.id.rb_shijing:
                mVpMain.setCurrentItem(1, false);
                break;
            case R.id.rb_me:
                mVpMain.setCurrentItem(2, false);
                break;
            case R.id.tv_left_3:
                Intent intent = new Intent(this, SelectCityActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_left_1:
                doLeftClick();
                break;
        }
    }

    //点击左边的返回按钮
    private void doLeftClick() {
        int currentIndex = mVpMain.getCurrentItem();
        if (2 == currentIndex) {
            RxBus.getInstance().send(new RxBean(RxConstant.NEWS_GO_BACK));
        }
    }

    @Override
    public void onBackPressed() {
        exitApp();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setLayoutWhenOnPageSelected(mVpMain.getCurrentItem());
        StatusBarUtil.setStatusBarColor(this, R.color.baseThemeColor, false);
        StatusBarUtil.setStatusBarTransparent(this);
    }

    private void exitApp() {//退出app
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
            try {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                Process.killProcess(Process.myPid());//从操作系统中结束掉当前程序的进程
                System.exit(0);//退出JVM
            } catch (Exception e) {
                Process.killProcess(Process.myPid());
                e.printStackTrace();
            }
        }
    }

}
