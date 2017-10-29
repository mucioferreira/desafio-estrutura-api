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
	@NamedQuery(name = "ServidorService.findById", query = "SELECT rede FROM NoDaRede rede WHERE rede.servidor.id = :servidorId"),
	@NamedQuery(name = "ServidorService.findByIp", query = "SELECT rede FROM NoDaRede rede WHERE rede.servidor.ip = :servidorIp")
})
public interface NoDaRedeService extends JpaRepository<NoDaRede, Long>{

	Optional<NoDaRede> findById(Long id);
	Page<NoDaRede> findByServidorId(@Param("servidorId") Long id, Pageable page);
	Page<NoDaRede> findByServidorIp(@Param("servidorIp") String ip, Pageable page);

}
