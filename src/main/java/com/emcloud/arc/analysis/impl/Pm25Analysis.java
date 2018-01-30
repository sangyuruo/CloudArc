package com.emcloud.arc.analysis.impl;

import com.emcloud.arc.analysis.DefaultAnalysisResult;
import com.emcloud.arc.analysis.DefaultOneParamAnalysis;

public class Pm25Analysis extends DefaultOneParamAnalysis {

    @Override
    public DefaultAnalysisResult handle(String pm25Str) {
        DefaultAnalysisResult defaultAnalysis = new DefaultAnalysisResult();
        int pm25 = Integer.parseInt(pm25Str);
        int alarmLevel = 0;
        boolean alarm = true;
        if (pm25 > 10 && pm25 < 20) {
            alarmLevel = 1;
        } else if (pm25 > 20 && pm25 < 30) {
            alarmLevel = 2;
        } else if (pm25 > 30) {
            alarmLevel = 3;
        }else{
            alarm = false;
        }
        defaultAnalysis.setAlarm( alarm );
        defaultAnalysis.setAlarmLevel(alarmLevel);
        return defaultAnalysis;
    }
}
