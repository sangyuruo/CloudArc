package com.emcloud.arc.analysis.impl;

import com.emcloud.arc.analysis.DefaultAnalysisResult;
import com.emcloud.arc.analysis.DefaultOneParamAnalysis;

public class Pm10Analysis extends DefaultOneParamAnalysis {

    @Override
    public DefaultAnalysisResult handle(String pm10Str) {
        DefaultAnalysisResult defaultAnalysis = new DefaultAnalysisResult();
        int pm10 = Integer.parseInt(pm10Str);
        int alarmLevel = 0;
        boolean alarm = true;
        if (pm10 > 10 && pm10 < 20) {
            alarmLevel = 1;
        } else if (pm10 >= 20 && pm10 < 30) {
            alarmLevel = 2;
        } else if (pm10 >= 30) {
            alarmLevel = 3;
        }else{
            alarm = false;
        }
        defaultAnalysis.setAlarm( alarm );
        defaultAnalysis.setAlarmLevel(alarmLevel);
        return defaultAnalysis;
    }
}
