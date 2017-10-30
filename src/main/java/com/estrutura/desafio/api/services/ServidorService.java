package com.estrutura.desafio.api.services;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.estrutura.desafio.api.entities.Servidor;

public interface ServidorService extends JpaRepository<Servidor, Long> {
	
	
	Optional<Servidor> findByIp(String ip);
	Optional<Servidor> findById(Long id);
	
}
