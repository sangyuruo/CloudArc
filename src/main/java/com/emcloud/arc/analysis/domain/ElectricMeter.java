package com.emcloud.arc.analysis.domain;

import com.emcloud.arc.analysis.impl.ElectricityAnalysis;
import com.emcloud.arc.analysis.impl.PowerAnalysis;
import com.emcloud.arc.analysis.impl.VoltageAnalysis;

public class ElectricMeter {
    private ElectricityAnalysis electricityAnalysis;
    private VoltageAnalysis voltageAnalysis;
    private PowerAnalysis powerAnalysis;

    public ElectricityAnalysis getElectricityAnalysis() {
        return electricityAnalysis;
    }

    public void setElectricityAnalysis(ElectricityAnalysis electricityAnalysis) {
        this.electricityAnalysis = electricityAnalysis;
    }

    public VoltageAnalysis getVoltageAnalysis() {
        return voltageAnalysis;
    }

    public void setVoltageAnalysis(VoltageAnalysis voltageAnalysis) {
        this.voltageAnalysis = voltageAnalysis;
    }

    public PowerAnalysis getPowerAnalysis() {
        return powerAnalysis;
    }

    public void setPowerAnalysis(PowerAnalysis powerAnalysis) {
        this.powerAnalysis = powerAnalysis;
    }
}
