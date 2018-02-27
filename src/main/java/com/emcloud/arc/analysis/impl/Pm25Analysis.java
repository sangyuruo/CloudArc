package com.emcloud.arc.analysis.impl;

import com.emcloud.arc.analysis.analysis.DefaultAnalysisResult;
import com.emcloud.arc.analysis.analysis.DefaultOneParamAnalysis;
import com.emcloud.arc.domain.RuleAttributes;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

public abstract class Pm25Analysis extends DefaultOneParamAnalysis {
    @Override
    public DefaultAnalysisResult handle(Float pm25, List<RuleAttributes> ruleAttributesList) {
        if(CollectionUtils.isEmpty( ruleAttributesList )){
            return defaultHandle(pm25);
        }else{
            return handleFromDB(pm25, ruleAttributesList);
        }
    }



    public DefaultAnalysisResult defaultHandle(Float pm25) {
        DefaultAnalysisResult defaultAnalysis = new DefaultAnalysisResult();
        int alarmLevel = 0;
        boolean alarm = false;
        if (pm25 >=10 && pm25 < 30) {
            alarmLevel = 1;  alarm = true;
        } else if (pm25 >= 30 && pm25 < 50) {
            alarmLevel = 2;  alarm = true;
        } else if (pm25 >= 50) {
            alarmLevel = 3;  alarm = true;
        }else{
            alarm = false;
        }
        defaultAnalysis.setAlarm( alarm );
        defaultAnalysis.setAlarmLevel(alarmLevel);
        return defaultAnalysis;
    }

    @Override
    public DefaultAnalysisResult handle(Map<String, Float> data, List<RuleAttributes> ruleAttributesList) {
        return handle(data.get(getKey()),ruleAttributesList );

    }
}
