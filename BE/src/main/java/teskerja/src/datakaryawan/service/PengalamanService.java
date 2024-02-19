package teskerja.src.datakaryawan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import teskerja.src.datakaryawan.entity.Biodata;
import teskerja.src.datakaryawan.entity.Pengalaman;
import teskerja.src.datakaryawan.model.pengalaman.CreatePengalamanRequest;
import teskerja.src.datakaryawan.model.pengalaman.PengalamanResponse;
import teskerja.src.datakaryawan.model.pengalaman.UpdatePengalamanRequest;
import teskerja.src.datakaryawan.repository.PengalamanRepository;

import java.util.List;
import java.util.UUID;

@Service
public class PengalamanService {

    @Autowired
    private PengalamanRepository pengalamanRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public PengalamanResponse create(Biodata user, CreatePengalamanRequest request) {
        validationService.validate(request);

        Pengalaman pengalaman = new Pengalaman();
        pengalaman.setId(UUID.randomUUID().toString());
        pengalaman.setNamaPerusahaan(request.getNamaPerusahaan());
        pengalaman.setPosisiTerakhir(request.getPosisiTerakhir());
        pengalaman.setPendapatanTerakhir(request.getPendapatanTerakhir());
        pengalaman.setTahun(request.getTahun());
        pengalaman.setUser(user);

        pengalamanRepository.save(pengalaman);

        return toPengalamanResponse(pengalaman);
    }

    private PengalamanResponse toPengalamanResponse(Pengalaman pengalaman) {
        return PengalamanResponse.builder()
                .id(pengalaman.getId())
                .namaPerusahaan(pengalaman.getNamaPerusahaan())
                .posisiTerakhir(pengalaman.getPosisiTerakhir())
                .pendapatanTerakhir(pengalaman.getPendapatanTerakhir())
                .tahun(pengalaman.getTahun())
                .build();
    }

    @Transactional(readOnly = true)
    public PengalamanResponse get(Biodata user, String id) {
        Pengalaman pengalaman = pengalamanRepository.findFirstByUserAndId(user, id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));

        return toPengalamanResponse(pengalaman);
    }

    @Transactional(readOnly = true)
    public List<PengalamanResponse> list(Biodata user){
        List<Pengalaman> pengalamans = pengalamanRepository.findAllByUser(user);
        return pengalamans.stream().map(this::toPengalamanResponse).toList();
    }

    @Transactional
    public PengalamanResponse update(Biodata user, UpdatePengalamanRequest request) {
        validationService.validate(request);

        Pengalaman pengalaman = pengalamanRepository.findFirstByUserAndId(user, request.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));

        pengalaman.setNamaPerusahaan(request.getNamaPerusahaan());
        pengalaman.setPosisiTerakhir(request.getPosisiTerakhir());
        pengalaman.setPendapatanTerakhir(request.getPendapatanTerakhir());
        pengalaman.setTahun(request.getTahun());

        pengalamanRepository.save(pengalaman);

        return toPengalamanResponse(pengalaman);
    }

    @Transactional
    public void delete(Biodata user, String id) {
        Pengalaman pengalaman = pengalamanRepository.findFirstByUserAndId(user, id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));

        pengalamanRepository.delete(pengalaman);
    }

}
