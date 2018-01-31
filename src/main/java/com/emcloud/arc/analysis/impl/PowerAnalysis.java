package com.emcloud.arc.analysis.impl;


import com.emcloud.arc.analysis.analysis.DefaultAnalysisResult;
import com.emcloud.arc.analysis.analysis.DefaultOneParamAnalysis;

public class PowerAnalysis extends DefaultOneParamAnalysis {
    @Override
    public DefaultAnalysisResult handle(String powerStr) {
        DefaultAnalysisResult defaultAnalysis = new DefaultAnalysisResult();
        int power = Integer.parseInt(powerStr);
        int alarmLevel = 0;
        boolean alarm = true;
        if (power > 10 && power < 20) {
            alarmLevel = 1;
        } else if (power >= 20 && power < 30) {
            alarmLevel = 2;
        } else if (power >= 30) {
            alarmLevel = 3;
        }else{
            alarm = false;
        }
        defaultAnalysis.setAlarm( alarm );
        defaultAnalysis.setAlarmLevel(alarmLevel);
        return defaultAnalysis;
    }
}
