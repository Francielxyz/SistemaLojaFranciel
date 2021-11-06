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
    public ModelAndView salvar(@Validated Funcionario funcionario, BindingResult result) {

        if (result.hasErrors()) {
            return cadastrar(funcionario);
        }

        funcionario.setSenha(new BCryptPasswordEncoder().encode(funcionario.getSenha()));// criptar senha

        funcionarioRepositorio.saveAndFlush(funcionario);
        return cadastrar(new Funcionario());

    }

}