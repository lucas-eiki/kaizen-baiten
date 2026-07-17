package io.github.lucas_eiki.kaizen_baiten_api.auth.listener;

import io.github.lucas_eiki.kaizen_baiten_api.auth.event.AtivacaoContaSolicitadaEvent;
import io.github.lucas_eiki.kaizen_baiten_api.auth.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class AtivacaoContaSolicitadaListener {

    private final EmailService emailService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void on(AtivacaoContaSolicitadaEvent event) {
        try {
            emailService.enviarAtivacaoConta(event.nome(), event.email(), event.token());
        } catch (MessagingException e) {
            throw new RuntimeException("Erro ao enviar email de ativação de conta", e);
        }
    }
}
