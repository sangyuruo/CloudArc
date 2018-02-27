package com.emcloud.arc.analysis.analysis;


import com.emcloud.arc.domain.RuleAttributes;
import org.springframework.security.access.method.P;

import java.util.List;

public abstract class DefaultMutilParamAnalysis  implements Analysis<DefaultAnalysisResult> {
    @Override
    public DefaultAnalysisResult handle(Float data, List<RuleAttributes> ruleAttributesList) {
        throw new RuntimeException("not handle string data");
    }

    public DefaultAnalysisResult handleFromDB(Float electricity, List<RuleAttributes> ruleAttributesList) {
        int alarmLevel = 0;
        boolean alarm = false;

        Float startValue;
        Float endValue;

        DefaultAnalysisResult defaultAnalysis = new DefaultAnalysisResult();
        for (RuleAttributes ruleAttributes : ruleAttributesList) {

            startValue = ruleAttributes.getStartValue();
            endValue = ruleAttributes.getEndValue();
            if (null != startValue && null != endValue) {
                if (isBetween(electricity, startValue, endValue)) {
                    alarmLevel = ruleAttributes.getAlarmLevel();
                    break;
                }
            } else if (null != startValue) {
                if (isLessThan(electricity, startValue)) {
                    alarmLevel = ruleAttributes.getAlarmLevel();
                    break;
                }
            } else if (null != endValue) {
                if (isMoreThan(electricity, endValue)) {
                    alarmLevel = ruleAttributes.getAlarmLevel();
                    break;
                }
            } else {
                throw new RuntimeException("both start and end value is null");
            }
        }

        if (alarmLevel > 0) {
            alarm = true;
        }
        defaultAnalysis.setAlarm(alarm);
        defaultAnalysis.setAlarmLevel(alarmLevel);
        return defaultAnalysis;
    }
    private boolean isBetween(Float electricity, Float startValue, Float endValue) {
        if (electricity >= startValue && electricity <= endValue) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isLessThan(Float electricity, Float startValue) {
        if (electricity < startValue) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isMoreThan(Float electricity, Float endValue) {
        if (electricity > endValue) {
            return true;
        } else {
            return false;
        }
    }


}
