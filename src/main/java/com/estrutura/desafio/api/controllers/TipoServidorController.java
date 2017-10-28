package com.estrutura.desafio.api.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estrutura.desafio.api.enums.TipoServidorEnum;
import com.estrutura.desafio.api.response.Response;

@RestController
@RequestMapping("/api/tipo-servidor")
@CrossOrigin(origins = "*")
public class TipoServidorController {
	
	@GetMapping
	public ResponseEntity<Response<Map<String, TipoServidorEnum>>> cadastrarServidor() throws NoSuchAlgorithmException {
		Response<Map<String, TipoServidorEnum>> response = new Response<Map<String, TipoServidorEnum>>();
		
		response.setData(TipoServidorEnum.getNomesTipoServidor());
		return ResponseEntity.ok(response);
	}

	
	
}
