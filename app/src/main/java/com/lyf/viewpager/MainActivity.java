package com.lyf.viewpager;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity {
    private ViewPager viewPager;
    private HorizontalScrollView hsv;
    private RadioGroup rg;
    private List<String> radioGroupData;
    private MyViewPagerAdapter viewPagerAdapter;
    private List<Fragment> fragments;
    private int pagerCount = 24;//每页加载GridView中的item数量


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        hsv = (HorizontalScrollView) findViewById(R.id.hsv);
        rg = (RadioGroup) findViewById(R.id.rg);

        radioGroupData = new ArrayList<String>();
        fragments = new ArrayList<Fragment>();


        viewPager.addOnPageChangeListener(new MyListener());
        viewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        initRadioGroup();
    }

    private void initRadioGroup() {
        initRadioGroupData();
        for (int i = 0; i < radioGroupData.size(); i++) {
            RadioButton rb = new RadioButton(this);
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            rb.setPadding(10, 0, 10, 0);
            rb.setGravity(Gravity.CENTER);
            rb.setLayoutParams(params);
            rb.setText(radioGroupData.get(i));
            rb.setButtonDrawable(new BitmapDrawable());
            rb.setBackgroundResource(R.drawable.rg_bg_selected);
            rg.addView(rb);
            if (i == 0) {
                rg.check(rb.getId());
            }
        }
        initViewPager(0, pagerCount * 2);//viewPager要预加载一页
    }

    private void initViewPager(int start, int length) {
        viewPager.removeAllViews();
        fragments.clear();
        viewPagerAdapter.setFragments(fragments);
        viewPager.setAdapter(viewPagerAdapter);
        initViewPagerData(start, length);
    }

    private void initViewPagerData(int start, int length) {
        if (start < 5*pagerCount) {
            ArrayList<String> food_infos = new ArrayList<String>();
            for (int i = start; i < start + length; i++) {
                food_infos.add("小明" + i);
            }

            if (food_infos.size() > pagerCount) {
                ArrayList<String> sub1 = new ArrayList<String>();
                ArrayList<String> sub = new ArrayList<String>();
                sub1.addAll(food_infos.subList(0, pagerCount));
                sub.addAll(food_infos.subList(pagerCount, food_infos.size()));
                BlankFragment fragment1 = BlankFragment.newInstance(sub1);
                BlankFragment fragment = BlankFragment.newInstance(sub);
                if (fragment1 != null) {
                    fragments.add(fragment1);
                }
                if (fragment != null) {
                    fragments.add(fragment);
                }
            } else {
                BlankFragment f = BlankFragment.newInstance(food_infos);
                if (f != null) {
                    fragments.add(f);
                }
            }
            viewPagerAdapter.notifyDataSetChanged();
        }
    }

    private void initRadioGroupData() {
        for (int i = 0; i < 10; i++) {
            radioGroupData.add("Type" + i);
        }
    }

    private class MyListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (position == viewPager.getAdapter().getCount() - 1) {
                initViewPagerData((position + 1) * pagerCount, pagerCount);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

}
