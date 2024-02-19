package teskerja.src.datakaryawan.model.pendidikan;

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
public class UpdatePendidikanRequest {
    @NotBlank
    @Size(max = 100)
    private String jenjangPendidikan;
    @NotBlank
    @Size(max = 100)
    private String id;
    @Size(max = 100)
    private String namaInstitusi;
    @Size(max = 100)
    private String jurusan;
    @Size(max = 100)
    private String tahunLulus;
    @Size(max = 100)
    private String ipk;
}
