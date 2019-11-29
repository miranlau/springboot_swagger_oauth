package com.tieto.springbootdemo.model;

public class AlarmHistory {
    private Long id;
    private Long alarmId;
    private String ts;
    private String action;
    private String details;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(Long alarmId) {
        this.alarmId = alarmId;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "AlarmHistory{" +
                "id=" + id +
                ", alarmId=" + alarmId +
                ", ts='" + ts + '\'' +
                ", action='" + action + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
