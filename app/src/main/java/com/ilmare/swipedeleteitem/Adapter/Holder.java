package com.ilmare.swipedeleteitem.Adapter;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.ilmare.swipedeleteitem.R;
import com.ilmare.swipedeleteitem.View.MySwipeLayout;

import java.util.ArrayList;
import java.util.List;

public class Holder {
    private TextView tvCall;
    private TextView tvDel;
    private ImageView ivIcon;
    private TextView tvName;
    private MySwipeLayout rootView;
    private TextView point;

    public Holder(MySwipeLayout view) {
        tvCall = (TextView) view.findViewById(R.id.tv_call);
        tvDel = (TextView) view.findViewById(R.id.tv_del);
        ivIcon = (ImageView) view.findViewById(R.id.iv_icon);
        tvName = (TextView) view.findViewById(R.id.tv_name);
        point = (TextView) view.findViewById(R.id.point);
        this.rootView=view;

    }

    public TextView getPoint() {
        return point;
    }

    public void setPoint(TextView point) {
        this.point = point;
    }

    public MySwipeLayout getRootView() {
        return rootView;
    }

    public void setRootView(MySwipeLayout rootView) {
        this.rootView = rootView;
    }

    public ImageView getIvIcon() {
        return ivIcon;
    }

    public TextView getTvName() {
        return tvName;
    }

    public TextView getTvDel() {
        return tvDel;
    }

    public TextView getTvCall() {
        return tvCall;
    }
}
