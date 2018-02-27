package com.emcloud.arc.analysis.impl;

import com.emcloud.arc.analysis.analysis.DefaultAnalysisResult;
import com.emcloud.arc.analysis.analysis.DefaultOneParamAnalysis;
import com.emcloud.arc.domain.RuleAttributes;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

public abstract class Pm10Analysis extends DefaultOneParamAnalysis {
    @Override
    public DefaultAnalysisResult handle(Float pm10, List<RuleAttributes> ruleAttributesList) {
        if (CollectionUtils.isEmpty(ruleAttributesList)) {
            return defaultHandle(pm10);
        } else {
            return handleFromDB(pm10, ruleAttributesList);
        }
    }


    public DefaultAnalysisResult defaultHandle(Float pm10) {
        DefaultAnalysisResult defaultAnalysis = new DefaultAnalysisResult();
        int alarmLevel = 0;
        boolean alarm = false;
        if (pm10 >= 10 && pm10 < 35) {
            alarmLevel = 1;
            alarm = true;
        } else if (pm10 >= 35 && pm10 < 50) {
            alarmLevel = 2;
            alarm = true;
        } else if (pm10 >= 50) {
            alarmLevel = 3;  alarm = true;
        } else {
            alarm = false;
        }
        defaultAnalysis.setAlarm(alarm);
        defaultAnalysis.setAlarmLevel(alarmLevel);
        return defaultAnalysis;
    }

    @Override
    public DefaultAnalysisResult handle(Map<String, Float> data, List<RuleAttributes> ruleAttributesList) {
        return handle(data.get(getKey()), ruleAttributesList);

    }
}
