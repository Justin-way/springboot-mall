package com.wayneyeh.springbootmall.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.wayneyeh.springbootmall.dao.UserDao;
import com.wayneyeh.springbootmall.dto.UserLoginRequest;
import com.wayneyeh.springbootmall.dto.UserRegisterRequest;
import com.wayneyeh.springbootmall.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.DigestUtils;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserDao userDao;

    private ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void register_success() throws Exception {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setEmail("test12345@gmail.com");
        userRegisterRequest.setPassword("123");

        String json = objectMapper.writeValueAsString(userRegisterRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().is(201))
                .andExpect(jsonPath("$.userId",notNullValue()))
                .andExpect(jsonPath("$.email",equalTo("test12345@gmail.com")))
                .andExpect(jsonPath("$.createDate",notNullValue()))
                .andExpect(jsonPath("$.lastModifiedDate",notNullValue()));

        String pw = DigestUtils.md5DigestAsHex(userRegisterRequest.getPassword().getBytes());

        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());
        assertNotEquals(userRegisterRequest.getPassword(),user.getPassword());

    }
    @Test
    public void register_invalidEmailFormat() throws Exception {

        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setEmail("test1234");
        userRegisterRequest.setPassword("123");

        String json = objectMapper.writeValueAsString(userRegisterRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().is(400));

    }
    @Test
    public void register_emailAlreadyExist() throws Exception {

        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setEmail("test1234@gamil");
        userRegisterRequest.setPassword("123");

        String json = objectMapper.writeValueAsString(userRegisterRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().is(201));
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().is(400));



        }
    // 登入
    @Test
    public void login_success() throws Exception {


        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setEmail("test1234@gmail.com");
        userRegisterRequest.setPassword("123");

        String json = objectMapper.writeValueAsString(userRegisterRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().is(201));

        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setEmail("test1234@gmail.com");
        userLoginRequest.setPassword("123");

        String json2 = objectMapper.writeValueAsString(userLoginRequest);


        RequestBuilder requestBuilder2 = MockMvcRequestBuilders
                .post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json2);
        mockMvc.perform(requestBuilder2)
                .andExpect(MockMvcResultMatchers.status().is(200));


    }

    @Test
    public void login_wrongPassword() throws Exception {

        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setEmail("te@gmail.com");
        userRegisterRequest.setPassword("321");

        String json = objectMapper.writeValueAsString(userRegisterRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().is(201));

        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setEmail("te@gmail.com");
        userLoginRequest.setPassword("123");

        String json2 = objectMapper.writeValueAsString(userLoginRequest);


        RequestBuilder requestBuilder2 = MockMvcRequestBuilders
                .post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json2);
        mockMvc.perform(requestBuilder2)
                .andExpect(MockMvcResultMatchers.status().is(400));


    }

    @Test
    public void login_invalidEmailFormat() throws Exception {

        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setEmail("test1234");
        userRegisterRequest.setPassword("123");

        String json = objectMapper.writeValueAsString(userRegisterRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().is(400));

        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setEmail("test1234");
        userLoginRequest.setPassword("123");

        String json2 = objectMapper.writeValueAsString(userLoginRequest);


        RequestBuilder requestBuilder2 = MockMvcRequestBuilders
                .post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json2);
        mockMvc.perform(requestBuilder2)
                .andExpect(MockMvcResultMatchers.status().is(400));

    }

    @Test
    public void login_emailNotExist() throws Exception {

        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setEmail("tes@gmail.com");
        userRegisterRequest.setPassword("123");

        String json = objectMapper.writeValueAsString(userRegisterRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().is(201));

        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setEmail("test3456@gmail.com");
        userLoginRequest.setPassword("123");

        String json2 = objectMapper.writeValueAsString(userLoginRequest);


        RequestBuilder requestBuilder2 = MockMvcRequestBuilders
                .post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json2);
        mockMvc.perform(requestBuilder2)
                .andExpect(MockMvcResultMatchers.status().is(400));


    }



}