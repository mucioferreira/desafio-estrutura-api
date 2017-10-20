package com.estrutura.desafio.api.services;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.estrutura.desafio.api.entities.Servidor;

@Transactional(readOnly = true)
public interface ServidorService extends JpaRepository<Servidor, Long> {
	
	Optional<Servidor> findByIp(String ip);
	Optional<Servidor> findById(Long id);
	
}
