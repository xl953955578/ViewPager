package com.lyf.viewpager;

import android.graphics.drawable.BitmapDrawable;
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


public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener {
    private CustomViewPager mViewPager;
    private MyViewPagerAdapter viewPagerAdapter;
    private HorizontalScrollView hsv;
    private RadioGroup rg;
    private int pageCount = 21;//每页加载GridView中的item数量
    private String type = "Type0";//顶部横向到导航类型

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
        int ScreenWidth = displayMetrics.widthPixels;
        //存储顶部类型list
        List<String> radioGroupData = new ArrayList<String>();
        //模拟数据动态添加RadioButton
        for (int i = 0; i < 10; i++) {
            radioGroupData.add("Type" + i);
            RadioButton rb = new RadioButton(this);
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(ScreenWidth/5, ViewGroup.LayoutParams.MATCH_PARENT);
            rb.setPadding(10, 0, 10, 0);
            rb.setGravity(Gravity.CENTER);
            rb.setLayoutParams(params);
            rb.setText(radioGroupData.get(i));
            rb.setButtonDrawable(new BitmapDrawable());
            rb.setBackgroundResource(R.drawable.rg_bg_selected);
            rg.addView(rb);
            //首次进入第一个类被选中
            if (i == 0) {
                rg.check(rb.getId());
            }
        }
        viewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), mViewPager, pageCount, this);
        viewPagerAdapter.initViewPager(0, pageCount * 2, type);//pagerCount * 2（viewPager要预加载一页）
        rg.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        //获取RadioGroup中选中的RadioButton
        RadioButton rb = (RadioButton) group.findViewById(checkedId);
        CharSequence typeName = rb.getText();
        if (!TextUtils.isEmpty(typeName)) {
            type = rb.getText().toString();
            viewPagerAdapter.initViewPager(0, pageCount * 2, type);
        }
    }
}
