package io.github.lucas_eiki.kaizen_baiten_api.auth.dto;

import io.github.lucas_eiki.kaizen_baiten_api.permissao.model.NomePermissao;

import java.util.List;

public record DadosTokenJwt(
        Long usuarioId,
        List<NomePermissao> permissoes
) {
}
