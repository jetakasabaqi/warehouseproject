package com.project.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.project.config.Constants;
import com.project.domain.Employee;
import com.project.domain.Person;
import com.project.domain.User;
import com.project.domain.Vendor;
import com.project.repository.EmployeeRepository;
import com.project.repository.UserRepository;
import com.project.rsql1.jpa.JpaCriteriaQueryVisitor;
import com.project.security.AuthoritiesConstants;
import com.project.service.*;
import com.project.service.dto.EmployeeDTO;
import com.project.service.dto.PersonDTO;
import com.project.service.dto.UserDTO;
import com.project.service.dto.VendorDTO;
import com.project.service.util.Mail.MailService;
import com.project.web.rest.errors.BadRequestAlertException;
import com.project.web.rest.errors.EmailAlreadyUsedException;
import com.project.web.rest.errors.LoginAlreadyUsedException;
import com.project.web.rest.util.HeaderUtil;
import com.project.web.rest.util.PaginationUtil;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing users.
 * <p>
 * This class accesses the User entity, and needs to fetch its collection of authorities.
 * <p>
 * For a normal use-case, it would be better to have an eager relationship between User and Authority,
 * and send everything to the client side: there would be no View Model and DTO, a lot less code, and an outer-join
 * which would be good for performance.
 * <p>
 * We use a View Model and a DTO for 3 reasons:
 * <ul>
 * <li>We want to keep a lazy association between the user and the authorities, because people will
 * quite often do relationships with the user, and we don't want them to get the authorities all
 * the time for nothing (for performance reasons). This is the #1 goal: we should not impact our users'
 * application because of this use-case.</li>
 * <li> Not having an outer join causes n+1 requests to the database. This is not a real issue as
 * we have by default a second-level cache. This means on the first HTTP call we do the n+1 requests,
 * but then all authorities come from the cache, so in fact it's much better than doing an outer join
 * (which will get lots of data from the database, for each HTTP call).</li>
 * <li> As this manages users, for security reasons, we'd rather have a DTO layer.</li>
 * </ul>
 * <p>
 * Another option would be to have a specific JPA entity graph to handle this case.
 */
@RestController
@RequestMapping("/api")
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    private final UserRepository userRepository;

    private final UserService userService;

    private final MailService mailService;

    private final EmployeeService employeeService;

    private final VendorService vendorService;

    private final PersonService personService;

    private final EmployeeRepository employeeRepository;

    private final EntityManager entityManager;

    @Autowired
    private CronJobsService cronJobsService;


    public UserResource(UserRepository userRepository, UserService userService, MailService mailService, VendorService vendorService, PersonService personService, EmployeeService employeeService, EmployeeRepository employeeRepository, EntityManager entityManager) {

        this.userRepository = userRepository;
        this.userService = userService;
        this.mailService = mailService;
        this.employeeService = employeeService;
        this.personService = personService;
        this.employeeRepository = employeeRepository;
        this.vendorService = vendorService;
        this.entityManager = entityManager;
    }


    /**
     * POST  /users  : Creates a new user.
     * <p>
     * Creates a new user if the login and email are not already used, and sends an
     * mail with an activation link.
     * The user needs to be activated on creation.
     *
     * @param userDTO the user to create
     * @return the ResponseEntity with status 201 (Created) and with body the new user, or with status 400 (Bad Request) if the login or email is already in use
     * @throws URISyntaxException       if the Location URI syntax is incorrect
     * @throws BadRequestAlertException 400 (Bad Request) if the login or email is already in use
     */
    @PostMapping("/users")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDTO userDTO) throws URISyntaxException {
        log.debug("REST request to save User : {}", userDTO);

        if (userDTO.getId() != null) {
            throw new BadRequestAlertException("A new user cannot already have an ID", "userManagement", "idexists");
            // Lowercase the user login before comparing with database
        } else if (userRepository.findOneByLogin(userDTO.getLogin().toLowerCase()).isPresent()) {
            throw new LoginAlreadyUsedException();
        } else if (userRepository.findOneByEmailIgnoreCase(userDTO.getEmail()).isPresent()) {
            throw new EmailAlreadyUsedException();
        } else {
            User newUser = userService.createUser(userDTO);
            mailService.sendCreationEmail(newUser);
            return ResponseEntity.created(new URI("/api/users/" + newUser.getLogin()))
                .headers(HeaderUtil.createAlert("A user is created with identifier " + newUser.getLogin(), newUser.getLogin()))
                .body(newUser);
        }
    }

    @PostMapping("/users/employee")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) throws URISyntaxException {
        log.debug("REST request to save Employee : {}", employeeDTO);

//        UserRepository employeeResource;
        if (employeeDTO.getEmployeeid() != null) {
            throw new BadRequestAlertException("A new employee cannot already have an ID", "userManagement", "idexists");
            // Lowercase the user login before comparing with database
//        } else if (employeeRepository.findOneByLogin(employeeDTO.getLogin().toLowerCase()).isPresent()) {
//            throw new LoginAlreadyUsedException();
//        } else if (employeeRepository.findOneByEmailIgnoreCase(employeeDTO.getEmail()).isPresent()) {
//            throw new EmailAlreadyUsedException();
        } else {
            User user = new User(employeeDTO.getLogin(), employeeDTO.getLastName(), employeeDTO.getEmail(), employeeDTO.getName());
            User registerUser = userService.registerUser(new UserDTO(user), "user");
            //User newUser= userService.createUser(new UserDTO(user));

            Employee employee = new Employee()
                .name(employeeDTO.getName())
                .age(employeeDTO.getAge())
                .tel(employeeDTO.getTel())
                .email(employeeDTO.getEmail())
                .lastName(employeeDTO.getLastName())
                .age(employeeDTO.getAge());
            employee.setUser(registerUser);

            employeeService.save(employee);


            //  mailService.sendCreationEmail(newUser);
            return ResponseEntity.created(new URI("/api/users/" + user.getLogin()))
                .headers(HeaderUtil.createAlert("A Employee is created with identifier " + user.getLogin(), user.getLogin()))
                .body(employee);
        }
    }


    @PostMapping("/users/person")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Person> createPerson(@Valid @RequestBody PersonDTO personDTO) throws URISyntaxException {
        log.debug("REST request to save Person : {}", personDTO);

//        UserRepository employeeResource;
        if (personDTO.getPersonId() != null) {
            throw new BadRequestAlertException("A new person cannot already have an ID", "userManagement", "idexists");
            // Lowercase the user login before comparing with database
//        } else if (employeeRepository.findOneByLogin(employeeDTO.getLogin().toLowerCase()).isPresent()) {
//            throw new LoginAlreadyUsedException();
//        } else if (employeeRepository.findOneByEmailIgnoreCase(employeeDTO.getEmail()).isPresent()) {
//            throw new EmailAlreadyUsedException();
        } else {
            User user = new User(personDTO.getLogin(), personDTO.getLastName(), personDTO.getEmail(), personDTO.getFirstName());
            user.setLangKey("en");
            User registerUser = userService.registerUser(new UserDTO(user), "user");
            //User newUser= userService.createUser(new UserDTO(user));

            Person person = new Person()
                .fullName(personDTO.getFullName())
                .zipCode(personDTO.getZipCode())
                .tel(personDTO.getTel())
                .email(personDTO.getEmail())
                .address(personDTO.getAddress());
            person.setUser(registerUser);

            personService.save(person);


            // mailService.sendCreationEmail(newUser);
            return ResponseEntity.created(new URI("/api/users/" + user.getLogin()))
                .headers(HeaderUtil.createAlert("A Person is created with identifier " + user.getLogin(), user.getLogin()))
                .body(person);
        }
    }

    @PostMapping("/users/vendor")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Vendor> createVendor(@Valid @RequestBody VendorDTO vendorDTO) throws URISyntaxException {
        log.debug("REST request to save Vendor : {}", vendorDTO);

//        UserRepository employeeResource;
        if (vendorDTO.getVendorId() != null) {
            throw new BadRequestAlertException("A new person cannot already have an ID", "userManagement", "idexists");
            // Lowercase the user login before comparing with database
//        } else if (employeeRepository.findOneByLogin(employeeDTO.getLogin().toLowerCase()).isPresent()) {
//            throw new LoginAlreadyUsedException();
//        } else if (employeeRepository.findOneByEmailIgnoreCase(employeeDTO.getEmail()).isPresent()) {
//            throw new EmailAlreadyUsedException();
        } else {
            User user = new User(vendorDTO.getLogin(), vendorDTO.getLastName(), vendorDTO.getEmail(), vendorDTO.getFirstName());
            user.setLangKey("en");
            User registerUser = userService.registerUser(new UserDTO(user), "user");
            //User newUser= userService.createUser(new UserDTO(user));

            Vendor vendor = new Vendor();
            vendor.setFirstName(vendorDTO.getFirstName());
            vendor.zipCode(vendorDTO.getZipCode());
            vendor.contactPerson(vendorDTO.getContactPerson());
            vendor.email(vendorDTO.getEmail());
            vendor.address(vendorDTO.getAddress());
            vendor.website(vendorDTO.getWebsite());
            vendor.setUser(registerUser);
            vendor.setLastName(vendorDTO.getLastName());

            vendorService.save(vendor);


            //  mailService.sendCreationEmail(newUser);
            return ResponseEntity.created(new URI("/api/users/" + user.getLogin()))
                .headers(HeaderUtil.createAlert("A Person is created with identifier " + user.getLogin(), user.getLogin()))
                .body(vendor);
        }
    }

    /**
     * PUT /users : Updates an existing User.
     *
     * @param userDTO the user to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated user
     * @throws EmailAlreadyUsedException 400 (Bad Request) if the email is already in use
     * @throws LoginAlreadyUsedException 400 (Bad Request) if the login is already in use
     */
    @PutMapping("/users")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO) {
        log.debug("REST request to update User : {}", userDTO);
        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDTO.getId()))) {
            throw new EmailAlreadyUsedException();
        }
        existingUser = userRepository.findOneByLogin(userDTO.getLogin().toLowerCase());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDTO.getId()))) {
            throw new LoginAlreadyUsedException();
        }
        Optional<UserDTO> updatedUser = userService.updateUser(userDTO);

        return ResponseUtil.wrapOrNotFound(updatedUser,
            HeaderUtil.createAlert("A user is updated with identifier " + userDTO.getLogin(), userDTO.getLogin()));
    }

    /**
     * GET /users : get all users.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
//    @GetMapping("/users")
//    @Timed
//    public ResponseEntity<List<UserDTO>> getAllUsers(Pageable pageable) {
//        final Page<UserDTO> page = userService.getAllManagedUsers(pageable);
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/users");
//        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
//    }

    /**
     * @return a string list of the all of the roles
     */
    @GetMapping("/users/authorities")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public List<String> getAuthorities() {
        return userService.getAuthorities();
    }

    /**
     * GET /users/:login : get the "login" user.
     *
     * @param login the login of the user to find
     * @return the ResponseEntity with status 200 (OK) and with body the "login" user, or with status 404 (Not Found)
     */
    @GetMapping("/users/{login:" + Constants.LOGIN_REGEX + "}")
    @Timed
    public ResponseEntity<UserDTO> getUser(@PathVariable String login) {
        log.debug("REST request to get User : {}", login);
        return ResponseUtil.wrapOrNotFound(
            userService.getUserWithAuthoritiesByLogin(login)
                .map(UserDTO::new));
    }

    /**
     * DELETE /users/:login : delete the "login" User.
     *
     * @param login the login of the user to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/users/{login:" + Constants.LOGIN_REGEX + "}")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Void> deleteUser(@PathVariable String login) {
        log.debug("REST request to delete User: {}", login);
        userService.deleteUser(login);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("A user is deleted with identifier " + login, login)).build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users")
    @ResponseBody
    public ResponseEntity<List<User>> findAllByRsql(@RequestParam(value = "search", required = false) String search, Pageable pageable) {


        if (search == null) {
            Page<User> page = userService.findAll(pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/users");
            return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        } else {
            RSQLVisitor<CriteriaQuery<User>, EntityManager> visitor = new JpaCriteriaQueryVisitor<User>();
            final Node rootNode = new RSQLParser().parse(search);
            CriteriaQuery<User> query = rootNode.accept(visitor, entityManager);
            List<User> users = userService.findAll(query);


            return new ResponseEntity<>(users, HttpStatus.OK);
        }
    }

//
//    @GetMapping("/user/email")
//    protected boolean sendEmails() throws IOException, DocumentException {
//
//        User user=new User();
//        user.setEmail("jetakasabaqi@gmail.com");
//        user.setLangKey("en");
//        user.setFirstName("Zana");
//
//        mailService.sendEmailFromTemplateAttachment(user);
//        return true;
//    }


}


