package com.matheussilvadev.springbootmvc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.matheussilvadev.springbootmvc.model.Pessoa;

@Repository
@Transactional
public interface PessoaRepository extends CrudRepository<Pessoa, Long> {

}
