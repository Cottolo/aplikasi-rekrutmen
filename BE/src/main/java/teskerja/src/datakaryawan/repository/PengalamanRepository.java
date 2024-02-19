package teskerja.src.datakaryawan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teskerja.src.datakaryawan.entity.Biodata;
import teskerja.src.datakaryawan.entity.Pengalaman;
import teskerja.src.datakaryawan.entity.Pengalaman;

import java.util.List;
import java.util.Optional;

@Repository
public interface PengalamanRepository extends JpaRepository<Pengalaman,String> {
    Optional<Pengalaman> findFirstByUserAndId(Biodata user, String id);
    List<Pengalaman> findAllByUser(Biodata user);
}
