package com.buckleup.gpstracker;

/**
 * Created by root on 10/1/18.
 */

public class Information {
    String email;
    String name;
    String phonenumber;
    String otp;
    Long otpvalidity;
    public Information(){

    }



    public Information(String email, String name, String phonenumber, String otp, Long otpvalidity){
        this.email = email;
        this.name = name;
        this.phonenumber = phonenumber;
        this.otp = otp;
        this.otpvalidity = otpvalidity;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhonenumber(String number) {
        this.phonenumber = number;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOtpvalidity(Long otpvalidity) {
        this.otpvalidity = otpvalidity;
    }

    public Long getOtpvalidity() {
        return otpvalidity;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public String getOtp() {
        return otp;
    }


}

