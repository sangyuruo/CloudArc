package com.emcloud.arc.analysis.impl;

import com.emcloud.arc.analysis.analysis.DefaultAnalysisResult;
import com.emcloud.arc.analysis.analysis.DefaultOneParamAnalysis;

public class HumidityAnalysis extends DefaultOneParamAnalysis {
    @Override
    public DefaultAnalysisResult handle(String humStr) {
        DefaultAnalysisResult defaultAnalysis = new DefaultAnalysisResult();
        int hum = Integer.parseInt(humStr);
        int alarmLevel = 0;
        boolean alarm = true;
        if (hum > 30 && hum < 45) {
            alarmLevel = 1;
        } else if (hum >= 50 && hum < 60) {
            alarmLevel = 2;
        } else if (hum >= 60) {
            alarmLevel = 3;
        }else{
            alarm = false;
        }
        defaultAnalysis.setAlarm( alarm );
        defaultAnalysis.setAlarmLevel(alarmLevel);
        return defaultAnalysis;
    }
}
