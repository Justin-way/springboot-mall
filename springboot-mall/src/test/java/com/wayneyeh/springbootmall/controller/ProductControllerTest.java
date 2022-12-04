package com.wayneyeh.springbootmall.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wayneyeh.springbootmall.constant.ProductCategory;
import com.wayneyeh.springbootmall.dto.ProductRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.JsonPath;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.ResponseEntity.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {


    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void getProduct_sucess() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products/{productId}", 1);


        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.productName", equalTo("蘋果（澳洲）")));

    }

    @Test
    public void getProduct_nonFound() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products/{productId}", 2000);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().is(404));
    }

    @Transactional
    @Test
    public void createProduct_sucess() throws Exception {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductName("test food");
        productRequest.setCategory(ProductCategory.FOOD);
        productRequest.setImageUrl("http//test.com");
        productRequest.setPrice(100);
        productRequest.setStock(2);

        String json = objectMapper.writeValueAsString(productRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().is(201))
                .andExpect(jsonPath("$.productName", equalTo("test food")));

    }

    @Test
    @Transactional
    public void createProduct_illegalArgument() throws Exception {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductName("test food product");

        String json = objectMapper.writeValueAsString(productRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    @Transactional
    public void updateProduct_success() throws Exception {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductName("test food product");
        productRequest.setCategory(ProductCategory.FOOD);
        productRequest.setImageUrl("http://test.com");
        productRequest.setPrice(100);
        productRequest.setStock(2);

        String json = objectMapper.writeValueAsString(productRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/products/{productId}", 3)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(jsonPath("$.productName", equalTo("test food product")))
                .andExpect(jsonPath("$.category", equalTo("FOOD")))
                .andExpect(jsonPath("$.imageUrl", equalTo("http://test.com")))
                .andExpect(jsonPath("$.price", equalTo(100)))
                .andExpect(jsonPath("$.stock", equalTo(2)))
                .andExpect(jsonPath("$.description", nullValue()))
                .andExpect(jsonPath("$.lastModifiedDate", notNullValue()));
    }


    @Test
    @Transactional
    public void updateProduct_productNotFound() throws Exception {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductName("test food product");
        productRequest.setCategory(ProductCategory.FOOD);
        productRequest.setImageUrl("http://test.com");
        productRequest.setPrice(100);
        productRequest.setStock(2);

        String json = objectMapper.writeValueAsString(productRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/products/{productId}", 20000)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().is(404));
    }

    @Test
    @Transactional
    public void deleteProduct_success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/products/{productId}", 5);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().is(204));
    }




    @Test
    @Transactional
    public void deleteProduct_deleteNonExistingProduct() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/products/{productId}", 200000);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().is(204));
    }

    @Test
    public void getProducts() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.offset", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.results", hasSize(5)));//預期有5筆
    }

    public void getProducts_filtering() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products")
                .param("search", "B")
                .param("category", "CAR");

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.offset", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.results", hasSize(2)));

    }

    @Test
    public void getProducts_sorting() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products")
                .param("orderBy", "price")
                .param("sort", "desc");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.offset", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.results", hasSize(5)))
                .andExpect(jsonPath("$.results.[0].productId", equalTo(6)))
                .andExpect(jsonPath("$.results.[1].productId", equalTo(5)))
                .andExpect(jsonPath("$.results.[2].productId", equalTo(7)))
                .andExpect(jsonPath("$.results.[3].productId", equalTo(4)))
                .andExpect(jsonPath("$.results.[4].productId", equalTo(2)));

    }

    @Test
    public void getProducts_pagination() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products")
                .param("limit","2")
                .param("offset","2");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.offset", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.results", hasSize(2)))
                .andExpect(jsonPath("$.results[0].productId", equalTo(5)))
                .andExpect(jsonPath("$.results[1].productId", equalTo(4)));
    }
}