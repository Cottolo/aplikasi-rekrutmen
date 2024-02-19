package teskerja.src.datakaryawan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import teskerja.src.datakaryawan.entity.Biodata;
import teskerja.src.datakaryawan.model.LoginRequest;
import teskerja.src.datakaryawan.model.TokenResponse;
import teskerja.src.datakaryawan.repository.BiodataRepository;
import teskerja.src.datakaryawan.security.BCrypt;

import java.util.UUID;

@Service
public class AuthService {
    @Autowired
    private BiodataRepository biodataRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public TokenResponse login(LoginRequest request){
        validationService.validate(request);

        Biodata user = biodataRepository.findById(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong password or username"));
        if(BCrypt.checkpw(request.getPassword(), user.getPassword())){
            user.setToken(UUID.randomUUID().toString());
            user.setTokenExpiredAt(createSession());

            return TokenResponse.builder()
                    .token(user.getToken())
                    .expiredAt(user.getTokenExpiredAt())
                    .role(user.getRole())
                    .build();
        }else {
            throw  new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong password or username");
        }
    }

    private Long createSession(){
        return System.currentTimeMillis()+(1000*16*24*30);
    }

    @Transactional
    public void logout(Biodata user) {
        user.setToken(null);
        user.setTokenExpiredAt(null);

        biodataRepository.save(user);
    }
}
