package teskerja.src.datakaryawan.model.pendidikan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PendidikanResponse {
    private String id;
    private String jenjangPendidikan;
    private String namaInstitusi;
    private String jurusan;
    private String tahunLulus;
    private String ipk;
}
