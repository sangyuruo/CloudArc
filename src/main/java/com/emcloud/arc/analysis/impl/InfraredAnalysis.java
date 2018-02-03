package com.emcloud.arc.analysis.impl;

import com.emcloud.arc.analysis.analysis.DefaultAnalysisResult;
import com.emcloud.arc.analysis.analysis.DefaultOneParamAnalysis;
import com.emcloud.arc.domain.RuleAttributes;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

public abstract class InfraredAnalysis extends DefaultOneParamAnalysis {

    @Override
    public DefaultAnalysisResult handle(Float infrared, List<RuleAttributes> ruleAttributesList) {
        if(CollectionUtils.isEmpty( ruleAttributesList )){
            return defaultHandle(infrared);
        }else{
            return handleFromDB(infrared, ruleAttributesList);
        }
    }



    public DefaultAnalysisResult defaultHandle(Float infrared ) {
        DefaultAnalysisResult defaultAnalysis = new DefaultAnalysisResult();
        int alarmLevel = 0;
        boolean alarm = false;
        if (infrared > 0 && infrared < 15) {
            alarmLevel = 1;
            alarm = true;
        } else if (infrared >= 15 && infrared < 40) {
            alarmLevel = 2;
            alarm = true;
        } else if (infrared >= 40) {
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
        return this.handle(data.get(getKey() ),ruleAttributesList );
    }
}
