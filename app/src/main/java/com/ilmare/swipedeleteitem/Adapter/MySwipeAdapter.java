package com.ilmare.swipedeleteitem.Adapter;

import android.app.job.JobInfo;
import android.content.Context;
import android.graphics.PointF;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.ilmare.swipedeleteitem.R;
import com.ilmare.swipedeleteitem.View.GooViewListener;
import com.ilmare.swipedeleteitem.View.MySwipeLayout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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
    private List<MySwipeLayout> list=new ArrayList<>();
    private HashSet<Integer> mRemoved = new HashSet<Integer>();

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
    public View getView(final int position, View convertView, ViewGroup parent) {
         Holder holder=null;
        if (convertView==null){
            convertView=View.inflate(context, R.layout.layout,null);
            holder=new Holder((MySwipeLayout)convertView);
            convertView.setTag(holder);
        }else{
            holder= (Holder) convertView.getTag();
        }

        holder.getTvName().setText(NAMES[position]);
        holder.getRootView().setOnSwipeLayoutListener(new MySwipeLayout.OnSwipeLayoutListener() {
            @Override
            public void onClose(MySwipeLayout mSwipeLayout) {
                Log.d("Holder", "onClose");
                list.remove(mSwipeLayout);
            }

            @Override
            public void onOpen(MySwipeLayout mSwipeLayout) {
                Log.d("Holder", "onOpen");
                list.add(mSwipeLayout);
            }

            @Override
            public void onStartClose(MySwipeLayout mSwipeLayout) {
                Log.d("Holder", "onStartClose");
            }

            @Override
            public void onStartOpen(MySwipeLayout mSwipeLayout) {
                Log.d("Holder", "onStartOpen");
                for (MySwipeLayout swipeLayout : list) {
                    swipeLayout.close();
                }
            }
        });

        boolean visiable = !mRemoved.contains(position);
        holder.getPoint().setVisibility(visiable ? View.VISIBLE : View.GONE);

        if (visiable) {
            holder.getPoint().setText(String.valueOf(position));
            holder.getPoint().setTag(position);

            final GooViewListener mGooListener = new GooViewListener(context, holder.getPoint()) {
                @Override
                public void onDisappear(PointF mDragCenter) {
                    super.onDisappear(mDragCenter);

                    mRemoved.add(position);
                    notifyDataSetChanged();

                    Toast.makeText(context, "Cheers! We have get rid of it!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onReset(boolean isOutOfRange) {
                    super.onReset(isOutOfRange);

                    notifyDataSetChanged();

                    Toast.makeText(context, "Are you regret?:Try again!", Toast.LENGTH_SHORT).show();
                }

            };

            holder.getPoint().setOnTouchListener(mGooListener);

        }

        return convertView;
    }
}
