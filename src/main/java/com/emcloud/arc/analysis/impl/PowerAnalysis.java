package com.emcloud.arc.analysis.impl;


import com.emcloud.arc.analysis.analysis.DefaultAnalysisResult;
import com.emcloud.arc.analysis.analysis.DefaultOneParamAnalysis;

import java.util.Map;

public class PowerAnalysis extends DefaultOneParamAnalysis {
    @Override
    public DefaultAnalysisResult handle(Float power) {
        DefaultAnalysisResult defaultAnalysis = new DefaultAnalysisResult();

        int alarmLevel = 0;
        boolean alarm = true;
        if (power > 1000 && power < 2000) {
            alarmLevel = 1;
        } else if (power >= 2000 && power < 3000) {
            alarmLevel = 2;
        } else if (power >= 3000) {
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
