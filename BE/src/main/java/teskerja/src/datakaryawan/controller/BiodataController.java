package teskerja.src.datakaryawan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import teskerja.src.datakaryawan.entity.Biodata;
import teskerja.src.datakaryawan.model.RegisterRequest;
import teskerja.src.datakaryawan.model.UpdateUserRequest;
import teskerja.src.datakaryawan.model.UserResponse;
import teskerja.src.datakaryawan.model.WebResponse;
import teskerja.src.datakaryawan.model.pengalaman.PengalamanResponse;
import teskerja.src.datakaryawan.service.BiodataService;

import java.util.List;

@RestController
@CrossOrigin
public class BiodataController {
    @Autowired
    private BiodataService biodataService;

    @PostMapping(
            path = "/api/users",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> register(@RequestBody RegisterRequest request){
        biodataService.register(request);
        return WebResponse.<String>builder().data("OK").build();
    }

    @GetMapping(
            path = "/api/users/current",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<UserResponse> get(Biodata user) {
        UserResponse userResponse = biodataService.get(user);
        return WebResponse.<UserResponse>builder().data(userResponse).build();
    }
    @PatchMapping(
            path = "/api/users/current",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<UserResponse> update(Biodata user, @RequestBody UpdateUserRequest request) {
        UserResponse userResponse = biodataService.update(user, request);
        return WebResponse.<UserResponse>builder().data(userResponse).build();
    }

    @PatchMapping(
            path = "/api/users",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<UserResponse> updateByEmail(Biodata user, @RequestBody UpdateUserRequest request) {
        UserResponse userResponse = biodataService.updateByEmail(user, request);
        return WebResponse.<UserResponse>builder().data(userResponse).build();
    }

    @GetMapping(
            path = "/api/users",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<UserResponse>> list(Biodata user) {
        List<UserResponse> userResponses = biodataService.list(user);
        return WebResponse.<List<UserResponse>>builder().data(userResponses).build();
    }

    @DeleteMapping(
            path = "/api/user/{email}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> delete(Biodata user, @PathVariable("email") String email) {
        biodataService.delete(user, email);
        return WebResponse.<String>builder().data("OK").build();
    }
}
