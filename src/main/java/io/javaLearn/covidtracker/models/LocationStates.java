package io.javaLearn.covidtracker.models;

import java.util.StringJoiner;

public class LocationStates {

    private String state;
    private String country;
    private Integer latesCases;
    private Integer diffFromPrevDay;

    public Integer getDiffFromPrevDay() {
        return diffFromPrevDay;
    }

    public void setDiffFromPrevDay(Integer diffFromPrevDay) {
        this.diffFromPrevDay = diffFromPrevDay;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getLatesCases() {
        return latesCases;
    }

    public void setLatesCases(Integer latesCases) {
        this.latesCases = latesCases;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", LocationStates.class.getSimpleName() + "[", "]")
                .add("state='" + state + "'")
                .add("country='" + country + "'")
                .add("latesCases='" + latesCases + "'")
                .add("diffFromPrevDay='" + diffFromPrevDay + "'")
                .toString();
    }
}
