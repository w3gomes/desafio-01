package br.rpe.cadastropessoa.business.service.impl;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Optional;

import br.rpe.cadastropessoa.domain.service.FuncionarioService;
import br.rpe.cadastropessoa.domain.entity.Funcionario;
import br.rpe.cadastropessoa.domain.repository.FuncionarioRepository;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Override
	public Funcionario create (Funcionario funcionario) {
		if((findByCpf(funcionario.getCpf()) != null)){
			throw new IllegalStateException("Funcionario ja cadastrado!");
		}
		return funcionarioRepository.save(funcionario);
	}

	@Override
	public Funcionario update(Funcionario funcionario) {
		Funcionario funcionarioUp = findById(funcionario.getId());
		if(funcionarioUp == null) {
			throw new NoSuchElementException("Funcionário inexistente!");
		}
		funcionarioUp.getAddress().setCity(funcionario.getAddress().getCity());
		funcionarioUp.getAddress().setComplement(funcionario.getAddress().getComplement());
		funcionarioUp.getAddress().setCity(funcionario.getAddress().getCity());
		funcionarioUp.getAddress().setState(funcionario.getAddress().getState());
		funcionarioUp.getAddress().setZipCode(funcionario.getAddress().getZipCode());
		
		funcionarioUp.setCpf(funcionario.getCpf());
		funcionarioUp.setHiringDate(funcionario.getHiringDate());
		funcionarioUp.setName(funcionario.getName());
		funcionarioUp.setTelephone(funcionario.getTelephone());
		
		return funcionarioRepository.save(funcionarioUp);
	}

	@Override
	public void delete(Long id) {
		if(findById(id) == null) {
			throw new NoSuchElementException("Funcionário inexistente!");
		}
		funcionarioRepository.deleteById(id);
	}

	@Override
	public Funcionario findByName(String name) {
		return null;
	}

	@Override
	public Funcionario findById(Long id) {
		return funcionarioRepository.findById(id).get();
	}
	
	@Override
	public Funcionario findByCpf(String cpf) {
		if(cpf == null) {
			throw new IllegalStateException("CPF cannot be null");
		}
		Optional<Funcionario> optional = funcionarioRepository.findByCpf(cpf);
		return optional.isPresent() ? optional.get() : null;
	}
	
	
}
