package io.github.lucas_eiki.kaizen_baiten_api.usuario.service;

import io.github.lucas_eiki.kaizen_baiten_api.auth.service.TokenService;
import io.github.lucas_eiki.kaizen_baiten_api.usuario.dto.CriarUsuarioRequest;
import io.github.lucas_eiki.kaizen_baiten_api.usuario.dto.UsuarioResponse;
import io.github.lucas_eiki.kaizen_baiten_api.usuario.model.Usuario;
import io.github.lucas_eiki.kaizen_baiten_api.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioResponse criarUsuario(CriarUsuarioRequest request) {
        var usuario = new Usuario();
        usuario.setNome(request.nome());
        usuario.setEmail(request.email());
        usuario.setCargo(request.cargo());

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

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
