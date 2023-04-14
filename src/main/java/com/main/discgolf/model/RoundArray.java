package com.main.discgolf.model;

import java.util.List;

public class RoundArray {

    private Long id;
    private List<Integer> roundArray;

    public RoundArray() {}

    public RoundArray(Long id, List<Integer> roundArray) {
        this.id = id;
        this.roundArray = roundArray;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Integer> getRoundArray() {
        return roundArray;
    }

    public void setRoundArray(List<Integer> roundArray) {
        this.roundArray = roundArray;
    }

    @Override
    public String toString() {
        return "RoundArray{" +
                "id=" + id +
                ", roundArrays=" + roundArray +
                '}';
    }
}
