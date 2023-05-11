package com.main.discgolf.model;

import java.util.List;

public class UserInfo {

    private Long userId;
    private String name;

    private int numberCoursesPlayed;
    private int roundsPlayed;
    private int holes;
    private double playerAverage;
    private int aces;

    private List<Round> bestRound;

    private List<Score> scoreList;

    public UserInfo(){}

    public UserInfo(Long userId, String name, int numberCoursesPlayed, int roundsPlayed, int holes, double playerAverage, int aces,
                    List<Round> bestRound, List<Score> scoreList) {
        this.userId = userId;
        this.name = name;
        this.numberCoursesPlayed = numberCoursesPlayed;
        this.roundsPlayed = roundsPlayed;
        this.holes = holes;
        this.playerAverage = playerAverage;
        this.aces = aces;
        this.bestRound = bestRound;
        this.scoreList = scoreList;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberCoursesPlayed() {
        return numberCoursesPlayed;
    }

    public void setNumberCoursesPlayed(int numberCoursesPlayed) {
        this.numberCoursesPlayed = numberCoursesPlayed;
    }

    public int getRoundsPlayed() {
        return roundsPlayed;
    }

    public void setRoundsPlayed(int roundsPlayed) {
        this.roundsPlayed = roundsPlayed;
    }

    public int getHoles() {
        return holes;
    }

    public void setHoles(int holes) {
        this.holes = holes;
    }

    public double getPlayerAverage() {
        return playerAverage;
    }

    public void setPlayerAverage(double playerAverage) {
        this.playerAverage = playerAverage;
    }

    public int getAces() {
        return aces;
    }

    public void setAces(int aces) {
        this.aces = aces;
    }

    public List<Round> getBestRound() {
        return bestRound;
    }

    public void setBestRound(List<Round> bestRound) {
        this.bestRound = bestRound;
    }

    public List<Score> getScoreList() {
        return scoreList;
    }

    public void setScoreList(List<Score> scoreList) {
        this.scoreList = scoreList;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", roundsPlayed=" + roundsPlayed +
                ", holes=" + holes +
                ", playerAverage=" + playerAverage +
                ", aces=" + aces +
                ", bestRound=" + bestRound +
                ", scoreList=" + scoreList +
                '}';
    }
}
