package com.lyf.viewpager;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hc on 2015/11/20.
 */
public class MyViewPagerAdapter extends FragmentStatePagerAdapter implements ViewPager.OnPageChangeListener {
    private static final String TAG="MyViewPagerAdapter";
    private Context mContext;
    private List<Fragment> fragments;
    private CustomViewPager mViewPager;
    private int pagerCount;//每页加载GridView中的item数量
    private String type;//顶部横向导航
    private GestureDetector gestureDetector;//手势
    private MyOnGestureListener mGestureListener;//手势监听
    private static final int FLING_MIN_DISTANCE = 50;//手势最小滑动距离
    private static final int FLING_MIN_VELOCITY = 100;//手势最小滑动速度
    private boolean isScrolled;//滑动状态标示
    private boolean left = false;//向左滑动
    private boolean right = false;//向右滑动

    public MyViewPagerAdapter(FragmentManager fm, CustomViewPager viewPager, int pagerCount, Activity activity) {
        super(fm);
        this.mContext=activity;
        this.mViewPager = viewPager;
        this.pagerCount = pagerCount;
        fragments = new ArrayList<Fragment>();
        mViewPager.addOnPageChangeListener(this);
        gestureDetector = new GestureDetector(activity, new MyOnGestureListener());
        mViewPager.setGestureDetector(gestureDetector);
    }

    public void initViewPager(int start, int length, String type) {
        this.type = type;
        mViewPager.removeAllViews();
        fragments.clear();
        mViewPager.setAdapter(this);
        initViewPagerData(start, length, type);
    }

    private void initViewPagerData(int start, int length, String type) {
        //模拟数据
        if (start < 8 * pagerCount) {
            ArrayList<String> food_infos = new ArrayList<String>();
            for (int i = start; i < start + length; i++) {
                food_infos.add(type + i);
            }
            if (food_infos.size() > pagerCount) {
                for (int i = 0; i < (food_infos.size() % pagerCount == 0 ? food_infos.size() / pagerCount : (food_infos.size() / pagerCount) + 1); i++) {
                    ArrayList<String> sub = new ArrayList<String>();
                    if (food_infos.size() % pagerCount == 0) {
                        sub.addAll(food_infos.subList(i * pagerCount, (i + 1) * pagerCount));
                    } else {
                        if (i < food_infos.size() / pagerCount) {
                            sub.addAll(food_infos.subList(i * pagerCount, (i + 1) * pagerCount));
                        } else {
                            sub.addAll(food_infos.subList(i * pagerCount, food_infos.size()));
                        }
                    }
                    BlankFragment fragment = BlankFragment.newInstance(sub);
                    if (fragment != null) {
                        fragments.add(fragment);
                    }
                }
            } else {
                BlankFragment f = BlankFragment.newInstance(food_infos);
                if (f != null) {
                    fragments.add(f);
                }
            }
            notifyDataSetChanged();
        }
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == getCount() - 1) {
            initViewPagerData((position + 1) * pagerCount, pagerCount, type);
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        switch (arg0) {
            case ViewPager.SCROLL_STATE_DRAGGING:
                isScrolled = true;
                break;
            case ViewPager.SCROLL_STATE_SETTLING:
                isScrolled = false;
                break;
            case ViewPager.SCROLL_STATE_IDLE:
                //首先判断ViewPager有多页
                if (getCount() != 1) {
                    //多于一页
                    //最后一页并且向左滑动
                    if (mViewPager.getCurrentItem() == getCount() - 1 && isScrolled && left) {
                        Toast.makeText(mContext, "最后一页并且向左滑动", Toast.LENGTH_SHORT).show();
                    }
                    //第一页并且向右滑动
                    if (mViewPager.getCurrentItem() == 0 && isScrolled&& right ) {
                        Toast.makeText(mContext, "第一页并且向右滑动", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //只有一页
                    //向左滑动
                    if (right) {

                    }
                    //向右滑动
                    if (left) {

                    }
                }
                isScrolled = false;
                break;
        }

    }

    private class MyOnGestureListener implements GestureDetector.OnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
                // 左滑动
                left = true;
                right = false;
                Log.i(TAG, "left");
            } else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
                // 右滑动
                left = false;
                right = true;
                Log.i(TAG, "right");
            }
            return false;
        }
    }
}
