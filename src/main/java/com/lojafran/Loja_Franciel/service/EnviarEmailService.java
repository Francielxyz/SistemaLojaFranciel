package com.lojafran.Loja_Franciel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EnviarEmailService{

    @Autowired
    private JavaMailSender mailSender;

    public String enviarEmail(String destinatario, String assunto, String mensagemCorpo) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("projetoTeste45@gmail.com");
        message.setTo(destinatario);
        message.setSubject(assunto);
        message.setText(mensagemCorpo);
        try {
            mailSender.send(message);
            return "Email enviado com sucesso!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao enviar email.";
        }
    }

}