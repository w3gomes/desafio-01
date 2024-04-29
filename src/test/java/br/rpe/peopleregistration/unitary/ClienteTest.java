package br.rpe.peopleregistration.unitary;

import br.rpe.cadastropessoa.domain.entity.Endereco;
import br.rpe.cadastropessoa.domain.entity.Cliente;
import br.rpe.cadastropessoa.domain.enums.TipoCliente;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.commons.annotation.Testable;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Testable
@DisplayName("Client")
class ClienteTest {

    private static Validator validator;


    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Order(1)
    @ParameterizedTest
    @ValueSource(strings = {"Tha", "Jan4", "Thallyta Maria Medeiros xxxxxxxxxxxxxxxxxxxxxxxxx5", "Thallyta Maria Medeiros xxxxxxxxxxxxxxxxxxxxxxxx50", ""})
    void nameValidTest(String nome) {
        final var cliente = newCliente(
                null,
                "111.111.111-13",
                new Endereco()
        );

        cliente.setNome(nome);
        Set<ConstraintViolation<Cliente>> validacoes = validator.validateProperty(cliente, "name");

        Assertions.assertEquals(0, validacoes.size());
    }

    @Order(2)
    @ParameterizedTest
    @ValueSource(strings = {"085.228.024-67", "111.111.111-13", "100.161.704-58"})
    void CpfValidTest(String cpf) {
        final var cliente = newCliente(
                "Fulano",
                null,
                new Endereco()
        );

        cliente.setCpf(cpf);

        Set<ConstraintViolation<Cliente>> validacoes = validator.validateProperty(cliente, "cpf");

        Assertions.assertEquals(0, validacoes.size());
    }

    private Cliente newCliente(
            final String nome,
            final String cpf,
            final Endereco endereco
    ) {
        final var cliente = new Cliente();
        cliente.setId(2L);
        cliente.setNome(nome);
        cliente.setCpf(cpf);
        cliente.setEndereco(endereco);
        cliente.setTelefone("(83) 22222-2222");
        cliente.setTipoCliente(TipoCliente.FAXINEIRO);

        return cliente;
    }
}
