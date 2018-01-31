package com.emcloud.arc.analysis.impl;

import com.emcloud.arc.analysis.analysis.DefaultAnalysisResult;
import com.emcloud.arc.analysis.analysis.DefaultOneParamAnalysis;

import java.util.Map;

public class VoltageAnalysis  extends DefaultOneParamAnalysis {

    @Override
    public DefaultAnalysisResult handle(Float voltage) {
        DefaultAnalysisResult defaultAnalysis = new DefaultAnalysisResult();
        int alarmLevel = 0;
        boolean alarm = true;
        if (voltage > 200 && voltage < 400) {
            alarmLevel = 1;
        } else if (voltage >400  && voltage < 450) {
            alarmLevel = 2;
        } else if (voltage > 450) {
            alarmLevel = 3;
        }else{
            alarm = false;
        }
        defaultAnalysis.setAlarm( alarm );
        defaultAnalysis.setAlarmLevel(alarmLevel);
        return defaultAnalysis;
    }

    @Override
    public DefaultAnalysisResult handle(Map<String, Float> data) {
        return handle(data.get(""));
    }

}
