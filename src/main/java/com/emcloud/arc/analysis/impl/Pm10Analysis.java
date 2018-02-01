package com.emcloud.arc.analysis.impl;

import com.emcloud.arc.analysis.analysis.DefaultAnalysisResult;
import com.emcloud.arc.analysis.analysis.DefaultOneParamAnalysis;
import com.emcloud.arc.domain.RuleAttributes;

import java.util.List;
import java.util.Map;

public class Pm10Analysis extends DefaultOneParamAnalysis {

    @Override
    public DefaultAnalysisResult handle(Float pm10, List<RuleAttributes> ruleAttributesList) {
        DefaultAnalysisResult defaultAnalysis = new DefaultAnalysisResult();
        int alarmLevel = 0;
        boolean alarm = true;
        if (pm10 > 10 && pm10 < 20) {
            alarmLevel = 1;
        } else if (pm10 >= 20 && pm10 < 30) {
            alarmLevel = 2;
        } else if (pm10 >= 30) {
            alarmLevel = 3;
        }else{
            alarm = false;
        }
        defaultAnalysis.setAlarm( alarm );
        defaultAnalysis.setAlarmLevel(alarmLevel);
        return defaultAnalysis;
    }

    @Override
    public DefaultAnalysisResult handle(Map<String, Float> data, List<RuleAttributes> ruleAttributesList) {
        return handle(data.get(""),ruleAttributesList );

    }
}
