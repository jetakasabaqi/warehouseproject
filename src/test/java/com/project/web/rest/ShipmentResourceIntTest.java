package com.project.web.rest;

import com.project.Jeta123App;

import com.project.domain.*;
import com.project.repository.*;
import com.project.service.*;
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
import java.math.BigDecimal;
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
    private ReceiverInfoService receiverInfoService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private StatusService statusService;
    @Autowired
    private ProductService productService;
    @Autowired
    private WarehouseLocationService warehouseLocationService;

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private VendorRepository vendorRepository;
    @Autowired
    private ReceiverInfoRepository repository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private WarehouseLocationRepository warehouseLocationRepository;
    @Autowired
    private  ReceiverInfoRepository receiverInfoRepository;

    private static final String DEFAULT_NAME="Blerim";

    private static final String DEFAULT_LASTNAME="Kabashi";

    private static final String DEFAULT_ADDRESS="Dardania";

    private static final String DEFAULT_TEL="044896571";

    private static final String DEFAULT_EMAIL="BlerimKabashi@live.com";

    private static final String DEFAULT_AGE="35";

    private static final String DEFAULT_WEBSITE="www.projekti.com";

    private static final String DEFAULT_CONTACTPERSON="artab@gmail.com";

    private static final String DEFAULT_ZIPCODE="10000";

    private static final String DEFAULT_STATUS="Delivered";

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);

    private static final String DEFAULT_COUNTRY="Kosova";

    private Employee employee;

    private Vendor vendor;

    private Person person;

    private Status status;

    private Product product;

    private ReceiverInfo receiverInfo;

    private WarehouseLocation warehouseLocation;



    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ShipmentResource shipmentResource = new ShipmentResource(shipmentService, personService, receiverInfoService, vendorService, employeeService, statusService, em, productService, warehouseLocationService);
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

    public Employee createEmployee()
    {Employee employee= new Employee()
        .name(DEFAULT_NAME)
        .lastName(DEFAULT_LASTNAME)
        .tel(DEFAULT_TEL)
        .email(DEFAULT_EMAIL)
        .age(DEFAULT_AGE);
    return employee;}

    public Vendor createVendor()
    {
        Vendor senderV=new Vendor()
            .firstName(DEFAULT_NAME)
            .lastName(DEFAULT_LASTNAME)
            .address(DEFAULT_ADDRESS)
            .email(DEFAULT_EMAIL)
            .website(DEFAULT_WEBSITE)
            .contactPerson(DEFAULT_CONTACTPERSON).zipCode(DEFAULT_ZIPCODE);
        return senderV;
    }

    public Person createPerson()
    {
        Person senderP=new Person()
            .fullName(DEFAULT_NAME)
            .tel(DEFAULT_TEL).address(DEFAULT_ADDRESS).zipCode(DEFAULT_ZIPCODE).email(DEFAULT_EMAIL);
        return senderP;
    }
    public Status createStatus(){
        Status status=new Status()
            .statusName(DEFAULT_STATUS);
       return status;
    }

    public Price createPrice()
    {
        Price price=new Price().price(DEFAULT_PRICE);
        return price;
    }

    public Product createProduct()
    {
        Product product=new Product();
        return  product;
    }

    public ReceiverInfo createReceiver()
    {
        ReceiverInfo receiverInfo=new ReceiverInfo().fullName(DEFAULT_NAME).address(DEFAULT_ADDRESS).zipCode(DEFAULT_ZIPCODE);
        return receiverInfo;
    }

    public WarehouseLocation createWarehouseLoc()
    {
        WarehouseLocation warehouseLocation=new WarehouseLocation().address(DEFAULT_ADDRESS).country(DEFAULT_COUNTRY);
        return warehouseLocation;

    }

    @Before
    public void initTest() {
        shipment = createEntity(em);

        employee=createEmployee();
        employeeRepository.save(employee);

        vendor=createVendor();
        vendorRepository.save(vendor);

        person=createPerson();
        personRepository.save(person);

        status=createStatus();
        statusRepository.save(status);

        product=createProduct();
        productRepository.save(product);

        receiverInfo=createReceiver();
        receiverInfoRepository.save(receiverInfo);

        warehouseLocation=createWarehouseLoc();
        warehouseLocationRepository.save(warehouseLocation);

        shipment.setStatus(status);
        shipment.setSenderV(vendor);
        shipment.setSenderP(person);
        shipment.setReceiver(receiverInfo);
        shipment.setProduct(product);
        shipment.setLocation(warehouseLocation);
        shipment.setEmployee(employee);


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
    public void getAllShipmentsSearch() throws Exception {
        // Initialize the database
        shipmentRepository.saveAndFlush(shipment);

        // Get all the cityList
        restShipmentMockMvc.perform(get("/api/shipments?search=senderP.tel==" + shipment.getSenderP().getTel() + "&sort=id,desc"))
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
