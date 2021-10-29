package com.example.demo;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.demo.model.User;
import com.example.demo.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = SpringSecurityWebAuxTestConfig.class)
// @WebMvcTest(controllers = UserRestController.class)

public class UserRestControllerTest {

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private UserService userService;

    // @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(SecurityMockMvcConfigurers.springSecurity()).build();
    }

    // @WithMockUser(value = "Gaby", roles = { "ADMIN" })
    @WithUserDetails("Gaby")
    @Test
    public void fetchUsersListTest() throws Exception {
        
        List<User> userList = new ArrayList<User>();
        userList.add(new User(1L, "Gaby", "dummyPassword"));
        
        Mockito.when(userService.findAllUsers()).thenReturn(userList);
        
        RequestBuilder requestBuilders = MockMvcRequestBuilders.get("/v1/users").accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilders)
                .andExpect(MockMvcResultMatchers.content()
                        .json("[" + "  {" + "    \"userId\": 1," + "    \"userName\": \"Gaby\"," + "    \"password\": \"dummyPassword\"," 
                                + "  }" + "]", false))
                .andReturn();

        assertNotNull(result);
        System.out.println("Response is" + result.getResponse().getContentAsString());
        // assertThat("Gaby", Matchers.containsString(result.getResponse().getContentAsString()));

    }

}
