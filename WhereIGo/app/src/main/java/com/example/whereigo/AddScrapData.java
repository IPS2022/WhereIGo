package com.example.whereigo;

public class AddScrapData {
    String name;
    String hospital;

    public AddScrapData(String name, String hospital){
        this.name = name;
        this.hospital = hospital;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }
}
