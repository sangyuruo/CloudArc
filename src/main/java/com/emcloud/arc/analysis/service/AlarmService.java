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

    public List<DefaultAnalysisResult> analysis(SmartMeterData smartMeterData) {
        List<DefaultAnalysisResult> list = new ArrayList<>();
        List<DefaultOneParamAnalysis> analyses = new ArrayList<>();

        if (smartMeterData.getCategory() == 45) {
            analyses.add(infraredAnalysis);
        }else  if (smartMeterData.getCategory() == 44) {
            analyses.add(waterOutAnalysis);
        }else  if (smartMeterData.getCategory() == 42) {
            analyses.add(temperatureAnalysis);
            analyses.add(humidityAnalysis);
        }else  if (smartMeterData.getCategory() == 43) {
            analyses.add(electricityAnalysis);
            analyses.add(voltageAnalysis);
            analyses.add(powerAnalysis);
            analyses.add(pm10Analysis);
            analyses.add(pm25Analysis);
        }else  if (smartMeterData.getCategory() == 22) {
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
