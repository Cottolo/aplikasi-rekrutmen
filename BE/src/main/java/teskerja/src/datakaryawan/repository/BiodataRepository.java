package teskerja.src.datakaryawan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teskerja.src.datakaryawan.entity.Biodata;

import java.util.Optional;

@Repository
public interface BiodataRepository extends JpaRepository<Biodata,String> {
    Optional<Biodata> findFirstByToken(String token);
    Optional<Biodata> findFirstByEmail(String email);
}
