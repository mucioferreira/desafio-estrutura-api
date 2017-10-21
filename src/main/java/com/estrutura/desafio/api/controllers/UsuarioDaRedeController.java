package com.estrutura.desafio.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estrutura.desafio.api.services.UsuarioDaRedeService;
import com.estrutura.desafio.api.services.UsuarioService;

@RestController
@RequestMapping("/api/usuario-da-rede")
@CrossOrigin(origins = "*")
public class UsuarioDaRedeController {
	
	@Autowired
	private UsuarioDaRedeService usuarioDaRedeService;

}
