package teskerja.src.datakaryawan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teskerja.src.datakaryawan.entity.Biodata;
import teskerja.src.datakaryawan.model.LoginRequest;
import teskerja.src.datakaryawan.model.TokenResponse;
import teskerja.src.datakaryawan.model.WebResponse;
import teskerja.src.datakaryawan.service.AuthService;

import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin
public class AuthController {
    @Autowired
    private AuthService authService;
    @PostMapping(
            path = "/api/auth/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<TokenResponse> login(@RequestBody LoginRequest request){
        TokenResponse tokenResponse = authService.login(request);
        return WebResponse.<TokenResponse>builder().data(tokenResponse).build();
    }

    @DeleteMapping(
            path = "/api/auth/logout",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> logout(Biodata user) {
        authService.logout(user);
        return WebResponse.<String>builder().data("OK").build();
    }
}
