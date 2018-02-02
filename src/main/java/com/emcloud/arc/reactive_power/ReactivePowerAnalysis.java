package com.emcloud.arc.reactive_power;

import com.emcloud.arc.analysis.analysis.DefaultAnalysisResult;
import com.emcloud.arc.analysis.analysis.DefaultOneParamAnalysis;
import com.emcloud.arc.domain.RuleAttributes;

import java.util.List;
import java.util.Map;

//无功
public class ReactivePowerAnalysis   extends DefaultOneParamAnalysis {
    public ReactivePowerAnalysis() {
        super(ruleAttributesRepository, meterRuleRepository);
    }

    @Override
    public DefaultAnalysisResult handle(Float reactivePower, List<RuleAttributes> ruleAttributesList) {
        DefaultAnalysisResult defaultAnalysis = new DefaultAnalysisResult();
        int alarmLevel = 0;
        boolean alarm = true;
        if (reactivePower > 10 && reactivePower < 20) {
            alarmLevel = 1;
        } else if (reactivePower >= 20 && reactivePower < 30) {
            alarmLevel = 2;
        } else if (reactivePower >= 30) {
            alarmLevel = 3;
        } else {
            alarm = false;
        }
        defaultAnalysis.setAlarm(alarm);
        defaultAnalysis.setAlarmLevel(alarmLevel);
        return defaultAnalysis;
    }

    @Override
    public DefaultAnalysisResult handle(Map<String, Float> data, List<RuleAttributes> ruleAttributesList) {
        return null;
    }
}
