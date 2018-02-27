package com.emcloud.arc.analysis.analysis;

import com.emcloud.arc.analysis.impl.*;

import java.util.HashMap;
import java.util.Map;

public class AnalysisFactory {
    private static AnalysisFactory instance = null;

    private AnalysisFactory() {
        init();
    }

    public static AnalysisFactory getInstance() {
        if (null == instance) {
            instance = new AnalysisFactory();
        }
        return instance;
    }

    static Map<String, DefaultOneParamAnalysis> analysisMap = new HashMap<>();

    public Map<String, DefaultOneParamAnalysis> init() {
        analysisMap.put(electricity_A_Analysis.getKey(), electricity_A_Analysis );
        analysisMap.put(electricity_B_analysis.getKey(), electricity_B_analysis);
        analysisMap.put(electricity_C_Analysis.getKey(), electricity_C_Analysis);
        analysisMap.put(humidityAnalysis.getKey(), humidityAnalysis);
        analysisMap.put(temperatureAnalysis.getKey(), temperatureAnalysis);
        analysisMap.put(power_A_Analysis.getKey(), power_A_Analysis);
        analysisMap.put(power_B_Analysis.getKey(), power_B_Analysis);
        analysisMap.put(power_C_Analysis.getKey(), power_C_Analysis);
        analysisMap.put(power_Fs_Analysis.getKey(),power_Fs_Analysis);
        analysisMap.put(power_S_Analysis.getKey(), power_S_Analysis);
        analysisMap.put(voltage_A_Analysis.getKey(), voltage_A_Analysis);
        analysisMap.put(voltage_B_Analysis.getKey(), voltage_B_Analysis);
        analysisMap.put(voltage_C_Analysis.getKey(), voltage_C_Analysis);
        //   for(int i=0;i<AnalysisFactory.class.getDeclaredFields().length;i++){ }
        for (String key : analysisMap.keySet()) {
            System.out.println("key= " + key + " and value= " + analysisMap.get(key));
        }
        return analysisMap;

    }


    public DefaultOneParamAnalysis getAnalysis(String key) {
        return analysisMap.get(key);
    }

    ElectricityAnalysis electricity_A_Analysis = new ElectricityAnalysis() {
        @Override
        public String getKey() {
            return "I_a";
        }

    };
    ElectricityAnalysis electricity_B_analysis = new ElectricityAnalysis() {
        @Override
        public String getKey() {
            return "I_b";
        }
    };
    ElectricityAnalysis electricity_C_Analysis = new ElectricityAnalysis() {
        @Override
        public String getKey() {
            return "I_c";
        }
    };
    InfraredAnalysis infraredAnalysis = new InfraredAnalysis() {
        @Override
        public String getKey() {
            return "";
        }
    };
    HumidityAnalysis humidityAnalysis = new HumidityAnalysis() {
        @Override
        public String getKey() {
            return "Humidity";
        }
    };
    Pm10Analysis pm10Analysis = new Pm10Analysis() {
        @Override
        public String getKey() {
            return "PM10";
        }
    };
    Pm25Analysis pm25Analysis = new Pm25Analysis() {
        @Override
        public String getKey() {
            return "PM2.5";
        }
    };
    PowerAnalysis power_A_Analysis = new PowerAnalysis() {
        @Override
        public String getKey() {
            return "P_a";
        }
    };
    PowerAnalysis power_B_Analysis = new PowerAnalysis() {
        @Override
        public String getKey() {
            return "P_b";
        }
    };
    PowerAnalysis power_C_Analysis = new PowerAnalysis() {
        @Override
        public String getKey() {
            return "P_c";
        }
    };
    PowerAnalysis power_Fs_Analysis = new PowerAnalysis() {
        @Override
        public String getKey() {
            return "P_f_s";
        }
    };
    PowerAnalysis power_S_Analysis = new PowerAnalysis() {
        @Override
        public String getKey() {
            return "P_s";
        }
    };
    ReactivePowerAnalysis reactivePowerAnalysis = new ReactivePowerAnalysis() {
        @Override
        public String getKey() {
            return "";
        }
    };
    TemperatureAnalysis temperatureAnalysis = new TemperatureAnalysis() {
        @Override
        public String getKey() {
            return "Temperature";
        }
    };
    VoltageAnalysis voltage_A_Analysis = new VoltageAnalysis() {
        @Override
        public String getKey() {
            return "U_a";
        }
    };
    VoltageAnalysis voltage_B_Analysis = new VoltageAnalysis() {
        @Override
        public String getKey() {
            return "U_b";
        }
    };
    VoltageAnalysis voltage_C_Analysis = new VoltageAnalysis() {
        @Override
        public String getKey() {
            return "U_c";
        }
    };
    WaterLevelAnalysis waterLevelAnalysis = new WaterLevelAnalysis() {
        @Override
        public String getKey() {
            return "Level";
        }
    };
    WaterOutAnalysis waterOutAnalysis = new WaterOutAnalysis() {
        @Override
        public String getKey() {
            return "";
        }
    };


}


