package com.lyf.viewpager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2015/11/18.
 */
public class MyAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> foodName;
    private LayoutInflater mInflater;
    public MyAdapter(Context context,List<String> foodName){
        this.mContext=context;
        this.foodName=foodName;
        mInflater=LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return foodName==null?0:foodName.size();
    }

    @Override
    public Object getItem(int position) {
        return foodName.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder=null;
        if(convertView==null){
            mHolder=new ViewHolder();
            convertView=mInflater.inflate(R.layout.item_gv_foodlist, parent, false);
            mHolder.food_name= (TextView) convertView.findViewById(R.id.food_name);
            convertView.setTag(mHolder);
        }else{
            mHolder= (ViewHolder) convertView.getTag();
        }
        String food_name = (String) getItem(position);
        if(food_name!=null){
            mHolder.food_name.setText(food_name);
        }
        return convertView;
    }

    private static class ViewHolder{
        TextView food_name;
    }
}
