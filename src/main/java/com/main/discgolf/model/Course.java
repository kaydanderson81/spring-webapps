package com.main.discgolf.model;

import lombok.Builder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "course")
@Builder
public class Course {
    @Id
    @Column(name = "course_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "course_id", referencedColumnName = "course_id")
    private List<Hole> holes = new ArrayList<>();

    @Column(name = "course_par", nullable = false)
    private int par;

    @Column(name = "record", nullable = false)
    private int record;

    @Column(name = "course_average", nullable = false)
    private double courseAverage;

    public Course() {
    }

    public Course(Long id, String name, List<Hole> holes, int par, int record, double courseAverage) {
        this.id = id;
        this.name = name;
        this.holes = holes;
        this.par = par;
        this.record = record;
        this.courseAverage = courseAverage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addHolesToCourse(Hole hole) {
        this.holes.add(hole);
    }

    public List<Hole> getHoles() {
        return holes;
    }

    public void setHoles(List<Hole> holes) {
        this.holes = holes;
    }

    public int getPar() {
        return par;
    }

    public void setPar(int par) {
        this.par = par;
    }

    public int getRecord() {
        return record;
    }

    public void setRecord(int record) {
        this.record = record;
    }

    public double getCourseAverage() {
        return courseAverage;
    }

    public void setCourseAverage(double courseAverage) {
        this.courseAverage = courseAverage;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", holes=" + holes +
                ", par=" + par +
                ", record=" + record +
                ", courseAverage=" + courseAverage +
                '}';
    }
}
