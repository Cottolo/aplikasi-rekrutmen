package teskerja.src.datakaryawan.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import teskerja.src.datakaryawan.entity.Biodata;
import teskerja.src.datakaryawan.model.WebResponse;
import teskerja.src.datakaryawan.model.pelatihan.CreatePelatihanRequest;
import teskerja.src.datakaryawan.model.pelatihan.PelatihanResponse;
import teskerja.src.datakaryawan.model.pelatihan.UpdatePelatihanRequest;
import teskerja.src.datakaryawan.service.PelatihanService;

import java.util.List;

@CrossOrigin
@RestController
public class PelatihanController {
    @Autowired
    private PelatihanService pelatihanService;

    @PostMapping(
            path = "/api/pelatihan",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<PelatihanResponse> create(Biodata user, @RequestBody CreatePelatihanRequest request) {
        PelatihanResponse pelatihanResponse = pelatihanService.create(user, request);
        return WebResponse.<PelatihanResponse>builder().data(pelatihanResponse).build();
    }

    @GetMapping(
            path = "/api/pelatihan/{pelatihanId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<PelatihanResponse> get(Biodata user, @PathVariable("pelatihanId") String pelatihanId) {
        PelatihanResponse pelatihanResponse = pelatihanService.get(user, pelatihanId);
        return WebResponse.<PelatihanResponse>builder().data(pelatihanResponse).build();
    }

    @GetMapping(
            path = "/api/pelatihan",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<PelatihanResponse>> list(Biodata user) {
        List<PelatihanResponse> pelatihanResponses = pelatihanService.list(user);
        return WebResponse.<List<PelatihanResponse>>builder().data(pelatihanResponses).build();
    }

    @PutMapping(
            path = "/api/pelatihan/{pelatihanId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<PelatihanResponse> update(Biodata user,
                                               @RequestBody UpdatePelatihanRequest request,
                                               @PathVariable("pelatihanId") String pelatihanId) {

        request.setId(pelatihanId);

        PelatihanResponse contactResponse = pelatihanService.update(user, request);
        return WebResponse.<PelatihanResponse>builder().data(contactResponse).build();
    }

    @DeleteMapping(
            path = "/api/pelatihan/{pelatihanId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> delete(Biodata user, @PathVariable("pelatihanId") String pelatihanId) {
        pelatihanService.delete(user, pelatihanId);
        return WebResponse.<String>builder().data("OK").build();
    }

}
