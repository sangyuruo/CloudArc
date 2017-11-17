package com.emcloud.arc.web.rest;

import com.emcloud.arc.EmCloudArcApp;

import com.emcloud.arc.config.SecurityBeanOverrideConfiguration;

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
 * Test class for the AlarmRuleResource REST controller.
 *
 * @see AlarmRuleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {EmCloudArcApp.class, SecurityBeanOverrideConfiguration.class})
public class AlarmRuleResourceIntTest {

    private static final String DEFAULT_RULE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_RULE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_RULE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_RULE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_RULE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_RULE_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_ALARM_LEVEL = 1;
    private static final Integer UPDATED_ALARM_LEVEL = 2;

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
    private AlarmRuleRepository alarmRuleRepository;

    @Autowired
    private AlarmRuleService alarmRuleService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAlarmRuleMockMvc;

    private AlarmRule alarmRule;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AlarmRuleResource alarmRuleResource = new AlarmRuleResource(alarmRuleService);
        this.restAlarmRuleMockMvc = MockMvcBuilders.standaloneSetup(alarmRuleResource)
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
    public static AlarmRule createEntity(EntityManager em) {
        AlarmRule alarmRule = new AlarmRule()
            .ruleName(DEFAULT_RULE_NAME)
            .ruleCode(DEFAULT_RULE_CODE)
            .ruleType(DEFAULT_RULE_TYPE)
            .alarmLevel(DEFAULT_ALARM_LEVEL)
            .enable(DEFAULT_ENABLE)
            .createdBy(DEFAULT_CREATED_BY)
            .createTime(DEFAULT_CREATE_TIME)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updateTime(DEFAULT_UPDATE_TIME);
        return alarmRule;
    }

    @Before
    public void initTest() {
        alarmRule = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlarmRule() throws Exception {
        int databaseSizeBeforeCreate = alarmRuleRepository.findAll().size();

        // Create the AlarmRule
        restAlarmRuleMockMvc.perform(post("/api/alarm-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alarmRule)))
            .andExpect(status().isCreated());

        // Validate the AlarmRule in the database
        List<AlarmRule> alarmRuleList = alarmRuleRepository.findAll();
        assertThat(alarmRuleList).hasSize(databaseSizeBeforeCreate + 1);
        AlarmRule testAlarmRule = alarmRuleList.get(alarmRuleList.size() - 1);
        assertThat(testAlarmRule.getRuleName()).isEqualTo(DEFAULT_RULE_NAME);
        assertThat(testAlarmRule.getRuleCode()).isEqualTo(DEFAULT_RULE_CODE);
        assertThat(testAlarmRule.getRuleType()).isEqualTo(DEFAULT_RULE_TYPE);
        assertThat(testAlarmRule.getAlarmLevel()).isEqualTo(DEFAULT_ALARM_LEVEL);
        assertThat(testAlarmRule.isEnable()).isEqualTo(DEFAULT_ENABLE);
        assertThat(testAlarmRule.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testAlarmRule.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testAlarmRule.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testAlarmRule.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void createAlarmRuleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = alarmRuleRepository.findAll().size();

        // Create the AlarmRule with an existing ID
        alarmRule.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlarmRuleMockMvc.perform(post("/api/alarm-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alarmRule)))
            .andExpect(status().isBadRequest());

        // Validate the AlarmRule in the database
        List<AlarmRule> alarmRuleList = alarmRuleRepository.findAll();
        assertThat(alarmRuleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkRuleNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = alarmRuleRepository.findAll().size();
        // set the field null
        alarmRule.setRuleName(null);

        // Create the AlarmRule, which fails.

        restAlarmRuleMockMvc.perform(post("/api/alarm-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alarmRule)))
            .andExpect(status().isBadRequest());

        List<AlarmRule> alarmRuleList = alarmRuleRepository.findAll();
        assertThat(alarmRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRuleCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = alarmRuleRepository.findAll().size();
        // set the field null
        alarmRule.setRuleCode(null);

        // Create the AlarmRule, which fails.

        restAlarmRuleMockMvc.perform(post("/api/alarm-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alarmRule)))
            .andExpect(status().isBadRequest());

        List<AlarmRule> alarmRuleList = alarmRuleRepository.findAll();
        assertThat(alarmRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRuleTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = alarmRuleRepository.findAll().size();
        // set the field null
        alarmRule.setRuleType(null);

        // Create the AlarmRule, which fails.

        restAlarmRuleMockMvc.perform(post("/api/alarm-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alarmRule)))
            .andExpect(status().isBadRequest());

        List<AlarmRule> alarmRuleList = alarmRuleRepository.findAll();
        assertThat(alarmRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAlarmLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = alarmRuleRepository.findAll().size();
        // set the field null
        alarmRule.setAlarmLevel(null);

        // Create the AlarmRule, which fails.

        restAlarmRuleMockMvc.perform(post("/api/alarm-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alarmRule)))
            .andExpect(status().isBadRequest());

        List<AlarmRule> alarmRuleList = alarmRuleRepository.findAll();
        assertThat(alarmRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnableIsRequired() throws Exception {
        int databaseSizeBeforeTest = alarmRuleRepository.findAll().size();
        // set the field null
        alarmRule.setEnable(null);

        // Create the AlarmRule, which fails.

        restAlarmRuleMockMvc.perform(post("/api/alarm-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alarmRule)))
            .andExpect(status().isBadRequest());

        List<AlarmRule> alarmRuleList = alarmRuleRepository.findAll();
        assertThat(alarmRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = alarmRuleRepository.findAll().size();
        // set the field null
        alarmRule.setCreatedBy(null);

        // Create the AlarmRule, which fails.

        restAlarmRuleMockMvc.perform(post("/api/alarm-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alarmRule)))
            .andExpect(status().isBadRequest());

        List<AlarmRule> alarmRuleList = alarmRuleRepository.findAll();
        assertThat(alarmRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = alarmRuleRepository.findAll().size();
        // set the field null
        alarmRule.setCreateTime(null);

        // Create the AlarmRule, which fails.

        restAlarmRuleMockMvc.perform(post("/api/alarm-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alarmRule)))
            .andExpect(status().isBadRequest());

        List<AlarmRule> alarmRuleList = alarmRuleRepository.findAll();
        assertThat(alarmRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = alarmRuleRepository.findAll().size();
        // set the field null
        alarmRule.setUpdatedBy(null);

        // Create the AlarmRule, which fails.

        restAlarmRuleMockMvc.perform(post("/api/alarm-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alarmRule)))
            .andExpect(status().isBadRequest());

        List<AlarmRule> alarmRuleList = alarmRuleRepository.findAll();
        assertThat(alarmRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = alarmRuleRepository.findAll().size();
        // set the field null
        alarmRule.setUpdateTime(null);

        // Create the AlarmRule, which fails.

        restAlarmRuleMockMvc.perform(post("/api/alarm-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alarmRule)))
            .andExpect(status().isBadRequest());

        List<AlarmRule> alarmRuleList = alarmRuleRepository.findAll();
        assertThat(alarmRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAlarmRules() throws Exception {
        // Initialize the database
        alarmRuleRepository.saveAndFlush(alarmRule);

        // Get all the alarmRuleList
        restAlarmRuleMockMvc.perform(get("/api/alarm-rules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alarmRule.getId().intValue())))
            .andExpect(jsonPath("$.[*].ruleName").value(hasItem(DEFAULT_RULE_NAME.toString())))
            .andExpect(jsonPath("$.[*].ruleCode").value(hasItem(DEFAULT_RULE_CODE.toString())))
            .andExpect(jsonPath("$.[*].ruleType").value(hasItem(DEFAULT_RULE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].alarmLevel").value(hasItem(DEFAULT_ALARM_LEVEL)))
            .andExpect(jsonPath("$.[*].enable").value(hasItem(DEFAULT_ENABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))));
    }

    @Test
    @Transactional
    public void getAlarmRule() throws Exception {
        // Initialize the database
        alarmRuleRepository.saveAndFlush(alarmRule);

        // Get the alarmRule
        restAlarmRuleMockMvc.perform(get("/api/alarm-rules/{id}", alarmRule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(alarmRule.getId().intValue()))
            .andExpect(jsonPath("$.ruleName").value(DEFAULT_RULE_NAME.toString()))
            .andExpect(jsonPath("$.ruleCode").value(DEFAULT_RULE_CODE.toString()))
            .andExpect(jsonPath("$.ruleType").value(DEFAULT_RULE_TYPE.toString()))
            .andExpect(jsonPath("$.alarmLevel").value(DEFAULT_ALARM_LEVEL))
            .andExpect(jsonPath("$.enable").value(DEFAULT_ENABLE.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updateTime").value(sameInstant(DEFAULT_UPDATE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingAlarmRule() throws Exception {
        // Get the alarmRule
        restAlarmRuleMockMvc.perform(get("/api/alarm-rules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlarmRule() throws Exception {
        // Initialize the database
        alarmRuleService.save(alarmRule);

        int databaseSizeBeforeUpdate = alarmRuleRepository.findAll().size();

        // Update the alarmRule
        AlarmRule updatedAlarmRule = alarmRuleRepository.findOne(alarmRule.getId());
        updatedAlarmRule
            .ruleName(UPDATED_RULE_NAME)
            .ruleCode(UPDATED_RULE_CODE)
            .ruleType(UPDATED_RULE_TYPE)
            .alarmLevel(UPDATED_ALARM_LEVEL)
            .enable(UPDATED_ENABLE)
            .createdBy(UPDATED_CREATED_BY)
            .createTime(UPDATED_CREATE_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updateTime(UPDATED_UPDATE_TIME);

        restAlarmRuleMockMvc.perform(put("/api/alarm-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAlarmRule)))
            .andExpect(status().isOk());

        // Validate the AlarmRule in the database
        List<AlarmRule> alarmRuleList = alarmRuleRepository.findAll();
        assertThat(alarmRuleList).hasSize(databaseSizeBeforeUpdate);
        AlarmRule testAlarmRule = alarmRuleList.get(alarmRuleList.size() - 1);
        assertThat(testAlarmRule.getRuleName()).isEqualTo(UPDATED_RULE_NAME);
        assertThat(testAlarmRule.getRuleCode()).isEqualTo(UPDATED_RULE_CODE);
        assertThat(testAlarmRule.getRuleType()).isEqualTo(UPDATED_RULE_TYPE);
        assertThat(testAlarmRule.getAlarmLevel()).isEqualTo(UPDATED_ALARM_LEVEL);
        assertThat(testAlarmRule.isEnable()).isEqualTo(UPDATED_ENABLE);
        assertThat(testAlarmRule.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAlarmRule.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testAlarmRule.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testAlarmRule.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingAlarmRule() throws Exception {
        int databaseSizeBeforeUpdate = alarmRuleRepository.findAll().size();

        // Create the AlarmRule

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAlarmRuleMockMvc.perform(put("/api/alarm-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alarmRule)))
            .andExpect(status().isCreated());

        // Validate the AlarmRule in the database
        List<AlarmRule> alarmRuleList = alarmRuleRepository.findAll();
        assertThat(alarmRuleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAlarmRule() throws Exception {
        // Initialize the database
        alarmRuleService.save(alarmRule);

        int databaseSizeBeforeDelete = alarmRuleRepository.findAll().size();

        // Get the alarmRule
        restAlarmRuleMockMvc.perform(delete("/api/alarm-rules/{id}", alarmRule.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AlarmRule> alarmRuleList = alarmRuleRepository.findAll();
        assertThat(alarmRuleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlarmRule.class);
        AlarmRule alarmRule1 = new AlarmRule();
        alarmRule1.setId(1L);
        AlarmRule alarmRule2 = new AlarmRule();
        alarmRule2.setId(alarmRule1.getId());
        assertThat(alarmRule1).isEqualTo(alarmRule2);
        alarmRule2.setId(2L);
        assertThat(alarmRule1).isNotEqualTo(alarmRule2);
        alarmRule1.setId(null);
        assertThat(alarmRule1).isNotEqualTo(alarmRule2);
    }
}
