package com.project.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.project.domain.Person;
import com.project.rsql1.jpa.JpaCriteriaQueryVisitor;
import com.project.service.PersonService;
import com.project.web.rest.errors.BadRequestAlertException;
import com.project.web.rest.util.HeaderUtil;
import com.project.web.rest.util.PaginationUtil;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Person.
 */
@RestController
@RequestMapping("/api")
public class PersonResource {

    private final Logger log = LoggerFactory.getLogger(PersonResource.class);

    private static final String ENTITY_NAME = "person";

    private final PersonService personService;

    private final EntityManager entityManager;

    public PersonResource(PersonService personService, EntityManager entityManager) {
        this.personService = personService;
        this.entityManager = entityManager;
    }

    /**
     * POST  /people : Create a new person.
     *
     * @param person the person to create
     * @return the ResponseEntity with status 201 (Created) and with body the new person, or with status 400 (Bad Request) if the person has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/people")
    @Timed
    public ResponseEntity<Person> createPerson(@RequestBody Person person) throws URISyntaxException {
        log.debug("REST request to save Person : {}", person);
        if (person.getId() != null) {
            throw new BadRequestAlertException("A new person cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (person.getFullName() == null || person.getEmail() == null || person.getCountry() == null || person.getTel() == null) {
            throw new BadRequestAlertException("Field cannot be null", ENTITY_NAME, "null");
        }

        Person result = personService.save(person);
        return ResponseEntity.created(new URI("/api/people/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /people : Updates an existing person.
     *
     * @param person the person to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated person,
     * or with status 400 (Bad Request) if the person is not valid,
     * or with status 500 (Internal Server Error) if the person couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/people")
    @Timed
    public ResponseEntity<Person> updatePerson(@RequestBody Person person) throws URISyntaxException {
        log.debug("REST request to update Person : {}", person);
        if (person.getTel() == null || person.getCountry() == null || person.getEmail() == null || person.getFullName() == null) {
            throw new BadRequestAlertException("Fields cannot be null", ENTITY_NAME, "null");
        } else {
            if (person.getId() == null) {
                return createPerson(person);
            }

            Person result = personService.save(person);
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, person.getId().toString()))
                .body(result);
        }
    }


    /**
     * GET  /people/:id : get the "id" person.
     *
     * @param id the id of the person to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the person, or with status 404 (Not Found)
     */
    @GetMapping("/people/{id}")
    @Timed
    public ResponseEntity<Person> getPerson(@PathVariable Long id) throws Exception {
        log.debug("REST request to get Person : {}", id);

        Person person = personService.findOne(id);

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(person));
    }

    /**
     * DELETE  /people/:id : delete the "id" person.
     *
     * @param id the id of the person to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/people/{id}")
    @Timed
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) throws Exception {
        log.debug("REST request to delete Person : {}", id);
        personService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/persons")
    @ResponseBody
    public ResponseEntity<List<Person>> findAllByRsql(@RequestParam(value = "search", required = false) String search, Pageable pageable) throws Exception {


        if (search == null) {
            Page<Person> page = personService.findAll(pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/persons");
            return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        } else {
            RSQLVisitor<CriteriaQuery<Person>, EntityManager> visitor = new JpaCriteriaQueryVisitor<Person>();
            final Node rootNode = new RSQLParser().parse(search);
            CriteriaQuery<Person> query = rootNode.accept(visitor, entityManager);
            List<Person> personList = personService.findAll(query);


            return new ResponseEntity<>(personList, HttpStatus.OK);
        }
    }
}
