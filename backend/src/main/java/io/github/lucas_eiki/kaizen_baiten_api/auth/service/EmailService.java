package io.github.lucas_eiki.kaizen_baiten_api.auth.service;

import io.github.lucas_eiki.kaizen_baiten_api.auth.model.TipoToken;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void enviarAtivacaoConta(String nome, String email, String tokenGerado) throws MessagingException {
        MimeMessage mensagem = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mensagem);

        helper.setTo(email);

        helper.setSubject("Ative sua conta do Kaizen Baiten");

        //todo: montar e estilizar o HTML com o DNS correto
        String html = """
                <h1>Kaizen Baiten</h1>
                <h2>Olá, %s</h2>
                <p>
                    Sua conta foi criada com sucesso. Para concluir seu cadastro e ativar sua conta,
                    defina sua senha clicando no botão abaixo:
                </p>
                <a href="http://localhost:8080/auth/ativacao?token=%s">
                    Ative sua conta
                </a>
                <p>
                    Este link é válido por <strong>%s</strong>.
                </p>
                <p>
                    Se você não esperava este e-mail, pode ignorá-lo com segurança.
                </p>
                """.formatted(nome, tokenGerado, TipoToken.ATIVACAO_CONTA.getDescricaoValidade());

        helper.setText(html, true);

        mailSender.send(mensagem);
    }
}
