package com.lojafran.Loja_Franciel.controller;

import com.lojafran.Loja_Franciel.entity.Funcionario;
import com.lojafran.Loja_Franciel.repository.FuncionarioRepository;
import com.lojafran.Loja_Franciel.service.EnviarEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.Random;

@Controller
public class AlterarSenhaController {

    @Autowired
    FuncionarioRepository funcionarioRepository;

    @Autowired
    private EnviarEmailService enviarEmailService;


    @GetMapping("/alterarSenha")
    public ModelAndView alterarSenha() {
        ModelAndView mv = new ModelAndView("/administrativo/recuperarSenha/alterarSenha");
        return mv;
    }

    @PostMapping("/alterarSenha")
    public String alterarSenha(@RequestParam(name = "email") String email, @RequestParam(name = "codigoRecuperacao") String codigoRecuperacao, @RequestParam(name = "senha") String senha, Model model) {
        Funcionario funcionario = funcionarioRepository.findByEmailAndCodigoRecuperacao(email, codigoRecuperacao);

        if(funcionario != null){

            Date diff = new Date(new Date().getTime() - funcionario.getDataCodigo().getTime());

            // TODO CORRIGIR VALIDAÇÃO DA HORA QUE NÃO ESTA CORRETA
            if (diff.getTime() / 1000 < 90000){ //900000 milisegundos = 900 segundos que é = 15 minutos
                funcionario.setCodigoRecuperacao(null);
                funcionario.setDataCodigo(null);

                funcionario.setSenha(new BCryptPasswordEncoder().encode(senha)); //criptografar a senha
                funcionarioRepository.saveAndFlush(funcionario);

                model.addAttribute("mensagem", "Senha alterada com sucesso");
                return "/administrativo/recuperarSenha/mensagem";
            } else{
                model.addAttribute("mensagem", "Código expirado, solicite novamente!");
                return "/administrativo/recuperarSenha/mensagem";
            }

        }

        model.addAttribute("mensagem", "Email e/ou código não encontrado");
        return "/administrativo/recuperarSenha/mensagem";
    }

}
