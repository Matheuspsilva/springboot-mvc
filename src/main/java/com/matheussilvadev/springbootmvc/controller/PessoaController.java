package com.matheussilvadev.springbootmvc.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.matheussilvadev.springbootmvc.model.Pessoa;
import com.matheussilvadev.springbootmvc.model.Telefone;
import com.matheussilvadev.springbootmvc.repository.PessoaRepository;
import com.matheussilvadev.springbootmvc.repository.TelefoneRepository;

@Controller
public class PessoaController {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private TelefoneRepository telefoneRepository;
	
	@Autowired
	private ReportUtil reportUtil;

	//Página de cadastro
	@RequestMapping(method = RequestMethod.GET, value = "/cadastropessoa")
	public ModelAndView inicio() {
		
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
		modelAndView.addObject("pessoaobj", new Pessoa());
		
		Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
		modelAndView.addObject("pessoas", pessoasIt);
		
		return modelAndView;
	}
	
	//Cadastro de pessoa
	@RequestMapping(method = RequestMethod.POST, value = "**/pessoa")
	public ModelAndView  salvar(@Valid Pessoa pessoa, BindingResult bindingResult) {
		
		pessoa.setTelefones(telefoneRepository.findTelefoneByUserId(pessoa.getId()));
		
		System.out.println(bindingResult.hasErrors());
		
		if(bindingResult.hasErrors()) {
			ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
			Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
			modelAndView.addObject("pessoas", pessoasIt);
			modelAndView.addObject("pessoaobj", pessoa);
			
			List<String> msg = new ArrayList<String>();
			for (ObjectError objectError : bindingResult.getAllErrors()) {
				msg.add(objectError.getDefaultMessage());// Vem das anotações do model
			}
			
			modelAndView.addObject("msg", msg);
			
			return modelAndView;
		}
		
		pessoaRepository.save(pessoa);
		
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
		Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
		modelAndView.addObject("pessoas", pessoasIt);
		modelAndView.addObject("pessoaobj", new Pessoa());
		
		return modelAndView;
	}
	
	//Listagem de pessoas
	@RequestMapping(method = RequestMethod.GET, value = "/pessoas")
	public ModelAndView pessoas() {
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
		Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
		modelAndView.addObject("pessoas", pessoasIt);
		modelAndView.addObject("pessoaobj", new Pessoa());
		
		return modelAndView;
	}
	
	//Editar Pessoa
	@GetMapping(value = "/editarpessoa/{idpessoa}")
	public ModelAndView editar(@PathVariable("idpessoa") Long idpessoa){
		
		Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa);
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
		
		modelAndView.addObject("pessoaobj", pessoa.get());
		
		return modelAndView;
		
	}	
	
	//Excluir Pessoa
	@GetMapping(value = "/excluirpessoa/{idpessoa}")
	public ModelAndView excluir(@PathVariable("idpessoa") Long idpessoa){
		
		pessoaRepository.deleteById(idpessoa);
		
		Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
		modelAndView.addObject("pessoas", pessoasIt);
		modelAndView.addObject("pessoaobj", new Pessoa());
		
		return modelAndView;
		
	}	
	
	@GetMapping(value = "/telefones/{idpessoa}")
	public ModelAndView telefones(@PathVariable("idpessoa") Long idpessoa){
		
		Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa);
		ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
		
		modelAndView.addObject("telefones", telefoneRepository.findTelefoneByUserId(idpessoa));
		modelAndView.addObject("pessoaobj", pessoa.get());
		
		return modelAndView;
		
	}	
	
	@PostMapping("**/pesquisarpessoa")
	public ModelAndView pesquisar(@RequestParam("nomepesquisa") String nomepesquisa, @RequestParam("pesquisasexo") String pesquisasexo) {
		
		List<Pessoa> pessoas = new ArrayList<Pessoa>();
		
		if(pesquisasexo != null && !pesquisasexo.isEmpty()) {	
			pessoas = pessoaRepository.findPessoaByNameSexo(nomepesquisa, pesquisasexo);
		}else {
			pessoas = pessoaRepository.findPessoaByName(nomepesquisa);
		}	
		
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
		modelAndView.addObject("pessoas",pessoas );
		modelAndView.addObject("pessoaobj", new Pessoa());
		
		return modelAndView;
		
	}
	

	@GetMapping("**/pesquisarpessoa")
	public void imprimePdf(@RequestParam("nomepesquisa") String nomepesquisa,
			               @RequestParam("pesquisasexo") String pesquisasexo,
			               HttpServletRequest request,
			               HttpServletResponse response) throws Exception
	{
		List<Pessoa> pessoas = new ArrayList<Pessoa>();
		
		if(pesquisasexo != null && !pesquisasexo.isEmpty() && nomepesquisa != null && !nomepesquisa.isEmpty()) {//Busca por nome e sexo
			pessoas = pessoaRepository.findPessoaByNameSexo(nomepesquisa, pesquisasexo);
		}else if(nomepesquisa != null && !nomepesquisa.isEmpty()) {//Busca por nome 
			pessoas = pessoaRepository.findPessoaByName(nomepesquisa);
		}else if(pesquisasexo != null && !pesquisasexo.isEmpty()) {//Busca por nome 
			pessoas = pessoaRepository.findPessoaBySexo(pesquisasexo);
		}else {//Busca todos
			Iterable<Pessoa> iterator = pessoaRepository.findAll();
			for (Pessoa pessoa : iterator) {
				pessoas.add(pessoa);
			}
		}
		
		//Chama serviço que gera relatório
		byte[] pdf = reportUtil.geraRelatorioPDF(pessoas, "pessoa", request.getServletContext());
		
		//Tamanho da resposta
		response.setContentLength(pdf.length);
		
		//Define tipo de arquivo
		response.setContentType("application/octet-stream");
		
		//Definir cabeçalho da resposta
		String headerKey = "Content-Disposition";
		String headerValue  = String.format("attachment; filename=\"%s\"", "relatorio-pdf");
		response.setHeader(headerKey, headerValue);
		
		//Finaliza a resposta pro navegador
		response.getOutputStream().write(pdf);
		
		
	}
	
	@PostMapping("**/addfonePessoa/{pessoaid}")
	public ModelAndView addFonePessoa(Telefone telefone, @PathVariable("pessoaid") Long pessoaid) {
		
		Pessoa pessoa = pessoaRepository.findById(pessoaid).get();
		//Se o telefone for nulo o java não realizara a segunda validação, evitando erro de NullPointer
		//telefone.getNumero() != null && telefone.getNumero().isEmpty() 
		if(telefone != null && telefone.getNumero().isEmpty() || telefone.getTipo().isEmpty()) {
			
			ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
			modelAndView.addObject("pessoaobj", pessoa);
			modelAndView.addObject("telefones", telefoneRepository.findTelefoneByUser(pessoa));
			
			List<String> msg = new ArrayList<String>();
			
			if(telefone.getNumero().isEmpty()) {
				msg.add("O Número deve ser informado");
			}
			
			if(telefone.getTipo().isEmpty()) {
				msg.add("O tipo deve ser informado");
			}
			
			modelAndView.addObject("msg", msg);
			return modelAndView;
			
		}
		
		telefone.setPessoa(pessoa);
		telefoneRepository.save(telefone);
		
		ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
		modelAndView.addObject("pessoaobj", pessoa);
		
		modelAndView.addObject("telefones", telefoneRepository.findTelefoneByUser(pessoa));
		
		
		return modelAndView;
		
	}
	
	//Excluir Telefone
	@GetMapping(value = "/excluirtelefone/{idtelefone}")
	public ModelAndView excluirTelefone(@PathVariable("idtelefone") Long idtelefone){
		
		Pessoa pessoa = telefoneRepository.findById(idtelefone).get().getPessoa();
		telefoneRepository.deleteById(idtelefone);
		
		ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
		modelAndView.addObject("pessoaobj", pessoa);
		modelAndView.addObject("telefones", telefoneRepository.findTelefoneByUser(pessoa));
		
		return modelAndView;
		
	}	
	
}
