package com.main.discgolf.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "round")
public class Round {

    @Id
    @Column(name = "round_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roundId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "round_id", referencedColumnName = "round_id")
    private List<Score> scores = new ArrayList<>();

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Column(name = "round_date")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date roundDate;

    @Column(name = "round_total")
    private int total;

    @Column(name = "played_alone", nullable = false)
    private boolean playedAlone = false;

    @Transient
    private List<Integer> barChartArray;

    @Transient
    private int timesPlayed;

    public Round() {}

    public Round(Long roundId, Course course, List<Score> scores, Date roundDate, int total) {
        this.roundId = roundId;
        this.course = course;
        this.scores = scores;
        this.roundDate = roundDate;
        this.total = total;
    }

    public Round(Long roundId, Course course, List<Score> scores, Date roundDate, int total,
                 boolean playedAlone, List<Integer> barChartArray, int timesPlayed) {
        this.roundId = roundId;
        this.course = course;
        this.scores = scores;
        this.roundDate = roundDate;
        this.total = total;
        this.playedAlone = playedAlone;
        this.barChartArray = barChartArray;
        this.timesPlayed = timesPlayed;
    }

    public Long getRoundId() {
        return roundId;
    }

    public void setRoundId(Long roundId) {
        this.roundId = roundId;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<Score> getScores() {
        return scores;
    }

    public void setScores(List<Score> scores) {
        this.scores = scores;
    }

    public Date getRoundDate() {
        return roundDate;
    }

    public void setRoundDate(Date roundDate) {
        this.roundDate = roundDate;
    }

    public LocalDate getDateWithoutTimeOfDay() {
        return roundDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public void addScoreToRound(Score score) {
        this.scores.add(score);
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public boolean isPlayedAlone() {
        return playedAlone;
    }

    public void setPlayedAlone(boolean playedAlone) {
        this.playedAlone = playedAlone;
    }

    public List<Integer> getBarChartArray() {
        return barChartArray;
    }

    public void setBarChartArray(List<Integer> barChartArray) {
        this.barChartArray = barChartArray;
    }

    public int getTimesPlayed() {
        return timesPlayed;
    }

    public void setTimesPlayed(int timesPlayed) {
        this.timesPlayed = timesPlayed;
    }

    @Override
    public String toString() {
        return "Round{" +
                "roundId=" + roundId +
                ", course=" + course +
                ", scores=" + scores +
                ", roundDate=" + roundDate +
                ", total=" + total +
                ", playedAlone=" + playedAlone +
                ", barChartArray=" + barChartArray +
                ", timesPlayed=" + timesPlayed +
                '}';
    }
}
