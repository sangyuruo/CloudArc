package com.emcloud.arc.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emcloud.arc.domain.AlarmRule;
import com.emcloud.arc.domain.SmartMeterData;
import com.emcloud.arc.service.AlarmRuleService;
import com.emcloud.arc.web.rest.errors.BadRequestAlertException;
import com.emcloud.arc.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;


/**
 * REST controller for managing AlarmRule.
 */
@RestController
@RequestMapping("/api")
public class SmartMeterDataResource {


    private final Logger log = LoggerFactory.getLogger(AlarmRuleResource.class);

    private static final String ENTITY_NAME = "alarmRule";

    private final SmartMeterDataResource smartMeterDataResource;

    public SmartMeterDataResource(SmartMeterDataResource SmartMeterDataResource) {
        this.smartMeterDataResource = SmartMeterDataResource;
    }


    @GetMapping("/alarm-rules/json")
    @Timed
    public String json(@Valid @RequestBody SmartMeterData smartMeterData) throws URISyntaxException {
        log.debug("REST request to save AlarmRule : {}", smartMeterData);
        if (smartMeterData.getId() != null) {
            throw new BadRequestAlertException("A new alarmRule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return "成功";
    }
}
