package com.emcloud.arc.analysis.service;


import com.emcloud.arc.analysis.analysis.AnalysisFactory;
import com.emcloud.arc.analysis.analysis.DefaultAnalysisResult;
import com.emcloud.arc.analysis.analysis.DefaultOneParamAnalysis;
import com.emcloud.arc.domain.*;
import com.emcloud.arc.repository.AlarmRuleRepository;
import com.emcloud.arc.repository.MeterCategoryRuleRepository;
import com.emcloud.arc.repository.MeterRuleRepository;
import com.emcloud.arc.repository.RuleAttributesRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlarmService {


    private AnalysisFactory analysisFactory = new AnalysisFactory();
    private AlarmRuleRepository alarmRuleRepository;
    private RuleAttributesRepository ruleAttributesRepository;
    private MeterCategoryRuleRepository meterCategoryRuleRepository;
    private MeterRuleRepository meterRuleRepository;

    public AlarmService(AlarmRuleRepository alarmRuleRepository,
                        RuleAttributesRepository ruleAttributesRepository,
                        MeterCategoryRuleRepository meterCategoryRuleRepository,
                        MeterRuleRepository meterRuleRepository) {
        this.alarmRuleRepository = alarmRuleRepository;
        this.ruleAttributesRepository = ruleAttributesRepository;
        this.meterCategoryRuleRepository = meterCategoryRuleRepository;
        this.meterRuleRepository = meterRuleRepository;
    }


    /**
     * 1.查分析器列表(alarm_rule : )    Repository: 查分析器的方法；
     * <p>
     * 2.查设备分类 的 规则列表
     * 3.查设备 的
     * 4.合并规则列表
     * list3 = new list;
     * list3.add(list1)
     * for(设备分类 list2 ){1，2，4
     * boolean ismatch = false;
     * for(设备 list1 ){2，3
     * if(设备分类.解析器（编码）==设备){
     * ismatch = true;
     * }
     * }
     * if(!isMatch){
     * list3.add
     * }
     * }
     * 5.循环解析
     * for(规则列表){
     * 1.找到分析器
     * 2.查到紧急度属性列表:
     * 3.规则分析:  handle()  flaot ： startvalue ,endvalue
     * 4.输出结果
     * }
     * 6.输出总的分析结果
     */
    public List<DefaultAnalysisResult> analysis(SmartMeterData smartMeterData) {

        List<RuleDTO> rules = new ArrayList<>();

        List<MeterRule> meterRuleList = meterRuleRepository.findAll();
        List<MeterCategoryRule> meterCategoryRuleList = meterCategoryRuleRepository.findAll();


        //* 4.合并规则列表
        for (MeterRule meterRule : meterRuleList) {
            //转换

            RuleDTO ruleDTO = covertToRuleDTO(meterRule);
            rules.add(ruleDTO);
        }
        for (MeterCategoryRule meterCategoryRule : meterCategoryRuleList) {
            boolean ismatch = false;
            for (MeterRule meterRule : meterRuleList) {
                if (meterRule.getClassName().equals(meterCategoryRule.getClassName())) {
                    ismatch = true;
                    break;
                }
            }

            if (!ismatch) {
                //转换
                RuleDTO ruleDTO = covertToRuleDTO(meterCategoryRule);
                rules.add(ruleDTO);
            }
        }

        List<DefaultAnalysisResult> results = new ArrayList<>();
        for (RuleDTO ruleDTO : rules) {
            //从数据库获取警告级别设置属性
            List<RuleAttributes> attributes = getRuleAttributes(ruleDTO.getRuleCode());
            DefaultOneParamAnalysis analysis = analysisFactory.getAnalysis(ruleDTO.getClassName());
            DefaultAnalysisResult result = analysis.handle(smartMeterData.getData(), attributes);
            if (result.isAlarm()) {
                results.add(result);
            }
        }

        return results;
    }

    private List<RuleAttributes> getRuleAttributes(String ruleCode) {
        return ruleAttributesRepository.findByRuleCode(ruleCode);

    }

    private RuleDTO covertToRuleDTO(MeterRule meterRule) {
        RuleDTO ruleDTO=new RuleDTO();
        ruleDTO.setClassName(meterRule.getClassName());
        ruleDTO.setRuleCode(meterRule.getRuleCode());
        return ruleDTO;
    }

    private RuleDTO covertToRuleDTO(MeterCategoryRule meterCategoryRule) {
        RuleDTO ruleDTO =new RuleDTO();
        ruleDTO.setRuleCode(meterCategoryRule.getRuleCode());
        ruleDTO.setClassName(meterCategoryRule.getClassName());
        return ruleDTO;
    }




}
