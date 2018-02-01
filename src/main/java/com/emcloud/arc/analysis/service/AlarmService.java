package com.emcloud.arc.analysis.service;


import com.emcloud.arc.analysis.analysis.DefaultAnalysisResult;
import com.emcloud.arc.analysis.analysis.DefaultOneParamAnalysis;
import com.emcloud.arc.analysis.impl.*;
import com.emcloud.arc.domain.SmartMeterData;

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
//1  	雅达电表
//2  	台州顶峰无功表
//3  	指明无功表
//4  	金来单相直通电表
//5  	仪歌多功能直通电表
//6  	仪歌单相直通电表
//7  	安科瑞多功能电表
//8  	DSSD332/DTSD342-1W电表
//20	依泉水表
//21	顺来达水表
//22	GL-100液位计
//30	KCMD-XJ4设备温度表
//41	建大仁科温湿亮三合一
//42	建大仁科温湿度
//43	建大仁科PM2.5和PM10
//44	建大仁科水浸检测
//45	建大仁科红外探测
//50	DL/T 645-2007 计量表
//51	XYM2L-125M/3N 漏电保护开关
//70	ZY112多路开关
//40	建大仁科感烟探测
//60	YDL-MACXX智能空调遥控器


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


        Map<String, Float> data = smartMeterData.getData();
        for (DefaultOneParamAnalysis dopa: analyses ){
            DefaultAnalysisResult result =  dopa.handle( data );
            if( result.isAlarm() ){
                list.add(result);
            }
        }

        return list;
    }
}
