package teskerja.src.datakaryawan.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "biodata")
public class Biodata {
    @Id
    private String email;
    private String password;
    private String token;
    @Column(name = "token_expired_at")
    private Long tokenExpiredAt;
    private String role;
    private String posisi;
    private String nama;
    private String nik;
    private String ttl;
    @Column(name = "jenis_kelamin")
    private String jenisKelamin;
    private String agama;
    @Column(name = "golongan_darah")
    private String golonganDarah;
    private String status;
    @Column(name = "alamat_ktp")
    private String alamatKtp;
    @Column(name = "alamat_tinggal")
    private String alamatTinggal;
    @Column(name = "no_telp")
    private String noTelp;
    @Column(name = "orang_terdekat")
    private String orangTerdekat;
    private String skill;
    @Column(name = "bersedia_ditempatkan")
    private String bersediaDitempatkan;
    @Column(name = "penghasilan_diharapkan")
    private String penghasilaDiharapkan;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pendidikan> pendidikan;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pelatihan> pelatihan;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pengalaman> pengalaman;


}
