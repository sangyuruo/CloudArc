package com.emcloud.arc.analysis.analysis;

import com.emcloud.arc.analysis.impl.*;

import java.util.HashMap;
import java.util.Map;

public class AnalysisFactory {

    Map<String, DefaultOneParamAnalysis> analysisMap = new HashMap<>();

    public  Map<String, DefaultOneParamAnalysis> put() {
        analysisMap.put(Electricity_A_Analysis.getKey(), getAnalysis(Electricity_A_Analysis.getKey()));
        analysisMap.put(Electricity_B_Analysis.getKey(), getAnalysis(Electricity_B_Analysis.getKey()));
        analysisMap.put(Electricity_C_Analysis.getKey(), getAnalysis(Electricity_C_Analysis.getKey()));
        analysisMap.put(humidityAnalysis.getKey(), getAnalysis(humidityAnalysis.getKey()));
        analysisMap.put(temperatureAnalysis.getKey(), getAnalysis(temperatureAnalysis.getKey()));
        analysisMap.put(Power_A_Analysis.getKey(), getAnalysis(Power_A_Analysis.getKey()));
        analysisMap.put(Power_B_Analysis.getKey(), getAnalysis(Power_B_Analysis.getKey()));
        analysisMap.put(Power_C_Analysis.getKey(), getAnalysis(Power_C_Analysis.getKey()));
        analysisMap.put(Power_Fs_Analysis.getKey(), getAnalysis(Power_Fs_Analysis.getKey()));
        analysisMap.put(Power_S_Analysis.getKey(), getAnalysis(Power_S_Analysis.getKey()));
        analysisMap.put(Voltage_A_Analysis.getKey(), getAnalysis(Voltage_A_Analysis.getKey()));
        analysisMap.put(Voltage_B_Analysis.getKey(), getAnalysis(Voltage_B_Analysis.getKey()));
        analysisMap.put(Voltage_C_Analysis.getKey(), getAnalysis(Voltage_C_Analysis.getKey()));
   //       for(int i=0;i<AnalysisFactory.class.getDeclaredFields().length;i++){ }
        for (String key : analysisMap.keySet()) {
             System.out.println("key= "+ key + " and value= " + analysisMap.get(key));
              }
        return analysisMap;

    }


    public DefaultOneParamAnalysis getAnalysis(String key) {
        return analysisMap.get(key);
    }

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
    static InfraredAnalysis infraredAnalysis = new InfraredAnalysis() {
        @Override
        public String getKey() {
            return "";
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
    static PowerAnalysis Power_Fs_Analysis = new PowerAnalysis() {
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
            return "";
        }
    };

    public AnalysisFactory() {
    }


}


