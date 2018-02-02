package com.emcloud.arc.web.rest;

import com.emcloud.arc.EmCloudArcApp;

import com.emcloud.arc.config.SecurityBeanOverrideConfiguration;

import com.emcloud.arc.domain.MeterCategoryRule;
import com.emcloud.arc.repository.MeterCategoryRuleRepository;
import com.emcloud.arc.service.MeterCategoryRuleService;
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
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.emcloud.arc.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MeterCategoryRuleResource REST controller.
 *
 * @see MeterCategoryRuleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {EmCloudArcApp.class, SecurityBeanOverrideConfiguration.class})
public class MeterCategoryRuleResourceIntTest {

    private static final Integer DEFAULT_METER_CATEGORY_CODE = 1;
    private static final Integer UPDATED_METER_CATEGORY_CODE = 2;

    private static final String DEFAULT_METER_CATEGORY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_METER_CATEGORY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_RULE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_RULE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_RULE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_RULE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ANALYSIS = "AAAAAAAAAA";
    private static final String UPDATED_ANALYSIS = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private MeterCategoryRuleRepository meterCategoryRuleRepository;

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

    private MockMvc restMeterCategoryRuleMockMvc;

    private MeterCategoryRule meterCategoryRule;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MeterCategoryRuleResource meterCategoryRuleResource = new MeterCategoryRuleResource(meterCategoryRuleService);
        this.restMeterCategoryRuleMockMvc = MockMvcBuilders.standaloneSetup(meterCategoryRuleResource)
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
    public static MeterCategoryRule createEntity(EntityManager em) {
        MeterCategoryRule meterCategoryRule = new MeterCategoryRule()
            .meterCategoryCode(DEFAULT_METER_CATEGORY_CODE)
            .meterCategoryName(DEFAULT_METER_CATEGORY_NAME)
            .ruleCode(DEFAULT_RULE_CODE)
            .ruleName(DEFAULT_RULE_NAME)
            .analysis(DEFAULT_ANALYSIS)
            .createdBy(DEFAULT_CREATED_BY)
            .createTime(DEFAULT_CREATE_TIME)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updateTime(DEFAULT_UPDATE_TIME);
        return meterCategoryRule;
    }

    @Before
    public void initTest() {
        meterCategoryRule = createEntity(em);
    }

    @Test
    @Transactional
    public void createMeterCategoryRule() throws Exception {
        int databaseSizeBeforeCreate = meterCategoryRuleRepository.findAll().size();

        // Create the MeterCategoryRule
        restMeterCategoryRuleMockMvc.perform(post("/api/meter-category-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterCategoryRule)))
            .andExpect(status().isCreated());

        // Validate the MeterCategoryRule in the database
        List<MeterCategoryRule> meterCategoryRuleList = meterCategoryRuleRepository.findAll();
        assertThat(meterCategoryRuleList).hasSize(databaseSizeBeforeCreate + 1);
        MeterCategoryRule testMeterCategoryRule = meterCategoryRuleList.get(meterCategoryRuleList.size() - 1);
        assertThat(testMeterCategoryRule.getMeterCategoryCode()).isEqualTo(DEFAULT_METER_CATEGORY_CODE);
        assertThat(testMeterCategoryRule.getMeterCategoryName()).isEqualTo(DEFAULT_METER_CATEGORY_NAME);
        assertThat(testMeterCategoryRule.getRuleCode()).isEqualTo(DEFAULT_RULE_CODE);
        assertThat(testMeterCategoryRule.getRuleName()).isEqualTo(DEFAULT_RULE_NAME);
        assertThat(testMeterCategoryRule.getAnalysis()).isEqualTo(DEFAULT_ANALYSIS);
        assertThat(testMeterCategoryRule.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testMeterCategoryRule.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testMeterCategoryRule.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testMeterCategoryRule.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void createMeterCategoryRuleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = meterCategoryRuleRepository.findAll().size();

        // Create the MeterCategoryRule with an existing ID
        meterCategoryRule.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMeterCategoryRuleMockMvc.perform(post("/api/meter-category-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterCategoryRule)))
            .andExpect(status().isBadRequest());

        // Validate the MeterCategoryRule in the database
        List<MeterCategoryRule> meterCategoryRuleList = meterCategoryRuleRepository.findAll();
        assertThat(meterCategoryRuleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMeterCategoryCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterCategoryRuleRepository.findAll().size();
        // set the field null
        meterCategoryRule.setMeterCategoryCode(null);

        // Create the MeterCategoryRule, which fails.

        restMeterCategoryRuleMockMvc.perform(post("/api/meter-category-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterCategoryRule)))
            .andExpect(status().isBadRequest());

        List<MeterCategoryRule> meterCategoryRuleList = meterCategoryRuleRepository.findAll();
        assertThat(meterCategoryRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMeterCategoryNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterCategoryRuleRepository.findAll().size();
        // set the field null
        meterCategoryRule.setMeterCategoryName(null);

        // Create the MeterCategoryRule, which fails.

        restMeterCategoryRuleMockMvc.perform(post("/api/meter-category-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterCategoryRule)))
            .andExpect(status().isBadRequest());

        List<MeterCategoryRule> meterCategoryRuleList = meterCategoryRuleRepository.findAll();
        assertThat(meterCategoryRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRuleCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterCategoryRuleRepository.findAll().size();
        // set the field null
        meterCategoryRule.setRuleCode(null);

        // Create the MeterCategoryRule, which fails.

        restMeterCategoryRuleMockMvc.perform(post("/api/meter-category-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterCategoryRule)))
            .andExpect(status().isBadRequest());

        List<MeterCategoryRule> meterCategoryRuleList = meterCategoryRuleRepository.findAll();
        assertThat(meterCategoryRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRuleNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterCategoryRuleRepository.findAll().size();
        // set the field null
        meterCategoryRule.setRuleName(null);

        // Create the MeterCategoryRule, which fails.

        restMeterCategoryRuleMockMvc.perform(post("/api/meter-category-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterCategoryRule)))
            .andExpect(status().isBadRequest());

        List<MeterCategoryRule> meterCategoryRuleList = meterCategoryRuleRepository.findAll();
        assertThat(meterCategoryRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAnalysisIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterCategoryRuleRepository.findAll().size();
        // set the field null
        meterCategoryRule.setAnalysis(null);

        // Create the MeterCategoryRule, which fails.

        restMeterCategoryRuleMockMvc.perform(post("/api/meter-category-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterCategoryRule)))
            .andExpect(status().isBadRequest());

        List<MeterCategoryRule> meterCategoryRuleList = meterCategoryRuleRepository.findAll();
        assertThat(meterCategoryRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterCategoryRuleRepository.findAll().size();
        // set the field null
        meterCategoryRule.setCreatedBy(null);

        // Create the MeterCategoryRule, which fails.

        restMeterCategoryRuleMockMvc.perform(post("/api/meter-category-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterCategoryRule)))
            .andExpect(status().isBadRequest());

        List<MeterCategoryRule> meterCategoryRuleList = meterCategoryRuleRepository.findAll();
        assertThat(meterCategoryRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterCategoryRuleRepository.findAll().size();
        // set the field null
        meterCategoryRule.setCreateTime(null);

        // Create the MeterCategoryRule, which fails.

        restMeterCategoryRuleMockMvc.perform(post("/api/meter-category-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterCategoryRule)))
            .andExpect(status().isBadRequest());

        List<MeterCategoryRule> meterCategoryRuleList = meterCategoryRuleRepository.findAll();
        assertThat(meterCategoryRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterCategoryRuleRepository.findAll().size();
        // set the field null
        meterCategoryRule.setUpdatedBy(null);

        // Create the MeterCategoryRule, which fails.

        restMeterCategoryRuleMockMvc.perform(post("/api/meter-category-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterCategoryRule)))
            .andExpect(status().isBadRequest());

        List<MeterCategoryRule> meterCategoryRuleList = meterCategoryRuleRepository.findAll();
        assertThat(meterCategoryRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterCategoryRuleRepository.findAll().size();
        // set the field null
        meterCategoryRule.setUpdateTime(null);

        // Create the MeterCategoryRule, which fails.

        restMeterCategoryRuleMockMvc.perform(post("/api/meter-category-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterCategoryRule)))
            .andExpect(status().isBadRequest());

        List<MeterCategoryRule> meterCategoryRuleList = meterCategoryRuleRepository.findAll();
        assertThat(meterCategoryRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMeterCategoryRules() throws Exception {
        // Initialize the database
        meterCategoryRuleRepository.saveAndFlush(meterCategoryRule);

        // Get all the meterCategoryRuleList
        restMeterCategoryRuleMockMvc.perform(get("/api/meter-category-rules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(meterCategoryRule.getId().intValue())))
            .andExpect(jsonPath("$.[*].meterCategoryCode").value(hasItem(DEFAULT_METER_CATEGORY_CODE)))
            .andExpect(jsonPath("$.[*].meterCategoryName").value(hasItem(DEFAULT_METER_CATEGORY_NAME.toString())))
            .andExpect(jsonPath("$.[*].ruleCode").value(hasItem(DEFAULT_RULE_CODE.toString())))
            .andExpect(jsonPath("$.[*].ruleName").value(hasItem(DEFAULT_RULE_NAME.toString())))
            .andExpect(jsonPath("$.[*].analysis").value(hasItem(DEFAULT_ANALYSIS.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())));
    }

    @Test
    @Transactional
    public void getMeterCategoryRule() throws Exception {
        // Initialize the database
        meterCategoryRuleRepository.saveAndFlush(meterCategoryRule);

        // Get the meterCategoryRule
        restMeterCategoryRuleMockMvc.perform(get("/api/meter-category-rules/{id}", meterCategoryRule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(meterCategoryRule.getId().intValue()))
            .andExpect(jsonPath("$.meterCategoryCode").value(DEFAULT_METER_CATEGORY_CODE))
            .andExpect(jsonPath("$.meterCategoryName").value(DEFAULT_METER_CATEGORY_NAME.toString()))
            .andExpect(jsonPath("$.ruleCode").value(DEFAULT_RULE_CODE.toString()))
            .andExpect(jsonPath("$.ruleName").value(DEFAULT_RULE_NAME.toString()))
            .andExpect(jsonPath("$.analysis").value(DEFAULT_ANALYSIS.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMeterCategoryRule() throws Exception {
        // Get the meterCategoryRule
        restMeterCategoryRuleMockMvc.perform(get("/api/meter-category-rules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMeterCategoryRule() throws Exception {
        // Initialize the database
        meterCategoryRuleService.save(meterCategoryRule);

        int databaseSizeBeforeUpdate = meterCategoryRuleRepository.findAll().size();

        // Update the meterCategoryRule
        MeterCategoryRule updatedMeterCategoryRule = meterCategoryRuleRepository.findOne(meterCategoryRule.getId());
        updatedMeterCategoryRule
            .meterCategoryCode(UPDATED_METER_CATEGORY_CODE)
            .meterCategoryName(UPDATED_METER_CATEGORY_NAME)
            .ruleCode(UPDATED_RULE_CODE)
            .ruleName(UPDATED_RULE_NAME)
            .analysis(UPDATED_ANALYSIS)
            .createdBy(UPDATED_CREATED_BY)
            .createTime(UPDATED_CREATE_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updateTime(UPDATED_UPDATE_TIME);

        restMeterCategoryRuleMockMvc.perform(put("/api/meter-category-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMeterCategoryRule)))
            .andExpect(status().isOk());

        // Validate the MeterCategoryRule in the database
        List<MeterCategoryRule> meterCategoryRuleList = meterCategoryRuleRepository.findAll();
        assertThat(meterCategoryRuleList).hasSize(databaseSizeBeforeUpdate);
        MeterCategoryRule testMeterCategoryRule = meterCategoryRuleList.get(meterCategoryRuleList.size() - 1);
        assertThat(testMeterCategoryRule.getMeterCategoryCode()).isEqualTo(UPDATED_METER_CATEGORY_CODE);
        assertThat(testMeterCategoryRule.getMeterCategoryName()).isEqualTo(UPDATED_METER_CATEGORY_NAME);
        assertThat(testMeterCategoryRule.getRuleCode()).isEqualTo(UPDATED_RULE_CODE);
        assertThat(testMeterCategoryRule.getRuleName()).isEqualTo(UPDATED_RULE_NAME);
        assertThat(testMeterCategoryRule.getAnalysis()).isEqualTo(UPDATED_ANALYSIS);
        assertThat(testMeterCategoryRule.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testMeterCategoryRule.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testMeterCategoryRule.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testMeterCategoryRule.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingMeterCategoryRule() throws Exception {
        int databaseSizeBeforeUpdate = meterCategoryRuleRepository.findAll().size();

        // Create the MeterCategoryRule

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMeterCategoryRuleMockMvc.perform(put("/api/meter-category-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterCategoryRule)))
            .andExpect(status().isCreated());

        // Validate the MeterCategoryRule in the database
        List<MeterCategoryRule> meterCategoryRuleList = meterCategoryRuleRepository.findAll();
        assertThat(meterCategoryRuleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMeterCategoryRule() throws Exception {
        // Initialize the database
        meterCategoryRuleService.save(meterCategoryRule);

        int databaseSizeBeforeDelete = meterCategoryRuleRepository.findAll().size();

        // Get the meterCategoryRule
        restMeterCategoryRuleMockMvc.perform(delete("/api/meter-category-rules/{id}", meterCategoryRule.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MeterCategoryRule> meterCategoryRuleList = meterCategoryRuleRepository.findAll();
        assertThat(meterCategoryRuleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MeterCategoryRule.class);
        MeterCategoryRule meterCategoryRule1 = new MeterCategoryRule();
        meterCategoryRule1.setId(1L);
        MeterCategoryRule meterCategoryRule2 = new MeterCategoryRule();
        meterCategoryRule2.setId(meterCategoryRule1.getId());
        assertThat(meterCategoryRule1).isEqualTo(meterCategoryRule2);
        meterCategoryRule2.setId(2L);
        assertThat(meterCategoryRule1).isNotEqualTo(meterCategoryRule2);
        meterCategoryRule1.setId(null);
        assertThat(meterCategoryRule1).isNotEqualTo(meterCategoryRule2);
    }
}
