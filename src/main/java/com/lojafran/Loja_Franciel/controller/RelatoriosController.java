package com.lojafran.Loja_Franciel.controller;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

@Controller
@RequestMapping("/relatorio")
public class RelatoriosController {

    @Autowired
    private DataSource dataSource;

    @GetMapping("/funcionarios")
    public void relatorioFuncionarios(HttpServletResponse servletResponse) throws JRException, SQLException, IOException {
        relatorio(servletResponse, "Funcionarios.jasper", "relatorioFuncionarios.pdf");
    }

    @GetMapping("/produtos")
    public void relatorioProdutos(HttpServletResponse servletResponse) throws JRException, SQLException, IOException {
        relatorio(servletResponse, "Produtos.jasper", "relatorioProdutos.pdf");
    }

    public void relatorio(HttpServletResponse servletResponse, String jasperName, String relatorioNome) throws JRException, SQLException, IOException {
        InputStream jasperFile = this.getClass().getResourceAsStream("/relatorios/" + jasperName);

        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperFile);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource.getConnection());

        servletResponse.setContentType("application/pdf");
        servletResponse.setHeader("Content-Disposition", "attachment;filename=" + relatorioNome);

        OutputStream outputStream = servletResponse.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
    }

}
