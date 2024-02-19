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
@Table(name = "riwayat_pekerjaan")
public class Pengalaman {
    @Id
    private String id;
    @Column(name = "nama_perusahaan")
    private String namaPerusahaan;
    @Column(name = "posisi_terakhir")
    private String posisiTerakhir;
    @Column(name = "pendapatan_terakhir")
    private String pendapatanTerakhir;
    private String tahun;

    @ManyToOne
    @JoinColumn(name = "email", referencedColumnName = "email")
    private Biodata user;
}
