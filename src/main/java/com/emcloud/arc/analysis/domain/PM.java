package com.emcloud.arc.analysis.domain;

import com.emcloud.arc.analysis.impl.Pm10Analysis;
import com.emcloud.arc.analysis.impl.Pm25Analysis;

public class PM {
   private  Pm10Analysis pm10Analysis;
   private  Pm25Analysis pm25Analysis;

    public Pm10Analysis getPm10Analysis() {
        return pm10Analysis;
    }

    public void setPm10Analysis(Pm10Analysis pm10Analysis) {
        this.pm10Analysis = pm10Analysis;
    }

    public Pm25Analysis getPm25Analysis() {
        return pm25Analysis;
    }

    public void setPm25Analysis(Pm25Analysis pm25Analysis) {
        this.pm25Analysis = pm25Analysis;
    }
}
