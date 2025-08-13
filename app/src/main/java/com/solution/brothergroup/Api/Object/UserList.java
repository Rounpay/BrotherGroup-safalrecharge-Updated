package com.solution.brothergroup.Api.Object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.brothergroup.Fragments.dto.BalanceType;

import java.util.ArrayList;

public class UserList implements Parcelable {
    @SerializedName("osBalance")
    @Expose
    public double osBalance;
    @SerializedName("rental")
    @Expose
    public String rental;
    @SerializedName("rentalAmt")
    @Expose
    public double rentalAmt;
    @SerializedName("capping")
    @Expose
    public double capping;
    @SerializedName("referalID")
    @Expose
    public int referalID;
    @SerializedName("isPaymentGateway")
    @Expose
    public boolean isPaymentGateway;
    @SerializedName("isDownLinePG")
    @Expose
    public boolean isDownLinePG;
    @SerializedName("isECollection")
    @Expose
    public boolean isECollection;
    @SerializedName("isCalculateCommissionFromCircle")
    @Expose
    public boolean isCalculateCommissionFromCircle;
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("role")
    @Expose
    public String role;
    @SerializedName("kycStatus")
    @Expose
    public String kycStatus;
    @SerializedName("kycStatus_")
    @Expose
    public int kycStatus_;
    @SerializedName("rentalStatus")
    @Expose
    public String rentalStatus;
    @SerializedName("packageName")
    @Expose
    public String packageName;
    @SerializedName("outletName")
    @Expose
    public String outletName;
    @SerializedName("mobileNo")
    @Expose
    public String mobileNo;
    @SerializedName("whatsAppNumber")
    @Expose
    public String whatsAppNumber;
    @SerializedName("eMail")
    @Expose
    public String eMail;
    @SerializedName("isEmailVerified")
    @Expose
    public boolean isEmailVerified;
    @SerializedName("emailVerifiedStatus")
    @Expose
    public String emailVerifiedStatus;
    @SerializedName("status")
    @Expose
    public boolean status;
    @SerializedName("isOTP")
    @Expose
    public boolean isOTP;
    @SerializedName("joinDate")
    @Expose
    public String joinDate;
    @SerializedName("joinBy")
    @Expose
    public String joinBy;
    @SerializedName("slab")
    @Expose
    public String slab;
    @SerializedName("websiteName")
    @Expose
    public String websiteName;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("prefix")
    @Expose
    public String prefix;
    @SerializedName("roleID")
    @Expose
    public int roleID;
    @SerializedName("introID")
    @Expose
    public int introID;
    @SerializedName("fosId")
    @Expose
    public int fosId;
    @SerializedName("fosName")
    @Expose
    public String fosName;
    @SerializedName("fosMobile")
    @Expose
    public String fosMobile;
    @SerializedName("joinByMobile")
    @Expose
    public String joinByMobile;
    @SerializedName("userArea")
    @Expose
    public Object userArea;
    @SerializedName("isVirtual")
    @Expose
    public boolean isVirtual;
    @SerializedName("isFlatCommission")
    @Expose
    public boolean isFlatCommission;
    @SerializedName("empID")
    @Expose
    public int empID;
    @SerializedName("isAutoBilling")
    @Expose
    public boolean isAutoBilling;
    @SerializedName("maxBillingCountAB")
    @Expose
    public int maxBillingCountAB;
    @SerializedName("balanceForAB")
    @Expose
    public int balanceForAB;
    @SerializedName("fromFOSAB")
    @Expose
    public boolean fromFOSAB;
    @SerializedName("maxCreditLimitAB")
    @Expose
    public int maxCreditLimitAB;
    @SerializedName("maxTransferLimitAB")
    @Expose
    public int maxTransferLimitAB;
    @SerializedName("isShowLBA")
    @Expose
    public boolean isShowLBA;
    @SerializedName("balance")
    @Expose
    public double balance;
    @SerializedName("bCapping")
    @Expose
    public double bCapping;
    @SerializedName("isBalance")
    @Expose
    public boolean isBalance;
    @SerializedName("isBalanceFund")
    @Expose
    public boolean isBalanceFund;
    @SerializedName("uBalance")
    @Expose
    public double uBalance;
    @SerializedName("uCapping")
    @Expose
    public double uCapping;
    @SerializedName("isUBalance")
    @Expose
    public boolean isUBalance;
    @SerializedName("isUBalanceFund")
    @Expose
    public boolean isUBalanceFund;
    @SerializedName("bBalance")
    @Expose
    public double bBalance;
    @SerializedName("bbCapping")
    @Expose
    public double bbCapping;
    @SerializedName("isBBalance")
    @Expose
    public boolean isBBalance;
    @SerializedName("isBBalanceFund")
    @Expose
    public boolean isBBalanceFund;
    @SerializedName("cBalance")
    @Expose
    public double cBalance;
    @SerializedName("cCapping")
    @Expose
    public double cCapping;
    @SerializedName("isCBalance")
    @Expose
    public boolean isCBalance;
    @SerializedName("isCBalanceFund")
    @Expose
    public boolean isCBalanceFund;
    @SerializedName("idBalnace")
    @Expose
    public double idBalnace;
    @SerializedName("idCapping")
    @Expose
    public double idCapping;
    @SerializedName("isIDBalance")
    @Expose
    public boolean isIDBalance;
    @SerializedName("isIDBalanceFund")
    @Expose
    public boolean isIDBalanceFund;
    @SerializedName("pacakgeBalance")
    @Expose
    public double pacakgeBalance;
    @SerializedName("packageCapping")
    @Expose
    public double packageCapping;
    @SerializedName("isPacakgeBalance")
    @Expose
    public boolean isPacakgeBalance;
    @SerializedName("isPacakgeBalanceFund")
    @Expose
    public boolean isPacakgeBalanceFund;
    @SerializedName("isP")
    @Expose
    public boolean isP;
    @SerializedName("isPN")
    @Expose
    public boolean isPN;
    @SerializedName("isLowBalance")
    @Expose
    public boolean isLowBalance;
    @SerializedName("isFlatCommissionU")
    @Expose
    public boolean isFlatCommissionU;
    @SerializedName("isAdminDefined")
    @Expose
    public boolean isAdminDefined;
    @SerializedName("commRate")
    @Expose
    public double commRate;
    @SerializedName("vian")
    @Expose
    public Object vian;
    @SerializedName("invoiceByAdmin")
    @Expose
    public boolean invoiceByAdmin;
    @SerializedName("isMarkedGreen")
    @Expose
    public boolean isMarkedGreen;
    ArrayList<BalanceType> balanceTypes;

    protected UserList(Parcel in) {
        osBalance = in.readDouble();
        rental = in.readString();
        rentalAmt = in.readDouble();
        capping = in.readDouble();
        referalID = in.readInt();
        isPaymentGateway = in.readByte() != 0;
        isDownLinePG = in.readByte() != 0;
        isECollection = in.readByte() != 0;
        isCalculateCommissionFromCircle = in.readByte() != 0;
        id = in.readInt();
        role = in.readString();
        kycStatus = in.readString();
        kycStatus_ = in.readInt();
        rentalStatus = in.readString();
        packageName = in.readString();
        outletName = in.readString();
        mobileNo = in.readString();
        whatsAppNumber = in.readString();
        eMail = in.readString();
        isEmailVerified = in.readByte() != 0;
        emailVerifiedStatus = in.readString();
        status = in.readByte() != 0;
        isOTP = in.readByte() != 0;
        joinDate = in.readString();
        joinBy = in.readString();
        slab = in.readString();
        websiteName = in.readString();
        name = in.readString();
        prefix = in.readString();
        roleID = in.readInt();
        introID = in.readInt();
        fosId = in.readInt();
        fosName = in.readString();
        fosMobile = in.readString();
        joinByMobile = in.readString();
        isVirtual = in.readByte() != 0;
        isFlatCommission = in.readByte() != 0;
        empID = in.readInt();
        isAutoBilling = in.readByte() != 0;
        maxBillingCountAB = in.readInt();
        balanceForAB = in.readInt();
        fromFOSAB = in.readByte() != 0;
        maxCreditLimitAB = in.readInt();
        maxTransferLimitAB = in.readInt();
        isShowLBA = in.readByte() != 0;
        balance = in.readDouble();
        bCapping = in.readDouble();
        isBalance = in.readByte() != 0;
        isBalanceFund = in.readByte() != 0;
        uBalance = in.readDouble();
        uCapping = in.readDouble();
        isUBalance = in.readByte() != 0;
        isUBalanceFund = in.readByte() != 0;
        bBalance = in.readDouble();
        bbCapping = in.readDouble();
        isBBalance = in.readByte() != 0;
        isBBalanceFund = in.readByte() != 0;
        cBalance = in.readDouble();
        cCapping = in.readDouble();
        isCBalance = in.readByte() != 0;
        isCBalanceFund = in.readByte() != 0;
        idBalnace = in.readDouble();
        idCapping = in.readDouble();
        isIDBalance = in.readByte() != 0;
        isIDBalanceFund = in.readByte() != 0;
        pacakgeBalance = in.readDouble();
        packageCapping = in.readDouble();
        isPacakgeBalance = in.readByte() != 0;
        isPacakgeBalanceFund = in.readByte() != 0;
        isP = in.readByte() != 0;
        isPN = in.readByte() != 0;
        isLowBalance = in.readByte() != 0;
        isFlatCommissionU = in.readByte() != 0;
        isAdminDefined = in.readByte() != 0;
        commRate = in.readDouble();
        invoiceByAdmin = in.readByte() != 0;
        isMarkedGreen = in.readByte() != 0;
    }

    public static final Creator<UserList> CREATOR = new Creator<UserList>() {
        @Override
        public UserList createFromParcel(Parcel in) {
            return new UserList(in);
        }

        @Override
        public UserList[] newArray(int size) {
            return new UserList[size];
        }
    };

    public ArrayList<BalanceType> getBalanceTypes() {
        return balanceTypes;
    }

    public void setBalanceTypes(ArrayList<BalanceType> balanceTypes) {
        this.balanceTypes = balanceTypes;
    }

    public double getOsBalance() {
        return osBalance;
    }

    public String getRental() {
        return rental;
    }

    public double getRentalAmt() {
        return rentalAmt;
    }

    public double getCapping() {
        return capping;
    }

    public int getReferalID() {
        return referalID;
    }

    public int getRoleID() {
        return roleID;
    }

    public double getCommRate() {
        return commRate;
    }

    public int getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public String getKycStatusStr() {
        return kycStatus;
    }

    public int getKycStatus() {
        return kycStatus_;
    }

    public String getOutletName() {
        return outletName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public boolean isStatus() {
        return status;
    }

    public boolean isOTP() {
        return isOTP;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public String getJoinBy() {
        return joinBy;
    }

    public String getSlab() {
        return slab;
    }

    public String getWebsiteName() {
        return websiteName;
    }

    public double getBalance() {
        return balance;
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public int getIntroID() {
        return introID;
    }

    public int getFosId() {
        return fosId;
    }

    public String getFosName() {
        return fosName;
    }

    public String getFosMobile() {
        return fosMobile;
    }

    public String getJoinByMobile() {
        return joinByMobile;
    }

    public boolean isVirtual() {
        return isVirtual;
    }

    public double getbCapping() {
        return bCapping;
    }

    public boolean isBalance() {
        return isBalance;
    }

    public boolean isBalanceFund() {
        return isBalanceFund;
    }

    public double getuBalance() {
        return uBalance;
    }

    public double getuCapping() {
        return uCapping;
    }

    public boolean isUBalance() {
        return isUBalance;
    }

    public boolean isUBalanceFund() {
        return isUBalanceFund;
    }

    public double getbBalance() {
        return bBalance;
    }

    public double getBbCapping() {
        return bbCapping;
    }

    public boolean isBBalance() {
        return isBBalance;
    }

    public boolean isBBalanceFund() {
        return isBBalanceFund;
    }

    public double getcBalance() {
        return cBalance;
    }

    public double getcCapping() {
        return cCapping;
    }

    public boolean isCBalance() {
        return isCBalance;
    }

    public boolean isCBalanceFund() {
        return isCBalanceFund;
    }

    public double getIdBalnace() {
        return idBalnace;
    }

    public double getIdCapping() {
        return idCapping;
    }

    public boolean isIDBalance() {
        return isIDBalance;
    }

    public boolean isIDBalanceFund() {
        return isIDBalanceFund;
    }

    public double getPacakgeBalance() {
        return pacakgeBalance;
    }

    public double getPackageCapping() {
        return packageCapping;
    }

    public boolean isPacakgeBalance() {
        return isPacakgeBalance;
    }

    public boolean isPacakgeBalanceFund() {
        return isPacakgeBalanceFund;
    }

    public boolean isP() {
        return isP;
    }

    public boolean isPN() {
        return isPN;
    }

    public boolean isLowBalance() {
        return isLowBalance;
    }

    public boolean isAdminDefined() {
        return isAdminDefined;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getMaxBillingCountAB() {
        return maxBillingCountAB;
    }

    public boolean isAutoBilling() {
        return isAutoBilling;
    }

    public int getMaxCreditLimitAB() {
        return maxCreditLimitAB;
    }

    public int getMaxTransferLimitAB() {
        return maxTransferLimitAB;
    }

    public int getBalanceForAB() {
        return balanceForAB;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(osBalance);
        dest.writeString(rental);
        dest.writeDouble(rentalAmt);
        dest.writeDouble(capping);
        dest.writeInt(referalID);
        dest.writeByte((byte) (isPaymentGateway ? 1 : 0));
        dest.writeByte((byte) (isDownLinePG ? 1 : 0));
        dest.writeByte((byte) (isECollection ? 1 : 0));
        dest.writeByte((byte) (isCalculateCommissionFromCircle ? 1 : 0));
        dest.writeInt(id);
        dest.writeString(role);
        dest.writeString(kycStatus);
        dest.writeInt(kycStatus_);
        dest.writeString(rentalStatus);
        dest.writeString(packageName);
        dest.writeString(outletName);
        dest.writeString(mobileNo);
        dest.writeString(whatsAppNumber);
        dest.writeString(eMail);
        dest.writeByte((byte) (isEmailVerified ? 1 : 0));
        dest.writeString(emailVerifiedStatus);
        dest.writeByte((byte) (status ? 1 : 0));
        dest.writeByte((byte) (isOTP ? 1 : 0));
        dest.writeString(joinDate);
        dest.writeString(joinBy);
        dest.writeString(slab);
        dest.writeString(websiteName);
        dest.writeString(name);
        dest.writeString(prefix);
        dest.writeInt(roleID);
        dest.writeInt(introID);
        dest.writeInt(fosId);
        dest.writeString(fosName);
        dest.writeString(fosMobile);
        dest.writeString(joinByMobile);
        dest.writeByte((byte) (isVirtual ? 1 : 0));
        dest.writeByte((byte) (isFlatCommission ? 1 : 0));
        dest.writeInt(empID);
        dest.writeByte((byte) (isAutoBilling ? 1 : 0));
        dest.writeInt(maxBillingCountAB);
        dest.writeInt(balanceForAB);
        dest.writeByte((byte) (fromFOSAB ? 1 : 0));
        dest.writeInt(maxCreditLimitAB);
        dest.writeInt(maxTransferLimitAB);
        dest.writeByte((byte) (isShowLBA ? 1 : 0));
        dest.writeDouble(balance);
        dest.writeDouble(bCapping);
        dest.writeByte((byte) (isBalance ? 1 : 0));
        dest.writeByte((byte) (isBalanceFund ? 1 : 0));
        dest.writeDouble(uBalance);
        dest.writeDouble(uCapping);
        dest.writeByte((byte) (isUBalance ? 1 : 0));
        dest.writeByte((byte) (isUBalanceFund ? 1 : 0));
        dest.writeDouble(bBalance);
        dest.writeDouble(bbCapping);
        dest.writeByte((byte) (isBBalance ? 1 : 0));
        dest.writeByte((byte) (isBBalanceFund ? 1 : 0));
        dest.writeDouble(cBalance);
        dest.writeDouble(cCapping);
        dest.writeByte((byte) (isCBalance ? 1 : 0));
        dest.writeByte((byte) (isCBalanceFund ? 1 : 0));
        dest.writeDouble(idBalnace);
        dest.writeDouble(idCapping);
        dest.writeByte((byte) (isIDBalance ? 1 : 0));
        dest.writeByte((byte) (isIDBalanceFund ? 1 : 0));
        dest.writeDouble(pacakgeBalance);
        dest.writeDouble(packageCapping);
        dest.writeByte((byte) (isPacakgeBalance ? 1 : 0));
        dest.writeByte((byte) (isPacakgeBalanceFund ? 1 : 0));
        dest.writeByte((byte) (isP ? 1 : 0));
        dest.writeByte((byte) (isPN ? 1 : 0));
        dest.writeByte((byte) (isLowBalance ? 1 : 0));
        dest.writeByte((byte) (isFlatCommissionU ? 1 : 0));
        dest.writeByte((byte) (isAdminDefined ? 1 : 0));
        dest.writeDouble(commRate);
        dest.writeByte((byte) (invoiceByAdmin ? 1 : 0));
        dest.writeByte((byte) (isMarkedGreen ? 1 : 0));
    }
}
