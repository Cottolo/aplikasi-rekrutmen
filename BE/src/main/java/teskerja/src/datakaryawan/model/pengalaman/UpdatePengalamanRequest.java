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
public class UpdatePengalamanRequest {
    @NotBlank
    @Size(max = 100)
    private String namaPerusahaan;
    @NotBlank
    @Size(max = 100)
    private String id;
    @Size(max = 100)
    private String posisiTerakhir;
    @Size(max = 100)
    private String pendapatanTerakhir;
    private String tahun;
}
