package com.project.web.rest;

import com.project.Jeta123App;

import com.project.domain.City;
import com.project.domain.WarehouseLocation;
import com.project.repository.CityRepository;
import com.project.repository.WarehouseLocationRepository;
import com.project.service.CityService;
import com.project.service.WarehouseLocationService;
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
 * Test class for the WarehouseLocationResource REST controller.
 *
 * @see WarehouseLocationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Jeta123App.class)
public class WarehouseLocationResourceIntTest {

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    @Autowired
    private WarehouseLocationRepository warehouseLocationRepository;

    @Autowired
    private WarehouseLocationService warehouseLocationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWarehouseLocationMockMvc;

    private WarehouseLocation warehouseLocation;
    @Autowired
    private CityService cityService;

    private City city;

    @Autowired
    private CityRepository cityRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WarehouseLocationResource warehouseLocationResource = new WarehouseLocationResource(warehouseLocationService, cityService, em);
        this.restWarehouseLocationMockMvc = MockMvcBuilders.standaloneSetup(warehouseLocationResource)
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
    public static WarehouseLocation createEntity(EntityManager em) {
        WarehouseLocation warehouseLocation = new WarehouseLocation()
            .address(DEFAULT_ADDRESS)
            .country(DEFAULT_COUNTRY);
        return warehouseLocation;
    }
    private  City createCity()
    {
        City city=new City().cityName(DEFAULT_COUNTRY);
        return city;
    }
    @Before
    public void initTest() {

        warehouseLocation = createEntity(em);

        city=createCity();
        cityRepository.save(city);
        warehouseLocation.setCity(city);

    }

    @Test
    @Transactional
    public void createWarehouseLocation() throws Exception {
        int databaseSizeBeforeCreate = warehouseLocationRepository.findAll().size();

        // Create the WarehouseLocation
        restWarehouseLocationMockMvc.perform(post("/api/warehouse-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(warehouseLocation)))
            .andExpect(status().isCreated());

        // Validate the WarehouseLocation in the database
        List<WarehouseLocation> warehouseLocationList = warehouseLocationRepository.findAll();
        assertThat(warehouseLocationList).hasSize(databaseSizeBeforeCreate + 1);
        WarehouseLocation testWarehouseLocation = warehouseLocationList.get(warehouseLocationList.size() - 1);
        assertThat(testWarehouseLocation.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testWarehouseLocation.getCountry()).isEqualTo(DEFAULT_COUNTRY);
    }

    @Test
    @Transactional
    public void createWarehouseLocationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = warehouseLocationRepository.findAll().size();

        // Create the WarehouseLocation with an existing ID
        warehouseLocation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWarehouseLocationMockMvc.perform(post("/api/warehouse-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(warehouseLocation)))
            .andExpect(status().isBadRequest());

        // Validate the WarehouseLocation in the database
        List<WarehouseLocation> warehouseLocationList = warehouseLocationRepository.findAll();
        assertThat(warehouseLocationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllWarehouseLocations() throws Exception {
        // Initialize the database
        warehouseLocationRepository.saveAndFlush(warehouseLocation);

        // Get all the warehouseLocationList
        restWarehouseLocationMockMvc.perform(get("/api/warehouses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(warehouseLocation.getId().intValue())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())));
    }
    @Test
    @Transactional
    public void getAllWarehousesSearch() throws Exception {
        // Initialize the database
        warehouseLocationRepository.saveAndFlush(warehouseLocation);

        // Get all the cityList
        restWarehouseLocationMockMvc.perform(get("/api/warehouses?search=id==" + warehouseLocation.getId() + "&sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())));



    }

    @Test
    @Transactional
    public void getWarehouseLocation() throws Exception {
        // Initialize the database
        warehouseLocationRepository.saveAndFlush(warehouseLocation);

        // Get the warehouseLocation
        restWarehouseLocationMockMvc.perform(get("/api/warehouse-locations/{id}", warehouseLocation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(warehouseLocation.getId().intValue()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWarehouseLocation() throws Exception {
        // Get the warehouseLocation
        restWarehouseLocationMockMvc.perform(get("/api/warehouse-locations/{id}", Long.MAX_VALUE))
            .andExpect(status().isInternalServerError());
    }

    @Test
    @Transactional
    public void updateWarehouseLocation() throws Exception {
        // Initialize the database
        warehouseLocationService.save(warehouseLocation);

        int databaseSizeBeforeUpdate = warehouseLocationRepository.findAll().size();

        // Update the warehouseLocation
        WarehouseLocation updatedWarehouseLocation = warehouseLocationRepository.findOne(warehouseLocation.getId());
        // Disconnect from session so that the updates on updatedWarehouseLocation are not directly saved in db
        em.detach(updatedWarehouseLocation);
        updatedWarehouseLocation
            .address(UPDATED_ADDRESS)
            .country(UPDATED_COUNTRY);

        restWarehouseLocationMockMvc.perform(put("/api/warehouse-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedWarehouseLocation)))
            .andExpect(status().isOk());

        // Validate the WarehouseLocation in the database
        List<WarehouseLocation> warehouseLocationList = warehouseLocationRepository.findAll();
        assertThat(warehouseLocationList).hasSize(databaseSizeBeforeUpdate);
        WarehouseLocation testWarehouseLocation = warehouseLocationList.get(warehouseLocationList.size() - 1);
        assertThat(testWarehouseLocation.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testWarehouseLocation.getCountry()).isEqualTo(UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    public void updateNonExistingWarehouseLocation() throws Exception {
        int databaseSizeBeforeUpdate = warehouseLocationRepository.findAll().size();

        // Create the WarehouseLocation

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWarehouseLocationMockMvc.perform(put("/api/warehouse-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(warehouseLocation)))
            .andExpect(status().isCreated());

        // Validate the WarehouseLocation in the database
        List<WarehouseLocation> warehouseLocationList = warehouseLocationRepository.findAll();
        assertThat(warehouseLocationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteWarehouseLocation() throws Exception {
        // Initialize the database
        warehouseLocationService.save(warehouseLocation);

        int databaseSizeBeforeDelete = warehouseLocationRepository.findAll().size();

        // Get the warehouseLocation
        restWarehouseLocationMockMvc.perform(delete("/api/warehouse-locations/{id}", warehouseLocation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<WarehouseLocation> warehouseLocationList = warehouseLocationRepository.findAll();
        assertThat(warehouseLocationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WarehouseLocation.class);
        WarehouseLocation warehouseLocation1 = new WarehouseLocation();
        warehouseLocation1.setId(1L);
        WarehouseLocation warehouseLocation2 = new WarehouseLocation();
        warehouseLocation2.setId(warehouseLocation1.getId());
        assertThat(warehouseLocation1).isEqualTo(warehouseLocation2);
        warehouseLocation2.setId(2L);
        assertThat(warehouseLocation1).isNotEqualTo(warehouseLocation2);
        warehouseLocation1.setId(null);
        assertThat(warehouseLocation1).isNotEqualTo(warehouseLocation2);
    }
}
