package com.example.cropinsurance;

import com.google.firebase.database.Exclude;

public class Upload {
    private String mName;
    private String mImageUrl;
    private String mKey;
    private String Email;
    private String Address;
    private String TimeStamp;
    private String Process;
    private String InsuranceId;
    private String Latitude;
    private String Longitude;


    public Upload() {
        //empty constructor needed
    }

    public Upload(String name, String imageUrl, String email, String address, String timeStamp, String process, String insuranceId, String latitude,
            String longitude) {

        if (name.trim().equals("")) {
            name = "No Name";
        }

        mName = name;
        mImageUrl = imageUrl;
        Email = email;
        Address = address;
        TimeStamp = timeStamp;
        Process = process;
        InsuranceId = insuranceId;
        Longitude = longitude;
        Latitude = latitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getInsuranceId() {
        return InsuranceId;
    }

    public void setInsuranceId(String insuranceId) {
        InsuranceId = insuranceId;
    }

    public String getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.TimeStamp = timeStamp;
    }

    public void setProcess(String process) {
        this.Process = process;
    }

    public String getProcess() {
        return Process;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String addresses) {
        Address = addresses;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    @Exclude
    public String getKey() {
        return mKey;
    }

    @Exclude
    public void setKey(String key) {
        mKey = key;
    }
}