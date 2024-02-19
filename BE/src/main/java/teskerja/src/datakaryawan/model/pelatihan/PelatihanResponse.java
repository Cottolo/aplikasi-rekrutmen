package teskerja.src.datakaryawan.model.pelatihan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PelatihanResponse {
    private String id;
    private String namaPelatihan;
    private String sertifikat;
    private String tahun;
}
