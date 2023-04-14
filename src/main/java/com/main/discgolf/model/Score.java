package com.main.discgolf.model;

import javax.persistence.*;

@Entity
@Table(name = "score")
public class Score {

    @Id
    @Column(name = "score_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scoreId;

    @Column(name = "score")
    private int score;

    @Column(name = "hole_par")
    private int holePar;

    private String name;

    private String color;

    public Score() {}

    public Score(Long scoreId, int score, int holePar, String name, String color) {
        this.scoreId = scoreId;
        this.score = score;
        this.holePar = holePar;
        this.name = name;
        this.color = color;
    }

    public Long getScoreId() {
        return scoreId;
    }

    public void setScoreId(Long scoreId) {
        this.scoreId = scoreId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getHolePar() {
        return holePar;
    }

    public void setHolePar(int holePar) {
        this.holePar = holePar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Score{" +
                "scoreId=" + scoreId +
                ", score=" + score +
                ", holePar=" + holePar +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
