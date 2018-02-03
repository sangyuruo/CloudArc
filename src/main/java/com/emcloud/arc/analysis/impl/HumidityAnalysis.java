package com.emcloud.arc.analysis.impl;

import com.emcloud.arc.analysis.analysis.DefaultAnalysisResult;
import com.emcloud.arc.analysis.analysis.DefaultOneParamAnalysis;
import com.emcloud.arc.domain.RuleAttributes;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

public  abstract class HumidityAnalysis  extends DefaultOneParamAnalysis {

    @Override
    public DefaultAnalysisResult handle(Float hum, List<RuleAttributes> ruleAttributesList) {
        if(CollectionUtils.isEmpty( ruleAttributesList )){
            return defaultHandle(hum);
        }else{
            return handleFromDB(hum, ruleAttributesList);
        }
    }

    public DefaultAnalysisResult defaultHandle(Float hum) {
        DefaultAnalysisResult defaultAnalysis = new DefaultAnalysisResult();
        int alarmLevel = 0;
        boolean alarm = false;
        if (hum >= 30  && hum < 50) {
            alarmLevel = 1;
            alarm = true;
        } else if (hum >=50  && hum < 80) {
            alarmLevel = 2;
            alarm = true;
        } else if (hum >= 80) {
            alarmLevel = 3;
            alarm = true;
        }else{
            alarm = false;
        }
        defaultAnalysis.setAlarm( alarm );
        defaultAnalysis.setAlarmLevel(alarmLevel);
        return defaultAnalysis;
    }

    @Override
    public DefaultAnalysisResult handle(Map<String, Float> data, List<RuleAttributes> ruleAttributesList) {
        return handle(data.get(getKey()), ruleAttributesList);
    }

}
