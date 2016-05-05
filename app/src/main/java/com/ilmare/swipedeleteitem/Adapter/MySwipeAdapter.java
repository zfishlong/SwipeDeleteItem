package com.ilmare.swipedeleteitem.Adapter;

import android.app.job.JobInfo;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ilmare.swipedeleteitem.R;
import com.ilmare.swipedeleteitem.View.MySwipeLayout;

import  static com.ilmare.swipedeleteitem.Bean.Cheeses.NAMES;
/**
 * Created by zhangchenggeng
 * Time 2016/5/5 9:50.
 * Descripton:
 * History:
 * 版权所有
 */
public class MySwipeAdapter extends BaseAdapter {


    private Context context;

    public MySwipeAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return NAMES.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder=null;
        if (convertView==null){
            convertView=View.inflate(context, R.layout.layout,null);
            holder=new Holder((MySwipeLayout)convertView);
            convertView.setTag(holder);
        }else{
            holder= (Holder) convertView.getTag();
        }

        holder.getTvName().setText(NAMES[position]);

        return convertView;
    }
}
