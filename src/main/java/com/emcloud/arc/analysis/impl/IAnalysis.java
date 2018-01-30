package com.emcloud.arc.analysis.impl;


import com.emcloud.arc.analysis.DefaultAnalysisResult;
import com.emcloud.arc.analysis.DefaultOneParamAnalysis;

public class IAnalysis  extends DefaultOneParamAnalysis {

    @Override
    public DefaultAnalysisResult handle(String electricityStr) {
        DefaultAnalysisResult defaultAnalysis = new DefaultAnalysisResult();
        int electricity = Integer.parseInt(electricityStr);
        int alarmLevel = 0;
        boolean alarm = true;
        if (electricity > 30 && electricity < 40) {
            alarmLevel = 1;
        } else if (electricity >40  && electricity < 50) {
            alarmLevel = 2;
        } else if (electricity > 50) {
            alarmLevel = 3;
        }else{
            alarm = false;
        }
        defaultAnalysis.setAlarm( alarm );
        defaultAnalysis.setAlarmLevel(alarmLevel);
        return defaultAnalysis;
    }
}
