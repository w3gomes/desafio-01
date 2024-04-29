package br.rpe.peopleregistration.unitary;

import br.rpe.cadastropessoa.api.presentation.dto.FuncionarioDto;
import br.rpe.cadastropessoa.domain.entity.Endereco;
import br.rpe.cadastropessoa.domain.entity.Funcionario;
import br.rpe.cadastropessoa.domain.repository.FuncionarioRepository;
import br.rpe.cadastropessoa.domain.service.FuncionarioService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
class FuncionarioServiceTest {

    private static FuncionarioDto funcionarioDto;
    @Mock
    private FuncionarioRepository funcionarioRepository;
    @Mock
    private FuncionarioService funcionarioService;
    @Mock
    private List<Funcionario> funcionarioMock;
    @Mock
    private ModelMapper mapper;

    @BeforeAll
    static void before() {

    }

    void startClient() {


    }

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        startClient();
    }

    @Order(1)
    @Test
    void testClientSave() throws Exception {


        verifyNoInteractions(funcionarioService); //verifica chamadas se há outras além desta
        funcionarioService.create(newFuncionario());
        verify(funcionarioService, times(1)).create(newFuncionario());
    }

    @Order(2)
    @Test
    void testUpdateName() {
        when(funcionarioMock.get(0)).thenReturn(
                newFuncionario(1L, "111.111.111-11", "Maria", new Endereco(), "(83) 22222-2222")
        );
        when(funcionarioService.update(any(Funcionario.class))).thenReturn(
                newFuncionario(1L, "111.111.111-11", "João", new Endereco(), "(83) 22222-2222")
        );

        String upName = "João";
        Funcionario funcionario = funcionarioMock.get(0);
        funcionario.setNome(upName);

        Funcionario clientUp = funcionarioService.update(funcionario);

        Assertions.assertEquals(upName, clientUp.getNome());
    }

    @Order(3)
    @Test
    void testDelete() {
        assertDoesNotThrow(() -> funcionarioService.delete(1L));
    }

    private Funcionario newFuncionario() {
        final var funcionario = new Funcionario();
        funcionario.setId(1L);
        funcionario.setCpf("111.111.111-11");
        funcionario.setNome("Maria");
        funcionario.setEndereco(new Endereco());
        funcionario.setTelefone("(83) 22222-2222");

        return funcionario;
    }

    private Funcionario newFuncionario(
            final Long id,
            final String cpf,
            final String nome,
            final Endereco endereco,
            final String telefone
    ) {
        final var funcionario = new Funcionario();
        funcionario.setId(id);
        funcionario.setCpf(cpf);
        funcionario.setNome(nome);
        funcionario.setEndereco(endereco);
        funcionario.setTelefone(telefone);

        return funcionario;
    }

}