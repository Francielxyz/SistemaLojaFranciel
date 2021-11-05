package com.lojafran.Loja_Franciel.controller;

import com.lojafran.Loja_Franciel.entity.Funcionario;
import com.lojafran.Loja_Franciel.repository.CidadeRepository;
import com.lojafran.Loja_Franciel.repository.FuncionarioRepository;
import com.lojafran.Loja_Franciel.service.EnviarEmailService;
import com.lojafran.Loja_Franciel.util.ValidarCpf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.Optional;
import java.util.Random;


@Controller
@RequestMapping("/administrativo/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioRepository funcionarioRepositorio;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EnviarEmailService enviarEmailService;

    @GetMapping("/cadastrar")
    public ModelAndView cadastrar(Funcionario funcionario) {
        ModelAndView mv = new ModelAndView("administrativo/funcionarios/cadastro");
        mv.addObject("funcionario", funcionario);
        mv.addObject("listaCidades", cidadeRepository.findAll());
        return mv;
    }

    @GetMapping("/listar")
    public ModelAndView listar() {
        ModelAndView mv = new ModelAndView("/administrativo/funcionarios/lista");
        mv.addObject("listaFuncionarios", funcionarioRepositorio.findAll());
        return mv;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
        Optional<Funcionario> funcionario = funcionarioRepositorio.findById(id);
        return cadastrar(funcionario.get());
    }

    @GetMapping("/remover/{id}")
    public ModelAndView remover(@PathVariable("id") Long id) {
        Optional<Funcionario> funcionario = funcionarioRepositorio.findById(id);
        funcionarioRepositorio.delete(funcionario.get());
        return listar();
    }

    @PostMapping("/salvar")
    public ModelAndView salvar(@Validated Funcionario funcionario, BindingResult result, Model model) {

//        if (ValidarCpf.isCPF(funcionario.getCpf())) {
            if (result.hasErrors()) {
                return cadastrar(funcionario);
            }

            // criptar senha
            funcionario.setSenha(new BCryptPasswordEncoder().encode(funcionario.getSenha()));

            funcionarioRepositorio.saveAndFlush(funcionario);
            return cadastrar(new Funcionario());
//        }

//        model.addAttribute("cpfValido", "inválido");

    }


    @PostMapping("solicitarCodigo")
    public String solicitarCodigo(@RequestParam(name = "email") String email, Model model){
        Random gerador = new Random(); // Gerar números aleatórios

        Funcionario funcionario = funcionarioRepositorio.findByEmail(email);
        if(funcionario != null){

            funcionario.setCodigoRecuperacao("" + funcionario.getId() + gerador.nextInt(1000)); //gerando número aleatório
            funcionario.setDataCodigo(new Date()); //setando nova data
            funcionario.setSenha("");
            funcionarioRepositorio.saveAndFlush(funcionario);
            enviarEmailService.enviarEmail(email, "Solicitação para Recuperação de Senha", "O código de recuperação é o seguinte: " + funcionario.getCodigoRecuperacao());
            model.addAttribute("mensagem", "O código foi encaminhado para o seu e-mail!");
            return "alterarSenha";
        }
        model.addAttribute("mensagem", "Email não encontrado");
        return "A";
    }

}