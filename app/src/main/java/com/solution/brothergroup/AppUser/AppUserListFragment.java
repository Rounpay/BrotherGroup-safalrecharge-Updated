package com.solution.brothergroup.AppUser;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.solution.brothergroup.AppUser.Activity.FosCollectionActivity;
import com.solution.brothergroup.AppUser.Adapter.AppUserListAdapter;
import com.solution.brothergroup.Api.Object.BalanceData;
import com.solution.brothergroup.Api.Object.UserList;
import com.solution.brothergroup.Api.Request.AppUserListRequest;
import com.solution.brothergroup.Api.Response.AppUserListResponse;
import com.solution.brothergroup.Authentication.dto.LoginResponse;
import com.solution.brothergroup.BuildConfig;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.ApiClient;
import com.solution.brothergroup.Util.ApplicationConstant;
import com.solution.brothergroup.Util.EndPointInterface;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.usefull.CustomLoader;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

import static android.app.Activity.RESULT_OK;

public class AppUserListFragment extends Fragment {

    AppUserListAdapter mAppUserListAdapter;
    View loader;
    RecyclerView mRecyclerView;
    String rollId;
    TextView errorMsg;
    EditText searchEt;
    ImageView clearIcon;
    private ArrayList<UserList> mUserLists = new ArrayList<>();
    private int INTENT_COLLECTION=523;
    private LoginResponse LoginDataResponse;

    public AppUserListFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_app_user_list, container, false);
        mRecyclerView = rootView.findViewById(R.id.recyclerView);
        loader = rootView.findViewById(R.id.loader);
        searchEt = rootView.findViewById(R.id.searchEt);
        clearIcon = rootView.findViewById(R.id.clearIcon);
        String LoginResponseStr = UtilMethods.INSTANCE.getLoginPref(getActivity());
         LoginDataResponse = new Gson().fromJson(LoginResponseStr, LoginResponse.class);
        errorMsg = rootView.findViewById(R.id.errorMsg);
        errorMsg.setText("User list not found.");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        boolean isAccountStatement = getArguments().getBoolean("isAccountStatement");
        BalanceData mBalanceData = (BalanceData) getArguments().getSerializable("BalanceData");
        mAppUserListAdapter = new AppUserListAdapter(getActivity(), mUserLists, new AppUserListAdapter.FundTransferCallBAck() {
            @Override
            public void onSucessFund() {
                appUserListApi();
            }

            @Override
            public void onSucessEdit() {
                appUserListApi();
            }
        },
                new AppUserListAdapter.AutoBillingCallBAck() {
                    @Override
                    public void onSucessFund() {
                        appUserListApi();
                    }

                    @Override
                    public void onSucessEdit() {
                        appUserListApi();
                    }
                }, mBalanceData, isAccountStatement, this,new CustomLoader(getActivity(), android.R.style.Theme_Translucent_NoTitleBar)
                ,LoginDataResponse);
        mRecyclerView.setAdapter(mAppUserListAdapter);
        rollId = getArguments().getString("Id");
        clearIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEt.setText("");
            }
        });
        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    clearIcon.setVisibility(View.VISIBLE);
                } else {
                    clearIcon.setVisibility(View.GONE);
                }

                mAppUserListAdapter.getFilter().filter(s);
            }
        });

        appUserListApi();
        return rootView;
    }

  /*  @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        appUserListApi();
    }*/

    public void appUserListApi() {
        try {
            loader.setVisibility(View.VISIBLE);
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<AppUserListResponse> call = git.AppUserList(new AppUserListRequest(rollId, LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(getActivity()),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(getActivity()), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<AppUserListResponse>() {

                @Override
                public void onResponse(Call<AppUserListResponse> call, retrofit2.Response<AppUserListResponse> response) {
//                     //Log.e("Payment response", "hello response : " + new Gson().toJson(response.body()).toString());

                    AppUserListResponse data = response.body();
                    errorMsg.setVisibility(View.GONE);
                    if (data != null) {
                        if (data.getStatuscode() == 1) {
                            if (data.getUserList() != null && data.getUserList().size() > 0) {
                                errorMsg.setVisibility(View.GONE);
                                mUserLists.clear();
                                mUserLists.addAll(data.getUserList());
                                mAppUserListAdapter.notifyDataSetChanged();
                            } else if (data.getUserListRoleWise() != null && data.getUserListRoleWise().size() > 0 &&
                                    data.getUserListRoleWise().get(0) != null && data.getUserListRoleWise().get(0).getUserList() != null &&
                                    data.getUserListRoleWise().get(0).getUserList().size() > 0) {
                                errorMsg.setVisibility(View.GONE);
                                mUserLists.clear();
                                mUserLists.addAll(data.getUserListRoleWise().get(0).getUserList());
                                mAppUserListAdapter.notifyDataSetChanged();
                            } else {
                                errorMsg.setVisibility(View.VISIBLE);
                            }


                        } else if (response.body().getStatuscode() == -1) {
                            UtilMethods.INSTANCE.Error(getActivity(), data.getMsg() + "");
                        } else if (response.body().getStatuscode() == 0) {
                            UtilMethods.INSTANCE.Error(getActivity(), data.getMsg() + "");
                        } else {
                            UtilMethods.INSTANCE.Error(getActivity(), data.getMsg() + "");
                        }

                    } else {
                        UtilMethods.INSTANCE.Error(getActivity(), getString(R.string.some_thing_error));
                    }
                    loader.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<AppUserListResponse> call, Throwable t) {
                    //Log.e("response", "error ");
                    loader.setVisibility(View.GONE);
                    try {

                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {
                                UtilMethods.INSTANCE.Error(getActivity(), getString(R.string.err_msg_network));


                            } else {
                                UtilMethods.INSTANCE.Error(getActivity(), t.getMessage());


                            }

                        } else {
                            UtilMethods.INSTANCE.Error(getActivity(), getString(R.string.some_thing_error));
                        }
                    } catch (IllegalStateException ise) {
                        loader.setVisibility(View.GONE);
                        UtilMethods.INSTANCE.Error(getActivity(), getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            loader.setVisibility(View.GONE);
            UtilMethods.INSTANCE.Error(getActivity(), getString(R.string.some_thing_error));
        }

    }

    public void DistributorCollectionFromFosActivity(UserList mItem) {
        Intent intent = new Intent(getActivity(), FosCollectionActivity.class);
        intent.putExtra("id" , mItem.getId());
        intent.putExtra("mobile" , mItem.getMobileNo());
        intent.putExtra("outletName" , mItem.getOutletName());
        startActivityForResult(intent,INTENT_COLLECTION);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==INTENT_COLLECTION && resultCode==RESULT_OK){
            appUserListApi();
        }
    }
}
