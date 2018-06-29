package com.project.web.rest;

import com.project.Jeta123App;

import com.project.domain.ReceiverInfo;
import com.project.repository.ReceiverInfoRepository;
import com.project.service.ReceiverInfoService;
import com.project.web.rest.errors.ExceptionTranslator;

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
import java.util.List;

import static com.project.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ReceiverInfoResource REST controller.
 *
 * @see ReceiverInfoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Jeta123App.class)
public class ReceiverInfoResourceIntTest {

    private static final String DEFAULT_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_ZIP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ZIP_CODE = "BBBBBBBBBB";

    @Autowired
    private ReceiverInfoRepository receiverInfoRepository;

    @Autowired
    private ReceiverInfoService receiverInfoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restReceiverInfoMockMvc;

    private ReceiverInfo receiverInfo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReceiverInfoResource receiverInfoResource = new ReceiverInfoResource(receiverInfoService, em);
        this.restReceiverInfoMockMvc = MockMvcBuilders.standaloneSetup(receiverInfoResource)
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
    public static ReceiverInfo createEntity(EntityManager em) {
        ReceiverInfo receiverInfo = new ReceiverInfo()
            .fullName(DEFAULT_FULL_NAME)
            .address(DEFAULT_ADDRESS)
            .zipCode(DEFAULT_ZIP_CODE);
        return receiverInfo;
    }

    @Before
    public void initTest() {
        receiverInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createReceiverInfo() throws Exception {
        int databaseSizeBeforeCreate = receiverInfoRepository.findAll().size();

        // Create the ReceiverInfo
        restReceiverInfoMockMvc.perform(post("/api/receiver-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(receiverInfo)))
            .andExpect(status().isCreated());

        // Validate the ReceiverInfo in the database
        List<ReceiverInfo> receiverInfoList = receiverInfoRepository.findAll();
        assertThat(receiverInfoList).hasSize(databaseSizeBeforeCreate + 1);
        ReceiverInfo testReceiverInfo = receiverInfoList.get(receiverInfoList.size() - 1);
        assertThat(testReceiverInfo.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testReceiverInfo.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testReceiverInfo.getZipCode()).isEqualTo(DEFAULT_ZIP_CODE);
    }

    @Test
    @Transactional
    public void createReceiverInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = receiverInfoRepository.findAll().size();

        // Create the ReceiverInfo with an existing ID
        receiverInfo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReceiverInfoMockMvc.perform(post("/api/receiver-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(receiverInfo)))
            .andExpect(status().isBadRequest());

        // Validate the ReceiverInfo in the database
        List<ReceiverInfo> receiverInfoList = receiverInfoRepository.findAll();
        assertThat(receiverInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllReceiverInfos() throws Exception {
        // Initialize the database
        receiverInfoRepository.saveAndFlush(receiverInfo);

        // Get all the receiverInfoList
        restReceiverInfoMockMvc.perform(get("/api/receivers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(receiverInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].zipCode").value(hasItem(DEFAULT_ZIP_CODE.toString())));
    }
    @Test
    @Transactional
    public void getAllReceiversSearch() throws Exception {
        // Initialize the database
        receiverInfoRepository.saveAndFlush(receiverInfo);

        // Get all the cityList
        restReceiverInfoMockMvc.perform(get("/api/receivers?search=address==" + receiverInfo.getAddress() + "&sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(receiverInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].zipCode").value(hasItem(DEFAULT_ZIP_CODE.toString())));

    }

    @Test
    @Transactional
    public void getReceiverInfo() throws Exception {
        // Initialize the database
        receiverInfoRepository.saveAndFlush(receiverInfo);

        // Get the receiverInfo
        restReceiverInfoMockMvc.perform(get("/api/receiver-infos/{id}", receiverInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(receiverInfo.getId().intValue()))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.zipCode").value(DEFAULT_ZIP_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReceiverInfo() throws Exception {
        // Get the receiverInfo
        restReceiverInfoMockMvc.perform(get("/api/receiver-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isInternalServerError());
    }

    @Test
    @Transactional
    public void updateReceiverInfo() throws Exception {
        // Initialize the database
        receiverInfoService.save(receiverInfo);

        int databaseSizeBeforeUpdate = receiverInfoRepository.findAll().size();

        // Update the receiverInfo
        ReceiverInfo updatedReceiverInfo = receiverInfoRepository.findOne(receiverInfo.getId());
        // Disconnect from session so that the updates on updatedReceiverInfo are not directly saved in db
        em.detach(updatedReceiverInfo);
        updatedReceiverInfo
            .fullName(UPDATED_FULL_NAME)
            .address(UPDATED_ADDRESS)
            .zipCode(UPDATED_ZIP_CODE);

        restReceiverInfoMockMvc.perform(put("/api/receiver-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedReceiverInfo)))
            .andExpect(status().isOk());

        // Validate the ReceiverInfo in the database
        List<ReceiverInfo> receiverInfoList = receiverInfoRepository.findAll();
        assertThat(receiverInfoList).hasSize(databaseSizeBeforeUpdate);
        ReceiverInfo testReceiverInfo = receiverInfoList.get(receiverInfoList.size() - 1);
        assertThat(testReceiverInfo.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testReceiverInfo.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testReceiverInfo.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingReceiverInfo() throws Exception {
        int databaseSizeBeforeUpdate = receiverInfoRepository.findAll().size();

        // Create the ReceiverInfo

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restReceiverInfoMockMvc.perform(put("/api/receiver-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(receiverInfo)))
            .andExpect(status().isCreated());

        // Validate the ReceiverInfo in the database
        List<ReceiverInfo> receiverInfoList = receiverInfoRepository.findAll();
        assertThat(receiverInfoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteReceiverInfo() throws Exception {
        // Initialize the database
        receiverInfoService.save(receiverInfo);

        int databaseSizeBeforeDelete = receiverInfoRepository.findAll().size();

        // Get the receiverInfo
        restReceiverInfoMockMvc.perform(delete("/api/receiver-infos/{id}", receiverInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ReceiverInfo> receiverInfoList = receiverInfoRepository.findAll();
        assertThat(receiverInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReceiverInfo.class);
        ReceiverInfo receiverInfo1 = new ReceiverInfo();
        receiverInfo1.setId(1L);
        ReceiverInfo receiverInfo2 = new ReceiverInfo();
        receiverInfo2.setId(receiverInfo1.getId());
        assertThat(receiverInfo1).isEqualTo(receiverInfo2);
        receiverInfo2.setId(2L);
        assertThat(receiverInfo1).isNotEqualTo(receiverInfo2);
        receiverInfo1.setId(null);
        assertThat(receiverInfo1).isNotEqualTo(receiverInfo2);
    }
}
