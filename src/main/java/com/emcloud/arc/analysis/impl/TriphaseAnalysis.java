package com.emcloud.arc.analysis.impl;

import com.emcloud.arc.analysis.analysis.DefaultAnalysisResult;
import com.emcloud.arc.analysis.analysis.DefaultMutilParamAnalysis;
import com.emcloud.arc.domain.RuleAttributes;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

public abstract class TriphaseAnalysis extends DefaultMutilParamAnalysis {
    @Override
    public DefaultAnalysisResult handle(Map<String, Float> data, List<RuleAttributes> ruleAttributesList) {
        if (CollectionUtils.isEmpty(ruleAttributesList)) {
            return defaultHandle(data.get(calculate(data.get("I_a"), data.get("I_b"), data.get("I_c"))));
        } else {
            return handleFromDB(data.get(calculate(data.get("I_a"), data.get("I_b"), data.get("I_c"))), ruleAttributesList);
        }

    }

    public DefaultAnalysisResult defaultHandle(Float rate) {
        DefaultAnalysisResult defaultAnalysis = new DefaultAnalysisResult();
        int alarmLevel = 0;
        boolean alarm = false;
        if (rate >= 10 && rate < 20) {
            alarmLevel = 1;
            alarm = true;
        } else if (rate >= 20 && rate < 30) {
            alarmLevel = 2;
            alarm = true;
        } else if (rate >= 30) {
            alarmLevel = 3;
            alarm = true;
        }else{
            alarm = false;
        }
        defaultAnalysis.setAlarm(alarm);
        defaultAnalysis.setAlarmLevel(alarmLevel);
        return defaultAnalysis;
    }

    private Float calculate(Float a, Float b, Float c) {
        float[] unBalance = {a, b, c};
        float max = 0;
        float min = 0;
        float average = 0;
        float rate = 0;
        for (int i = 0; i < unBalance.length; i++) {

            min = unBalance[0];
            max = unBalance[0];

            if (max < unBalance[i]) {
                max = unBalance[i];
            }
            if (min > unBalance[i]) {
                min = unBalance[i];
            }

            average = average + unBalance[i];
        }

        return rate = (max - min) / (average / 3);
    }

}
