package com.emcloud.arc.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emcloud.arc.analysis.analysis.DefaultAnalysisResult;
import com.emcloud.arc.analysis.service.AlarmService;
import com.emcloud.arc.domain.SmartMeterData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AnalysisTestResource {
    @Autowired
    private final AlarmService alarmService;

    public AnalysisTestResource(AlarmService alarmService) {
        this.alarmService = alarmService;
    }

    @PostMapping("/meter-rules/json")
    @Timed
    public List<DefaultAnalysisResult> json(@Valid @RequestBody SmartMeterData smartMeterData) throws URISyntaxException {

        List<DefaultAnalysisResult> list = alarmService.analysis(smartMeterData);
        for(int i=0;i <list.size();i++){
            System.out.println(i);
        }
      //  return "成功了哈哈哈哈哈哈哈哈";
        return  list;
    }
}
