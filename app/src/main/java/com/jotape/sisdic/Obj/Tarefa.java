package com.jotape.sisdic.Obj;

import java.util.Calendar;
import java.util.Map;
import java.util.Objects;

/**
 * Created by João Bittencourt on 12/04/2017.
 */

public class Tarefa {

    private String description;
    private String date;
    private String person;
    private String status;
    private boolean isCompleted = false;
    private Map<String,Object> obs;



    public Tarefa(String des, String date, String per){
        this.description =  des;
        this.date  = date;
        this.person = per;
        this.status = "Sem responsável";

    }
    Tarefa(){}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isCompleted() {
        return isCompleted;
    }


    public Map<String, Object> getObs() {
        return obs;
    }

    public void setObs(Map<String, Object> obs) {
        this.obs = obs;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
