package teskerja.src.datakaryawan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import teskerja.src.datakaryawan.entity.Biodata;
import teskerja.src.datakaryawan.entity.Pendidikan;
import teskerja.src.datakaryawan.model.pendidikan.CreatePendidikanRequest;
import teskerja.src.datakaryawan.model.pendidikan.PendidikanResponse;
import teskerja.src.datakaryawan.model.pendidikan.UpdatePendidikanRequest;
import teskerja.src.datakaryawan.repository.PendidikanRepository;
import teskerja.src.datakaryawan.service.ValidationService;

import java.util.List;
import java.util.UUID;

@Service
public class PendididkanService {

    @Autowired
    private PendidikanRepository pendidikanRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public PendidikanResponse create(Biodata user, CreatePendidikanRequest request) {
        validationService.validate(request);

        Pendidikan pendidikan = new Pendidikan();
        pendidikan.setId(UUID.randomUUID().toString());
        pendidikan.setJenjangPendidikan(request.getJenjangPendidikan());
        pendidikan.setNamaInstitusi(request.getNamaInstitusi());
        pendidikan.setJurusan(request.getJurusan());
        pendidikan.setTahunLulus(request.getTahunLulus());
        pendidikan.setIpk(request.getIpk());
        pendidikan.setUser(user);

        pendidikanRepository.save(pendidikan);

        return toPendidikanResponse(pendidikan);
    }

    private PendidikanResponse toPendidikanResponse(Pendidikan pendidikan) {
        return PendidikanResponse.builder()
                .id(pendidikan.getId())
                .jenjangPendidikan(pendidikan.getJenjangPendidikan())
                .namaInstitusi(pendidikan.getNamaInstitusi())
                .jurusan(pendidikan.getJurusan())
                .tahunLulus(pendidikan.getTahunLulus())
                .ipk(pendidikan.getIpk())
                .build();
    }

    @Transactional(readOnly = true)
    public PendidikanResponse get(Biodata user, String id) {
        Pendidikan pendidikan = pendidikanRepository.findFirstByUserAndId(user, id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));

        return toPendidikanResponse(pendidikan);
    }

    @Transactional(readOnly = true)
    public List<PendidikanResponse> list(Biodata user){
        List<Pendidikan> pendidikans = pendidikanRepository.findAllByUser(user);
        return pendidikans.stream().map(this::toPendidikanResponse).toList();
    }

    @Transactional
    public PendidikanResponse update(Biodata user, UpdatePendidikanRequest request) {
        validationService.validate(request);

        Pendidikan pendidikan = pendidikanRepository.findFirstByUserAndId(user, request.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));

//        Pendidikan pendidikan = new Pendidikan();
//        pendidikan.setId(UUID.randomUUID().toString());
        pendidikan.setJenjangPendidikan(request.getJenjangPendidikan());
        pendidikan.setNamaInstitusi(request.getNamaInstitusi());
        pendidikan.setJurusan(request.getJurusan());
        pendidikan.setTahunLulus(request.getTahunLulus());
        pendidikan.setIpk(request.getIpk());

        pendidikanRepository.save(pendidikan);

        return toPendidikanResponse(pendidikan);
    }

    @Transactional
    public void delete(Biodata user, String id) {
        Pendidikan pendidikan = pendidikanRepository.findFirstByUserAndId(user, id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));

        pendidikanRepository.delete(pendidikan);
    }

}
