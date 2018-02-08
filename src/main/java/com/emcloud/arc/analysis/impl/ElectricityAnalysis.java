package com.emcloud.arc.analysis.impl;


import com.emcloud.arc.analysis.analysis.DefaultAnalysisResult;
import com.emcloud.arc.analysis.analysis.DefaultOneParamAnalysis;
import com.emcloud.arc.domain.RuleAttributes;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

public abstract class ElectricityAnalysis extends DefaultOneParamAnalysis {


    @Override
    public DefaultAnalysisResult handle(Float electricity, List<RuleAttributes> ruleAttributesList) {
        if(CollectionUtils.isEmpty( ruleAttributesList )){
            return defaultHandle(electricity);
        }else{
            return handleFromDB(electricity, ruleAttributesList);
        }
    }



    public DefaultAnalysisResult defaultHandle(Float electricity) {
        DefaultAnalysisResult defaultAnalysis = new DefaultAnalysisResult();
        int alarmLevel = 0;
        boolean alarm = false;
        if (electricity >= 10 && electricity < 50) {
            alarmLevel = 1;
            alarm = true;
        } else if (electricity >= 50 && electricity < 100) {
            alarmLevel = 2;
            alarm = true;
        } else if (electricity >= 100) {
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
        return handle(data.get( getKey() ), ruleAttributesList);
    }
}
