package com.emcloud.arc.analysis.analysis;


import com.emcloud.arc.domain.RuleAttributes;

import java.util.List;

public abstract class DefaultMutilParamAnalysis<P extends DefaultAnalysisResult> implements Analysis<P> {
    @Override
    public P handle(Float data, List<RuleAttributes> ruleAttributesList) {
        throw new RuntimeException("not handle string data");
    }
}
