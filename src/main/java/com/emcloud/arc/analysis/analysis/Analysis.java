package com.emcloud.arc.analysis.analysis;


import com.emcloud.arc.domain.RuleAttributes;

import java.util.List;
import java.util.Map;

public interface Analysis<T> {
    String getKey();
    T handle(Float data, List<RuleAttributes> ruleAttributesList);
    T handle(Map<String, Float> data, List<RuleAttributes> ruleAttributesList);
}
