package io.github.lucas_eiki.kaizen_baiten_api.auth.service;

import io.github.lucas_eiki.kaizen_baiten_api.auth.model.TipoToken;
import io.github.lucas_eiki.kaizen_baiten_api.auth.model.Token;
import io.github.lucas_eiki.kaizen_baiten_api.auth.repository.TokenRepository;
import io.github.lucas_eiki.kaizen_baiten_api.usuario.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.HexFormat;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

    public String criarToken(Usuario usuario, TipoToken tipoToken) {
        String tokenGerado = gerarToken();
        String tokenHash = gerarHash(tokenGerado);

        var token = new Token();
        token.setUsuario(usuario);
        token.setTipo(tipoToken);
        token.setTokenHash(tokenHash);
        token.setExpiraEm(Instant.now().plus(tipoToken.getValidade()));

        tokenRepository.save(token);

        return tokenGerado;
    }

    private String gerarToken() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);

        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(bytes);
    }

    public String gerarHash(String token) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

            byte[] hash = messageDigest.digest(token.getBytes(StandardCharsets.UTF_8));

            return HexFormat.of().formatHex(hash);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void desativarTokenPorUsuario(Long idUsuario) {
        var tokens = tokenRepository.findByUsuarioIdAndTipoAndUtilizadoEmIsNullAndExpiraEmAfter(idUsuario, TipoToken.ATIVACAO_CONTA, Instant.now());

        tokens.forEach(token -> token.setUtilizadoEm(Instant.now()));

        tokenRepository.saveAll(tokens);
    }
}
