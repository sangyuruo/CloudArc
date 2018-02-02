package com.emcloud.arc.web.rest;

import com.emcloud.arc.EmCloudArcApp;

import com.emcloud.arc.config.SecurityBeanOverrideConfiguration;

import com.emcloud.arc.domain.RuleAttributes;
import com.emcloud.arc.repository.RuleAttributesRepository;
import com.emcloud.arc.service.RuleAttributesService;
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
 * Test class for the RuleAttributesResource REST controller.
 *
 * @see RuleAttributesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {EmCloudArcApp.class, SecurityBeanOverrideConfiguration.class})
public class RuleAttributesResourceIntTest {

    private static final String DEFAULT_RULE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_RULE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_RULE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_RULE_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_ALARM_LEVEL = 1;
    private static final Integer UPDATED_ALARM_LEVEL = 2;

    private static final Double DEFAULT_START_VALUE = 1D;
    private static final Double UPDATED_START_VALUE = 2D;

    private static final Double DEFAULT_END_VALUE = 1D;
    private static final Double UPDATED_END_VALUE = 2D;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private RuleAttributesRepository ruleAttributesRepository;

    @Autowired
    private RuleAttributesService ruleAttributesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRuleAttributesMockMvc;

    private RuleAttributes ruleAttributes;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RuleAttributesResource ruleAttributesResource = new RuleAttributesResource(ruleAttributesService);
        this.restRuleAttributesMockMvc = MockMvcBuilders.standaloneSetup(ruleAttributesResource)
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
    public static RuleAttributes createEntity(EntityManager em) {
        RuleAttributes ruleAttributes = new RuleAttributes()
            .ruleCode(DEFAULT_RULE_CODE)
            .ruleName(DEFAULT_RULE_NAME)
            .alarmLevel(DEFAULT_ALARM_LEVEL)
            .startValue(DEFAULT_START_VALUE)
            .endValue(DEFAULT_END_VALUE)
            .createdBy(DEFAULT_CREATED_BY)
            .createTime(DEFAULT_CREATE_TIME)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updateTime(DEFAULT_UPDATE_TIME);
        return ruleAttributes;
    }

    @Before
    public void initTest() {
        ruleAttributes = createEntity(em);
    }

    @Test
    @Transactional
    public void createRuleAttributes() throws Exception {
        int databaseSizeBeforeCreate = ruleAttributesRepository.findAll().size();

        // Create the RuleAttributes
        restRuleAttributesMockMvc.perform(post("/api/rule-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ruleAttributes)))
            .andExpect(status().isCreated());

        // Validate the RuleAttributes in the database
        List<RuleAttributes> ruleAttributesList = ruleAttributesRepository.findAll();
        assertThat(ruleAttributesList).hasSize(databaseSizeBeforeCreate + 1);
        RuleAttributes testRuleAttributes = ruleAttributesList.get(ruleAttributesList.size() - 1);
        assertThat(testRuleAttributes.getRuleCode()).isEqualTo(DEFAULT_RULE_CODE);
        assertThat(testRuleAttributes.getRuleName()).isEqualTo(DEFAULT_RULE_NAME);
        assertThat(testRuleAttributes.getAlarmLevel()).isEqualTo(DEFAULT_ALARM_LEVEL);
        assertThat(testRuleAttributes.getStartValue()).isEqualTo(DEFAULT_START_VALUE);
        assertThat(testRuleAttributes.getEndValue()).isEqualTo(DEFAULT_END_VALUE);
        assertThat(testRuleAttributes.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testRuleAttributes.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testRuleAttributes.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testRuleAttributes.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void createRuleAttributesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ruleAttributesRepository.findAll().size();

        // Create the RuleAttributes with an existing ID
        ruleAttributes.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRuleAttributesMockMvc.perform(post("/api/rule-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ruleAttributes)))
            .andExpect(status().isBadRequest());

        // Validate the RuleAttributes in the database
        List<RuleAttributes> ruleAttributesList = ruleAttributesRepository.findAll();
        assertThat(ruleAttributesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkRuleCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = ruleAttributesRepository.findAll().size();
        // set the field null
        ruleAttributes.setRuleCode(null);

        // Create the RuleAttributes, which fails.

        restRuleAttributesMockMvc.perform(post("/api/rule-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ruleAttributes)))
            .andExpect(status().isBadRequest());

        List<RuleAttributes> ruleAttributesList = ruleAttributesRepository.findAll();
        assertThat(ruleAttributesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRuleNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = ruleAttributesRepository.findAll().size();
        // set the field null
        ruleAttributes.setRuleName(null);

        // Create the RuleAttributes, which fails.

        restRuleAttributesMockMvc.perform(post("/api/rule-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ruleAttributes)))
            .andExpect(status().isBadRequest());

        List<RuleAttributes> ruleAttributesList = ruleAttributesRepository.findAll();
        assertThat(ruleAttributesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAlarmLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = ruleAttributesRepository.findAll().size();
        // set the field null
        ruleAttributes.setAlarmLevel(null);

        // Create the RuleAttributes, which fails.

        restRuleAttributesMockMvc.perform(post("/api/rule-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ruleAttributes)))
            .andExpect(status().isBadRequest());

        List<RuleAttributes> ruleAttributesList = ruleAttributesRepository.findAll();
        assertThat(ruleAttributesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = ruleAttributesRepository.findAll().size();
        // set the field null
        ruleAttributes.setStartValue(null);

        // Create the RuleAttributes, which fails.

        restRuleAttributesMockMvc.perform(post("/api/rule-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ruleAttributes)))
            .andExpect(status().isBadRequest());

        List<RuleAttributes> ruleAttributesList = ruleAttributesRepository.findAll();
        assertThat(ruleAttributesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = ruleAttributesRepository.findAll().size();
        // set the field null
        ruleAttributes.setEndValue(null);

        // Create the RuleAttributes, which fails.

        restRuleAttributesMockMvc.perform(post("/api/rule-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ruleAttributes)))
            .andExpect(status().isBadRequest());

        List<RuleAttributes> ruleAttributesList = ruleAttributesRepository.findAll();
        assertThat(ruleAttributesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = ruleAttributesRepository.findAll().size();
        // set the field null
        ruleAttributes.setCreatedBy(null);

        // Create the RuleAttributes, which fails.

        restRuleAttributesMockMvc.perform(post("/api/rule-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ruleAttributes)))
            .andExpect(status().isBadRequest());

        List<RuleAttributes> ruleAttributesList = ruleAttributesRepository.findAll();
        assertThat(ruleAttributesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = ruleAttributesRepository.findAll().size();
        // set the field null
        ruleAttributes.setCreateTime(null);

        // Create the RuleAttributes, which fails.

        restRuleAttributesMockMvc.perform(post("/api/rule-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ruleAttributes)))
            .andExpect(status().isBadRequest());

        List<RuleAttributes> ruleAttributesList = ruleAttributesRepository.findAll();
        assertThat(ruleAttributesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = ruleAttributesRepository.findAll().size();
        // set the field null
        ruleAttributes.setUpdatedBy(null);

        // Create the RuleAttributes, which fails.

        restRuleAttributesMockMvc.perform(post("/api/rule-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ruleAttributes)))
            .andExpect(status().isBadRequest());

        List<RuleAttributes> ruleAttributesList = ruleAttributesRepository.findAll();
        assertThat(ruleAttributesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = ruleAttributesRepository.findAll().size();
        // set the field null
        ruleAttributes.setUpdateTime(null);

        // Create the RuleAttributes, which fails.

        restRuleAttributesMockMvc.perform(post("/api/rule-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ruleAttributes)))
            .andExpect(status().isBadRequest());

        List<RuleAttributes> ruleAttributesList = ruleAttributesRepository.findAll();
        assertThat(ruleAttributesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRuleAttributes() throws Exception {
        // Initialize the database
        ruleAttributesRepository.saveAndFlush(ruleAttributes);

        // Get all the ruleAttributesList
        restRuleAttributesMockMvc.perform(get("/api/rule-attributes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ruleAttributes.getId().intValue())))
            .andExpect(jsonPath("$.[*].ruleCode").value(hasItem(DEFAULT_RULE_CODE.toString())))
            .andExpect(jsonPath("$.[*].ruleName").value(hasItem(DEFAULT_RULE_NAME.toString())))
            .andExpect(jsonPath("$.[*].alarmLevel").value(hasItem(DEFAULT_ALARM_LEVEL)))
            .andExpect(jsonPath("$.[*].startValue").value(hasItem(DEFAULT_START_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].endValue").value(hasItem(DEFAULT_END_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())));
    }

    @Test
    @Transactional
    public void getRuleAttributes() throws Exception {
        // Initialize the database
        ruleAttributesRepository.saveAndFlush(ruleAttributes);

        // Get the ruleAttributes
        restRuleAttributesMockMvc.perform(get("/api/rule-attributes/{id}", ruleAttributes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ruleAttributes.getId().intValue()))
            .andExpect(jsonPath("$.ruleCode").value(DEFAULT_RULE_CODE.toString()))
            .andExpect(jsonPath("$.ruleName").value(DEFAULT_RULE_NAME.toString()))
            .andExpect(jsonPath("$.alarmLevel").value(DEFAULT_ALARM_LEVEL))
            .andExpect(jsonPath("$.startValue").value(DEFAULT_START_VALUE.doubleValue()))
            .andExpect(jsonPath("$.endValue").value(DEFAULT_END_VALUE.doubleValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRuleAttributes() throws Exception {
        // Get the ruleAttributes
        restRuleAttributesMockMvc.perform(get("/api/rule-attributes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRuleAttributes() throws Exception {
        // Initialize the database
        ruleAttributesService.save(ruleAttributes);

        int databaseSizeBeforeUpdate = ruleAttributesRepository.findAll().size();

        // Update the ruleAttributes
        RuleAttributes updatedRuleAttributes = ruleAttributesRepository.findOne(ruleAttributes.getId());
        updatedRuleAttributes
            .ruleCode(UPDATED_RULE_CODE)
            .ruleName(UPDATED_RULE_NAME)
            .alarmLevel(UPDATED_ALARM_LEVEL)
            .startValue(UPDATED_START_VALUE)
            .endValue(UPDATED_END_VALUE)
            .createdBy(UPDATED_CREATED_BY)
            .createTime(UPDATED_CREATE_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updateTime(UPDATED_UPDATE_TIME);

        restRuleAttributesMockMvc.perform(put("/api/rule-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRuleAttributes)))
            .andExpect(status().isOk());

        // Validate the RuleAttributes in the database
        List<RuleAttributes> ruleAttributesList = ruleAttributesRepository.findAll();
        assertThat(ruleAttributesList).hasSize(databaseSizeBeforeUpdate);
        RuleAttributes testRuleAttributes = ruleAttributesList.get(ruleAttributesList.size() - 1);
        assertThat(testRuleAttributes.getRuleCode()).isEqualTo(UPDATED_RULE_CODE);
        assertThat(testRuleAttributes.getRuleName()).isEqualTo(UPDATED_RULE_NAME);
        assertThat(testRuleAttributes.getAlarmLevel()).isEqualTo(UPDATED_ALARM_LEVEL);
        assertThat(testRuleAttributes.getStartValue()).isEqualTo(UPDATED_START_VALUE);
        assertThat(testRuleAttributes.getEndValue()).isEqualTo(UPDATED_END_VALUE);
        assertThat(testRuleAttributes.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testRuleAttributes.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testRuleAttributes.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testRuleAttributes.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingRuleAttributes() throws Exception {
        int databaseSizeBeforeUpdate = ruleAttributesRepository.findAll().size();

        // Create the RuleAttributes

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRuleAttributesMockMvc.perform(put("/api/rule-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ruleAttributes)))
            .andExpect(status().isCreated());

        // Validate the RuleAttributes in the database
        List<RuleAttributes> ruleAttributesList = ruleAttributesRepository.findAll();
        assertThat(ruleAttributesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRuleAttributes() throws Exception {
        // Initialize the database
        ruleAttributesService.save(ruleAttributes);

        int databaseSizeBeforeDelete = ruleAttributesRepository.findAll().size();

        // Get the ruleAttributes
        restRuleAttributesMockMvc.perform(delete("/api/rule-attributes/{id}", ruleAttributes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RuleAttributes> ruleAttributesList = ruleAttributesRepository.findAll();
        assertThat(ruleAttributesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RuleAttributes.class);
        RuleAttributes ruleAttributes1 = new RuleAttributes();
        ruleAttributes1.setId(1L);
        RuleAttributes ruleAttributes2 = new RuleAttributes();
        ruleAttributes2.setId(ruleAttributes1.getId());
        assertThat(ruleAttributes1).isEqualTo(ruleAttributes2);
        ruleAttributes2.setId(2L);
        assertThat(ruleAttributes1).isNotEqualTo(ruleAttributes2);
        ruleAttributes1.setId(null);
        assertThat(ruleAttributes1).isNotEqualTo(ruleAttributes2);
    }
}
