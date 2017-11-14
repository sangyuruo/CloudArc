package com.emcloud.arc.web.rest;

import com.emcloud.arc.EmCloudArcApp;

import com.emcloud.arc.config.SecurityBeanOverrideConfiguration;

import com.emcloud.arc.domain.MeterRule;
import com.emcloud.arc.repository.MeterRuleRepository;
import com.emcloud.arc.service.MeterRuleService;
import com.emcloud.arc.web.rest.errors.ExceptionTranslator;

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
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.emcloud.arc.web.rest.TestUtil.sameInstant;
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

    private static final String DEFAULT_RULE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_RULE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_RULE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_RULE_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENABLE = false;
    private static final Boolean UPDATED_ENABLE = true;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_UPDATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private MeterRuleRepository meterRuleRepository;

    @Autowired
    private MeterRuleService meterRuleService;

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
        final MeterRuleResource meterRuleResource = new MeterRuleResource(meterRuleService);
        this.restMeterRuleMockMvc = MockMvcBuilders.standaloneSetup(meterRuleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
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
            .ruleName(DEFAULT_RULE_NAME)
            .ruleCode(DEFAULT_RULE_CODE)
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
        assertThat(testMeterRule.getRuleName()).isEqualTo(DEFAULT_RULE_NAME);
        assertThat(testMeterRule.getRuleCode()).isEqualTo(DEFAULT_RULE_CODE);
        assertThat(testMeterRule.isEnable()).isEqualTo(DEFAULT_ENABLE);
        assertThat(testMeterRule.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testMeterRule.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testMeterRule.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testMeterRule.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
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
            .andExpect(jsonPath("$.[*].ruleName").value(hasItem(DEFAULT_RULE_NAME.toString())))
            .andExpect(jsonPath("$.[*].ruleCode").value(hasItem(DEFAULT_RULE_CODE.toString())))
            .andExpect(jsonPath("$.[*].enable").value(hasItem(DEFAULT_ENABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))));
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
            .andExpect(jsonPath("$.ruleName").value(DEFAULT_RULE_NAME.toString()))
            .andExpect(jsonPath("$.ruleCode").value(DEFAULT_RULE_CODE.toString()))
            .andExpect(jsonPath("$.enable").value(DEFAULT_ENABLE.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updateTime").value(sameInstant(DEFAULT_UPDATE_TIME)));
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
            .ruleName(UPDATED_RULE_NAME)
            .ruleCode(UPDATED_RULE_CODE)
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
        assertThat(testMeterRule.getRuleName()).isEqualTo(UPDATED_RULE_NAME);
        assertThat(testMeterRule.getRuleCode()).isEqualTo(UPDATED_RULE_CODE);
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
}
