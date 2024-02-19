package teskerja.src.datakaryawan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teskerja.src.datakaryawan.entity.Biodata;
import teskerja.src.datakaryawan.entity.Pendidikan;

import java.util.List;
import java.util.Optional;

@Repository
public interface PendidikanRepository  extends JpaRepository<Pendidikan,String> {
    Optional<Pendidikan> findFirstByUserAndId(Biodata user, String id);
    List<Pendidikan> findAllByUser(Biodata user);
}
