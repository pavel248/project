package com.company;

import java.util.ArrayList;

public class Course {

    private String nameOfCourse;
    private ArrayList<Theme> listOfThemes;
    private Integer pointPerCourse;

    public Course(String nameOfCourse, ArrayList<Theme> listOfCourses, Integer pointPerCourse) {
        this.nameOfCourse = nameOfCourse;
        this.listOfThemes = listOfCourses;
        this.pointPerCourse = pointPerCourse;
    }


    public ArrayList<Theme> getListOfThemes() {
        return listOfThemes;
    }

    public Integer getPointPerCourse() {
        return pointPerCourse;
    }

    @Override
    public String toString() {
        return "Course{" +
                "nameOfCourse='" + nameOfCourse + '\'' +
                ", listOfThemes=" + listOfThemes.toString() +
                ", pointPerCourse=" + pointPerCourse +
                '}';
    }

    public String getNameOfCourse() {
        return nameOfCourse;
    }
}
