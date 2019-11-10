package com.jiankang.bean;



public class Message {
    private int messageid;
    private int receiveid;
    private int sendid;
    private String sendtime;
    private String sendcontent;
    private int messagestatus;
    private String sendname;

    public int getMessageid() {
        return messageid;
    }

    public void setMessageid(int messageid) {
        this.messageid = messageid;
    }

    public int getReceiveid() {
        return receiveid;
    }

    public void setReceiveid(int receiveid) {
        this.receiveid = receiveid;
    }

    public int getSendid() {
        return sendid;
    }

    public void setSendid(int sendid) {
        this.sendid = sendid;
    }

    public String getSendtime() {
        return sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }

    public String getSendcontent() {
        return sendcontent;
    }

    public void setSendcontent(String sendcontent) {
        this.sendcontent = sendcontent;
    }

    public int getMessagestatus() {
        return messagestatus;
    }

    public void setMessagestatus(int messagestatus) {
        this.messagestatus = messagestatus;
    }

    public String getSendname() {
        return sendname;
    }

    public void setSendname(String sendname) {
        this.sendname = sendname;
    }
}
