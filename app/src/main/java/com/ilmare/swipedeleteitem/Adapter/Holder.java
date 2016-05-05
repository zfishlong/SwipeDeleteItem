package com.ilmare.swipedeleteitem.Adapter;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.ilmare.swipedeleteitem.R;
import com.ilmare.swipedeleteitem.View.MySwipeLayout;

public class Holder {
    private TextView tvCall;
    private TextView tvDel;
    private ImageView ivIcon;
    private TextView tvName;
    private MySwipeLayout rootView;

    public Holder(MySwipeLayout view) {
        tvCall = (TextView) view.findViewById(R.id.tv_call);
        tvDel = (TextView) view.findViewById(R.id.tv_del);
        ivIcon = (ImageView) view.findViewById(R.id.iv_icon);
        tvName = (TextView) view.findViewById(R.id.tv_name);
        this.rootView=view;
        view.setOnSwipeLayoutListener(new MySwipeLayout.OnSwipeLayoutListener() {
            @Override
            public void onClose(MySwipeLayout mSwipeLayout) {
                Log.d("Holder", "onClose");
            }

            @Override
            public void onOpen(MySwipeLayout mSwipeLayout) {
                Log.d("Holder", "onOpen");
            }

            @Override
            public void onStartClose(MySwipeLayout mSwipeLayout) {
                Log.d("Holder", "onStartClose");
            }

            @Override
            public void onStartOpen(MySwipeLayout mSwipeLayout) {
                Log.d("Holder", "onStartOpen");
            }
        });
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
