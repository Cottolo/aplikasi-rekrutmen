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
@Table(name = "riwayat_pelatihan")
public class Pelatihan {
    @Id
    private String id;
    @Column(name = "nama_pelatihan")
    private String namaPelatihan;
    private String sertifikat;
    private String tahun;
    @ManyToOne
    @JoinColumn(name = "email", referencedColumnName = "email")
    private Biodata user;
}
