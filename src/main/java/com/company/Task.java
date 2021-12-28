package com.company;

public class Task {
    private String nameOfTask;
    private Integer score;
    private Integer maxScore;


    public Task(String nameOfTask, Integer score, Integer maxScore) {
        this.nameOfTask = nameOfTask;
        this.score = score;
        this.maxScore = maxScore;
    }

    public String getNameOfTask() {
        return nameOfTask;
    }

    public Integer getScore() {
        return score;
    }

    public Integer getMaxScore() {
        return maxScore;
    }

    @Override
    public String toString() {
        return "Task{" +
                "nameOfTask='" + nameOfTask + '\'' +
                ", score=" + score +
                ", maxScore=" + maxScore +
                '}';
    }
}
