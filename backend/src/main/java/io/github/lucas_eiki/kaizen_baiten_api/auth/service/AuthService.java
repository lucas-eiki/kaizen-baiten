package io.github.lucas_eiki.kaizen_baiten_api.auth.service;

import io.github.lucas_eiki.kaizen_baiten_api.auth.dto.DadosToken;
import io.github.lucas_eiki.kaizen_baiten_api.auth.dto.LoginRequest;
import io.github.lucas_eiki.kaizen_baiten_api.auth.dto.LoginResponse;
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
        var usuario = autenticarUsuario(request);

        var dadosToken = criarDadosToken(usuario);

        String token = jwtService.gerarToken(dadosToken);

        return new LoginResponse(token);
    }

    private Usuario autenticarUsuario(LoginRequest request) {
        var usuario = usuarioRepository.findByEmail(request.email())
                .orElseThrow(() -> new BadCredentialsException("Usuário ou senha inválidos"));

        if(!passwordEncoder.matches(request.senha(), usuario.getSenhaHash())) {
            throw new BadCredentialsException("Usuário ou senha inválidos");
        }

        return usuario;
    }

    private DadosToken criarDadosToken(Usuario usuario) {
        var permissoes = usuario.getCargo()
                .getPermissoes()
                .stream()
                .map(cargoPermissao -> cargoPermissao.getPermissao().getNome())
                .toList();

        return new DadosToken(usuario.getId(), permissoes);
    }
}
