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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        hsv = (HorizontalScrollView) findViewById(R.id.hsv);
        rg = (RadioGroup) findViewById(R.id.rg);
        radioGroupData = new ArrayList<String>();
        viewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        initRadioGroup();
    }

    private void initRadioGroup() {
        initRadioGroupData();
        for (int i = 0; i < radioGroupData.size(); i++) {
            RadioButton rb = new RadioButton(this);
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            rb.setPadding(10,0,10,0);
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
        initViewPager();
    }

    private void initViewPager() {
        viewPager.removeAllViews();
        viewPagerAdapter.setFragments(initViewPagerData());
        viewPager.setAdapter(viewPagerAdapter);
    }

    private List<Fragment> initViewPagerData() {
        List<Fragment> fragments = new ArrayList<Fragment>();
        BlankFragment fragment = BlankFragment.newInstance(0, 24);
        BlankFragment fragment1 = BlankFragment.newInstance(24, 24);
        fragments.add(fragment);
        fragments.add(fragment1);
        return fragments;
    }

    private void initRadioGroupData() {
        for (int i = 0; i < 10; i++) {
            radioGroupData.add("小样" + i);
        }
    }

}
