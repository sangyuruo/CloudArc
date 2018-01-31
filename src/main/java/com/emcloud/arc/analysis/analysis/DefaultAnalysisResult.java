package com.emcloud.arc.analysis.analysis;

public class DefaultAnalysisResult {
 private boolean alarm;
   // 0 : 不报警， 1： 一般 ， 2：严重 ， 3： 紧急
 private int alarmLevel;
 private String message;
 private String type;
 private String meterId;
    public boolean isAlarm() {
        return alarm;
    }

    public void setAlarm(boolean alarm) {
        this.alarm = alarm;
    }

    public int getAlarmLevel() {
        return alarmLevel;
    }

    public void setAlarmLevel(int alarmLevel) {
        this.alarmLevel = alarmLevel;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMeterId() {
        return meterId;
    }

    public void setMeterId(String meterId) {
        this.meterId = meterId;
    }
}
