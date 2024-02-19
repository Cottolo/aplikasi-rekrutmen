package teskerja.src.datakaryawan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import teskerja.src.datakaryawan.entity.Biodata;
import teskerja.src.datakaryawan.entity.Pelatihan;
import teskerja.src.datakaryawan.model.pelatihan.CreatePelatihanRequest;
import teskerja.src.datakaryawan.model.pelatihan.PelatihanResponse;
import teskerja.src.datakaryawan.model.pelatihan.UpdatePelatihanRequest;
import teskerja.src.datakaryawan.repository.PelatihanRepository;

import java.util.List;
import java.util.UUID;

@Service
public class PelatihanService {

    @Autowired
    private PelatihanRepository pelatihanRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public PelatihanResponse create(Biodata user, CreatePelatihanRequest request) {
        validationService.validate(request);

        Pelatihan pelatihan = new Pelatihan();
        pelatihan.setId(UUID.randomUUID().toString());
        pelatihan.setNamaPelatihan(request.getNamaPelatihan());
        pelatihan.setSertifikat(request.getSertifikat());
        pelatihan.setTahun(request.getTahun());
        pelatihan.setUser(user);

        pelatihanRepository.save(pelatihan);

        return toPelatihanResponse(pelatihan);
    }

    private PelatihanResponse toPelatihanResponse(Pelatihan pelatihan) {
        return PelatihanResponse.builder()
                .id(pelatihan.getId())
                .namaPelatihan(pelatihan.getNamaPelatihan())
                .sertifikat(pelatihan.getSertifikat())
                .tahun(pelatihan.getTahun())
                .build();
    }

    @Transactional(readOnly = true)
    public PelatihanResponse get(Biodata user, String id) {
        Pelatihan pelatihan = pelatihanRepository.findFirstByUserAndId(user, id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));

        return toPelatihanResponse(pelatihan);
    }

    @Transactional(readOnly = true)
    public List<PelatihanResponse> list(Biodata user){
        List<Pelatihan> pelatihans = pelatihanRepository.findAllByUser(user);
        return pelatihans.stream().map(this::toPelatihanResponse).toList();
    }

    @Transactional
    public PelatihanResponse update(Biodata user, UpdatePelatihanRequest request) {
        validationService.validate(request);

        Pelatihan pelatihan = pelatihanRepository.findFirstByUserAndId(user, request.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));

        pelatihan.setNamaPelatihan(request.getNamaPelatihan());
        pelatihan.setSertifikat(request.getSertifikat());
        pelatihan.setTahun(request.getTahun());

        pelatihanRepository.save(pelatihan);

        return toPelatihanResponse(pelatihan);
    }

    @Transactional
    public void delete(Biodata user, String id) {
        Pelatihan pelatihan = pelatihanRepository.findFirstByUserAndId(user, id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));

        pelatihanRepository.delete(pelatihan);
    }

}
