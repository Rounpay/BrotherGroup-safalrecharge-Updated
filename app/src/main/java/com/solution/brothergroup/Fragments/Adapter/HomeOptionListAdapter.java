package com.solution.brothergroup.Fragments.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import com.solution.brothergroup.Api.Object.AssignedOpType;
import com.solution.brothergroup.Api.Object.DataOpType;
import com.solution.brothergroup.Api.Response.AppUserListResponse;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.UtilMethods;

/**
 * Created by Vishnu on 18-01-2017.
 */

public class HomeOptionListAdapter extends RecyclerView.Adapter<HomeOptionListAdapter.MyViewHolder> {

    ClickView mClickView;
    int layout;
    private List<AssignedOpType> operatorList;
    private Activity mContext;
    private DataOpType data;
    public TextView tv_customerNo, tv_testingNo;

    public HomeOptionListAdapter(List<AssignedOpType> operatorList, Activity mContext, ClickView mClickView, int layout) {
        this.operatorList = operatorList;
        this.mContext = mContext;
        this.mClickView = mClickView;
        this.layout = layout;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final AssignedOpType operator = operatorList.get(position);

        if (operator.getSubOpTypeList() != null && operator.getSubOpTypeList().size() > 0) {
            holder.name.setText(layout == R.layout.adapter_dashboard_option ? operator.getService().replaceAll("\n", " ") : operator.getService());
            parentIcon(operator.getParentID(), holder);

        } else {
            holder.name.setText(layout == R.layout.adapter_dashboard_option ? operator.getName().replaceAll("\n", " ") : operator.getName());
            setIcon(operator.getServiceID(), holder);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickView != null) {

                    if (!operator.getIsServiceActive()) {
                        Toast.makeText(mContext, "Coming Soon", Toast.LENGTH_SHORT).show();
                    } else if (!operator.getIsActive()) {
                        String conytactTxt = "";
                        if (operator.getUpline() != null && !operator.getUpline().isEmpty() && operator.getUplineMobile() != null && !operator.getUplineMobile().isEmpty()) {
                            conytactTxt = operator.getUpline() + " : " + operator.getUplineMobile();
                        }
                        if (operator.getCcContact() != null && !operator.getCcContact().isEmpty()) {
                            conytactTxt = conytactTxt + "\nCustomer Care : " + operator.getCcContact();
                        }
                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
                        final AlertDialog dialog = dialogBuilder.create();
                        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View dialogView = inflater.inflate(R.layout.upgrade_package_dialog, null);
                        dialog.setView(dialogView);

                        TextView tv_upgradepackage = (TextView) dialogView.findViewById(R.id.tv_upgradepackage);
                        tv_customerNo = (TextView) dialogView.findViewById(R.id.tv_customerNo);
                        tv_testingNo = (TextView) dialogView.findViewById(R.id.tv_testingNo);
                        tv_customerNo.setText(conytactTxt);
                        tv_upgradepackage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (mClickView != null) {
                                    mClickView.onPackageUpgradeClick();
                                }
                                dialog.dismiss();
                            }
                        });

                        dialog.show();

                    } else {
                        if (mClickView != null) {
                            mClickView.onClick(operator);
                        }
                    }

                }
            }
        });

    }

    private void getDetail() {
        if (UtilMethods.INSTANCE.isNetworkAvialable(mContext)) {
            try {
                UtilMethods.INSTANCE.GetCompanyProfile(mContext, new UtilMethods.ApiCallBack() {
                    @Override
                    public void onSucess(Object object) {
                        AppUserListResponse data = (AppUserListResponse) object;
                        setContactData(data);
                    }
                });


            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            UtilMethods.INSTANCE.NetworkError(mContext, mContext.getResources().getString(R.string.err_msg_network_title),
                    mContext.getResources().getString(R.string.err_msg_network));
        }
    }

    private void setContactData(AppUserListResponse mContactData) {
        if (mContactData != null && mContactData.getCompanyProfile() != null) {
            //ll_contactus.setVisibility(View.VISIBLE);


            if (mContactData.getCompanyProfile().getCustomerCareMobileNos() != null && !mContactData.getCompanyProfile().getCustomerCareMobileNos().isEmpty()) {
                // customerCareView.setVisibility(View.VISIBLE);
                //  custCareCallUsView.setVisibility(View.VISIBLE);
                tv_customerNo.setText("Customer Care :  " +/*customerCareNumber.getText().toString() + ", " +*/ mContactData.getCompanyProfile().getCustomerCareMobileNos());


            }
            if (mContactData.getCompanyProfile().getCustomerWhatsAppNos() != null && !mContactData.getCompanyProfile().getCustomerWhatsAppNos().isEmpty()) {
                tv_testingNo.setText("Whatsapp No :  " + mContactData.getCompanyProfile().getCustomerWhatsAppNos());
            }
        }
    }

    @Override
    public int getItemCount() {
        return operatorList.size();
    }

    void setIcon(int id, MyViewHolder holder) {
        /*1	Prepaid
2	Postpaid
3	DTH
4	Landline
5	Electricity
6	Gas
7	Domestic Hotel
8	International Hotel
9	Domestic Flight
10	International Flight
11	Bus
12	Connection
13	MPOS
14	DMT
15	DMR Charge
16	Broadband
17	Water
18	Train
19	Shopping
20	PAN Card
22	AEPS
110 Fund Request*/
        if (id == 1) {
            holder.icon.setImageResource(R.drawable.ic_prepaid);
        }
        if (id == 2) {
            holder.icon.setImageResource(R.drawable.ic_postpaid);
        }
        if (id == 3) {
            holder.icon.setImageResource(R.drawable.ic_satellite_dish);
           
        }
        if (id == 4) {
            holder.icon.setImageResource(R.drawable.ic_landline);
        }
        if (id == 5) {
            holder.icon.setImageResource(R.drawable.ic_bulb);
        }
        if (id == 6) {
            holder.icon.setImageResource(R.drawable.ic_gas_pipe);
        }
        if (id == 7) {
            holder.icon.setImageResource(R.mipmap.ic_launcher);
        }
        if (id == 8) {
            holder.icon.setImageResource(R.mipmap.ic_launcher);
        }
        if (id == 9) {
            holder.icon.setImageResource(R.mipmap.ic_launcher);
        }
        if (id == 10) {
            holder.icon.setImageResource(R.mipmap.ic_launcher);
        }
        if (id == 11) {
            holder.icon.setImageResource(R.mipmap.ic_launcher);
        }
        if (id == 12) {
            holder.icon.setImageResource(R.mipmap.ic_launcher);
        }
        if (id == 13) {
            holder.icon.setImageResource(R.drawable.ic_atm);
        }
        if (id == 14) {
            holder.icon.setImageResource(R.drawable.ic_bank);
          
        }
        if (id == 15) {
            holder.icon.setImageResource(R.mipmap.ic_launcher);
        }
        if (id == 16) {

            holder.icon.setImageResource(R.drawable.ic_broadband);
        }
        if (id == 17) {
           
            holder.icon.setImageResource(R.drawable.ic_water);
        }
        if (id == 18) {
            holder.icon.setImageResource(R.mipmap.ic_launcher);
        }
        if (id == 19) {
            holder.icon.setImageResource(R.mipmap.ic_launcher);
        }
        if (id == 20) {
            holder.icon.setImageResource(R.mipmap.ic_launcher);
        }
        if (id == 21) {
            holder.icon.setImageResource(R.mipmap.ic_launcher);
        }
        if (id == 22) { //For AEPS
          
            holder.icon.setImageResource(R.drawable.ic_eaadharcard);
        }
        if (id == 23) {
            holder.icon.setImageResource(R.mipmap.ic_launcher);
        }
        if (id == 24) {
            //For PSA
           
            holder.icon.setImageResource(R.drawable.ic_pan);
        }
        if (id == 25) {
            holder.icon.setImageResource(R.drawable.ic_loan);
           

        }
        if (id == 26) {
            holder.icon.setImageResource(R.drawable.ic_gas);
           

        }
        if (id == 27) {
            holder.icon.setImageResource(R.drawable.ic_insurance);
        

        }
        if (id == 28) {
            //Bike Insurance
            holder.icon.setImageResource(R.drawable.ic_bike_insurance);
         

        }
        if (id == 29) {
            //Car Insurance
            holder.icon.setImageResource(R.drawable.ic_car_insurance);
           

        }
        if (id == 30) {
            holder.icon.setImageResource(R.mipmap.ic_launcher);
        }
        if (id == 31) {
            holder.icon.setImageResource(R.mipmap.ic_launcher);
        }
        if (id == 32) {
            holder.icon.setImageResource(R.drawable.ic_prepaid);
           
        }
        if (id == 33) {
            holder.icon.setImageResource(R.drawable.ic_satellite_dish);
           
        }
        if (id == 35) {
           
            holder.icon.setImageResource(R.drawable.ic_hd_box);
            // return R.drawable.ic_hd_box;
        }
        if (id == 36) {
           
            holder.icon.setImageResource(R.drawable.ic_sd_box);
            // return R.drawable.ic_sd_box;
        }
        if (id == 37) {
            holder.icon.setImageResource(R.drawable.ic_add_wallet);
          
        }
        if (id == 38) {
            holder.icon.setImageResource(R.drawable.ic_fastag);
           
        }


        if (id == 39) {
            holder.icon.setImageResource(R.drawable.ic_cable_tv);
          
        }

        if (id == 43) {
         
            holder.icon.setImageResource(R.drawable.ic_health_insurance);
        }
        if (id == 44) {
            holder.icon.setImageResource(R.drawable.ic_atm);
           
        }
        if (id == 45) {
         ;
            holder.icon.setImageResource(R.drawable.ic_online_shopping);
        }
        if (id == 46) {
            holder.icon.setImageResource(R.drawable.ic_municiple_tax);
          
        }


        if (id == 47) {
            holder.icon.setImageResource(R.drawable.ic_education_fee);
        
        }


        if (id == 48) {
            holder.icon.setImageResource(R.drawable.ic_housing_society);
          
        }


        if (id == 49) {
            holder.icon.setImageResource(R.drawable.ic_health_insurance);
           
        }
        if (id == 50) {
         
            holder.icon.setImageResource(R.drawable.ic_wallet);
        }

        if (id == 52) {
            holder.icon.setImageResource(R.drawable.ic_hospital);
          
        }

        if (id == 62) {
          
            holder.icon.setImageResource(R.drawable.ic_upi_icon);
        }
        if (id == 63) {
           
            holder.icon.setImageResource(R.drawable.ic_scan_pay);
        }
        if (id == 74) {
            holder.icon.setImageResource(R.drawable.ic_account_open);

        }


        if (id == 100) {
            //Fund Request

            holder.icon.setImageResource(R.drawable.ic_fund_request);
        }if (id == 101) {//CMS
//            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.color_brown));
            holder.icon.setImageResource(R.drawable.ic_commission_slab);
        }else if (id == 104) {//NSDL
//            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.black));
            holder.icon.setImageResource(R.drawable.ic_nsdl);
        }
        if (id == 1001) {
            holder.icon.setImageResource(R.drawable.ic_recharge_report);
           
        }
        if (id == 102) {
            holder.icon.setImageResource(R.drawable.ic_ledger_report);
        
        }
        if (id == 103) {
            holder.icon.setImageResource(R.drawable.ic_fund_order_report);
         
        }
        if (id == 1004) {
            holder.icon.setImageResource(R.drawable.ic_dispute);
  
        }
        if (id == 105) {
            //DMT Report
            holder.icon.setImageResource(R.drawable.ic_dmt_transaction);
       

        }
        if (id == 106) {
            holder.icon.setImageResource(R.drawable.ic_exchnage_fund_24dp);
        
        }
        if (id == 107) {
            //Debit Credit Report
            holder.icon.setImageResource(R.drawable.ic_fund_receive);
        
        }
        if (id == 108) {
            holder.icon.setImageResource(R.drawable.ic_user_daybook);
     
        }
        if (id == 109) {
            holder.icon.setImageResource(R.drawable.ic_report_pending);
        
        }
        if (id == 110) {
            holder.icon.setImageResource(R.drawable.ic_fund_request);
          
        }
        if (id == 111) {
            holder.icon.setImageResource(R.drawable.ic_create_user);
          
        }
        if (id == 112) {
            holder.icon.setImageResource(R.drawable.ic_multi_user);
        
        }
        if (id == 113) {
            //Commission Slab
            holder.icon.setImageResource(R.drawable.ic_commission_slab);
          
        }
        if (id == 114) {
            holder.icon.setImageResource(R.drawable.ic_wrong_right);
           
        }
        if (id == 120) {
            holder.icon.setImageResource(R.drawable.ic_aeps_report_bubble_svg);
            /*return R.drawable.ic_aeps_report_bubble_svg;*/
        }
        if (id == 115) {
            holder.icon.setImageResource(R.drawable.ic_daybook_dmt);
         
        }
        if (id == 116) {
            holder.icon.setImageResource(R.drawable.ic_call_request);
           
        }
        if (id == 117) {
            holder.icon.setImageResource(R.drawable.ic_scan_pay);
           
        }
        if (id == 118) {
            holder.icon.setImageResource(R.drawable.ic_specific_report);
         
        } if (id == 121) {
            holder.icon.setImageResource(R.drawable.ic_statement_collection);

        }if (id == 122) {
            holder.icon.setImageResource(R.drawable.ic_statement_channel);

        }if (id == 123) {
            holder.icon.setImageResource(R.drawable.ic_statement_channel);
           
        } if (id == 124) {
            holder.icon.setImageResource(R.drawable.ic_statement_collection);
           

        }if (id == 125) {
            holder.icon.setImageResource(R.drawable.ic_voucher);

        }if (id == 128) {
            holder.icon.setImageResource(R.drawable.ic_address_white);
           
        }if (id == 135) {
            holder.icon.setImageResource(R.drawable.ic_move_to_bank);
        }

//        if (id == 119) {
//            holder.icon.setImageResource(R.drawable.ic_add_wallet);
//
//        }


    }

    public int parentIcon(int parentId, MyViewHolder holder) {
            /*
19	Genral Insurance
30	DTH Subscription
*/
        if (parentId == 1) {
            holder.icon.setImageResource(R.mipmap.ic_launcher);
            //
        } else if (parentId == 11) {
            holder.icon.setImageResource(R.drawable.ic_bill_payment);
           
        } else if (parentId == 19) {
            holder.icon.setImageResource(R.drawable.ic_general_insurance);
           
        } else if (parentId == 30) {
            holder.icon.setImageResource(R.mipmap.ic_launcher);
            //ViewCompat.setBackgroundTintList
        }else if (parentId == 34) {
            holder.icon.setImageResource(R.drawable.ic_atm);
            //ViewCompat.setBackgroundTintList
        }
        else if (parentId == 11) {
            holder.icon.setImageResource(R.drawable.ic_bill_payment);
            //ViewCompat.setBackgroundTintList
        }

        return R.mipmap.ic_launcher;
    }

    public interface ClickView {
        void onClick(AssignedOpType operator);

        void onPackageUpgradeClick();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView icon, bgView;
        RelativeLayout imageContainer;
        View itemView;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            icon = view.findViewById(R.id.icon);
            imageContainer = view.findViewById(R.id.imageContainer);
            bgView = view.findViewById(R.id.bgView);
            itemView = view;

        }
    }
}
