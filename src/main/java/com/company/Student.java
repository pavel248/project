package com.company;

import java.util.ArrayList;

public class Student extends Person {
    private String group;
    private ArrayList<Course> listOfCourses = new ArrayList<Course>();

    public Student(String fio, String group, Course course) {
        super(fio);
        this.group = group;
        this.listOfCourses.add(course);
    }


    public String getGroup() {
        return group;
    }


    public ArrayList<Course> getListOfCourses() {
        return listOfCourses;
    }

    @Override
    public String toString() {
        return "Student{" +
                "Fio = " + this.getFio() + " " +
                "City = " + this.getCity() + " " +
                "Sex = " + this.getSex() + " " +
                "BDate = " + this.getDateOfBirth() + " " +
                "group='" + group + '\'' +
                ", listOfCourses=" + listOfCourses.toString() +
                '}';
    }
}

