package com.shihuo.shihuo.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

public class WXResponse implements Parcelable {
    public String appId;
    public String partnerId;
    public String prepayId;
    public String pkg;
    public String nonceStr;
    public String timeStamp;
    public String paySign;

    public static WXResponse parseFormJsonStr(String jsonStr) {
        Gson gson = new Gson();
        WXResponse wxResponse = gson.fromJson(jsonStr, WXResponse.class);
        return wxResponse;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.appId);
        dest.writeString(this.partnerId);
        dest.writeString(this.prepayId);
        dest.writeString(this.pkg);
        dest.writeString(this.nonceStr);
        dest.writeString(this.timeStamp);
        dest.writeString(this.paySign);
    }

    public WXResponse() {
    }

    protected WXResponse(Parcel in) {
        this.appId = in.readString();
        this.partnerId = in.readString();
        this.prepayId = in.readString();
        this.pkg = in.readString();
        this.nonceStr = in.readString();
        this.timeStamp = in.readString();
        this.paySign = in.readString();
    }

    public static final Parcelable.Creator<WXResponse> CREATOR = new Parcelable.Creator<WXResponse>() {
        @Override
        public WXResponse createFromParcel(Parcel source) {
            return new WXResponse(source);
        }

        @Override
        public WXResponse[] newArray(int size) {
            return new WXResponse[size];
        }
    };
}