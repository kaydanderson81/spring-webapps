package com.main.discgolf.model;

import java.util.List;

public class CourseByRound {

    private Long courseId;
    private String courseName;
    private int coursePar;
    private int courseRecord;
    private double courseAverage;
    private int timesPlayed;

    private List<Round> rounds;

    public CourseByRound(){}

    public CourseByRound(Long courseId, String courseName, int coursePar, int courseRecord, double courseAverage, int timesPlayed, List<Round> rounds) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.coursePar = coursePar;
        this.courseRecord = courseRecord;
        this.courseAverage = courseAverage;
        this.timesPlayed = timesPlayed;
        this.rounds = rounds;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCoursePar() {
        return coursePar;
    }

    public void setCoursePar(int coursePar) {
        this.coursePar = coursePar;
    }

    public int getCourseRecord() {
        return courseRecord;
    }

    public void setCourseRecord(int courseRecord) {
        this.courseRecord = courseRecord;
    }

    public double getCourseAverage() {
        return courseAverage;
    }

    public void setCourseAverage(double courseAverage) {
        this.courseAverage = courseAverage;
    }

    public int getTimesPlayed() {
        return timesPlayed;
    }

    public void setTimesPlayed(int timesPlayed) {
        this.timesPlayed = timesPlayed;
    }

    public List<Round> getRounds() {
        return rounds;
    }

    public void setRounds(List<Round> rounds) {
        this.rounds = rounds;
    }

    @Override
    public String toString() {
        return "CourseByRound{" +
                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", coursePar=" + coursePar +
                ", courseRecord=" + courseRecord +
                ", courseAverage=" + courseAverage +
                ", timesPlayed=" + timesPlayed +
                ", rounds=" + rounds +
                '}';
    }
}
