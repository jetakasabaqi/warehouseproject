package com.project.web.rest;

import com.project.Jeta123App;

import com.project.domain.*;
import com.project.repository.*;
import com.project.service.*;
import com.project.web.rest.errors.ExceptionTranslator;

import org.checkerframework.checker.units.qual.A;
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
 * Test class for the ProductResource REST controller.
 *
 * @see ProductResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Jeta123App.class)
public class ProductDetailsResourceIntTest {

    @Autowired
    private ProductDetailsRepository productDetailsRepository;

    @Autowired
    private ProductDetailsService productDetailsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;
    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    @Autowired
    private EntityManager em;


    @Autowired
    private ProductTypeRepository productTypeRepository;
    @Autowired
    private WeightUnitRepository weightUnitRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PriceRepository priceRepository;




    private ProductTypeService productTypeService;


    private WeightUnitService weightUnitService;

    @Autowired
    private ProductService productService;

    @Autowired
    private PriceService priceService;

    private MockMvc restProductDetailsMockMvc;

    private ProductType productType;


    private ProductDetails productDetails;

    private WeightUnit weightUnit;

    private Product product;


    private Price price;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductDetailsResource productResource = new ProductDetailsResource(productDetailsService, productTypeService,priceService,weightUnitService, productService,em);
        this.restProductDetailsMockMvc = MockMvcBuilders.standaloneSetup(productResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductDetails createEntity(EntityManager em) {
        ProductDetails productDetails = new ProductDetails();
        return productDetails;
    }

    public Price createPrice() {
        Price price = new Price()
            .price(DEFAULT_PRICE);
        return price;
    }

    public Product createProduct() {
        Product product = new Product();
        return product;
    }
    public ProductType createProductType() {
        ProductType productType = new ProductType().type("envelope");

        return productType;
    }

    public WeightUnit createWeightUnit() {
        WeightUnit weightUnit = new WeightUnit().unit("kg");

        return weightUnit;
    }

    @Before
    public void initTest() {
        productDetails = createEntity(em);

        price = createPrice();
        priceRepository.save(price);
        product=createProduct();
        productRepository.save(product);
        product.setPrice(price);

        productType=createProductType();
        productTypeRepository.save(productType);

        weightUnit=createWeightUnit();
        weightUnitRepository.save(weightUnit);


        productDetails.setProduct(product);
        productDetails.setWeightUnit(weightUnit);
        productDetails.setType(productType);
    }

    @Test
    @Transactional
    public void createProductDetails() throws Exception {
        int databaseSizeBeforeCreate = productTypeRepository.findAll().size();

        // Create the ProductType
        restProductDetailsMockMvc.perform(post("/api/product_details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productDetails)))
            .andExpect(status().isCreated());

        // Validate the Product in the database
        List<ProductDetails> productDetails = productDetailsRepository.findAll();
        assertThat(productDetails).hasSize(databaseSizeBeforeCreate );
        ProductDetails testProduct = productDetails.get(productDetails.size() - 1);
    }

    @Test
    @Transactional
    public void createProductDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productDetailsRepository.findAll().size();

        // Create the Product with an existing ID
        productDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductDetailsMockMvc.perform(post("/api/product_details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productDetails)))
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<ProductDetails> productList = productDetailsRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProductDetails() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productList
        restProductDetailsMockMvc.perform(get("/api/product_details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productDetails.getId().intValue())));
    }

    @Test
    @Transactional
    public void getAllProductDetailsSearch() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the cityList
        restProductDetailsMockMvc.perform(get("/api/product_details?search=id==" + productDetails.getId().intValue() + "&sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productDetails.getId().intValue())));

    }




    @Test
    @Transactional
    public void getProductDetails() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get the product
        restProductDetailsMockMvc.perform(get("/api/product_details/{id}", productDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productDetails.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProductDetails() throws Exception {
        // Get the product
        restProductDetailsMockMvc.perform(get("/api/product_details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductDetails() throws Exception {
        // Initialize the database
        productDetailsService.save(productDetails);

        int databaseSizeBeforeUpdate = productDetailsRepository.findAll().size();

        // Update the product
        ProductDetails updatedProduct = productDetailsRepository.findOne(productDetails.getId());
        // Disconnect from session so that the updates on updatedProduct are not directly saved in db
        em.detach(updatedProduct);

        restProductDetailsMockMvc.perform(put("/api/product_details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProduct)))
            .andExpect(status().isOk());

        // Validate the Product in the database
        List<ProductDetails> productList = productDetailsRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
        ProductDetails testProduct = productList.get(productList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingProductDetails() throws Exception {
        int databaseSizeBeforeUpdate = productDetailsRepository.findAll().size();

        // Create the Product

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProductDetailsMockMvc.perform(put("/api/product_details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productDetails)))
            .andExpect(status().isCreated());

        // Validate the Product in the database
        List<ProductDetails> productList = productDetailsRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProductDetails() throws Exception {
        // Initialize the database
        productDetailsService.save(productDetails);

        int databaseSizeBeforeDelete = productDetailsRepository.findAll().size();

        // Get the product
        restProductDetailsMockMvc.perform(delete("/api/product_details/{id}", productDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProductDetails> productList = productDetailsRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Product.class);
        ProductDetails product1 = new ProductDetails();
        product1.setId(1L);
        ProductDetails product2 = new ProductDetails();
        product2.setId(product1.getId());
        assertThat(product1).isEqualTo(product2);
        product2.setId(2L);
        assertThat(product1).isNotEqualTo(product2);
        product1.setId(null);
        assertThat(product1).isNotEqualTo(product2);
    }
}
