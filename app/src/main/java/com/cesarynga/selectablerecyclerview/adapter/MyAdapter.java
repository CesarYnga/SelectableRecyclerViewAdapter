package com.cesarynga.selectablerecyclerview.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MyAdapter extends SelectableAdapter<MyAdapter.ViewHolder> {

    private final List<String> list;
    private MyItemClickListener myItemClickListener;

    public MyAdapter(RecyclerView recyclerView, List<String> list) {
        super(recyclerView);
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_activated_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnItemClickListener(MyItemClickListener listener) {
        this.myItemClickListener = listener;
    }

    public class ViewHolder extends SelectableAdapter.ViewHolder {

        private TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(android.R.id.text1);
            setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick() {
                    if (myItemClickListener != null) {
                        myItemClickListener.onItemClick(list.get(getAdapterPosition()));
                    }
                }
            });
        }

        public void bind(String text) {
            textView.setText(text);
        }
    }

    public interface MyItemClickListener {
        void onItemClick(String string);
    }
}
