package teskerja.src.datakaryawan.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import teskerja.src.datakaryawan.entity.Biodata;
import teskerja.src.datakaryawan.model.WebResponse;
import teskerja.src.datakaryawan.model.pendidikan.CreatePendidikanRequest;
import teskerja.src.datakaryawan.model.pendidikan.PendidikanResponse;
import teskerja.src.datakaryawan.model.pendidikan.UpdatePendidikanRequest;
import teskerja.src.datakaryawan.service.PendididkanService;

import java.util.List;

@CrossOrigin
@RestController
public class PendidikanController {
    @Autowired
    private PendididkanService pendidikanService;

    @PostMapping(
            path = "/api/pendidikan",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<PendidikanResponse> create(Biodata user, @RequestBody CreatePendidikanRequest request) {
        PendidikanResponse pendidikanResponse = pendidikanService.create(user, request);
        return WebResponse.<PendidikanResponse>builder().data(pendidikanResponse).build();
    }

    @GetMapping(
            path = "/api/pendidikan/{pendidikanId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<PendidikanResponse> get(Biodata user, @PathVariable("pendidikanId") String pendidikanId) {
        PendidikanResponse pendidikanResponse = pendidikanService.get(user, pendidikanId);
        return WebResponse.<PendidikanResponse>builder().data(pendidikanResponse).build();
    }

    @GetMapping(
            path = "/api/pendidikan",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<PendidikanResponse>> list(Biodata user) {
        List<PendidikanResponse> pendidikanResponses = pendidikanService.list(user);
        return WebResponse.<List<PendidikanResponse>>builder().data(pendidikanResponses).build();
    }

    @PutMapping(
            path = "/api/pendidikan/{pendidikanId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<PendidikanResponse> update(Biodata user,
                                               @RequestBody UpdatePendidikanRequest request,
                                               @PathVariable("pendidikanId") String pendidikanId) {

        request.setId(pendidikanId);

        PendidikanResponse contactResponse = pendidikanService.update(user, request);
        return WebResponse.<PendidikanResponse>builder().data(contactResponse).build();
    }

    @DeleteMapping(
            path = "/api/pendidikan/{pendidikanId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> delete(Biodata user, @PathVariable("pendidikanId") String pendidikanId) {
        pendidikanService.delete(user, pendidikanId);
        return WebResponse.<String>builder().data("OK").build();
    }

}
