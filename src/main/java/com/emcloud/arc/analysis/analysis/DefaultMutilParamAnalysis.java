package com.emcloud.arc.analysis.analysis;


public abstract class DefaultMutilParamAnalysis<P extends DefaultAnalysisResult> implements Analysis<P> {
    @Override
    public P handle(Float data) {
        throw new RuntimeException("not handle string data");
    }
}
