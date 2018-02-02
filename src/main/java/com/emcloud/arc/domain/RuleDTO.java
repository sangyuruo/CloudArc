package com.emcloud.arc.domain;


public class RuleDTO {
    public String getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * 规则名称
     */
    private String ruleCode; // ruleAttributes

    /**
     * 分析器名
     */
    private String className; // Analysis



}
