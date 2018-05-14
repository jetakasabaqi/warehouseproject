package com.project.web.rest;

import com.project.Jeta123App;

import com.project.domain.Shipment;
import com.project.repository.ShipmentRepository;
import com.project.service.*;
import com.project.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Ignore;
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
 * Test class for the ShipmentResource REST controller.
 *
 * @see ShipmentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Jeta123App.class)
public class ShipmentResourceIntTest {

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private ShipmentService shipmentService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restShipmentMockMvc;

    private Shipment shipment;
    @Autowired
    private PersonService personService;
    @Autowired
    private VendorService vendorService;
    @Autowired
    private ReceiverInfoService receiverInfo;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private StatusService statusService;
    @Autowired
    private ProductService productService;
    @Autowired
    private WarehouseLocationService warehouseLocationService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ShipmentResource shipmentResource = new ShipmentResource(shipmentService, personService, receiverInfo, vendorService, employeeService, statusService, productService, warehouseLocationService);
        this.restShipmentMockMvc = MockMvcBuilders.standaloneSetup(shipmentResource)
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
    public static Shipment createEntity(EntityManager em) {
        Shipment shipment = new Shipment();
        return shipment;
    }

    @Before
    public void initTest() {
        shipment = createEntity(em);
    }

    @Test
    @Transactional
    public void createShipment() throws Exception {
        int databaseSizeBeforeCreate = shipmentRepository.findAll().size();

        // Create the Shipment
        restShipmentMockMvc.perform(post("/api/shipments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipment)))
            .andExpect(status().isCreated());

        // Validate the Shipment in the database
        List<Shipment> shipmentList = shipmentRepository.findAll();
        assertThat(shipmentList).hasSize(databaseSizeBeforeCreate + 1);
        Shipment testShipment = shipmentList.get(shipmentList.size() - 1);
    }

    @Test
    @Transactional
    public void createShipmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shipmentRepository.findAll().size();

        // Create the Shipment with an existing ID
        shipment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShipmentMockMvc.perform(post("/api/shipments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipment)))
            .andExpect(status().isBadRequest());

        // Validate the Shipment in the database
        List<Shipment> shipmentList = shipmentRepository.findAll();
        assertThat(shipmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllShipments() throws Exception {
        // Initialize the database
        shipmentRepository.saveAndFlush(shipment);

        // Get all the shipmentList
        restShipmentMockMvc.perform(get("/api/shipments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shipment.getId().intValue())));
    }

    @Test
    @Transactional
    public void getShipment() throws Exception {
        // Initialize the database
        shipmentRepository.saveAndFlush(shipment);

        // Get the shipment
        restShipmentMockMvc.perform(get("/api/shipments/{id}", shipment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(shipment.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingShipment() throws Exception {
        // Get the shipment
        restShipmentMockMvc.perform(get("/api/shipments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShipment() throws Exception {
        // Initialize the database
        shipmentService.save(shipment);

        int databaseSizeBeforeUpdate = shipmentRepository.findAll().size();

        // Update the shipment
        Shipment updatedShipment = shipmentRepository.findOne(shipment.getId());
        // Disconnect from session so that the updates on updatedShipment are not directly saved in db
        em.detach(updatedShipment);

        restShipmentMockMvc.perform(put("/api/shipments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedShipment)))
            .andExpect(status().isOk());

        // Validate the Shipment in the database
        List<Shipment> shipmentList = shipmentRepository.findAll();
        assertThat(shipmentList).hasSize(databaseSizeBeforeUpdate);
        Shipment testShipment = shipmentList.get(shipmentList.size() - 1);
    }


    @Test
    @Transactional
    public void updateNonExistingShipment() throws Exception {
        int databaseSizeBeforeUpdate = shipmentRepository.findAll().size();

        // Create the Shipment

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restShipmentMockMvc.perform(put("/api/shipments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipment)))
            .andExpect(status().isCreated());

        // Validate the Shipment in the database
        List<Shipment> shipmentList = shipmentRepository.findAll();
        assertThat(shipmentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteShipment() throws Exception {
        // Initialize the database
        shipmentService.save(shipment);

        int databaseSizeBeforeDelete = shipmentRepository.findAll().size();

        // Get the shipment
        restShipmentMockMvc.perform(delete("/api/shipments/{id}", shipment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Shipment> shipmentList = shipmentRepository.findAll();
        assertThat(shipmentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Shipment.class);
        Shipment shipment1 = new Shipment();
        shipment1.setId(1L);
        Shipment shipment2 = new Shipment();
        shipment2.setId(shipment1.getId());
        assertThat(shipment1).isEqualTo(shipment2);
        shipment2.setId(2L);
        assertThat(shipment1).isNotEqualTo(shipment2);
        shipment1.setId(null);
        assertThat(shipment1).isNotEqualTo(shipment2);
    }
}
