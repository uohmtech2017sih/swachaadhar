package com.prototype.uoh.sacchaadhaar.database;

/**
 * Created by kc on 30/3/17.
 */

public class ProcessInfo {
    private static ProcessInfo ourInstance = new ProcessInfo();
    private String aadharnum;
    private String centerid;
    private String phonenum;


    public String getCenterid() {
        return ourInstance.centerid;
    }

    public void setCenterid(String centerid) {
        ourInstance.centerid = centerid;
    }


    public String getPhonenum() {
        return ourInstance.phonenum;
    }

    public void setPhonenum(String phonenum) {
        ourInstance.phonenum = phonenum;
    }

    private ProcessInfo() {


    }
    public static ProcessInfo getInstance() {
        return ourInstance;
    }

    public String getAadharnum() {
        return ourInstance.aadharnum;
    }

    public void setAadharnum(String aadharnum) {
        ourInstance.aadharnum = aadharnum;
    }


}