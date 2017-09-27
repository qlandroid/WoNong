package com.shqtn.wonong.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by android on 2017/9/26.
 */

public class ManifestDetails implements Parcelable {
    /**
     * {
     \"mescode\":\"200\",
     \"mesmessage\":\"执行成功！\",
     \"list\":null,
     \"data\":[
     {\"cdefine2\":null,
     \"ccode\":\"0000000001\",
     \"cbustype\":\"一般贸易进口\",
     \"cdepcode\":\"0301\",
     \"cdepname\":\"市场部\",
     \"ddate\":\"2014-12-30T00:00:00\",
     \"cvencode\":\"10001\",
     \"cvenname\":\"KMSMEDIA\",
     \"cexch_name\":\"美元\",
     \"cinvcode\":\"fx\",
     \"cinvname\":\"出口器具\",
     \"iquantity\":100.000000,
     \"ioritaxcost\":8.000000,
     \"ioricost\":8.000000,
     \"iorimoney\":800.0000,
     \"ioritaxPrice\":null,
     \"isum\":4800.0000,
     \"itaxrate\":0.000000
     }

     */
    private String ccode;//单号
    private String cbustype;
    private String cdepcode;//部门编码
    private String cdepname;//部门名称
    private String ddate;//单据日期
    private String cvenname;//供应商名称
    private String cvencode;//供应商编码
    private String cexch_name;//币种名称
    private String iquantity;//数量
    private String ioritaxcost;//原币含税单价;
    private String cinvname;//存货名称
    private String isum;//价税合计

    public String getCcode() {
        return ccode;
    }

    public void setCcode(String ccode) {
        this.ccode = ccode;
    }

    public String getCbustype() {
        return cbustype;
    }

    public void setCbustype(String cbustype) {
        this.cbustype = cbustype;
    }

    public String getCdepcode() {
        return cdepcode;
    }

    public void setCdepcode(String cdepcode) {
        this.cdepcode = cdepcode;
    }

    public String getCdepname() {
        return cdepname;
    }

    public void setCdepname(String cdepname) {
        this.cdepname = cdepname;
    }

    public String getDdate() {
        return ddate;
    }

    public void setDdate(String ddate) {
        this.ddate = ddate;
    }

    public String getCvenname() {
        return cvenname;
    }

    public void setCvenname(String cvenname) {
        this.cvenname = cvenname;
    }

    public String getCvencode() {
        return cvencode;
    }

    public void setCvencode(String cvencode) {
        this.cvencode = cvencode;
    }

    public String getCexch_name() {
        return cexch_name;
    }

    public void setCexch_name(String cexch_name) {
        this.cexch_name = cexch_name;
    }

    public String getIquantity() {
        return iquantity;
    }

    public void setIquantity(String iquantity) {
        this.iquantity = iquantity;
    }

    public String getIoritaxcost() {
        return ioritaxcost;
    }

    public void setIoritaxcost(String ioritaxcost) {
        this.ioritaxcost = ioritaxcost;
    }

    public String getCinvname() {
        return cinvname;
    }

    public void setCinvname(String cinvname) {
        this.cinvname = cinvname;
    }

    public String getIsum() {
        return isum;
    }

    public void setIsum(String isum) {
        this.isum = isum;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ccode);
        dest.writeString(this.cbustype);
        dest.writeString(this.cdepcode);
        dest.writeString(this.cdepname);
        dest.writeString(this.ddate);
        dest.writeString(this.cvenname);
        dest.writeString(this.cvencode);
        dest.writeString(this.cexch_name);
        dest.writeString(this.iquantity);
        dest.writeString(this.ioritaxcost);
        dest.writeString(this.cinvname);
        dest.writeString(this.isum);
    }

    public ManifestDetails() {
    }

    protected ManifestDetails(Parcel in) {
        this.ccode = in.readString();
        this.cbustype = in.readString();
        this.cdepcode = in.readString();
        this.cdepname = in.readString();
        this.ddate = in.readString();
        this.cvenname = in.readString();
        this.cvencode = in.readString();
        this.cexch_name = in.readString();
        this.iquantity = in.readString();
        this.ioritaxcost = in.readString();
        this.cinvname = in.readString();
        this.isum = in.readString();
    }

    public static final Parcelable.Creator<ManifestDetails> CREATOR = new Parcelable.Creator<ManifestDetails>() {
        @Override
        public ManifestDetails createFromParcel(Parcel source) {
            return new ManifestDetails(source);
        }

        @Override
        public ManifestDetails[] newArray(int size) {
            return new ManifestDetails[size];
        }
    };
}
