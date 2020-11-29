package com.qf.elasticsearch.doman;

/**
 * @Author chenzhongjun
 * @Date 2020/11/29
 */
public class SmsLog {


    /**
     * corpName : 盒马鲜生
     * mobile : 13100000001
     * createDate :
     * sendDate :
     * longCode : 10660000988
     * smsContent : 【盒马】您尾号12345678的订单已开始配送，请在您指定的时间收货不要走开哦~配送员：刘三，电话：13800000000
     * state : 0
     * province : 北京
     * operatorId : 2
     * ipAddr : 10.126.2.9
     * replyTotal : 15
     * fee : 5
     */

    private String corpName;
    private String mobile;
    private Long createDate;
    private Long sendDate;
    private String longCode;
    private String smsContent;
    private int state;
    private String province;
    private int operatorId;
    private String ipAddr;
    private int replyTotal;
    private int fee;

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    public Long getSendDate() {
        return sendDate;
    }

    public void setSendDate(Long sendDate) {
        this.sendDate = sendDate;
    }

    public String getLongCode() {
        return longCode;
    }

    public void setLongCode(String longCode) {
        this.longCode = longCode;
    }

    public String getSmsContent() {
        return smsContent;
    }

    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public int getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(int operatorId) {
        this.operatorId = operatorId;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public int getReplyTotal() {
        return replyTotal;
    }

    public void setReplyTotal(int replyTotal) {
        this.replyTotal = replyTotal;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }
}
