package com.emcloud.arc.analysis.impl;

import com.emcloud.arc.analysis.analysis.DefaultAnalysisResult;
import com.emcloud.arc.analysis.analysis.DefaultOneParamAnalysis;
import com.emcloud.arc.domain.RuleAttributes;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

public abstract class ReactivePowerAnalysis extends DefaultOneParamAnalysis {

    @Override
    public DefaultAnalysisResult handle(Float reactivePower, List<RuleAttributes> ruleAttributesList) {
        if(CollectionUtils.isEmpty( ruleAttributesList )){
            return defaultHandle(reactivePower);
        }else{
            return handleFromDB(reactivePower, ruleAttributesList);
        }
    }



    public DefaultAnalysisResult defaultHandle(Float reactivePower) {
        DefaultAnalysisResult defaultAnalysis = new DefaultAnalysisResult();
        int alarmLevel = 0;
        boolean alarm = false;
        if (reactivePower > 10 && reactivePower < 20) {
            alarmLevel = 1;  alarm = true;
        } else if (reactivePower >= 20 && reactivePower < 40) {
            alarmLevel = 2;  alarm = true;
        } else if (reactivePower >= 40) {
            alarmLevel=3;  alarm = true;
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
