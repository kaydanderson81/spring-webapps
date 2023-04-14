package com.main.discgolf.model;

import javax.persistence.*;

@Entity
@Table(name = "hole")
public class Hole {

    @Id
    @Column(name = "hole_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long holeId;

    @Column(name = "name")
    private int number;

    @Column(name = "par")
    private int par;

    public Hole() {}

    public Hole(Long holeId, int number, int par) {
        this.holeId = holeId;
        this.number = number;
        this.par = par;
    }

    public Long getHoleId() {
        return holeId;
    }

    public void setHoleId(Long holeId) {
        this.holeId = holeId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getPar() {
        return par;
    }

    public void setPar(int par) {
        this.par = par;
    }

    @Override
    public String toString() {
        return "Hole{" +
                "holeId=" + holeId +
                ", number=" + number +
                ", par=" + par +
                '}';
    }
}
