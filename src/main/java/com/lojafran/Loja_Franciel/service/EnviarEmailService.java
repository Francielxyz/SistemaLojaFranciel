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
        try {
            SimpleMailMessage mensagem = new SimpleMailMessage();
            mensagem.setFrom("projetoLojaFranciel@gmail.com");//remetente
            mensagem.setSubject(assunto);//assunto
            mensagem.setTo(destinatario);//destinatário
            mensagem.setText(mensagemCorpo); // mensagem
            mailSender.send(mensagem); //enviar
            return "E-mail enviado com sucesso!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Não foi possível enviar o email!";
        }

    }

}