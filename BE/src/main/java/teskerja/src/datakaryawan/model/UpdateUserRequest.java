package teskerja.src.datakaryawan.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserRequest {
    @Size(max = 100)
    @NotBlank
    @Email
    private String email;
//    @Size(max = 100)
//    private String role;
    @Size(max = 100)
    private String posisi;
    @Size(max = 100)
    private String nama;
    @Size(max = 100)
    private String nik;
    @Size(max = 100)
    private String ttl;
    @Size(max = 100)
    private String jenisKelamin;
    @Size(max = 100)
    private String agama;
    @Size(max = 100)
    private String golonganDarah;
    @Size(max = 100)
    private String status;
    @Size(max = 100)
    private String alamatKtp;
    @Size(max = 100)
    private String alamatTinggal;
    @Size(max = 100)
    private String noTelp;
    @Size(max = 100)
    private String orangTerdekat;
    @Size(max = 100)
    private String skill;
    @Size(max = 100)
    private String bersediaDitempatkan;
    @Size(max = 100)
    private String penghasilaDiharapkan;
}
