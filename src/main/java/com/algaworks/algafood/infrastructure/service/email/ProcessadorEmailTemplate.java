package com.algaworks.algafood.infrastructure.service.email;

import com.algaworks.algafood.domain.service.EnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Component
public class ProcessadorEmailTemplate {

    @Autowired
    private SpringTemplateEngine templateEngine;

    protected String processarTemplate(EnvioEmailService.Mensagem mensagem) {
        try {
            Context thymeleafContext = new Context();
            thymeleafContext.setVariables(mensagem.getVariaveis());

            return templateEngine.process(mensagem.getCorpo(), thymeleafContext);
        } catch (Exception e) {
            throw new EmailException("Não foi possível montar o template do e-mail");
        }
    }
}
