package br.rpe.cadastropessoa.domain.repository;

import br.rpe.cadastropessoa.domain.entity.Funcionario;
import com.google.common.base.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    Optional<Funcionario> findByCpf(String cpf);

}
