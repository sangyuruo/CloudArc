package com.emcloud.arc.analysis.impl;

import com.emcloud.arc.analysis.DefaultAnalysisResult;
import com.emcloud.arc.analysis.DefaultOneParamAnalysis;

public class RedAnalysis   extends DefaultOneParamAnalysis {
    @Override
    public DefaultAnalysisResult handle(String infraredStr) {
        DefaultAnalysisResult defaultAnalysis = new DefaultAnalysisResult();
        int infrared = Integer.parseInt(infraredStr);
        int alarmLevel = 0;
        boolean alarm = true;
        if (infrared > 10 && infrared < 20) {
            alarmLevel = 1;
        } else if (infrared >= 20 && infrared < 30) {
            alarmLevel = 2;
        } else if (infrared >= 30) {
            alarmLevel = 3;
        }else{
            alarm = false;
        }
        defaultAnalysis.setAlarm( alarm );
        defaultAnalysis.setAlarmLevel(alarmLevel);
        return defaultAnalysis;
    }
}
