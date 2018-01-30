package com.emcloud.arc.analysis.impl;

import com.emcloud.arc.analysis.DefaultAnalysisResult;

public class PMAnalysisResult extends DefaultAnalysisResult {
    // 0 : 不报警， 1： 一般 ， 2：严重 ， 3： 紧急
    int pm10AlarmLevel;
    int pm25AlarmLevel;


}
