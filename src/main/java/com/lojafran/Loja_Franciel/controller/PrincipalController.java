package com.lojafran.Loja_Franciel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PrincipalController {
	
	@GetMapping("/administrativo")
	public String acessarPrincipal() {
		return "administrativo/home";
	}
}