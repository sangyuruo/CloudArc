package com.emcloud.arc.web.rest;

import com.emcloud.arc.EmCloudArcApp;

import com.emcloud.arc.analysis.analysis.DefaultAnalysisResult;
import com.emcloud.arc.analysis.service.AlarmService;
import com.emcloud.arc.config.SecurityBeanOverrideConfiguration;

import com.emcloud.arc.domain.MeterCategoryRule;
import com.emcloud.arc.domain.MeterRule;
import com.emcloud.arc.domain.SmartMeterData;
import com.emcloud.arc.domain.RuleDTO;
import com.emcloud.arc.repository.MeterRuleRepository;
import com.emcloud.arc.service.MeterCategoryRuleService;
import com.emcloud.arc.service.MeterRuleService;
import com.emcloud.arc.web.rest.errors.ExceptionTranslator;

import io.advantageous.boon.core.Sys;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.emcloud.arc.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MeterRuleResource REST controller.
 *
 * @see MeterRuleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {EmCloudArcApp.class, SecurityBeanOverrideConfiguration.class})
public class MeterRuleResourceIntTest {

    private static final String DEFAULT_METER_CODE = "AAAAAAAAAA";
    private static final String UPDATED_METER_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_METER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_METER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_RULE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_RULE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_RULE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_RULE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ANALYSIS = "AAAAAAAAAA";
    private static final String UPDATED_ANALYSIS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENABLE = false;
    private static final Boolean UPDATED_ENABLE = true;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private MeterRuleRepository meterRuleRepository;

    @Autowired
    private MeterRuleService meterRuleService;
@Autowired
private AlarmService alarmService;

    @Autowired
    private MeterCategoryRuleService meterCategoryRuleService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMeterRuleMockMvc;

    private MeterRule meterRule;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MeterRuleResource meterRuleResource = new MeterRuleResource(meterRuleService, meterCategoryRuleService);
        this.restMeterRuleMockMvc = MockMvcBuilders.standaloneSetup(meterRuleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }



    @Test
    @Transactional
    public void test(){
        SmartMeterData meterData=new SmartMeterData();
        Map<String,Float> map=new HashMap<>();
        map.put("Level",7.19F);
        meterData.setData(map);
        meterData.setMeterId(UUID.fromString("6663141a-fcfa-11e7-9994-0242ac111007"));
        meterData.setCategory(22);
        meterData.setName("徐家坝进水位");

        List<DefaultAnalysisResult> list= alarmService.analysis(meterData);
        System.out.println(list+"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
    }




    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MeterRule createEntity(EntityManager em) {
        MeterRule meterRule = new MeterRule()
            .meterCode(DEFAULT_METER_CODE)
            .meterName(DEFAULT_METER_NAME)
            .ruleCode(DEFAULT_RULE_CODE)
            .ruleName(DEFAULT_RULE_NAME)
            .analysis(DEFAULT_ANALYSIS)
            .enable(DEFAULT_ENABLE)
            .createdBy(DEFAULT_CREATED_BY)
            .createTime(DEFAULT_CREATE_TIME)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updateTime(DEFAULT_UPDATE_TIME);
        return meterRule;
    }

    @Before
    public void initTest() {
        meterRule = createEntity(em);
    }

    @Test
    @Transactional
    public void createMeterRule() throws Exception {
        int databaseSizeBeforeCreate = meterRuleRepository.findAll().size();

        // Create the MeterRule
        restMeterRuleMockMvc.perform(post("/api/meter-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterRule)))
            .andExpect(status().isCreated());

        // Validate the MeterRule in the database
        List<MeterRule> meterRuleList = meterRuleRepository.findAll();
        assertThat(meterRuleList).hasSize(databaseSizeBeforeCreate + 1);
        MeterRule testMeterRule = meterRuleList.get(meterRuleList.size() - 1);
        assertThat(testMeterRule.getMeterCode()).isEqualTo(DEFAULT_METER_CODE);
        assertThat(testMeterRule.getMeterName()).isEqualTo(DEFAULT_METER_NAME);
        assertThat(testMeterRule.getRuleCode()).isEqualTo(DEFAULT_RULE_CODE);
        assertThat(testMeterRule.getRuleName()).isEqualTo(DEFAULT_RULE_NAME);
        assertThat(testMeterRule.getAnalysis()).isEqualTo(DEFAULT_ANALYSIS);
        assertThat(testMeterRule.isEnable()).isEqualTo(DEFAULT_ENABLE);
        assertThat(testMeterRule.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testMeterRule.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testMeterRule.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testMeterRule.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void getTest() throws Exception {
        List<MeterCategoryRule> list = meterCategoryRuleService.findAll();
        List<MeterRule> list2 = meterRuleService.findAll();
        List<RuleDTO> ruleDTOList = new ArrayList<>();
        List<RuleDTO> ruleDTOList2 = new ArrayList<>();

       /* String mcr;
        String  mr;*/

        for (MeterCategoryRule rule : list){
            RuleDTO r=new RuleDTO();
            r.setRuleName(rule.getRuleName());
            r.setRuleCode(rule.getRuleCode());
            ruleDTOList.add(r);
        }
        for (MeterRule rule1:list2) {
            RuleDTO r=new RuleDTO();
            r.setRuleName(rule1.getRuleName());
            r.setRuleCode(rule1.getRuleCode());
            ruleDTOList2.add(r);
        }
        ruleDTOList2.addAll(ruleDTOList);

        System.out.println(ruleDTOList2);

    }



    @Test
    @Transactional
    public void createMeterRuleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = meterRuleRepository.findAll().size();

        // Create the MeterRule with an existing ID
        meterRule.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMeterRuleMockMvc.perform(post("/api/meter-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterRule)))
            .andExpect(status().isBadRequest());

        // Validate the MeterRule in the database
        List<MeterRule> meterRuleList = meterRuleRepository.findAll();
        assertThat(meterRuleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMeterCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterRuleRepository.findAll().size();
        // set the field null
        meterRule.setMeterCode(null);

        // Create the MeterRule, which fails.

        restMeterRuleMockMvc.perform(post("/api/meter-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterRule)))
            .andExpect(status().isBadRequest());

        List<MeterRule> meterRuleList = meterRuleRepository.findAll();
        assertThat(meterRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMeterNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterRuleRepository.findAll().size();
        // set the field null
        meterRule.setMeterName(null);

        // Create the MeterRule, which fails.

        restMeterRuleMockMvc.perform(post("/api/meter-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterRule)))
            .andExpect(status().isBadRequest());

        List<MeterRule> meterRuleList = meterRuleRepository.findAll();
        assertThat(meterRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRuleCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterRuleRepository.findAll().size();
        // set the field null
        meterRule.setRuleCode(null);

        // Create the MeterRule, which fails.

        restMeterRuleMockMvc.perform(post("/api/meter-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterRule)))
            .andExpect(status().isBadRequest());

        List<MeterRule> meterRuleList = meterRuleRepository.findAll();
        assertThat(meterRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRuleNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterRuleRepository.findAll().size();
        // set the field null
        meterRule.setRuleName(null);

        // Create the MeterRule, which fails.

        restMeterRuleMockMvc.perform(post("/api/meter-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterRule)))
            .andExpect(status().isBadRequest());

        List<MeterRule> meterRuleList = meterRuleRepository.findAll();
        assertThat(meterRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAnalysisIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterRuleRepository.findAll().size();
        // set the field null
        meterRule.setAnalysis(null);

        // Create the MeterRule, which fails.

        restMeterRuleMockMvc.perform(post("/api/meter-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterRule)))
            .andExpect(status().isBadRequest());

        List<MeterRule> meterRuleList = meterRuleRepository.findAll();
        assertThat(meterRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnableIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterRuleRepository.findAll().size();
        // set the field null
        meterRule.setEnable(null);

        // Create the MeterRule, which fails.

        restMeterRuleMockMvc.perform(post("/api/meter-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterRule)))
            .andExpect(status().isBadRequest());

        List<MeterRule> meterRuleList = meterRuleRepository.findAll();
        assertThat(meterRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterRuleRepository.findAll().size();
        // set the field null
        meterRule.setCreatedBy(null);

        // Create the MeterRule, which fails.

        restMeterRuleMockMvc.perform(post("/api/meter-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterRule)))
            .andExpect(status().isBadRequest());

        List<MeterRule> meterRuleList = meterRuleRepository.findAll();
        assertThat(meterRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterRuleRepository.findAll().size();
        // set the field null
        meterRule.setCreateTime(null);

        // Create the MeterRule, which fails.

        restMeterRuleMockMvc.perform(post("/api/meter-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterRule)))
            .andExpect(status().isBadRequest());

        List<MeterRule> meterRuleList = meterRuleRepository.findAll();
        assertThat(meterRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterRuleRepository.findAll().size();
        // set the field null
        meterRule.setUpdatedBy(null);

        // Create the MeterRule, which fails.

        restMeterRuleMockMvc.perform(post("/api/meter-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterRule)))
            .andExpect(status().isBadRequest());

        List<MeterRule> meterRuleList = meterRuleRepository.findAll();
        assertThat(meterRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterRuleRepository.findAll().size();
        // set the field null
        meterRule.setUpdateTime(null);

        // Create the MeterRule, which fails.

        restMeterRuleMockMvc.perform(post("/api/meter-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterRule)))
            .andExpect(status().isBadRequest());

        List<MeterRule> meterRuleList = meterRuleRepository.findAll();
        assertThat(meterRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMeterRules() throws Exception {
        // Initialize the database
        meterRuleRepository.saveAndFlush(meterRule);

        // Get all the meterRuleList
        restMeterRuleMockMvc.perform(get("/api/meter-rules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(meterRule.getId().intValue())))
            .andExpect(jsonPath("$.[*].meterCode").value(hasItem(DEFAULT_METER_CODE.toString())))
            .andExpect(jsonPath("$.[*].meterName").value(hasItem(DEFAULT_METER_NAME.toString())))
            .andExpect(jsonPath("$.[*].ruleCode").value(hasItem(DEFAULT_RULE_CODE.toString())))
            .andExpect(jsonPath("$.[*].ruleName").value(hasItem(DEFAULT_RULE_NAME.toString())))
            .andExpect(jsonPath("$.[*].analysis").value(hasItem(DEFAULT_ANALYSIS.toString())))
            .andExpect(jsonPath("$.[*].enable").value(hasItem(DEFAULT_ENABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())));
    }

    @Test
    @Transactional
    public void getMeterRule() throws Exception {
        // Initialize the database
        meterRuleRepository.saveAndFlush(meterRule);

        // Get the meterRule
        restMeterRuleMockMvc.perform(get("/api/meter-rules/{id}", meterRule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(meterRule.getId().intValue()))
            .andExpect(jsonPath("$.meterCode").value(DEFAULT_METER_CODE.toString()))
            .andExpect(jsonPath("$.meterName").value(DEFAULT_METER_NAME.toString()))
            .andExpect(jsonPath("$.ruleCode").value(DEFAULT_RULE_CODE.toString()))
            .andExpect(jsonPath("$.ruleName").value(DEFAULT_RULE_NAME.toString()))
            .andExpect(jsonPath("$.analysis").value(DEFAULT_ANALYSIS.toString()))
            .andExpect(jsonPath("$.enable").value(DEFAULT_ENABLE.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMeterRule() throws Exception {
        // Get the meterRule
        restMeterRuleMockMvc.perform(get("/api/meter-rules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMeterRule() throws Exception {
        // Initialize the database
        meterRuleService.save(meterRule);

        int databaseSizeBeforeUpdate = meterRuleRepository.findAll().size();

        // Update the meterRule
        MeterRule updatedMeterRule = meterRuleRepository.findOne(meterRule.getId());
        updatedMeterRule
            .meterCode(UPDATED_METER_CODE)
            .meterName(UPDATED_METER_NAME)
            .ruleCode(UPDATED_RULE_CODE)
            .ruleName(UPDATED_RULE_NAME)
            .analysis(UPDATED_ANALYSIS)
            .enable(UPDATED_ENABLE)
            .createdBy(UPDATED_CREATED_BY)
            .createTime(UPDATED_CREATE_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updateTime(UPDATED_UPDATE_TIME);

        restMeterRuleMockMvc.perform(put("/api/meter-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMeterRule)))
            .andExpect(status().isOk());

        // Validate the MeterRule in the database
        List<MeterRule> meterRuleList = meterRuleRepository.findAll();
        assertThat(meterRuleList).hasSize(databaseSizeBeforeUpdate);
        MeterRule testMeterRule = meterRuleList.get(meterRuleList.size() - 1);
        assertThat(testMeterRule.getMeterCode()).isEqualTo(UPDATED_METER_CODE);
        assertThat(testMeterRule.getMeterName()).isEqualTo(UPDATED_METER_NAME);
        assertThat(testMeterRule.getRuleCode()).isEqualTo(UPDATED_RULE_CODE);
        assertThat(testMeterRule.getRuleName()).isEqualTo(UPDATED_RULE_NAME);
        assertThat(testMeterRule.getAnalysis()).isEqualTo(UPDATED_ANALYSIS);
        assertThat(testMeterRule.isEnable()).isEqualTo(UPDATED_ENABLE);
        assertThat(testMeterRule.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testMeterRule.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testMeterRule.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testMeterRule.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingMeterRule() throws Exception {
        int databaseSizeBeforeUpdate = meterRuleRepository.findAll().size();

        // Create the MeterRule

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMeterRuleMockMvc.perform(put("/api/meter-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterRule)))
            .andExpect(status().isCreated());

        // Validate the MeterRule in the database
        List<MeterRule> meterRuleList = meterRuleRepository.findAll();
        assertThat(meterRuleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMeterRule() throws Exception {
        // Initialize the database
        meterRuleService.save(meterRule);

        int databaseSizeBeforeDelete = meterRuleRepository.findAll().size();

        // Get the meterRule
        restMeterRuleMockMvc.perform(delete("/api/meter-rules/{id}", meterRule.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MeterRule> meterRuleList = meterRuleRepository.findAll();
        assertThat(meterRuleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MeterRule.class);
        MeterRule meterRule1 = new MeterRule();
        meterRule1.setId(1L);
        MeterRule meterRule2 = new MeterRule();
        meterRule2.setId(meterRule1.getId());
        assertThat(meterRule1).isEqualTo(meterRule2);
        meterRule2.setId(2L);
        assertThat(meterRule1).isNotEqualTo(meterRule2);
        meterRule1.setId(null);
        assertThat(meterRule1).isNotEqualTo(meterRule2);
    }

    public AlarmService getAlarmService() {
        return alarmService;
    }

    public void setAlarmService(AlarmService alarmService) {
        this.alarmService = alarmService;
    }
}
