package com.lojafran.Loja_Franciel.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import com.lojafran.Loja_Franciel.constants.ConstantsImagens;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.lojafran.Loja_Franciel.model.Produto;
import com.lojafran.Loja_Franciel.repository.MarcaRepository;
import com.lojafran.Loja_Franciel.repository.ProdutoRepository;

@Controller
@RequestMapping("/administrativo/produtos")
public class ProdutoController {

    ConstantsImagens constantsImagens;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private MarcaRepository marcaRepository;

    @GetMapping("/cadastrar")
    public ModelAndView cadastrar(Produto produto) {
        ModelAndView mv = new ModelAndView("administrativo/produtos/cadastro");
        mv.addObject("produto", produto);
        mv.addObject("listaMarcas", marcaRepository.findAll());
        return mv;
    }

    @GetMapping("/listar")
    public ModelAndView listar() {
        ModelAndView mv = new ModelAndView("administrativo/produtos/lista");
        mv.addObject("listaProdutos", produtoRepository.findAll());
        return mv;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
        Optional<Produto> produto = produtoRepository.findById(id);
        return cadastrar(produto.get());
    }

    @GetMapping("/remover/{id}")
    public ModelAndView remover(@PathVariable("id") Long id) {
        Optional<Produto> produto = produtoRepository.findById(id);
        produtoRepository.delete(produto.get());
        return listar();
    }

    @ResponseBody
    @GetMapping("/mostrarImagem/{imagem}")
    public byte[] retornarImagem(@PathVariable("imagem") String imagem) {
        if (imagem != null || imagem.trim().length() > 0) {
            File imagemArquivo = new File(constantsImagens.CAMINHO_PASTA_IMAGENS + imagem);
            try {
                return Files.readAllBytes(imagemArquivo.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    @PostMapping("/salvar")
    public ModelAndView salvar(@Validated Produto produto, BindingResult result, @RequestParam("file") MultipartFile arquivo) {

        if (result.hasErrors()) {
            return cadastrar(produto);
        }
        produtoRepository.saveAndFlush(produto);
        try {

            if (!arquivo.isEmpty()) {
                byte[] bytes = arquivo.getBytes();

                // Caminho onde a imagem vai ser salva
                Path caminho = Paths.get(constantsImagens.CAMINHO_PASTA_IMAGENS + String.valueOf(produto.getId()) + arquivo.getOriginalFilename());
                Files.write(caminho, bytes);

                // Salva no banco de dados a imagem com tal nome
                produto.setNomeImagem(String.valueOf(produto.getId()) + arquivo.getOriginalFilename());

                produtoRepository.saveAndFlush(produto);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return cadastrar(new Produto());
    }

}
