package com.emcloud.arc.analysis.impl;

import com.emcloud.arc.analysis.analysis.DefaultAnalysisResult;
import com.emcloud.arc.analysis.analysis.DefaultOneParamAnalysis;


public class TemperatureAnalysis extends  DefaultOneParamAnalysis {
    @Override
    public DefaultAnalysisResult handle(String temStr) {
        DefaultAnalysisResult defaultAnalysis = new DefaultAnalysisResult();
        int tem = Integer.parseInt(temStr);
        int alarmLevel = 0;
        boolean alarm = true;
        if (tem > 10 && tem < 20) {
            alarmLevel = 1;
        } else if (tem >= 20 && tem < 40) {
            alarmLevel = 2;
        } else if (tem >= 40) {
            alarmLevel = 3;
        }else{
            alarm = false;
        }
        defaultAnalysis.setAlarm( alarm );
        defaultAnalysis.setAlarmLevel(alarmLevel);
        return defaultAnalysis;
    }
}
