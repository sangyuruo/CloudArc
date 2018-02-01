package com.emcloud.arc.analysis.service;


import com.emcloud.arc.analysis.analysis.DefaultAnalysisResult;
import com.emcloud.arc.analysis.analysis.DefaultOneParamAnalysis;
import com.emcloud.arc.analysis.impl.*;
import com.emcloud.arc.domain.AlarmRule;
import com.emcloud.arc.domain.SmartMeterData;
import com.emcloud.arc.repository.AlarmRuleRepository;
import com.emcloud.arc.repository.MeterCategoryRuleRepository;
import com.emcloud.arc.repository.MeterRuleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AlarmService {
    InfraredAnalysis infraredAnalysis = new InfraredAnalysis();
    ElectricityAnalysis electricityAnalysis = new ElectricityAnalysis();
    HumidityAnalysis humidityAnalysis = new HumidityAnalysis();
    Pm10Analysis pm10Analysis = new Pm10Analysis();
    Pm25Analysis pm25Analysis = new Pm25Analysis();
    PowerAnalysis powerAnalysis = new PowerAnalysis();
    ReactivePowerAnalysis reactivePowerAnalysis = new ReactivePowerAnalysis();
    TemperatureAnalysis temperatureAnalysis = new TemperatureAnalysis();
    VoltageAnalysis voltageAnalysis = new VoltageAnalysis();
    WaterLevelAnalysis waterLevelAnalysis = new WaterLevelAnalysis();
    WaterOutAnalysis waterOutAnalysis = new WaterOutAnalysis();

    private AlarmRuleRepository alarmRuleRepository;
    private MeterCategoryRuleRepository meterCategoryRuleRepository;
    private MeterRuleRepository meterRuleRepository;

/**
 * 1.查分析器列表(alarm_rule : )    Repository: 查分析器的方法；
 *
 * 2.查设备分类 的 规则列表
 * 3.查设备 的 规则列表
 * 4.合并规则列表
 * list3 = new list;
 * list3.add(list1)
 * for(设备分类 list2 ){1，2，4
 *     boolean ismatch = false;
 *     for(设备 list1 ){2，3
 *         if(设备分类.解析器（编码）==设备){
 *             ismatch = true;
 *         }
 *     }
 *
 *     if(!isMatch){
 *         list3.add
 *     }
 * }
 * 5.循环解析
 * for(规则列表){
 *     1.找到分析器
 *     2.查到紧急度属性列表
 *     3.规则分析
 *     4.输出结果
 * }
 * 6.输出总的分析结果
 * */
    public List<DefaultAnalysisResult> analysis(SmartMeterData smartMeterData) {
        List<DefaultAnalysisResult> list = new ArrayList<>();
        List<DefaultOneParamAnalysis> analyses = new ArrayList<>();

        if (smartMeterData.getCategory() == 45) {//45	建大仁科红外探测
            analyses.add(infraredAnalysis);
        }else  if (smartMeterData.getCategory() == 44) {//44	建大仁科水浸检测
            analyses.add(waterOutAnalysis);
        }else  if (smartMeterData.getCategory() == 42) {//42	建大仁科温湿度
            analyses.add(temperatureAnalysis);
            analyses.add(humidityAnalysis);
        }else  if (smartMeterData.getCategory() == 43) {//43	建大仁科PM2.5和PM10
            analyses.add(electricityAnalysis);
            analyses.add(voltageAnalysis);
            analyses.add(powerAnalysis);
            analyses.add(pm10Analysis);
            analyses.add(pm25Analysis);
        }else  if (smartMeterData.getCategory() == 22) {//22	GL-100液位计
            analyses.add(waterLevelAnalysis);
            analyses.add(reactivePowerAnalysis);
        }


//        Map<String, Float> data = smartMeterData.getData();
//        for (DefaultOneParamAnalysis dopa: analyses ){
//            DefaultAnalysisResult result =  dopa.handle(data,);
//            if( result.isAlarm() ){
//
//                list.add(result);
//            }
//        }

        return list;
    }
}
