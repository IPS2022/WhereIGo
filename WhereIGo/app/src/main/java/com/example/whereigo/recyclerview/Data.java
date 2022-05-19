package com.example.whereigo.recyclerview;

public class Data {
    String disease;
    String doctorpart;

    public Data(String disease,String doctorpart) {
        this.disease = disease;
        this.doctorpart = doctorpart;
    }

    public String getDisease() {
        return disease;
    }

    public String getDoctorpart() {
        return doctorpart;
    }

    public void setDisease(String disease){
        this.disease=disease;
    }

    public void setDoctorpart(String doctorpart){
        this.doctorpart=doctorpart;
    }
}
