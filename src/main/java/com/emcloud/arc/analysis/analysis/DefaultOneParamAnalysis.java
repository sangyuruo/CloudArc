package com.emcloud.arc.analysis.analysis;
import com.emcloud.arc.domain.RuleAttributes;
import java.util.List;
public abstract class DefaultOneParamAnalysis implements  Analysis<DefaultAnalysisResult> {
    @Override
    public DefaultAnalysisResult handle(Float electricity, List<RuleAttributes> ruleAttributesList) {
        throw new RuntimeException("not handle string data");
    }
}
