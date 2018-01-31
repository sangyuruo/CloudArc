package com.emcloud.arc.analysis.impl;

import com.emcloud.arc.analysis.analysis.DefaultAnalysisResult;
import com.emcloud.arc.analysis.analysis.DefaultOneParamAnalysis;

import java.util.Map;

public class ReactivePowerAnalysis extends DefaultOneParamAnalysis {
    @Override
    public DefaultAnalysisResult handle(Float tem) {
        DefaultAnalysisResult defaultAnalysis = new DefaultAnalysisResult();
        int alarmLevel = 0;
        boolean alarm = true;
        if (tem > 10 && tem < 20) {
            alarmLevel = 1;
        } else if (tem >= 20 && tem < 40) {
            alarmLevel = 2;
        } else if (tem >= 40) {
        } else {
            alarm = false;
        }
        defaultAnalysis.setAlarm(alarm);
        defaultAnalysis.setAlarmLevel(alarmLevel);
        return defaultAnalysis;
    }


    @Override
    public DefaultAnalysisResult handle(Map<String, Float> data) {
        return handle(data.get(""));

    }

}
