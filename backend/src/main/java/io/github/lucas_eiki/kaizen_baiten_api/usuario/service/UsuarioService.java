package io.github.lucas_eiki.kaizen_baiten_api.usuario.service;

import io.github.lucas_eiki.kaizen_baiten_api.auth.event.AtivacaoContaSolicitadaEvent;
import io.github.lucas_eiki.kaizen_baiten_api.auth.model.TipoToken;
import io.github.lucas_eiki.kaizen_baiten_api.auth.repository.TokenRepository;
import io.github.lucas_eiki.kaizen_baiten_api.auth.service.EmailService;
import io.github.lucas_eiki.kaizen_baiten_api.auth.service.TokenService;
import io.github.lucas_eiki.kaizen_baiten_api.cargo.exception.CargoNaoEncontradoException;
import io.github.lucas_eiki.kaizen_baiten_api.cargo.repository.CargoRepository;
import io.github.lucas_eiki.kaizen_baiten_api.common.exception.OperacaoNaoPermitidaException;
import io.github.lucas_eiki.kaizen_baiten_api.fiado.repository.FiadoRepositoy;
import io.github.lucas_eiki.kaizen_baiten_api.fiado.repository.MovimentacaoFiadoRepository;
import io.github.lucas_eiki.kaizen_baiten_api.log.model.Acao;
import io.github.lucas_eiki.kaizen_baiten_api.log.repository.LogRepository;
import io.github.lucas_eiki.kaizen_baiten_api.pedido.repository.PedidoRepository;
import io.github.lucas_eiki.kaizen_baiten_api.usuario.dto.CriarUsuarioRequest;
import io.github.lucas_eiki.kaizen_baiten_api.usuario.dto.UsuarioEdicaoRequest;
import io.github.lucas_eiki.kaizen_baiten_api.usuario.dto.UsuarioEdicaoResponse;
import io.github.lucas_eiki.kaizen_baiten_api.usuario.dto.UsuarioResponse;
import io.github.lucas_eiki.kaizen_baiten_api.usuario.exception.EmailJaCadastradoException;
import io.github.lucas_eiki.kaizen_baiten_api.usuario.exception.UsuarioInativoException;
import io.github.lucas_eiki.kaizen_baiten_api.usuario.exception.UsuarioNaoEncontradoException;
import io.github.lucas_eiki.kaizen_baiten_api.usuario.model.StatusUsuario;
import io.github.lucas_eiki.kaizen_baiten_api.usuario.model.Usuario;
import io.github.lucas_eiki.kaizen_baiten_api.usuario.repository.UsuarioRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final CargoRepository cargoRepository;
    private final PedidoRepository pedidoRepository;
    private final FiadoRepositoy fiadoRepositoy;
    private final MovimentacaoFiadoRepository movimentacaoFiadoRepository;
    private final LogRepository logRepository;
    private final TokenRepository tokenRepository;
    private final TokenService tokenService;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public UsuarioResponse criarUsuario(CriarUsuarioRequest request) {
        if (usuarioRepository.existsByEmail(request.email())) {
            throw new EmailJaCadastradoException();
        }
        var cargo = cargoRepository.findById(request.cargoId())
                .orElseThrow(() -> new CargoNaoEncontradoException(request.cargoId()));

        var usuario = new Usuario();
        usuario.setNome(request.nome());
        usuario.setEmail(request.email());
        usuario.setCargo(cargo);

        usuarioRepository.save(usuario);

        String token = tokenService.criarToken(usuario, TipoToken.ATIVACAO_CONTA);

        eventPublisher.publishEvent(
                new AtivacaoContaSolicitadaEvent(
                        usuario.getNome(),
                        usuario.getEmail(),
                        token)
        );

        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                StatusUsuario.from(usuario),
                usuario.getCargo().getNome(),
                null
        );
    }

    public Page<UsuarioResponse> listar(Pageable pageable) {
        return usuarioRepository.findAll(pageable)
                .map(usuario -> new UsuarioResponse(
                        usuario.getId(),
                        usuario.getNome(),
                        usuario.getEmail(),
                        StatusUsuario.from(usuario),
                        usuario.getCargo().getNome(),
                        usuario.getImagemPerfilPath()
                ));
    }

    public UsuarioEdicaoResponse buscar(Long id) {
        return usuarioRepository.findById(id)
                .map(usuario -> new UsuarioEdicaoResponse(
                        usuario.getId(),
                        usuario.getNome(),
                        usuario.getEmail(),
                        usuario.getCargo().getId(),
                        usuario.getImagemPerfilPath()
                ))
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));
    }

    @Transactional
    public UsuarioEdicaoResponse atualizar(Long id, UsuarioEdicaoRequest request) {
        var usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));

        atualizarDados(usuario, request);
        boolean emailAlterado = atualizarEmail(usuario, request);

        if (emailAlterado) {
            tokenService.desativarTokenPorUsuario(usuario.getId());
            String token = tokenService.criarToken(usuario, TipoToken.ATIVACAO_CONTA);
            eventPublisher.publishEvent(new AtivacaoContaSolicitadaEvent(usuario.getNome(), usuario.getEmail(), token));
        }

        return new UsuarioEdicaoResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getCargo().getId(),
                usuario.getImagemPerfilPath()
        );
    }

    private void atualizarDados(Usuario usuario, UsuarioEdicaoRequest request) {
        if (usuario.getDeletadoEm() != null) {
            throw new UsuarioInativoException(usuario.getId());
        }

        if (request.nome() != null && !request.nome().equals(usuario.getNome())) {
            usuario.setNome(request.nome());
        }

        if (request.cargoId() != null && !request.cargoId().equals(usuario.getCargo().getId())) {
            var cargo = cargoRepository.findById(request.cargoId())
                    .orElseThrow(() -> new CargoNaoEncontradoException(request.cargoId()));
            usuario.setCargo(cargo);
        }
    }

    private boolean atualizarEmail(Usuario usuario, UsuarioEdicaoRequest request) {
        if (request.email() != null && !request.email().equals(usuario.getEmail())) {
            if (usuario.getAtivadoEm() != null) {
                throw new OperacaoNaoPermitidaException("Não pode alterar o e-mail de um usuário ativo.");
            }

            if (usuarioRepository.existsByEmail(request.email())) {
                throw new EmailJaCadastradoException();
            }

            usuario.setEmail(request.email());
            return true;
        }

        return false;
    }

    @Transactional
    public void deletar(Long id) {
        var usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));

        if (usuarioEstaEmUso(id)) {
            usuario.setDeletadoEm(Instant.now());
        } else {
            tokenRepository.deleteAllByUsuarioId(id);
            logRepository.deleteAllByUsuarioId(id);
            usuarioRepository.deleteById(id);
        }

    }

    private boolean usuarioEstaEmUso(Long id) {
        return pedidoRepository.existsByUsuarioId(id) ||
                fiadoRepositoy.existsByUsuarioId(id) ||
                movimentacaoFiadoRepository.existsByUsuarioId(id) ||
                logRepository.existsByUsuarioIdAndAcaoNotIn(id,
                        List.of(Acao.LOGIN, Acao.LOGIN_FAILED, Acao.LOGOUT));
    }
}
