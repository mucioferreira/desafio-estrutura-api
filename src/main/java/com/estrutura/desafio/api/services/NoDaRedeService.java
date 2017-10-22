package com.estrutura.desafio.api.services;

import java.util.Optional;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.estrutura.desafio.api.entities.NoDaRede;

@Transactional(readOnly = true)
@NamedQueries({
	@NamedQuery(name = "ServidorService.findById", query = "SELECT rede FROM NoDaRede rede WHERE rede.primeiroServidor.id = :primeiroServidorId"),
	@NamedQuery(name = "ServidorService.findByIp", query = "SELECT rede FROM NoDaRede rede WHERE rede.primeiroServidor.ip = :primeiroServidorIp"),
	@NamedQuery(name = "ServidorService.findById", query = "SELECT rede FROM NoDaRede rede WHERE rede.segundoServidor.id = :segundoServidorId"),
	@NamedQuery(name = "ServidorService.findByIp", query = "SELECT rede FROM NoDaRede rede WHERE rede.segundoServidor.ip = :segundoServidorIp")
})
public interface NoDaRedeService extends JpaRepository<NoDaRede, Long>{

	Optional<NoDaRede> findById(Long id);
	Page<NoDaRede> findByPrimeiroServidorId(@Param("primeiroServidorId") Long id, Pageable page);
	Page<NoDaRede> findByPrimeiroServidorIp(@Param("primeiroServidorIp") String ip, Pageable page);
	Page<NoDaRede> findBySegundoServidorId(@Param("segundoServidorId") Long id, Pageable page);
	Page<NoDaRede> findBySegundoServidorIp(@Param("segundoServidorIp") String ip, Pageable page);
}
