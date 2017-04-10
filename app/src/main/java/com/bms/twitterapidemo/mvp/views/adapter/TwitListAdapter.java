package com.bms.twitterapidemo.mvp.views.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.bms.twitterapidemo.R;
import com.bms.twitterapidemo.mvp.model.Status;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by roshan on 06/04/17.
 */

public class TwitListAdapter extends RecyclerView.Adapter<TwitListAdapter.TwitListHolder> {

    private final ArrayList<Status> list;
    private final Context mContext;
    private int lastPosition;


    public TwitListAdapter(ArrayList<Status> list, Context context) {
        this.list = list;
        this.mContext = context;
    }

    @Override
    public TwitListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.twit_list_item, null);

        TwitListHolder holder = new TwitListHolder(view);


        return holder;
    }


    @Override
    public void onBindViewHolder(TwitListHolder holder, int position) {

        holder.setData(list.get(position));
        setAnimation(holder.getView(), position);
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.fade_in);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    class TwitListHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_username_twitter_item)
        TextView tvName;
        @BindView(R.id.tv_twit_twitter_item)
        TextView tvTwit;
        private View view;

        public TwitListHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            ButterKnife.bind(this, itemView);
        }

        public void setData(Status status) {
            tvName.setText(status.getUser().getName());
            tvTwit.setText(status.getText());
        }

        public View getView() {
            return this.view;
        }
    }


}
