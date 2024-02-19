package teskerja.src.datakaryawan.model.pengalaman;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PengalamanResponse {
    private String id;
    private String namaPerusahaan;
    private String posisiTerakhir;
    private String pendapatanTerakhir;
    private String tahun;
}
