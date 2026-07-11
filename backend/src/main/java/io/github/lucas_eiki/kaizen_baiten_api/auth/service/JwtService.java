package io.github.lucas_eiki.kaizen_baiten_api.auth.service;

import io.github.lucas_eiki.kaizen_baiten_api.auth.dto.DadosToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtEncoder jwtEncoder;

    public String gerarToken(DadosToken dados) {

        var agora = Instant.now();
        var expiraEmSegundos = 12L * 60 * 60;

        var claims = JwtClaimsSet.builder()
                .issuer("kaizen-baiten-api")
                .subject(dados.usuarioId().toString())
                .claim("permissoes", dados.permissoes())
                .issuedAt(agora)
                .expiresAt(agora.plusSeconds(expiraEmSegundos))
                .build();

        var valorJwt = jwtEncoder.encode(JwtEncoderParameters.from(claims));
        return valorJwt.getTokenValue();
    }

}
