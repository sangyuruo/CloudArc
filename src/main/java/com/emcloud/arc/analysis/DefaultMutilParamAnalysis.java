package com.emcloud.arc.analysis;


public abstract class DefaultMutilParamAnalysis<P extends DefaultAnalysisResult> implements Analysis<P> {
    @Override
    public P handle(String data) {
        throw new RuntimeException("not handle string data");
    }
}
