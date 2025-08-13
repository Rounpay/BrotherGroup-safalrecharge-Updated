package com.solution.brothergroup.CommissionSlab.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.solution.brothergroup.Api.Object.SlabDetailDisplayLvl;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.ApplicationConstant;
import com.solution.brothergroup.Util.RecyclerViewStickyHeader.HeaderStickyListener;
import com.solution.brothergroup.Util.UtilMethods;

import java.util.ArrayList;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Vishnu on 26-04-2017.
 */

public class CommissionAdapter extends ListAdapter<SlabDetailDisplayLvl, RecyclerView.ViewHolder> implements HeaderStickyListener {
    public static final Integer ListData = 1;
    public static final Integer Header = 2;
    public static final DiffUtil.ItemCallback<SlabDetailDisplayLvl> ModelDiffUtilCallback =
            new DiffUtil.ItemCallback<SlabDetailDisplayLvl>() {
                @Override
                public boolean areItemsTheSame(@NonNull SlabDetailDisplayLvl model, @NonNull SlabDetailDisplayLvl t1) {
                    return model.getHeader().equals(t1.getHeader());
                }

                @Override
                public boolean areContentsTheSame(@NonNull SlabDetailDisplayLvl model, @NonNull SlabDetailDisplayLvl t1) {
                    return model.equals(t1);
                }
            };
    String searchText = "";
    int resourceId = 0;
    String operatorType = "";
    private ArrayList<SlabDetailDisplayLvl> itemList = new ArrayList<>();
    private ArrayList<SlabDetailDisplayLvl> itemFilterList = new ArrayList<>();
    /*  public CommissionAdapter() {

      }*/
    RequestOptions requestOptions;
    private Context mContext;

    public CommissionAdapter(ArrayList<SlabDetailDisplayLvl> transactionsList, Context mContext) {
        super(ModelDiffUtilCallback);

        this.itemList = transactionsList;
        this.itemFilterList.addAll(itemList);
        this.mContext = mContext;
        requestOptions = new RequestOptions();
        requestOptions.placeholder(R.mipmap.ic_launcher);
        requestOptions.error(R.mipmap.ic_launcher);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Header) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sticky_header, parent, false);
            return new HeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_commision, parent, false);
            return new MyViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final SlabDetailDisplayLvl operator = itemFilterList.get(position);
        if (getItemViewType(position) == ListData) {
            if (operator.getCommSettingType() == 2) {
                ((MyViewHolder) holder).viewDetails.setVisibility(View.VISIBLE);
            } else {
                ((MyViewHolder) holder).viewDetails.setVisibility(View.GONE);
            }
            ((MyViewHolder) holder).operator.setText("" + operator.getOperator());
            ((MyViewHolder) holder).maxMin.setText("Range : " + UtilMethods.INSTANCE.formatedAmount(operator.getMin() + "") + " - " + UtilMethods.INSTANCE.formatedAmount(operator.getMax() + ""));
            ((MyViewHolder) holder).recycler_view.setAdapter(new com.solution.brothergroup.CommissionSlab.ui.CommissionRoleAdapter(operator.getRoleCommission(), mContext, operator.getRoleCommission().size(), 0));
            ((MyViewHolder) holder).recycler_view_real.setAdapter(new com.solution.brothergroup.CommissionSlab.ui.CommissionRoleAdapter(operator.getRoleCommission(), mContext, operator.getRoleCommission().size(), 1));


            Glide.with(mContext)
                    .load(ApplicationConstant.INSTANCE.baseIconUrl + operator.getOid() + ".png")
                    .apply(requestOptions)
                    .into(((MyViewHolder) holder).icon);
            //holder.operator.setText(getname(operator.getOid()));
            String faqsearchDescription = operator.getOperator();
            if (faqsearchDescription.contains(searchText)) {
                int startPos = faqsearchDescription.indexOf(searchText);
                int endPos = startPos + searchText.length();
                Spannable spanText = Spannable.Factory.getInstance().newSpannable(operator.getOperator()); // <- EDITED: Use the original string, as `country` has been converted to lowercase.
                spanText.setSpan(new ForegroundColorSpan(Color.BLUE), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                spanText.setSpan(new StyleSpan(Typeface.ITALIC), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ((MyViewHolder) holder).operator.setText(spanText, TextView.BufferType.SPANNABLE);

            } else {
                ((MyViewHolder) holder).operator.setText(operator.getOperator());
            }


            ((MyViewHolder) holder).viewDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mContext instanceof com.solution.brothergroup.CommissionSlab.ui.CommissionScreen) {
                        ((com.solution.brothergroup.CommissionSlab.ui.CommissionScreen) mContext).HitApiSlabDetail(operator.getOid(),operator.getOperator(),
                                UtilMethods.INSTANCE.formatedAmount(operator.getMin() + ""),UtilMethods.INSTANCE.formatedAmount(operator.getMax()+""));
                    }
                }
            });


        } else {
            ((HeaderViewHolder) holder).title.setText(operator.getHeader());
        }
    }
   /* @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.commision_adapter, parent, false);

        return new MyViewHolder(itemView);
    }*/

    @Override
    public int getItemViewType(int position) {
        if (itemFilterList.get(position).getHeader() != null && !itemFilterList.get(position).getHeader().isEmpty()) {
            return Header;
        } else {
            return ListData;
        }

    }

    @Override
    public int getHeaderPositionForItem(int itemPosition) {
        Integer headerPosition = 0;
        for (Integer i = itemPosition; i > 0; i--) {
            if (isHeader(i)) {
                headerPosition = i;
                return headerPosition;
            }
        }
        return headerPosition;
    }
    @Override
    public String getHeaderString(int headerPosition) {
        return itemList.get(headerPosition).getHeader();
    }
    @Override
    public int getHeaderLayout(int headerPosition) {
        return R.layout.item_sticky_header;
    }

    @Override
    public void bindHeaderData(View header, int headerPosition) {
        TextView tv = header.findViewById(R.id.opType);
        tv.setText(itemFilterList.get(headerPosition).getHeader());
    }

    @Override
    public boolean isHeader(int itemPosition) {
        if (itemFilterList.get(itemPosition).getHeader() != null && !itemFilterList.get(itemPosition).getHeader().isEmpty()) {
            return true;
        }
        return false;
    }

    // Filter Class
    public void filter(String charText) {
        this.searchText = charText.toLowerCase(Locale.getDefault());
        itemFilterList.clear();
        if (charText.length() == 0) {
            itemFilterList.addAll(itemList);
        } else {
            /*for (SlabDetailDisplayLvl wp : rechargeStatus) {
                if (wp.getOperator().toLowerCase(Locale.getDefault()).contains(charText) || wp.getOpType().toLowerCase(Locale.getDefault()).contains(charText)) {
                    transactionsList.add(wp);
                }
            }*/

            String HeaderName = "";

            for (SlabDetailDisplayLvl wp : itemList) {
                if (wp.getOperator().toLowerCase(Locale.getDefault()).contains(searchText) || wp.getOpType().toLowerCase(Locale.getDefault()).contains(searchText)) {
                    if (!HeaderName.equalsIgnoreCase(wp.getOpType())) {
                        HeaderName = wp.getOpType();
                        itemFilterList.add(new SlabDetailDisplayLvl(wp.getOpType(), 0, "", "", 0,0, 0, 0, null));
                    }
                    itemFilterList.add(new SlabDetailDisplayLvl(null, wp.getOid(), wp.getOperator(), wp.getOpType(), wp.getOpTypeId(), wp.getMin(), wp.getMax(), wp.getCommSettingType(), wp.getRoleCommission()));
                }
            }

        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return itemFilterList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView operator;
        TextView maxMin;
        ImageView icon;
        View viewDetails;
        RecyclerView recycler_view, recycler_view_real;

        public MyViewHolder(View view) {
            super(view);
            viewDetails = view.findViewById(R.id.viewDetails);

            maxMin = view.findViewById(R.id.maxMin);
            operator = (AppCompatTextView) view.findViewById(R.id.operator);
            recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
            recycler_view_real = view.findViewById(R.id.recycler_view_real);
            if (itemList.size() > 1 && itemList.get(1).getRoleCommission() != null && itemList.get(1).getRoleCommission().size() > 0) {
                recycler_view.setLayoutManager(new GridLayoutManager(mContext, itemList.get(1).getRoleCommission().size()));
                recycler_view_real.setLayoutManager(new GridLayoutManager(mContext, itemList.get(1).getRoleCommission().size()));
            } else {
                recycler_view.setLayoutManager(new LinearLayoutManager(mContext));
                recycler_view_real.setLayoutManager(new LinearLayoutManager(mContext));
            }
            recycler_view.setRecycledViewPool(new RecyclerView.RecycledViewPool());
            recycler_view_real.setRecycledViewPool(new RecyclerView.RecycledViewPool());
            icon = (ImageView) view.findViewById(R.id.icon);
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.opType);
        }

    }
}