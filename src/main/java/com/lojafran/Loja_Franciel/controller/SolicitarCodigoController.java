package com.lojafran.Loja_Franciel.controller;

import com.lojafran.Loja_Franciel.entity.Funcionario;
import com.lojafran.Loja_Franciel.repository.FuncionarioRepository;
import com.lojafran.Loja_Franciel.service.EnviarEmailService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SolicitarCodigoController {

    @Autowired
    FuncionarioRepository funcionarioRepository;

    @Autowired
    private EnviarEmailService enviarEmailService;

    @GetMapping("/solicitarCodigo")
    public ModelAndView solicitarCodigo() {
        ModelAndView mv = new ModelAndView("/administrativo/recuperarSenha/solicitarCodigo");
        return mv;
    }

    @PostMapping("/solicitarCodigo")
    public String solicitarCodigo(@RequestParam(name = "email") String email, Model model) {
        Random gerador = new Random(); // Gerar números aleatórios

        Funcionario funcionario = funcionarioRepository.findByEmail(email);
        if (funcionario != null) {
            funcionario.setCodigoRecuperacao("" + funcionario.getId() + gerador.nextInt(10000)); //gerando número aleatório
            funcionario.setDataCodigo(new Date()); //setando nova data
            funcionario.setSenha("");
            funcionarioRepository.saveAndFlush(funcionario);
            enviarEmailService.enviarEmail(email, "Solicitação para Recuperação de Senha", "O código de recuperação é o seguinte: " + funcionario.getCodigoRecuperacao());
            model.addAttribute("mensagem", "O código foi encaminhado para o seu e-mail!");

//            ModelAndView mv = new ModelAndView("/administrativo/recuperarSenha/alterarSenha");
            return "/administrativo/recuperarSenha/mensagem";
        }
        model.addAttribute("mensagem", "Email não encontrado");
//        ModelAndView mv = new ModelAndView("/administrativo/recuperarSenha/solicitarCodigo");
        return "/administrativo/recuperarSenha/mensagem";
    }

}
