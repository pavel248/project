package com.company;

import java.util.Calendar;

public class Person {
    private String fio;
    private String city = "Not mentioned";
    private String dateOfBirth = "Not mentioned";
    private String sex;


    public Person(String fio) {
        this.fio = fio;
    }

    public String getFio() {
        return fio;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}