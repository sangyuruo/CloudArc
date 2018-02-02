package com.emcloud.arc.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emcloud.arc.analysis.service.AlarmService;
import com.emcloud.arc.domain.SmartMeterData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;

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
    public String json(@Valid @RequestBody SmartMeterData smartMeterData) throws URISyntaxException {

        alarmService.analysis(smartMeterData);

        return "成功";
    }
}
