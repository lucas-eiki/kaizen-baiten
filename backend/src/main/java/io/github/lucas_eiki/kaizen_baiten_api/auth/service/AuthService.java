package io.github.lucas_eiki.kaizen_baiten_api.auth.service;

import io.github.lucas_eiki.kaizen_baiten_api.auth.dto.DadosTokenJwt;
import io.github.lucas_eiki.kaizen_baiten_api.auth.dto.LoginRequest;
import io.github.lucas_eiki.kaizen_baiten_api.auth.dto.LoginResponse;
import io.github.lucas_eiki.kaizen_baiten_api.auth.exception.ContaDesativadaException;
import io.github.lucas_eiki.kaizen_baiten_api.auth.exception.ContaNaoAtivadaException;
import io.github.lucas_eiki.kaizen_baiten_api.usuario.model.Usuario;
import io.github.lucas_eiki.kaizen_baiten_api.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        try {
            var usuario = autenticarUsuario(request);

            var dadosTokenJwt = criarDadosTokenJwt(usuario);

            String token = jwtService.gerarToken(dadosTokenJwt);

            return new LoginResponse(token);
        } catch (ContaNaoAtivadaException | ContaDesativadaException e) {
            throw new BadCredentialsException("Usuário ou senha inválidos", e);
        }
    }

    private Usuario autenticarUsuario(LoginRequest request) {
        var usuario = usuarioRepository.findByEmail(request.email())
                .orElseThrow(() -> new BadCredentialsException("Usuário ou senha inválidos"));

        if (!passwordEncoder.matches(request.senha(), usuario.getSenhaHash())) {
            throw new BadCredentialsException("Usuário ou senha inválidos");
        }

        if (usuario.getSenhaHash() == null || usuario.getAtivadoEm() == null) {
            throw new ContaNaoAtivadaException();
        }

        if (usuario.getDeletadoEm() != null) {
            throw new ContaDesativadaException();
        }

        return usuario;
    }

    private DadosTokenJwt criarDadosTokenJwt(Usuario usuario) {
        var permissoes = usuario.getCargo()
                .getPermissoes()
                .stream()
                .map(cargoPermissao -> cargoPermissao.getPermissao().getNome())
                .toList();

        return new DadosTokenJwt(usuario.getId(), permissoes);
    }
}
