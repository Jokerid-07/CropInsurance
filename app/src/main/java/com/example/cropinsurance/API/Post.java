package com.example.cropinsurance.API;

import com.google.gson.annotations.SerializedName;

public class Post {
    @SerializedName("id")
    private String id;
    @SerializedName("Name")
    private String Name;
    @SerializedName("EmailId")
    private String Email;
    @SerializedName("Address")
    private String Address;
    @SerializedName("PhoneNumber")
    private String Phone;
    @SerializedName("Land")
    private String Land;
    @SerializedName("Soil")
    private String Soil;
    @SerializedName("Crop")
    private String Crop;
    @SerializedName("LoanAmount")
    private String LoanAmount;
    @SerializedName("InsuranceAmount")
    private String InsuranceAmount;
    @SerializedName("LoanStartDate")
    private String LoanStartDate;
    @SerializedName("LoanEndDate")
    private String LoanEndData;
    @SerializedName("LoanValidity")
    private String LoanValidity;

    public String getId() {
        return id;
    }

    public String getName() {
        return Name;
    }

    public String getEmail() {
        return Email;
    }

    public String getAddress() {
        return Address;
    }

    public String getPhone() {
        return Phone;
    }

    public String getLand() {
        return Land;
    }

    public String getSoil() {
        return Soil;
    }

    public String getCrop() {
        return Crop;
    }

    public String getLoanAmount() {
        return LoanAmount;
    }

    public String getInsuranceAmount() {
        return InsuranceAmount;
    }

    public String getLoanStartDate() {
        return LoanStartDate;
    }

    public String getLoanEndData() {
        return LoanEndData;
    }

    public String getLoanValidity() {
        return LoanValidity;
    }
}
