package teskerja.src.datakaryawan.model.pelatihan;

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
public class UpdatePelatihanRequest {
    @NotBlank
    @Size(max = 100)
    private String namaPelatihan;
    @NotBlank
    @Size(max = 100)
    private String id;
    @Size(max = 100)
    private String sertifikat;
    @Size(max = 100)
    private String tahun;
}
