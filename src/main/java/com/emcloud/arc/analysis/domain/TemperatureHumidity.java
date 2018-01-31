package com.emcloud.arc.analysis.domain;

import com.emcloud.arc.analysis.impl.HumidityAnalysis;
import com.emcloud.arc.analysis.impl.TemperatureAnalysis;

public class TemperatureHumidity {
      private HumidityAnalysis humidityAnalysis;
      private TemperatureAnalysis temperatureAnalysis;

    public HumidityAnalysis getHumidityAnalysis() {
        return humidityAnalysis;
    }

    public void setHumidityAnalysis(HumidityAnalysis humidityAnalysis) {
        this.humidityAnalysis = humidityAnalysis;
    }

    public TemperatureAnalysis getTemperatureAnalysis() {
        return temperatureAnalysis;
    }

    public void setTemperatureAnalysis(TemperatureAnalysis temperatureAnalysis) {
        this.temperatureAnalysis = temperatureAnalysis;
    }
}
