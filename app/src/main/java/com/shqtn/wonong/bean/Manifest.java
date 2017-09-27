package com.shqtn.wonong.bean;

import java.util.ArrayList;

/**
 * Created by android on 2017/9/26.
 */

public class Manifest {
    /**
     * "{\"mesCode\":\"200\",\"mesMessage\":\"执行成功！\",\"List\":null,\"Data\":[{\"cDefine2\":null,\"cCode\":\"0000000001\",\"cBusType\":\"一般贸易进口\",\"cDepCode\":\"0301\",\"cDepName\":\"市场部\",\"dDate\":\"2014-12-30T00:00:00\",\"cVenCode\":\"10001\",\"cVenName\":\"KMSMEDIA\",\"cexch_name\":\"美元\",\"cInvCode\":\"fx\",\"cInvName\":\"出口器具\",\"iQuantity\":100.000000,\"iOriTaxCost\":8.000000,\"iOriCost\":8.000000,\"iOriMoney\":800.0000,\"iOriTaxPrice\":null,\"iSum\":4800.0000,\"iTaxRate\":0.000000},{\"cDefine2\":null,\"cCode\":\"0000000001\",\"cBusType\":\"一般贸易进口\",\"cDepCode\":\"0301\",\"cDepName\":\"市场部\",\"dDate\":\"2014-12-30T00:00:00\",\"cVenCode\":\"10001\",\"cVenName\":\"KMSMEDIA\",\"cexch_name\":\"美元\",\"cInvCode\":\"fx\",\"cInvName\":\"出口器具\",\"iQuantity\":-100.000000,\"iOriTaxCost\":8.000000,\"iOriCost\":8.000000,\"iOriMoney\":-800.0000,\"iOriTaxPrice\":null,\"iSum\":-4800.0000,\"iTaxRate\":0.000000},{\"cDefine2\":null,\"cCode\":\"0000000001\",\"cBusType\":\"普通采购\",\"cDepCode\":\"0401\",\"cDepName\":\"采购部\",\"dDate\":\"2014-12-03T00:00:00\",\"cVenCode\":\"01002\",\"cVenName\":\"深圳辰环手机配件有限公司\",\"cexch_name\":\"人民币\",\"cInvCode\":\"010204\",\"cInvName\":\"大容量存储器\",\"iQuantity\":100.000000,\"iOriTaxCost\":58.500000,\"iOriCost\":50.000000,\"iOriMoney\":5000.0000,\"iOriTaxPrice\":850.0000,\"iSum\":5850.0000,\"iTaxRate\":17.000000}],\"Count\":3}"
     */

    private String mesCode;
    private String mesMessage;
    private ArrayList<Datkk> Data;

    public String getMesCode() {
        return mesCode;
    }

    public void setMesCode(String mesCode) {
        this.mesCode = mesCode;
    }

    public String getMesMessage() {
        return mesMessage;
    }

    public void setMesMessage(String mesMessage) {
        this.mesMessage = mesMessage;
    }



    public ArrayList<Datkk> getData() {
        return Data;
    }

    public void setData(ArrayList<Datkk> data) {
        Data = data;
    }

    public static class Datkk{

        private String cDefine2;
        private String cCode;

        public String getcDefine2() {
            return cDefine2;
        }

        public void setcDefine2(String cDefine2) {
            this.cDefine2 = cDefine2;
        }

        public String getcCode() {
            return cCode;
        }

        public void setcCode(String cCode) {
            this.cCode = cCode;
        }
    }

}
