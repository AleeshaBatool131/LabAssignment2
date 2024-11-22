package com.example.dataentryform;

import java.io.File;

public class Person {
    private String name;
    private String fatherName;
    private String cnicNumber;
    private String gender;
    private String date;
    private String city;
    private File image;

    public Person(String name, String fatherName, String cnicNumber, String date, String gender, String city, File image) {
        this.name = name;
        this.fatherName = fatherName;
        this.cnicNumber = cnicNumber;
        this.date = date;
        this.gender = gender;
        this.city = city;
        this.image = image;
    }

    @Override
    public String toString() {
        return "Person{" +
                "Name='" + name + '\n' +
                ", Father Name='" + fatherName + '\n' +
                ", CNIC Number='" + cnicNumber + '\n' +
                ", Date of Birth='" + date + '\n' +
                ", Gender='" + gender + '\n' +
                ", City='" + city + '\n' +
                ", Image=" + image +
                '}';
    }
}
