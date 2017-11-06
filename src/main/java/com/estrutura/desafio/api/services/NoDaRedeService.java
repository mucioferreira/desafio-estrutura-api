package com.estrutura.desafio.api.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.estrutura.desafio.api.entities.NoDaRede;

@NamedQueries({
	@NamedQuery(name = "ServidorService.findById", query = "SELECT rede FROM NoDaRede rede WHERE rede.servidor.id = :servidorId"),
	@NamedQuery(name = "ServidorService.findByIp", query = "SELECT rede FROM NoDaRede rede WHERE rede.servidor.ip = :servidorIp")
})
public interface NoDaRedeService extends JpaRepository<NoDaRede, Long>{

	Optional<NoDaRede> findById(Long id);
	Page<NoDaRede> findByServidorId(@Param("servidorId") Long id, Pageable page);
	Page<NoDaRede> findByServidorIp(@Param("servidorIp") String ip, Pageable page);
	
	@Query("SELECT rede FROM NoDaRede rede WHERE rede.servidor.ip LIKE CONCAT('%',:servidorIp,'%')")
	List<NoDaRede> findByLikeIp(@Param("servidorIp") String ip);
	
	
}
