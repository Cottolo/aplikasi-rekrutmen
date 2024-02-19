package teskerja.src.datakaryawan.model.pengalaman;

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
public class CreatePengalamanRequest {
    @NotBlank
    @Size(max = 100)
    private String namaPerusahaan;
    @Size(max = 100)
    private String posisiTerakhir;
    @Size(max = 100)
    private String pendapatanTerakhir;
    @Size(max = 100)
    private String tahun;
}
