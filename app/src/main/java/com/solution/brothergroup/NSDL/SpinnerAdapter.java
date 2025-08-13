package com.solution.brothergroup.NSDL;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.solution.brothergroup.R;

import java.util.ArrayList;

public class SpinnerAdapter extends RecyclerView.Adapter<SpinnerAdapter.MyViewHolder> {
       private static ClickListner mClickListener;
        Context context;
        int selectPosition = -1;
        private ArrayList<String> mList;

        public SpinnerAdapter(Context context, ArrayList<String> mList, int selectPosition, ClickListner mClickListener) {
            this.mList = mList;
            this.context = context;
            this.mClickListener = mClickListener;
            this.selectPosition = selectPosition;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.spinner_model, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            final String item = mList.get(position);
            holder.text.setText(item + "");
            if (selectPosition == -1 || position != selectPosition) {
                holder.text.setTextColor(Color.BLACK);

            } else {
                holder.text.setTextColor(context.getResources().getColor(R.color.colorPrimary));

            }
            holder.itemView.setOnClickListener(v -> {
                if (mClickListener != null) {
                    selectPosition = position;
                    notifyDataSetChanged();
                    mClickListener.onItemClick(position, item + "", item + "");
                }
            });
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        public interface ClickListner {
            void onItemClick(int clickPosition, String value, String object);
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView text, amount;
            View itemView;


            public MyViewHolder(View view) {
                super(view);
                itemView = view;
                text = view.findViewById(R.id.text);
                amount = view.findViewById(R.id.amount);
                amount.setVisibility(View.GONE);

            }


        }
    }


