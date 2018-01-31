package com.emcloud.arc.analysis.impl;

import com.emcloud.arc.analysis.analysis.DefaultAnalysisResult;
import com.emcloud.arc.analysis.analysis.DefaultOneParamAnalysis;

import java.util.Map;

public class InfraredAnalysis extends DefaultOneParamAnalysis {
    static final String infraredkey = "";
    @Override
    public DefaultAnalysisResult handle(Float infrared) {
        DefaultAnalysisResult defaultAnalysis = new DefaultAnalysisResult();
        int alarmLevel = 0;
        boolean alarm = true;
        if (infrared > 0 && infrared < 15) {
            alarmLevel = 1;
        } else if (infrared >= 15 && infrared < 40) {
            alarmLevel = 2;
        } else if (infrared >= 40) {
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
        return this.handle(data.get( infraredkey ));
    }
}
