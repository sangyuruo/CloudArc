package com.emcloud.arc.web.rest;

import com.emcloud.arc.EmCloudArcApp;

import com.emcloud.arc.config.SecurityBeanOverrideConfiguration;

import com.emcloud.arc.domain.AnalysisEngine;
import com.emcloud.arc.repository.AnalysisEngineRepository;
import com.emcloud.arc.service.AnalysisEngineService;
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
 * Test class for the AnalysisEngineResource REST controller.
 *
 * @see AnalysisEngineResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {EmCloudArcApp.class, SecurityBeanOverrideConfiguration.class})
public class AnalysisEngineResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

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
    private AnalysisEngineRepository analysisEngineRepository;

    @Autowired
    private AnalysisEngineService analysisEngineService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAnalysisEngineMockMvc;

    private AnalysisEngine analysisEngine;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AnalysisEngineResource analysisEngineResource = new AnalysisEngineResource(analysisEngineService);
        this.restAnalysisEngineMockMvc = MockMvcBuilders.standaloneSetup(analysisEngineResource)
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
    public static AnalysisEngine createEntity(EntityManager em) {
        AnalysisEngine analysisEngine = new AnalysisEngine()
            .name(DEFAULT_NAME)
            .analysis(DEFAULT_ANALYSIS)
            .enable(DEFAULT_ENABLE)
            .createdBy(DEFAULT_CREATED_BY)
            .createTime(DEFAULT_CREATE_TIME)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updateTime(DEFAULT_UPDATE_TIME);
        return analysisEngine;
    }

    @Before
    public void initTest() {
        analysisEngine = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnalysisEngine() throws Exception {
        int databaseSizeBeforeCreate = analysisEngineRepository.findAll().size();

        // Create the AnalysisEngine
        restAnalysisEngineMockMvc.perform(post("/api/analysis-engines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(analysisEngine)))
            .andExpect(status().isCreated());

        // Validate the AnalysisEngine in the database
        List<AnalysisEngine> analysisEngineList = analysisEngineRepository.findAll();
        assertThat(analysisEngineList).hasSize(databaseSizeBeforeCreate + 1);
        AnalysisEngine testAnalysisEngine = analysisEngineList.get(analysisEngineList.size() - 1);
        assertThat(testAnalysisEngine.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAnalysisEngine.getAnalysis()).isEqualTo(DEFAULT_ANALYSIS);
        assertThat(testAnalysisEngine.isEnable()).isEqualTo(DEFAULT_ENABLE);
        assertThat(testAnalysisEngine.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testAnalysisEngine.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testAnalysisEngine.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testAnalysisEngine.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void createAnalysisEngineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = analysisEngineRepository.findAll().size();

        // Create the AnalysisEngine with an existing ID
        analysisEngine.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnalysisEngineMockMvc.perform(post("/api/analysis-engines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(analysisEngine)))
            .andExpect(status().isBadRequest());

        // Validate the AnalysisEngine in the database
        List<AnalysisEngine> analysisEngineList = analysisEngineRepository.findAll();
        assertThat(analysisEngineList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = analysisEngineRepository.findAll().size();
        // set the field null
        analysisEngine.setName(null);

        // Create the AnalysisEngine, which fails.

        restAnalysisEngineMockMvc.perform(post("/api/analysis-engines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(analysisEngine)))
            .andExpect(status().isBadRequest());

        List<AnalysisEngine> analysisEngineList = analysisEngineRepository.findAll();
        assertThat(analysisEngineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAnalysisIsRequired() throws Exception {
        int databaseSizeBeforeTest = analysisEngineRepository.findAll().size();
        // set the field null
        analysisEngine.setAnalysis(null);

        // Create the AnalysisEngine, which fails.

        restAnalysisEngineMockMvc.perform(post("/api/analysis-engines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(analysisEngine)))
            .andExpect(status().isBadRequest());

        List<AnalysisEngine> analysisEngineList = analysisEngineRepository.findAll();
        assertThat(analysisEngineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnableIsRequired() throws Exception {
        int databaseSizeBeforeTest = analysisEngineRepository.findAll().size();
        // set the field null
        analysisEngine.setEnable(null);

        // Create the AnalysisEngine, which fails.

        restAnalysisEngineMockMvc.perform(post("/api/analysis-engines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(analysisEngine)))
            .andExpect(status().isBadRequest());

        List<AnalysisEngine> analysisEngineList = analysisEngineRepository.findAll();
        assertThat(analysisEngineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = analysisEngineRepository.findAll().size();
        // set the field null
        analysisEngine.setCreatedBy(null);

        // Create the AnalysisEngine, which fails.

        restAnalysisEngineMockMvc.perform(post("/api/analysis-engines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(analysisEngine)))
            .andExpect(status().isBadRequest());

        List<AnalysisEngine> analysisEngineList = analysisEngineRepository.findAll();
        assertThat(analysisEngineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = analysisEngineRepository.findAll().size();
        // set the field null
        analysisEngine.setCreateTime(null);

        // Create the AnalysisEngine, which fails.

        restAnalysisEngineMockMvc.perform(post("/api/analysis-engines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(analysisEngine)))
            .andExpect(status().isBadRequest());

        List<AnalysisEngine> analysisEngineList = analysisEngineRepository.findAll();
        assertThat(analysisEngineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = analysisEngineRepository.findAll().size();
        // set the field null
        analysisEngine.setUpdatedBy(null);

        // Create the AnalysisEngine, which fails.

        restAnalysisEngineMockMvc.perform(post("/api/analysis-engines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(analysisEngine)))
            .andExpect(status().isBadRequest());

        List<AnalysisEngine> analysisEngineList = analysisEngineRepository.findAll();
        assertThat(analysisEngineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = analysisEngineRepository.findAll().size();
        // set the field null
        analysisEngine.setUpdateTime(null);

        // Create the AnalysisEngine, which fails.

        restAnalysisEngineMockMvc.perform(post("/api/analysis-engines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(analysisEngine)))
            .andExpect(status().isBadRequest());

        List<AnalysisEngine> analysisEngineList = analysisEngineRepository.findAll();
        assertThat(analysisEngineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAnalysisEngines() throws Exception {
        // Initialize the database
        analysisEngineRepository.saveAndFlush(analysisEngine);

        // Get all the analysisEngineList
        restAnalysisEngineMockMvc.perform(get("/api/analysis-engines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(analysisEngine.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].analysis").value(hasItem(DEFAULT_ANALYSIS.toString())))
            .andExpect(jsonPath("$.[*].enable").value(hasItem(DEFAULT_ENABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())));
    }

    @Test
    @Transactional
    public void getAnalysisEngine() throws Exception {
        // Initialize the database
        analysisEngineRepository.saveAndFlush(analysisEngine);

        // Get the analysisEngine
        restAnalysisEngineMockMvc.perform(get("/api/analysis-engines/{id}", analysisEngine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(analysisEngine.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.analysis").value(DEFAULT_ANALYSIS.toString()))
            .andExpect(jsonPath("$.enable").value(DEFAULT_ENABLE.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAnalysisEngine() throws Exception {
        // Get the analysisEngine
        restAnalysisEngineMockMvc.perform(get("/api/analysis-engines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnalysisEngine() throws Exception {
        // Initialize the database
        analysisEngineService.save(analysisEngine);

        int databaseSizeBeforeUpdate = analysisEngineRepository.findAll().size();

        // Update the analysisEngine
        AnalysisEngine updatedAnalysisEngine = analysisEngineRepository.findOne(analysisEngine.getId());
        // Disconnect from session so that the updates on updatedAnalysisEngine are not directly saved in db
        em.detach(updatedAnalysisEngine);
        updatedAnalysisEngine
            .name(UPDATED_NAME)
            .analysis(UPDATED_ANALYSIS)
            .enable(UPDATED_ENABLE)
            .createdBy(UPDATED_CREATED_BY)
            .createTime(UPDATED_CREATE_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updateTime(UPDATED_UPDATE_TIME);

        restAnalysisEngineMockMvc.perform(put("/api/analysis-engines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAnalysisEngine)))
            .andExpect(status().isOk());

        // Validate the AnalysisEngine in the database
        List<AnalysisEngine> analysisEngineList = analysisEngineRepository.findAll();
        assertThat(analysisEngineList).hasSize(databaseSizeBeforeUpdate);
        AnalysisEngine testAnalysisEngine = analysisEngineList.get(analysisEngineList.size() - 1);
        assertThat(testAnalysisEngine.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAnalysisEngine.getAnalysis()).isEqualTo(UPDATED_ANALYSIS);
        assertThat(testAnalysisEngine.isEnable()).isEqualTo(UPDATED_ENABLE);
        assertThat(testAnalysisEngine.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAnalysisEngine.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testAnalysisEngine.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testAnalysisEngine.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingAnalysisEngine() throws Exception {
        int databaseSizeBeforeUpdate = analysisEngineRepository.findAll().size();

        // Create the AnalysisEngine

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAnalysisEngineMockMvc.perform(put("/api/analysis-engines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(analysisEngine)))
            .andExpect(status().isCreated());

        // Validate the AnalysisEngine in the database
        List<AnalysisEngine> analysisEngineList = analysisEngineRepository.findAll();
        assertThat(analysisEngineList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAnalysisEngine() throws Exception {
        // Initialize the database
        analysisEngineService.save(analysisEngine);

        int databaseSizeBeforeDelete = analysisEngineRepository.findAll().size();

        // Get the analysisEngine
        restAnalysisEngineMockMvc.perform(delete("/api/analysis-engines/{id}", analysisEngine.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AnalysisEngine> analysisEngineList = analysisEngineRepository.findAll();
        assertThat(analysisEngineList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnalysisEngine.class);
        AnalysisEngine analysisEngine1 = new AnalysisEngine();
        analysisEngine1.setId(1L);
        AnalysisEngine analysisEngine2 = new AnalysisEngine();
        analysisEngine2.setId(analysisEngine1.getId());
        assertThat(analysisEngine1).isEqualTo(analysisEngine2);
        analysisEngine2.setId(2L);
        assertThat(analysisEngine1).isNotEqualTo(analysisEngine2);
        analysisEngine1.setId(null);
        assertThat(analysisEngine1).isNotEqualTo(analysisEngine2);
    }
}
