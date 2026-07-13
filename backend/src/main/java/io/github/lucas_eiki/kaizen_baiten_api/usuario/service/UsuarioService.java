package io.github.lucas_eiki.kaizen_baiten_api.usuario.service;

import io.github.lucas_eiki.kaizen_baiten_api.auth.model.TipoToken;
import io.github.lucas_eiki.kaizen_baiten_api.auth.service.EmailService;
import io.github.lucas_eiki.kaizen_baiten_api.auth.service.TokenService;
import io.github.lucas_eiki.kaizen_baiten_api.usuario.dto.CriarUsuarioRequest;
import io.github.lucas_eiki.kaizen_baiten_api.usuario.dto.UsuarioResponse;
import io.github.lucas_eiki.kaizen_baiten_api.usuario.model.Usuario;
import io.github.lucas_eiki.kaizen_baiten_api.usuario.repository.UsuarioRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final TokenService tokenService;
    private final EmailService emailService;

    public UsuarioResponse criarUsuario(CriarUsuarioRequest request) {
        var usuario = new Usuario();
        usuario.setNome(request.nome());
        usuario.setEmail(request.email());
        usuario.setCargo(request.cargo());

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        String tokenGerado = tokenService.criarToken(usuarioSalvo, TipoToken.ATIVACAO_CONTA);

        try {
            emailService.enviarAtivacaoConta(usuarioSalvo.getNome(), usuario.getEmail(), tokenGerado);
        } catch (MessagingException e) {
            throw new RuntimeException("Erro ao enviar email de ativação de conta", e);
        }

        return new UsuarioResponse(
                usuarioSalvo.getId(),
                usuarioSalvo.getNome(),
                usuarioSalvo.getEmail(),
                usuarioSalvo.getDeletadoEm(),
                usuarioSalvo.getAtivadoEm(),
                usuarioSalvo.getCargo()
        );
    }
}
