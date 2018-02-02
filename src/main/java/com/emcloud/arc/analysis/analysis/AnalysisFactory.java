package com.emcloud.arc.analysis.analysis;

import com.emcloud.arc.analysis.impl.*;

import java.util.HashMap;
import java.util.Map;

public class AnalysisFactory {

    static Map<String, DefaultOneParamAnalysis> analysisMap = new HashMap<>();
    static InfraredAnalysis infraredAnalysis = new InfraredAnalysis() {
        @Override
        public String getKey() {
            return "St";
        }
    };


    static ElectricityAnalysis Electricity_A_Analysis = new ElectricityAnalysis() {
        @Override
        public String getKey() {
            return "I_a";
        }
    };
    static ElectricityAnalysis Electricity_B_Analysis = new ElectricityAnalysis() {
        @Override
        public String getKey() {
            return "I_b";
        }
    };
    static ElectricityAnalysis Electricity_C_Analysis = new ElectricityAnalysis() {
        @Override
        public String getKey() {
            return "I_c";
        }
    };


    static HumidityAnalysis humidityAnalysis = new HumidityAnalysis() {
        @Override
        public String getKey() {
            return "Humidity";
        }
    };
    static Pm10Analysis pm10Analysis = new Pm10Analysis() {
        @Override
        public String getKey() {
            return "PM10";
        }
    };
    static Pm25Analysis pm25Analysis = new Pm25Analysis() {
        @Override
        public String getKey() {
            return "PM2.5";
        }
    };
    static PowerAnalysis Power_A_Analysis = new PowerAnalysis() {
        @Override
        public String getKey() {
            return "P_a";
        }
    };
    static PowerAnalysis Power_B_Analysis = new PowerAnalysis() {
        @Override
        public String getKey() {
            return "P_b";
        }
    };
    static PowerAnalysis Power_C_Analysis = new PowerAnalysis() {
        @Override
        public String getKey() {
            return "P_c";
        }
    };
    static PowerAnalysis Power__Fs_Analysis = new PowerAnalysis() {
        @Override
        public String getKey() {
            return "P_f_s";
        }
    };
    static PowerAnalysis Power_S_Analysis = new PowerAnalysis() {
        @Override
        public String getKey() {
            return "P_s";
        }
    };
    static ReactivePowerAnalysis reactivePowerAnalysis = new ReactivePowerAnalysis() {
        @Override
        public String getKey() {
            return "";
        }
    };
    static TemperatureAnalysis temperatureAnalysis = new TemperatureAnalysis() {
        @Override
        public String getKey() {
            return "Temperature";
        }
    };
    static VoltageAnalysis Voltage_A_Analysis = new VoltageAnalysis() {
        @Override
        public String getKey() {
            return "U_a";
        }
    };
    static VoltageAnalysis Voltage_B_Analysis = new VoltageAnalysis() {
        @Override
        public String getKey() {
            return "U_b";
        }
    };
    static VoltageAnalysis Voltage_C_Analysis = new VoltageAnalysis() {
        @Override
        public String getKey() {
            return "U_c";
        }
    };
    static WaterLevelAnalysis waterLevelAnalysis = new WaterLevelAnalysis() {
        @Override
        public String getKey() {
            return "Level";
        }
    };
    static WaterOutAnalysis waterOutAnalysis = new WaterOutAnalysis() {
        @Override
        public String getKey() {
            return "St";
        }
    };

   
    public AnalysisFactory(){
    }
    public DefaultOneParamAnalysis getAnalysis(String key ){
       return analysisMap.get(key);
    }
}


