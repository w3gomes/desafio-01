package br.rpe.cadastropessoa.api.controllers;

import br.rpe.cadastropessoa.api.presentation.dto.ClienteDto;
import br.rpe.cadastropessoa.business.service.converter.ConverterService;
import br.rpe.cadastropessoa.domain.entity.Cliente;
import br.rpe.cadastropessoa.domain.service.ClienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api
@RestController
@RequestMapping("/api/cliente")
public class ClientController {

    private final ClienteService clienteService;
    private final ConverterService converterService;

    public ClientController(ClienteService clienteService, ConverterService converterService) {
        this.clienteService = clienteService;
        this.converterService = converterService;
    }

    @ApiOperation(value = "Adicionar um Cliente")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody ClienteDto dto) {
        try {
            Cliente entity = converterService.dtoToCliente(dto);
            entity = clienteService.create(entity);
            dto = converterService.clienteToDto(entity);

            return ResponseEntity.status(HttpStatus.CREATED).body(dto);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation(value = "Alterar um Cliente")
    @PutMapping({"{id}"})
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ClienteDto dto) {
        try {
            Cliente cliente = clienteService.findById(id);

            if (cliente == null) {
                throw new IllegalStateException();
            }

            cliente = converterService.dtoToCliente(dto);
            cliente = clienteService.update(cliente);
            dto = converterService.clienteToDto(cliente);

            return ResponseEntity.ok(dto);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation(value = "Remover um Cliente")
    @DeleteMapping({"{id}"})
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        try {
            clienteService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation(value = "Consultar um Cliente por ID")
    @GetMapping("{id}")
    public ResponseEntity<Object> findById(@PathVariable("id") Long id) {

        try {
            Cliente entity = clienteService.findById(id);
            ClienteDto dto = converterService.clienteToDto(entity);
            return ResponseEntity.ok(dto);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation(value = "Consultar um Cliente por CPF")
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<?> findByCpf(@PathVariable("cpf") String cpf) {

        try {
            Cliente entity = clienteService.findByCpf(cpf);
            ClienteDto dto = converterService.clienteToDto(entity);
            return ResponseEntity.ok(dto);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}

