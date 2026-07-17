package io.github.lucas_eiki.kaizen_baiten_api.auth.service;

import io.github.lucas_eiki.kaizen_baiten_api.auth.dto.*;
import io.github.lucas_eiki.kaizen_baiten_api.auth.exception.ContaDesativadaException;
import io.github.lucas_eiki.kaizen_baiten_api.auth.exception.ContaNaoAtivadaException;
import io.github.lucas_eiki.kaizen_baiten_api.auth.exception.TokenInvalidoException;
import io.github.lucas_eiki.kaizen_baiten_api.auth.exception.TokenNaoEncontradoException;
import io.github.lucas_eiki.kaizen_baiten_api.auth.model.Token;
import io.github.lucas_eiki.kaizen_baiten_api.auth.repository.TokenRepository;
import io.github.lucas_eiki.kaizen_baiten_api.auth.exception.ContaJaAtivadaException;
import io.github.lucas_eiki.kaizen_baiten_api.usuario.exception.UsuarioNaoEncontradoException;
import io.github.lucas_eiki.kaizen_baiten_api.usuario.model.Usuario;
import io.github.lucas_eiki.kaizen_baiten_api.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final TokenRepository tokenRepository;
    private final TokenService tokenService;
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

    public ValidacaoTokenResponse validarToken(String token) {
        var tokenBuscado = tokenRepository.findByTokenHash(tokenService.gerarHash(token))
                .orElse(null);

        if (tokenBuscado == null) {
            return new ValidacaoTokenResponse(false);
        }

        var agora = Instant.now();

        return new ValidacaoTokenResponse(isTokenValido(tokenBuscado, agora));
    }

    @Transactional
    public void ativarConta(CriarSenhaRequest request) {
        var token = tokenRepository.findByTokenHash(tokenService.gerarHash(request.token()))
                .orElseThrow(() -> new TokenNaoEncontradoException("Token não encontrado"));

        var agora = Instant.now();

        if (!isTokenValido(token, agora)) {
            throw new TokenInvalidoException("Token inválido");
        }

        var usuario = usuarioRepository.findById(token.getUsuario().getId())
                .orElseThrow(() -> new UsuarioNaoEncontradoException(token.getUsuario().getId()));

        if (usuario.getAtivadoEm() != null) {
            throw new ContaJaAtivadaException(usuario.getId());
        }

        usuario.setSenhaHash(passwordEncoder.encode(request.senha()));
        usuario.setAtivadoEm(agora);
        token.setUtilizadoEm(agora);
    }

    private boolean isTokenValido(Token token, Instant agora) {
        return token.getExpiraEm().isAfter(agora) &&
                token.getUtilizadoEm() == null;
    }
}
