package teskerja.src.datakaryawan.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pendidikan_terakhir")
public class Pendidikan {
    @Id
    private String id;
    @Column(name = "jenjang_pendidikan")
    private String jenjangPendidikan;
    @Column(name = "nama_institusi")
    private String namaInstitusi;
    private String jurusan;
    @Column(name = "tahun_lulus")
    private String tahunLulus;
    private String ipk;
    @ManyToOne
    @JoinColumn(name = "email", referencedColumnName = "email")
    private Biodata user;


}
