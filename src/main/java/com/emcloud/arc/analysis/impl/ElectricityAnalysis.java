package com.emcloud.arc.analysis.impl;


import com.emcloud.arc.analysis.analysis.DefaultAnalysisResult;
import com.emcloud.arc.analysis.analysis.DefaultOneParamAnalysis;

public  class ElectricityAnalysis extends DefaultOneParamAnalysis {

    @Override
    public DefaultAnalysisResult handle(String electricityStr) {
        DefaultAnalysisResult defaultAnalysis = new DefaultAnalysisResult();
        int electricity = Integer.parseInt(electricityStr);
        int alarmLevel = 0;
        boolean alarm = true;
        if (electricity > 200 && electricity < 300) {
            alarmLevel = 1;
        } else if (electricity >300  && electricity < 400) {
            alarmLevel = 2;
        } else if (electricity > 400) {
            alarmLevel = 3;
        }else{
            alarm = false;
        }
        defaultAnalysis.setAlarm( alarm );
        defaultAnalysis.setAlarmLevel(alarmLevel);
        return defaultAnalysis;
    }
}
