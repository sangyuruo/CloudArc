package com.emcloud.arc.domain;


import java.security.MessageDigest;

public class RuleDTO {

    public String getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    /**
     * 规则名称
     */
    private String ruleCode; // ruleAttributes

    /**
     * 分析器名
     */
    private String analysis; // Analysis


    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    private String message;
    private String meterId;



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMeterId() {
        return meterId;
    }

    public void setMeterId(String meterId) {
        this.meterId = meterId;
    }
}
