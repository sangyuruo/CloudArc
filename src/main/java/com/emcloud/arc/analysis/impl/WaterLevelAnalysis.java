package com.emcloud.arc.analysis.impl;

import com.emcloud.arc.analysis.analysis.DefaultAnalysisResult;
import com.emcloud.arc.analysis.analysis.DefaultOneParamAnalysis;
import com.emcloud.arc.domain.RuleAttributes;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

public abstract class WaterLevelAnalysis extends DefaultOneParamAnalysis {

    @Override
    public DefaultAnalysisResult handle(Float waterlev, List<RuleAttributes> ruleAttributesList) {
        if(CollectionUtils.isEmpty( ruleAttributesList )){
            return defaultHandle(waterlev);
        }else{
            return handleFromDB(waterlev, ruleAttributesList);
        }
    }



    public DefaultAnalysisResult defaultHandle(Float waterlev) {
        DefaultAnalysisResult defaultAnalysis = new DefaultAnalysisResult();
        int alarmLevel = 0;
        boolean alarm = false;
        if (waterlev > 5 && waterlev < 10) {
            alarmLevel = 1;  alarm = true;
        } else if (waterlev >= 10 && waterlev < 20) {
            alarmLevel = 2;  alarm = true;
        } else if (waterlev >= 20) {
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
