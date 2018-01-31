package com.emcloud.arc.reactive_power;

import com.emcloud.arc.analysis.analysis.DefaultOneParamAnalysis;

//无功
public class ReactivePowerAnalysis   extends DefaultOneParamAnalysis {
    @Override
    public DefaultAnalysisResult handle(String reactivePowerStr) {
        DefaultAnalysisResult defaultAnalysis = new DefaultAnalysisResult();
        int reactivePower = Integer.parseInt(reactivePowerStr);
        int alarmLevel = 0;
        boolean alarm = true;
        if (reactivePower > 10 && reactivePower < 20) {
            alarmLevel = 1;
        } else if (reactivePower >= 20 && reactivePower < 30) {
            alarmLevel = 2;
        } else if (reactivePower >= 30) {
            alarmLevel = 3;
        } else {
            alarm = false;
        }
        defaultAnalysis.setAlarm(alarm);
        defaultAnalysis.setAlarmLevel(alarmLevel);
        return defaultAnalysis;
    }
}
