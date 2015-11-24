package com.lyf.viewpager;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener, MyViewPagerAdapter.ViewPagerLeftScrollListener, MyViewPagerAdapter.ViewPagerRightScrollListener {
    private CustomViewPager mViewPager;
    private MyViewPagerAdapter viewPagerAdapter;
    private HorizontalScrollView hsv;
    private RadioGroup rg;
    private int pageCount = 21;//每页加载GridView中的item数量
    private String type = "Type0";//顶部横向到导航类型
    private List<String> radioGroupData;//存储顶部类型list
    private int ScreenWidth;//屏幕宽度

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (CustomViewPager) findViewById(R.id.viewpager);
        hsv = (HorizontalScrollView) findViewById(R.id.hsv);
        rg = (RadioGroup) findViewById(R.id.rg);
        initRadioGroup();
    }

    private void initRadioGroup() {
        //获取屏幕宽度
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        ScreenWidth = displayMetrics.widthPixels;
        radioGroupData = new ArrayList<String>();
        //模拟数据动态添加RadioButton
        for (int i = 0; i < 10; i++) {
            radioGroupData.add("Type" + i);
            RadioButton rb = new RadioButton(this);
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(ScreenWidth / 5, ViewGroup.LayoutParams.MATCH_PARENT);
            rb.setPadding(10, 0, 10, 0);
            rb.setGravity(Gravity.CENTER);
            rb.setLayoutParams(params);
            rb.setText(radioGroupData.get(i));
            rb.setButtonDrawable(new ColorDrawable());
            rb.setBackgroundResource(R.drawable.rg_bg_selected);
            rg.addView(rb);
            //首次进入第一个类被选中
            if (i == 0) {
                rg.check(rb.getId());
            }
        }
        viewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), mViewPager, pageCount, this);
        //pagerCount * 2（viewPager要预加载一页）
        viewPagerAdapter.initViewPager(0, pageCount * 2, type);
        //设置滑动时不能再往左滑的监听
        viewPagerAdapter.setmViewPagerLeftScrollListener(this);
        //设置滑动时不能再往右滑的监听
        viewPagerAdapter.setmViewPagerRightScrollListener(this);
        rg.setOnCheckedChangeListener(this);
    }

    public void setSelectRadioGroup(String typeName, boolean isLeftScroll) {
        RadioButton rb;
        if (isLeftScroll) {
            rb = (RadioButton) rg.getChildAt((radioGroupData.indexOf(typeName) + 1) >= radioGroupData.size() ? (radioGroupData.size() - 1) : (radioGroupData.indexOf(typeName) + 1));
        } else {
            rb = (RadioButton) rg.getChildAt((radioGroupData.indexOf(typeName) - 1) <= 0 ? 0 : (radioGroupData.indexOf(typeName) - 1));
        }
        if (rb != null) {
            rg.check(rb.getId());
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        //获取RadioGroup中选中的RadioButton
        RadioButton rb = (RadioButton) group.findViewById(checkedId);
        CharSequence typeName = rb.getText();
        if (!TextUtils.isEmpty(typeName)) {
            type = rb.getText().toString();
            //根据顶部导航的选择初始化viewpager
            viewPagerAdapter.initViewPager(0, pageCount * 2, type);
            //顶部导航栏滑动
            hsv.scrollTo(radioGroupData.indexOf(type) * (ScreenWidth / 5), 0);
        }
    }

    @Override
    public void leftScroll(String typeName) {
        setSelectRadioGroup(typeName, true);
    }

    @Override
    public void rightScroll(String typeName) {
        setSelectRadioGroup(typeName, false);
    }
}
