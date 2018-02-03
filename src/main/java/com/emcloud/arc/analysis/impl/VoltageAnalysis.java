package com.emcloud.arc.analysis.impl;

import com.emcloud.arc.analysis.analysis.DefaultAnalysisResult;
import com.emcloud.arc.analysis.analysis.DefaultOneParamAnalysis;
import com.emcloud.arc.domain.RuleAttributes;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

public  abstract class VoltageAnalysis  extends DefaultOneParamAnalysis {

    @Override
    public DefaultAnalysisResult handle(Float voltage, List<RuleAttributes> ruleAttributesList) {
        if(CollectionUtils.isEmpty( ruleAttributesList )){
            return defaultHandle(voltage);
        }else{
            return handleFromDB(voltage, ruleAttributesList);
        }
    }



    public DefaultAnalysisResult defaultHandle(Float voltage) {
        DefaultAnalysisResult defaultAnalysis = new DefaultAnalysisResult();
        int alarmLevel = 0;
        boolean alarm = false;
        if (voltage > 200 && voltage < 400) {
            alarmLevel = 1;  alarm = true;
        } else if (voltage >400  && voltage < 450) {
            alarmLevel = 2;  alarm = true;
        } else if (voltage > 450) {
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
        return handle(data.get(getKey()), ruleAttributesList);
    }

}
