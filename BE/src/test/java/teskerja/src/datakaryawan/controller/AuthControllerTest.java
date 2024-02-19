package teskerja.src.datakaryawan.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import teskerja.src.datakaryawan.entity.Biodata;
import teskerja.src.datakaryawan.model.LoginRequest;
import teskerja.src.datakaryawan.model.TokenResponse;
import teskerja.src.datakaryawan.model.WebResponse;
import teskerja.src.datakaryawan.repository.BiodataRepository;
import teskerja.src.datakaryawan.security.BCrypt;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.MockMvcBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;


@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BiodataRepository biodataRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        biodataRepository.deleteAll();
    }

    @Test
    void loginFailedUserNotFound() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setEmail("test@mail.com");
        request.setPassword("test");

        mockMvc.perform(
                post("/api/auth/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isUnauthorized()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNotNull(response.getErrors());
        });
    }

    @Test
    void loginFailedWrongPassword() throws Exception {
        Biodata user = new Biodata();
        user.setEmail("Test@mail.com");
        user.setPassword(BCrypt.hashpw("test", BCrypt.gensalt()));
        biodataRepository.save(user);

        LoginRequest request = new LoginRequest();
        request.setEmail("test@mail.com");
        request.setPassword("salah");

        mockMvc.perform(
                post("/api/auth/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isUnauthorized()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNotNull(response.getErrors());
        });
    }

    @Test
    void loginSuccess() throws Exception {
        Biodata user = new Biodata();
        user.setEmail("test@mail.com");
        user.setPassword(BCrypt.hashpw("test", BCrypt.gensalt()));
        biodataRepository.save(user);

        LoginRequest request = new LoginRequest();
        request.setEmail("test@mail.com");
        request.setPassword("test");

        mockMvc.perform(
                post("/api/auth/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<TokenResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());
            assertNotNull(response.getData().getToken());
            assertNotNull(response.getData().getExpiredAt());

            Biodata userDb = biodataRepository.findById("test@mail.com").orElse(null);
            assertNotNull(userDb);
            assertEquals(userDb.getToken(), response.getData().getToken());
            assertEquals(userDb.getTokenExpiredAt(), response.getData().getExpiredAt());
        });
    }
}