package com.emcloud.arc.analysis.analysis;


import java.util.Map;

public abstract class DefaultOneParamAnalysis implements  Analysis<DefaultAnalysisResult> {
    @Override
    public DefaultAnalysisResult handle(Map<String, Object> data) {
        throw new RuntimeException("not handle map data");
    }
}
