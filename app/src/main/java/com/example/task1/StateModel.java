package com.example.task1;

public class StateModel {
    private String state , confirmedCases , confirmedCasesForeign ,discharged ,deaths,totalConfirmed;

    public StateModel() {

    }

    public StateModel(String state, String confirmedCases, String confirmedCasesForeign, String discharged, String deaths, String totalConfirmed) {
        this.state = state;
        this.confirmedCases = confirmedCases;
        this.confirmedCasesForeign = confirmedCasesForeign;
        this.discharged = discharged;
        this.deaths = deaths;
        this.totalConfirmed = totalConfirmed;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getConfirmedCases() {
        return confirmedCases;
    }

    public void setConfirmedCases(String confirmedCases) {
        this.confirmedCases = confirmedCases;
    }

    public String getConfirmedCasesForeign() {
        return confirmedCasesForeign;
    }

    public void setConfirmedCasesForeign(String confirmedCasesForeign) {
        this.confirmedCasesForeign = confirmedCasesForeign;
    }

    public String getDischarged() {
        return discharged;
    }

    public void setDischarged(String discharged) {
        this.discharged = discharged;
    }

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }

    public String getTotalConfirmed() {
        return totalConfirmed;
    }

    public void setTotalConfirmed(String totalConfirmed) {
        this.totalConfirmed = totalConfirmed;
    }
}
