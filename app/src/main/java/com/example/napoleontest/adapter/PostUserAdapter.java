package com.example.napoleontest.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.napoleontest.R;
import com.example.napoleontest.domain.model.PostUser;

import java.util.List;

public class PostUserAdapter extends RecyclerView.Adapter<PostUserAdapter.ViewHolder> {
    private Context mContext;
    private List mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public PostUserAdapter(Context mContext, List mData, ItemClickListener mClickListener) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
        this.mData = mData;
        this.mClickListener = mClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_rv_postuser, parent, false);
        return new ViewHolder(view,mClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String postMessage = ((PostUser) mData.get(position)).getTitle();
        holder.txv_postMessage.setText(postMessage);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
       // Toast.makeText(mContext,"PUFFF",Toast.LENGTH_LONG).show();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txv_postMessage;

        ItemClickListener itemClickListener;

        ViewHolder(View itemView, ItemClickListener itemClickListener) {
            super(itemView);
            txv_postMessage = itemView.findViewById(R.id.txv_postmessage);
            this.itemClickListener = itemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
            itemClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    String getItem(int id) {
        return (String) mData.get(id);
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
