package com.matheussilvadev.springbootmvc.security;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.matheussilvadev.springbootmvc.model.Usuario;
import com.matheussilvadev.springbootmvc.repository.UsuarioRepository;

@Service
@Transactional
public class ImplementacaoUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	//Método chamado automaticamente
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = usuarioRepository.findUserByLogin(username);
		
		if(usuario == null) {
			throw new UsernameNotFoundException("Usuário não foi encontrado");
		}
		
		return new User(usuario.getLogin(), usuario.getPassword(), usuario.isEnabled(),true, true, true, usuario.getAuthorities());
	}

}
