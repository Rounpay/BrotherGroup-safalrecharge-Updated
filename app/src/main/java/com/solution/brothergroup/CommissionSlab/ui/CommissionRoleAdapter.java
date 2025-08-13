package com.solution.brothergroup.CommissionSlab.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.solution.brothergroup.Api.Object.RoleCommission;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.UtilMethods;

import java.util.ArrayList;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Vishnu on 26-04-2017.
 */

public class CommissionRoleAdapter extends RecyclerView.Adapter<CommissionRoleAdapter.MyViewHolder> {
    String charText = "";
    int size = 0;
    int type;
    private ArrayList<RoleCommission> transactionsList;
    private Context mContext;

    public CommissionRoleAdapter(ArrayList<RoleCommission> transactionsList, Context mContext, int size, int type) {
        this.transactionsList = transactionsList;
        this.size = size;
        this.mContext = mContext;
        this.type = type;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_commission_role, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final RoleCommission operator = transactionsList.get(position);

        String comType, comm;
        if(type==1){
            if (size == 1) {
                if (operator.getrCommType() == 0) {
                    comType = "(COMMISSION)";
                } else {
                    comType = "(SURCHARGE)";
                }

                if (operator.getrAmtType() == 0) {
                    comm = UtilMethods.INSTANCE.formatedAmount(operator.getrComm()+"") + " %";
                } else {
                    comm = mContext.getResources().getString(R.string.rupiya) + " " + UtilMethods.INSTANCE.formatedAmount(operator.getrComm()+"");
                }

            }
            else if (size == 2) {
                if (operator.getrCommType() == 0) {
                    comType = "(COMMISSION)";
                } else {
                    comType = "(SURCHARGE)";
                }

                if (operator.getrAmtType() == 0) {
                    comm = UtilMethods.INSTANCE.formatedAmount(operator.getrComm()+"") + " %";
                } else {
                    comm = mContext.getResources().getString(R.string.rupiya) + " " + UtilMethods.INSTANCE.formatedAmount(operator.getrComm()+"");
                }
            }
            else {
                if (operator.getrCommType() == 0) {
                    comType = "" + "(COM)";
                } else {
                    comType = "" + "(SUR)";
                }

                if (operator.getrAmtType() == 0) {
                    comm = UtilMethods.INSTANCE.formatedAmount(operator.getrComm()+"") + " %";
                } else {
                    comm = mContext.getResources().getString(R.string.rupiya) + " " + UtilMethods.INSTANCE.formatedAmount(operator.getrComm()+"");
                }

            }
            if (size == 1) {
                holder.userRoleTv.setText(operator.getRole() + " : " + comm + " " + comType);
                holder.comTv.setVisibility(View.GONE);
            }
            else {
                holder.userRoleTv.setText(operator.getPrefix() +"");
                holder.comTv.setVisibility(View.VISIBLE);
                holder.comTv.setText(comm + " " + comType);
            }
        }



        else {
            if (size == 1) {
                if (operator.getCommType() == 0) {
                    comType = "(COMMISSION)";
                } else {
                    comType = "(SURCHARGE)";
                }

                if (operator.getAmtType() == 0) {
                    comm = UtilMethods.INSTANCE.formatedAmount(operator.getComm()+"") + " %";
                } else {
                    comm = mContext.getResources().getString(R.string.rupiya) + " " + UtilMethods.INSTANCE.formatedAmount(operator.getComm()+"");
                }

            }
            else if (size == 2) {
                if (operator.getCommType() == 0) {
                    comType = "(COMMISSION)";
                } else {
                    comType = "(SURCHARGE)";
                }

                if (operator.getAmtType() == 0) {
                    comm = UtilMethods.INSTANCE.formatedAmount(operator.getComm()+"") + " %";
                } else {
                    comm = mContext.getResources().getString(R.string.rupiya) + " " + UtilMethods.INSTANCE.formatedAmount(operator.getComm()+"");
                }

            }
            else {
                if (operator.getCommType() == 0) {
                    comType = "" + "(COM)";
                } else {
                    comType = "" + "(SUR)";
                }

                if (operator.getAmtType() == 0) {
                    comm = UtilMethods.INSTANCE.formatedAmount(operator.getComm()+"") + " %";
                } else {
                    comm = mContext.getResources().getString(R.string.rupiya) + " " + UtilMethods.INSTANCE.formatedAmount(operator.getComm()+"");
                }

            }
            if (size == 1) {
                holder.userRoleTv.setText(operator.getRole() + " : " + comm + " " + comType);
                holder.comTv.setVisibility(View.GONE);
            }
            else {
                holder.userRoleTv.setText(operator.getPrefix()+"" );
                holder.comTv.setVisibility(View.VISIBLE);
                holder.comTv.setText(comm + " " + comType);
            }
        }
    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView userRoleTv,comTv;


        public MyViewHolder(View view) {
            super(view);
            userRoleTv = (AppCompatTextView) view.findViewById(R.id.userRoleTv);
            comTv = (AppCompatTextView) view.findViewById(R.id.comTv);

            /*  commissionTypeTv = (AppCompatTextView) view.findViewById(R.id.commisionTypeTv);*/
        }
    }

}