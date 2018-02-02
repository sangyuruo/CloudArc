package com.emcloud.arc.analysis.impl;


import com.emcloud.arc.analysis.analysis.DefaultAnalysisResult;
import com.emcloud.arc.analysis.analysis.DefaultOneParamAnalysis;
import com.emcloud.arc.domain.RuleAttributes;

import java.util.List;
import java.util.Map;

public abstract class ElectricityAnalysis extends DefaultOneParamAnalysis {


    @Override
    public DefaultAnalysisResult handle(Float electricity, List<RuleAttributes> ruleAttributesList) {
        return null;

    }

    @Override
    public DefaultAnalysisResult handle(Map<String, Float> data, List<RuleAttributes> ruleAttributesList) {
        return handle(data.get( getKey() ), ruleAttributesList);
    }
}
