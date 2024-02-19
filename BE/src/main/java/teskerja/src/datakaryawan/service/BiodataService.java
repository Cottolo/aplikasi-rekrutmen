package teskerja.src.datakaryawan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import teskerja.src.datakaryawan.entity.Biodata;
import teskerja.src.datakaryawan.entity.Pelatihan;
import teskerja.src.datakaryawan.entity.Pengalaman;
import teskerja.src.datakaryawan.model.RegisterRequest;
import teskerja.src.datakaryawan.model.UpdateUserRequest;
import teskerja.src.datakaryawan.model.UserResponse;
import teskerja.src.datakaryawan.model.pengalaman.PengalamanResponse;
import teskerja.src.datakaryawan.repository.BiodataRepository;
import teskerja.src.datakaryawan.security.BCrypt;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Service
@Slf4j
public class BiodataService {
    @Autowired
    private BiodataRepository biodataRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public void register(RegisterRequest request){
        validationService.validate(request);

        if (biodataRepository.existsById(request.getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already registered");
        }

        Biodata user = new Biodata();
        user.setEmail(request.getEmail());
        user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));

        biodataRepository.save(user);
    }

    public UserResponse get(Biodata user) {
        return UserResponse.builder()
                .email(user.getEmail())
                .role(user.getRole())
                .posisi(user.getPosisi())
                .nama(user.getNama())
                .nik(user.getNik())
                .ttl(user.getTtl())
                .jenisKelamin(user.getJenisKelamin())
                .agama(user.getAgama())
                .golonganDarah(user.getGolonganDarah())
                .status(user.getStatus())
                .alamatKtp(user.getAlamatKtp())
                .alamatTinggal(user.getAlamatTinggal())
                .noTelp(user.getNoTelp())
                .orangTerdekat(user.getOrangTerdekat())
                .skill(user.getSkill())
                .bersediaDitempatkan(user.getBersediaDitempatkan())
                .penghasilaDiharapkan(user.getPenghasilaDiharapkan())
                .build();
    }

    @Transactional
    public UserResponse update(Biodata user, UpdateUserRequest request) {
        validationService.validate(request);

        log.info("REQUEST : {}", request);

        user.setEmail(request.getEmail());
        user.setPosisi(request.getPosisi());
        user.setNama(request.getNama());
        user.setNik(request.getNik());
        user.setTtl(request.getTtl());
        user.setJenisKelamin(request.getJenisKelamin());
        user.setAgama(request.getAgama());
        user.setGolonganDarah(request.getGolonganDarah());
        user.setStatus(request.getStatus());
        user.setAlamatKtp(request.getAlamatKtp());
        user.setAlamatTinggal(request.getAlamatTinggal());
        user.setNoTelp(request.getNoTelp());
        user.setOrangTerdekat(request.getOrangTerdekat());
        user.setSkill(request.getSkill());
        user.setBersediaDitempatkan(request.getBersediaDitempatkan());
        user.setPenghasilaDiharapkan(request.getPenghasilaDiharapkan());
        biodataRepository.save(user);

        log.info("USER : {}", user.getNama());

        return UserResponse.builder()
                .email(user.getEmail())
                .posisi(user.getPosisi())
                .nama(user.getNama())
                .nik(user.getNik())
                .ttl(user.getTtl())
                .jenisKelamin(user.getJenisKelamin())
                .agama(user.getAgama())
                .golonganDarah(user.getGolonganDarah())
                .status(user.getStatus())
                .alamatKtp(user.getAlamatKtp())
                .alamatTinggal(user.getAlamatTinggal())
                .noTelp(user.getNoTelp())
                .orangTerdekat(user.getOrangTerdekat())
                .skill(user.getSkill())
                .bersediaDitempatkan(user.getBersediaDitempatkan())
                .penghasilaDiharapkan(user.getPenghasilaDiharapkan())
                .build();
    }

    @Transactional(readOnly = true)
    public List<UserResponse> list(Biodata user){
        List<Biodata> users = biodataRepository.findAll();
        return users.stream().map(this::toUserResponse).toList();
    }

    private UserResponse toUserResponse(Biodata user){
        return UserResponse.builder()
                .email(user.getEmail())
                .posisi(user.getPosisi())
                .nama(user.getNama())
                .nik(user.getNik())
                .ttl(user.getTtl())
                .jenisKelamin(user.getJenisKelamin())
                .agama(user.getAgama())
                .golonganDarah(user.getGolonganDarah())
                .status(user.getStatus())
                .alamatKtp(user.getAlamatKtp())
                .alamatTinggal(user.getAlamatTinggal())
                .noTelp(user.getNoTelp())
                .orangTerdekat(user.getOrangTerdekat())
                .skill(user.getSkill())
                .bersediaDitempatkan(user.getBersediaDitempatkan())
                .penghasilaDiharapkan(user.getPenghasilaDiharapkan())
                .build();
    }

    @Transactional
    public UserResponse updateByEmail(Biodata user, UpdateUserRequest request) {
        validationService.validate(request);

        log.info("REQUEST : {}", request);
        Biodata userUpdate = new Biodata();
        userUpdate = biodataRepository.findFirstByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));;

        userUpdate.setEmail(request.getEmail());
        userUpdate.setPosisi(request.getPosisi());
        userUpdate.setNama(request.getNama());
        userUpdate.setNik(request.getNik());
        userUpdate.setTtl(request.getTtl());
        userUpdate.setJenisKelamin(request.getJenisKelamin());
        userUpdate.setAgama(request.getAgama());
        userUpdate.setGolonganDarah(request.getGolonganDarah());
        userUpdate.setStatus(request.getStatus());
        userUpdate.setAlamatKtp(request.getAlamatKtp());
        userUpdate.setAlamatTinggal(request.getAlamatTinggal());
        userUpdate.setNoTelp(request.getNoTelp());
        userUpdate.setOrangTerdekat(request.getOrangTerdekat());
        userUpdate.setSkill(request.getSkill());
        userUpdate.setBersediaDitempatkan(request.getBersediaDitempatkan());
        userUpdate.setPenghasilaDiharapkan(request.getPenghasilaDiharapkan());
        biodataRepository.save(user);

        log.info("USER : {}", user.getNama());

        return UserResponse.builder()
                .email(user.getEmail())
                .posisi(user.getPosisi())
                .nama(user.getNama())
                .nik(user.getNik())
                .ttl(user.getTtl())
                .jenisKelamin(user.getJenisKelamin())
                .agama(user.getAgama())
                .golonganDarah(user.getGolonganDarah())
                .status(user.getStatus())
                .alamatKtp(user.getAlamatKtp())
                .alamatTinggal(user.getAlamatTinggal())
                .noTelp(user.getNoTelp())
                .orangTerdekat(user.getOrangTerdekat())
                .skill(user.getSkill())
                .bersediaDitempatkan(user.getBersediaDitempatkan())
                .penghasilaDiharapkan(user.getPenghasilaDiharapkan())
                .build();
    }

    @Transactional
    public void delete(Biodata user, String email) {
        Biodata biodata = biodataRepository.findFirstByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));

        biodataRepository.delete(biodata);
    }

}
