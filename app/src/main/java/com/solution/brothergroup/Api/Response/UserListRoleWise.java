package com.solution.brothergroup.Api.Response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.brothergroup.Api.Object.UserList;

import java.util.ArrayList;

public class UserListRoleWise implements Parcelable {
    @SerializedName("roleID")
    @Expose
    public int roleID;
    @SerializedName("roleName")
    @Expose
    public String roleName;
    @SerializedName("userList")
    @Expose
    public ArrayList<UserList> userList = null;

    protected UserListRoleWise(Parcel in) {
        roleID = in.readInt();
        roleName = in.readString();
        userList = in.createTypedArrayList(UserList.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(roleID);
        dest.writeString(roleName);
        dest.writeTypedList(userList);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public static final Creator<UserListRoleWise> CREATOR = new Creator<UserListRoleWise>() {
        @Override
        public UserListRoleWise createFromParcel(Parcel in) {
            return new UserListRoleWise(in);
        }

        @Override
        public UserListRoleWise[] newArray(int size) {
            return new UserListRoleWise[size];
        }
    };

    public int getRoleID() {
        return roleID;
    }

    public String getRoleName() {
        return roleName;
    }

    public ArrayList<UserList> getUserList() {
        return userList;
    }
}
