package com.company;

import java.util.ArrayList;

public class Theme {
    private String Name;
    private ArrayList<Task> listOfTasks;
    private Integer Score;

    public Theme(String name, ArrayList<Task> listOfTasks, Integer score) {
        Name = name;
        this.listOfTasks = listOfTasks;
        this.Score = score;
    }

    public String getName() {
        return Name;
    }

    public Integer getScore() {
        return Score;
    }

    public ArrayList<Task> getListOfTasks() {
        return listOfTasks;
    }

    @Override
    public String toString() {
        return "Theme{" +
                "Name='" + Name + '\'' +
                ", listOfTasks=" + listOfTasks.toString() +
                '}';
    }
}