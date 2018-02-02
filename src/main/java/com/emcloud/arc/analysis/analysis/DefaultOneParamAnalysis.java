package com.emcloud.arc.analysis.analysis;


import com.emcloud.arc.domain.MeterRule;
import com.emcloud.arc.domain.RuleAttributes;
import com.emcloud.arc.repository.MeterRuleRepository;
import com.emcloud.arc.repository.RuleAttributesRepository;

import java.util.List;

public abstract class DefaultOneParamAnalysis implements  Analysis<DefaultAnalysisResult> {

    private RuleAttributesRepository ruleAttributesRepository;
    private MeterRuleRepository meterRuleRepository;



    @Override
    public DefaultAnalysisResult handle(Float electricity, List<RuleAttributes> ruleAttributesList) {
        int alarmLevel = 0;
        boolean alarm = true;
        DefaultAnalysisResult defaultAnalysis = new DefaultAnalysisResult();
        List<MeterRule> meterRuleList = meterRuleRepository.findAll();
        ruleAttributesList = ruleAttributesRepository.findAll();


        String meterID = "";
        String type="";
        String message="";
        for (Object r : ruleAttributesList) {
            RuleAttributes ruleAttributes = (RuleAttributes) r;
            String   rulecode = ruleAttributes.getRuleCode();
            for (Object mr : meterRuleList) {
                MeterRule meterRule = (MeterRule) mr;
                String   mrulecode=meterRule.getRuleCode();
                if(rulecode.equals(mrulecode)){
                    meterID=   meterRule.getMeterCode();
                    message=   meterRule.getRuleName();
                    type=      meterRule.getMeterName();
                }
            }
            if (ruleAttributes.getStartValue() == null) {
                if (electricity < ruleAttributes.getEndValue()) {
                    alarmLevel = ruleAttributes.getAlarmLevel();
                } else {
                    alarm = false;
                }

            } else if (ruleAttributes.getEndValue() == null) {
                if (electricity > ruleAttributes.getStartValue()) {

                    alarmLevel = ruleAttributes.getAlarmLevel();
                } else {
                    alarm = false;
                }
            } else {
                if (electricity > ruleAttributes.getStartValue() && electricity < ruleAttributes.getEndValue()) {

                    alarmLevel = ruleAttributes.getAlarmLevel();
                } else {
                    alarm = false;
                }
            }

        }
        defaultAnalysis.setAlarm(alarm);
        defaultAnalysis.setAlarmLevel(alarmLevel);
        defaultAnalysis.setMessage(message);
        defaultAnalysis.setType(type);
        defaultAnalysis.setMeterId(meterID);


        return defaultAnalysis;
    }
}
