package io.github.lucas_eiki.kaizen_baiten_api.usuario.model;

public enum StatusUsuario {
    ATIVO,
    PENDENTE,
    INATIVO;

    public static StatusUsuario from(Usuario usuario) {
        if (usuario.getDeletadoEm() != null) {
            return INATIVO;
        }

        if (usuario.getAtivadoEm() == null) {
            return PENDENTE;
        }

        return ATIVO;
    }
}
