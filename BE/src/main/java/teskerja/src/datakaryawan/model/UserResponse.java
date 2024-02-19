package teskerja.src.datakaryawan.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private String email;
    private String role;
    private String posisi;
    private String nama;
    private String nik;
    private String ttl;
    private String jenisKelamin;
    private String agama;
    private String golonganDarah;
    private String status;
    private String alamatKtp;
    private String alamatTinggal;
    private String noTelp;
    private String orangTerdekat;
    private String skill;
    private String bersediaDitempatkan;
    private String penghasilaDiharapkan;
}
