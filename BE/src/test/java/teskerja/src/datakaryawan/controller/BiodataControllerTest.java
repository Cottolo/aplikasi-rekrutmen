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
import teskerja.src.datakaryawan.model.RegisterRequest;
import teskerja.src.datakaryawan.model.UpdateUserRequest;
import teskerja.src.datakaryawan.model.UserResponse;
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
class BiodataControllerTest {
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
    void testRegisterSuccess() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@mail.com");
        request.setPassword("rahasia");

        mockMvc.perform(
                post("/api/users")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(
                    result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertEquals("OK", response.getData());
        });
    }

    @Test
    void testRegisterBadRequest() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("");
        request.setPassword("");

        mockMvc.perform(
                post("/api/users")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(
                    result.getResponse().getContentAsString(), new TypeReference<>() {
                    });

            assertNotNull(response.getErrors());
        });
    }

    @Test
    void testRegisterBadDuplicate() throws Exception {
        Biodata user = new Biodata();
        user.setEmail("test@mail.com");
        user.setPassword(BCrypt.hashpw("rahasia",BCrypt.gensalt()));
        biodataRepository.save(user);

        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@mail.com");
        request.setPassword("rahasia");

        mockMvc.perform(
                post("/api/users")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(
                    result.getResponse().getContentAsString(), new TypeReference<>() {
                    });

            assertNotNull(response.getErrors());
        });
    }

    @Test
    void getUserUnauthorized() throws Exception {
        mockMvc.perform(
                get("/api/users/current")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "notfound")
        ).andExpectAll(
                status().isUnauthorized()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response.getErrors());
        });
    }

    @Test
    void getUserUnauthorizedTokenNotSend() throws Exception {
        mockMvc.perform(
                get("/api/users/current")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isUnauthorized()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response.getErrors());
        });
    }

    @Test
    void getUserSuccess() throws Exception {
        Biodata user = new Biodata();
        user.setEmail("test@mail.com");
        user.setPassword(BCrypt.hashpw("rahasia", BCrypt.gensalt()));
        user.setToken("test");
        user.setTokenExpiredAt(System.currentTimeMillis() + 10000000000L);
        biodataRepository.save(user);

        mockMvc.perform(
                get("/api/users/current")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<UserResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNull(response.getErrors());
            assertEquals("test@mail.com", response.getData().getEmail());
        });
    }

    @Test
    void getUserTokenExpired() throws Exception {
        Biodata user = new Biodata();
        user.setEmail("test@mail.com");
        user.setPassword(BCrypt.hashpw("rahasia", BCrypt.gensalt()));
        user.setToken("test");
        user.setTokenExpiredAt(System.currentTimeMillis() - 10000000000L);
        biodataRepository.save(user);

        mockMvc.perform(
                get("/api/users/current")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isUnauthorized()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response.getErrors());
        });
    }

    @Test
    void updateUserUnauthorized() throws Exception {
        UpdateUserRequest request = new UpdateUserRequest();

        mockMvc.perform(
                patch("/api/users/current")
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
    void updateUserSuccess() throws Exception {
        Biodata user = new Biodata();
        user.setEmail("test@mail.com");
        user.setPosisi("");
        user.setNama("");
        user.setNik("");
        user.setTtl("");
        user.setJenisKelamin("");
        user.setAgama("");
        user.setGolonganDarah("");
        user.setStatus("");
        user.setAlamatKtp("");
        user.setAlamatTinggal("");
        user.setNoTelp("");
        user.setOrangTerdekat("");
        user.setSkill("");
        user.setBersediaDitempatkan("");
        user.setPenghasilaDiharapkan("");
        user.setPassword(BCrypt.hashpw("1234", BCrypt.gensalt()));
        user.setToken("test");
        user.setTokenExpiredAt(System.currentTimeMillis() + 100000000000L);
        biodataRepository.save(user);

        UpdateUserRequest request = getUpdateUserRequest();

        mockMvc.perform(
                patch("/api/users/current")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<UserResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNull(response.getErrors());
            assertEquals(request.getEmail(), response.getData().getEmail());
            assertEquals(request.getPosisi(), response.getData().getPosisi());
            assertEquals(request.getNama(), response.getData().getNama());
            assertEquals(request.getNik(), response.getData().getNik());
            assertEquals(request.getTtl(), response.getData().getTtl());
            assertEquals(request.getJenisKelamin(), response.getData().getJenisKelamin());
            assertEquals(request.getAgama(), response.getData().getAgama());
            assertEquals(request.getGolonganDarah(), response.getData().getGolonganDarah());
            assertEquals(request.getStatus(), response.getData().getStatus());
            assertEquals(request.getAlamatKtp(), response.getData().getAlamatKtp());
            assertEquals(request.getAlamatTinggal(), response.getData().getAlamatTinggal());
            assertEquals(request.getNoTelp(), response.getData().getNoTelp());
            assertEquals(request.getOrangTerdekat(), response.getData().getOrangTerdekat());
            assertEquals(request.getSkill(), response.getData().getSkill());
            assertEquals(request.getBersediaDitempatkan(), response.getData().getBersediaDitempatkan());
            assertEquals(request.getPenghasilaDiharapkan(), response.getData().getPenghasilaDiharapkan());

            Biodata userDb = biodataRepository.findById("test@mail.com").orElse(null);
            assertNotNull(userDb);
//            assertTrue(BCrypt.checkpw("1234", userDb.getPassword()));
        });
    }

    private static UpdateUserRequest getUpdateUserRequest() {
        UpdateUserRequest request = new UpdateUserRequest();
        request.setEmail("test@mail.com");
        request.setPosisi("test");
        request.setNama("test");
        request.setNik("123");
        request.setTtl("test");
        request.setJenisKelamin("test");
        request.setAgama("test");
        request.setGolonganDarah("test");
        request.setStatus("test");
        request.setAlamatKtp("test");
        request.setAlamatTinggal("test");
        request.setNoTelp("test");
        request.setOrangTerdekat("test");
        request.setSkill("test");
        request.setBersediaDitempatkan("test");
        request.setPenghasilaDiharapkan("test");
        return request;
    }
}