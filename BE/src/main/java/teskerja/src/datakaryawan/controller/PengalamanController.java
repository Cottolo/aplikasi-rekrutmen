package teskerja.src.datakaryawan.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import teskerja.src.datakaryawan.entity.Biodata;
import teskerja.src.datakaryawan.model.WebResponse;
import teskerja.src.datakaryawan.model.pengalaman.CreatePengalamanRequest;
import teskerja.src.datakaryawan.model.pengalaman.PengalamanResponse;
import teskerja.src.datakaryawan.model.pengalaman.UpdatePengalamanRequest;
import teskerja.src.datakaryawan.service.PengalamanService;

import java.util.List;

@CrossOrigin
@RestController
public class PengalamanController {
    @Autowired
    private PengalamanService pengalamanService;

    @PostMapping(
            path = "/api/pengalaman",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<PengalamanResponse> create(Biodata user, @RequestBody CreatePengalamanRequest request) {
        PengalamanResponse pengalamanResponse = pengalamanService.create(user, request);
        return WebResponse.<PengalamanResponse>builder().data(pengalamanResponse).build();
    }

    @GetMapping(
            path = "/api/pengalaman/{pengalamanId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<PengalamanResponse> get(Biodata user, @PathVariable("pengalamanId") String pengalamanId) {
        PengalamanResponse pengalamanResponse = pengalamanService.get(user, pengalamanId);
        return WebResponse.<PengalamanResponse>builder().data(pengalamanResponse).build();
    }

    @GetMapping(
            path = "/api/pengalaman",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<PengalamanResponse>> list(Biodata user) {
        List<PengalamanResponse> pengalamanResponses = pengalamanService.list(user);
        return WebResponse.<List<PengalamanResponse>>builder().data(pengalamanResponses).build();
    }

    @PutMapping(
            path = "/api/pengalaman/{pengalamanId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<PengalamanResponse> update(Biodata user,
                                               @RequestBody UpdatePengalamanRequest request,
                                               @PathVariable("pengalamanId") String pengalamanId) {

        request.setId(pengalamanId);

        PengalamanResponse contactResponse = pengalamanService.update(user, request);
        return WebResponse.<PengalamanResponse>builder().data(contactResponse).build();
    }

    @DeleteMapping(
            path = "/api/pengalaman/{pengalamanId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> delete(Biodata user, @PathVariable("pengalamanId") String pengalamanId) {
        pengalamanService.delete(user, pengalamanId);
        return WebResponse.<String>builder().data("OK").build();
    }

}
