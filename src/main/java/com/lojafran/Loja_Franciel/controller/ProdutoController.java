package com.lojafran.Loja_Franciel.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.lojafran.Loja_Franciel.constants.ConstantsImagens;
import com.lojafran.Loja_Franciel.entity.Categoria;
import com.lojafran.Loja_Franciel.entity.Imagem;
import com.lojafran.Loja_Franciel.entity.Marca;
import com.lojafran.Loja_Franciel.repository.CategoriaRepository;
import com.lojafran.Loja_Franciel.repository.ImagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.lojafran.Loja_Franciel.entity.Produto;
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

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ImagemRepository imagemRepository;

    @GetMapping("/cadastrar")
    public ModelAndView cadastrar(Produto produto) {
        ModelAndView mv = new ModelAndView("administrativo/produtos/cadastro");
        mv.addObject("produto", produto);
        mv.addObject("listaMarcas", marcaRepository.findAll());
        mv.addObject("listaCategorias", categoriaRepository.findAll());
        return mv;
    }

    @GetMapping("/listar")
    public ModelAndView listar() {
        ModelAndView mv = new ModelAndView("administrativo/produtos/lista");
        mv.addObject("listaProdutos", produtoRepository.findAll());
        return mv;
    }

    @GetMapping("/listar/descricao")
    public ModelAndView listarPorDescricao(String descricao) {
        ModelAndView mv = new ModelAndView("administrativo/produtos/lista");
        mv.addObject("listaProdutos", produtoRepository.findByDescricao(descricao));
        return mv;
    }

    @GetMapping("/listar/categoria") //TODO - TENTA IMPLEMENTA NUM SERVICE
    public ModelAndView listarPorCategoria(String nome) {

        List<Categoria> listCategoria = categoriaRepository.findByNome(nome);
        List<Produto> listProduto = new ArrayList<>();

        for (Categoria categoria : listCategoria) {
            listProduto = produtoRepository.findByCategoria(categoria);

        }

        ModelAndView mv = new ModelAndView("administrativo/produtos/lista");
        mv.addObject("listaProdutos", listProduto);

        return mv;
    }

    @GetMapping("/listar/marca") //TODO - TENTA IMPLEMENTA NUM SERVICE
    public ModelAndView listarPorMarca(String nome) {
        List<Produto> listProduto = new ArrayList<>();
        List<Marca> listMarca = marcaRepository.findByNome(nome);

        for (Marca marca : listMarca) {
            listProduto = produtoRepository.findByMarca(marca);
        }

        ModelAndView mv = new ModelAndView("administrativo/produtos/lista");
        mv.addObject("listaProdutos", listProduto);
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
    public ModelAndView salvar(@Validated Produto produto, BindingResult result, @RequestParam("file") MultipartFile[] files) {

        if (result.hasErrors()) {
            return cadastrar(produto);
        }
        produto = produtoRepository.saveAndFlush(produto);
        try {
            List<Imagem> imagemList = new ArrayList<>();

            for (MultipartFile file : files) {

                if(!file.getOriginalFilename().isEmpty()){
                    Imagem imagem = new Imagem();

                    byte[] bytes = file.getBytes();

                    // Caminho onde a imagem vai ser salva
                    Path caminho = Paths.get(constantsImagens.CAMINHO_PASTA_IMAGENS + String.valueOf(produto.getId()) + file.getOriginalFilename());
                    Files.write(caminho, bytes);

                    Imagem imagemComValores = setValoresImagens(imagem, produto, file);

                    imagemList.add(imagemRepository.saveAndFlush(imagemComValores));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cadastrar(new Produto());
    }

    @GetMapping("/salvarLotes")
    public ModelAndView salvarLotes() {
        List<Produto> listProdut = new ArrayList<>();

        for (int x = 0; x < 50000; x++) {
            Produto produto = new Produto();
            produto.setDescricao("Computador muito bom");
            produto.setValorVenda(10.0);
            produto.setQuantidadeEstoque(5);
            produto.setMarca(null);
            produto.setCategoria(null);

            listProdut.add(produto);
        }
        produtoRepository.saveAll(listProdut);

        return cadastrar(new Produto());
    }


    public Imagem setValoresImagens(Imagem imagem, Produto produto, MultipartFile file) {

        imagem.setNome(String.valueOf(produto.getId() + file.getOriginalFilename()));
        imagem.setProduto(produto);

        return imagem;
    }


}
