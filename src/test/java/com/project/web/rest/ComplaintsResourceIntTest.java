package com.project.web.rest;

import com.project.Jeta123App;
import com.project.domain.Complaints;
import com.project.domain.Employee;
import com.project.domain.User;
import com.project.repository.ComplaintsRepository;
import com.project.repository.EmployeeRepository;
import com.project.repository.UserRepository;
import com.project.service.ComplaintsService;
import com.project.service.EmployeeService;
import com.project.service.UserService;
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
 * Test class for the EmployeeResource REST controller.
 *
 * @see EmployeeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Jeta123App.class)
public class ComplaintsResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TEL = "AAAAAAAAAA";
    private static final String UPDATED_TEL = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "jetakasabaqi@gmai.com";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_AGE = "AAAAAAAAAA";
    private static final String UPDATED_AGE = "BBBBBBBBBB";

    @Autowired
    private ComplaintsRepository complaintsRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ComplaintsService complaintsService;
@Autowired
private UserService userService;
    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEmployeeMockMvc;

    private Complaints complaints;

    private User user;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ComplaintsResource complaintsResource = new ComplaintsResource(complaintsService,em);
        this.restEmployeeMockMvc = MockMvcBuilders.standaloneSetup(complaintsResource)
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
    public static Complaints createEntity(EntityManager em) {
     Complaints complaints=new Complaints();
     complaints.setDetails("Broken");
     return complaints;
    }


    @Before
    public void initTest() {
        complaints = createEntity(em);

    }

    @Test
    @Transactional
    public void createComplaint() throws Exception {
        int databaseSizeBeforeCreate = complaintsRepository.findAll().size();

        // Create the Employee
        restEmployeeMockMvc.perform(post("/api/complaints")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(complaints)))
            .andExpect(status().isCreated());

        // Validate the Employee in the database
        List<Complaints> complaintsList = complaintsRepository.findAll();
        assertThat(complaintsList).hasSize(databaseSizeBeforeCreate + 1);
        Complaints testComplaints = complaintsList.get(complaintsList.size() - 1);
        assertThat(testComplaints.getDetails()).isEqualTo("Broken");
    }

    @Test
    @Transactional
    public void createComplaintsWithExsitingId() throws Exception {
        int databaseSizeBeforeCreate = complaintsRepository.findAll().size();

        // Create the Employee with an existing ID
        complaints.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeMockMvc.perform(post("/api/complaints")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(complaints)))
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Complaints> complaintsList = complaintsRepository.findAll();
        assertThat(complaintsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllComplaints() throws Exception {
        // Initialize the database
        complaintsRepository.saveAndFlush(complaints);

        // Get all the employeeList
        restEmployeeMockMvc.perform(get("/api/complaints?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
           .andExpect(jsonPath("$.[*].id").value(hasItem(complaints.getId().intValue())));

    }
    @Test
    @Transactional
    public void getAllEmployeesSearch() throws Exception {
        // Initialize the database
        complaintsRepository.saveAndFlush(complaints);

        // Get all the cityList
        restEmployeeMockMvc.perform(get("/api/complaints?search=userName==" + complaints.getUserName() + "&sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));


    }
    @Test
    @Transactional
    public void getEmployee() throws Exception {
        // Initialize the database
        complaintsRepository.saveAndFlush(complaints);

        // Get the employee
        restEmployeeMockMvc.perform(get("/api/complaints/{id}", complaints.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));

    }

    @Test
    @Transactional
    public void getNonExsitingComplaint() throws Exception {
        // Get the employee
        restEmployeeMockMvc.perform(get("/api/complaints/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComplaints() throws Exception {
        // Initialize the database
        complaintsRepository.save(complaints);

        int databaseSizeBeforeUpdate = complaintsRepository.findAll().size();

        // Update the employee
        Complaints updatedComplaint = complaintsRepository.findOne(complaints.getId());
        // Disconnect from session so that the updates on updatedEmployee are not directly saved in db
        em.detach(updatedComplaint);

        restEmployeeMockMvc.perform(put("/api/complaints").contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedComplaint)))
            .andExpect(status().isOk());

        // Validate the Employee in the database
        List<Complaints> complaintsList = complaintsRepository.findAll();
        assertThat(complaintsList).hasSize(databaseSizeBeforeUpdate);
        Complaints testComplaints = complaintsList.get(complaintsList.size() - 1);

    }

    @Test
    @Transactional
    public void updateNonExistingComplaint() throws Exception {
        int databaseSizeBeforeUpdate = complaintsRepository.findAll().size();

        // Create the Employee

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEmployeeMockMvc.perform(put("/api/complaints")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(complaints)))
            .andExpect(status().isCreated());

        // Validate the Employee in the database
        List<Complaints> complaintsList = complaintsRepository.findAll();
        assertThat(complaintsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEmployee() throws Exception {
        // Initialize the database
        complaintsService.save(complaints);

        int databaseSizeBeforeDelete = complaintsRepository.findAll().size();

        // Get the employee
        restEmployeeMockMvc.perform(delete("/api/complaints/{id}", complaints.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Complaints> complaintsList = complaintsRepository.findAll();
        assertThat(complaintsList).hasSize(databaseSizeBeforeDelete - 1);
    }

//    @Test
//    @Transactional
//    public void equalsVerifier() throws Exception {
//        TestUtil.equalsVerifier(Complaints.class);
//        Employee employee1 = new Employee();
//        employee1.setId(1L);
//        Employee employee2 = new Employee();
//        employee2.setId(employee1.getId());
//        assertThat(employee1).isEqualTo(employee2);
//        employee2.setId(2L);
//        assertThat(employee1).isNotEqualTo(employee2);
//        employee1.setId(null);
//        assertThat(employee1).isNotEqualTo(employee2);
//    }
}
