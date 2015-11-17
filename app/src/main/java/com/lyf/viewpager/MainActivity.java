package com.lyf.viewpager;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;


public class MainActivity extends Activity {
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager= (ViewPager) findViewById(R.id.viewpager);
    }

}
