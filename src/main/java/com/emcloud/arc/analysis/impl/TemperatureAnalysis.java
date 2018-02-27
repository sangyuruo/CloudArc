package com.emcloud.arc.analysis.impl;

import com.emcloud.arc.analysis.analysis.DefaultAnalysisResult;
import com.emcloud.arc.analysis.analysis.DefaultOneParamAnalysis;
import com.emcloud.arc.domain.RuleAttributes;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;


public abstract class TemperatureAnalysis extends  DefaultOneParamAnalysis {

    @Override
    public DefaultAnalysisResult handle(Float tem, List<RuleAttributes> ruleAttributesList) {
        if(CollectionUtils.isEmpty( ruleAttributesList )){
            return defaultHandle(tem);
        }else{
            return handleFromDB(tem, ruleAttributesList);
        }
    }



    public DefaultAnalysisResult defaultHandle(Float tem) {
        DefaultAnalysisResult defaultAnalysis = new DefaultAnalysisResult();
        int alarmLevel = 0;
        boolean alarm = false;
        if (tem > 20.5 && tem < 30.5) {
            alarmLevel = 1;  alarm = true;
        } else if (tem >= 30.5 && tem < 50.5) {
            alarmLevel = 2;  alarm = true;
        } else if (tem >= 50.5) {
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
