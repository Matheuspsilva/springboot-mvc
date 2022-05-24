package com.matheussilvadev.springbootmvc.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.matheussilvadev.springbootmvc.model.Pessoa;
import com.matheussilvadev.springbootmvc.model.Telefone;

@Repository
@Transactional
public interface TelefoneRepository extends CrudRepository<Telefone, Long> {
	
	@Query("select t from Telefone t where t.pessoa = ?1")
	List<Telefone> findTelefoneByUser(Pessoa pessoa);
	
	@Query("select t from Telefone t where t.pessoa.id = ?1")
	List<Telefone> findTelefoneByUserId(Long pessoaid);

}
