package com.estrutura.desafio.api.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estrutura.desafio.api.enums.AmbienteDaRedeEnum;
import com.estrutura.desafio.api.response.Response;

@RestController
@RequestMapping("/api/ambiente-servidor")
@CrossOrigin(origins = "*")
public class AmbienteController {
	
	@GetMapping
	public ResponseEntity<Response<Map<String, AmbienteDaRedeEnum>>> listarAmbientes() throws NoSuchAlgorithmException {
		Response<Map<String, AmbienteDaRedeEnum>> response = new Response<Map<String, AmbienteDaRedeEnum>>();
		response.setData(AmbienteDaRedeEnum.getNomesAmbienteDaRede());
		return ResponseEntity.ok(response);
	}

}
