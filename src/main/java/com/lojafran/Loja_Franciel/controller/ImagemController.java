package com.lojafran.Loja_Franciel.controller;

import com.lojafran.Loja_Franciel.constants.ConstantsImagens;
import com.lojafran.Loja_Franciel.entity.Imagem;
import com.lojafran.Loja_Franciel.entity.Produto;
import com.lojafran.Loja_Franciel.repository.ImagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Controller
public class ImagemController {

    @Autowired
    private ImagemRepository imagemRepository;

    @GetMapping("/mostraImagem/{productid}")
    @ResponseBody
    public byte[] getImage(@PathVariable("productid") Long productid) throws IOException {
        Produto produto = new Produto();
        produto.setId(productid);
        List<Imagem> imagens = imagemRepository.findImagemByProduto(produto);
        File imageFile = new File(ConstantsImagens.CAMINHO_PASTA_IMAGENS + "erroImagem.png");
        if (imagens.size() > 0 && !imagens.isEmpty() && imagens != null) {
            imageFile = new File(ConstantsImagens.CAMINHO_PASTA_IMAGENS + imagens.get(0).getNome());
            return Files.readAllBytes(imageFile.toPath());
        } else {
            return Files.readAllBytes(imageFile.toPath());
        }
    }

}
