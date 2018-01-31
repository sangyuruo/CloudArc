package com.emcloud.arc.analysis.analysis;

public class DefaultAnalysisResult {
    boolean alarm;
    // 0 : 不报警， 1： 一般 ， 2：严重 ， 3： 紧急
    int alarmLevel;

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
}
