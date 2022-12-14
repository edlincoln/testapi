package com.testapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.testapi.entity.Cartoes;
import com.testapi.enums.TransactionErrorEnum;
import com.testapi.exceptions.NotFoundException;
import com.testapi.exceptions.ValidacaoException;
import com.testapi.repository.CartoesRepository;
import com.testapi.request.CartaoRequest;

@ExtendWith(MockitoExtension.class)
class CartoesServiceImplTest {

	@Mock
	CartoesRepository repository = mock(CartoesRepository.class);

	@InjectMocks
	CartoesServiceImpl service;

	Cartoes cartao;

	CartaoRequest request;

	private static final Long ID = 1l;
	private static final Long NUMERO = 12340987l;
	private static final String SENHA = "1234";
	private static final BigDecimal SALDO_INICIAL = new BigDecimal(500l);

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		cartao = Cartoes.builder()
				.id(ID)
				.numeroCartao(NUMERO)
				.senha(SENHA)
				.saldo(SALDO_INICIAL)
				.build();

		request = new CartaoRequest();
		request.setNumeroCartao(NUMERO);
		request.setSenha(SENHA);
	}

	@Test
    void testListarSaldoCartao() throws NotFoundException {
		
    	when(repository.findByNumeroCartao(NUMERO)).thenReturn(Optional.of(cartao));
    	
    	Cartoes cartao = service.listarSaldoCartao(NUMERO);
    	
    	assertEquals(ID, cartao.getId());
    	assertEquals(NUMERO, cartao.getNumeroCartao());
    	assertEquals(SENHA, cartao.getSenha());
    	assertEquals(SALDO_INICIAL, cartao.getSaldo());
    }

	@Test
    void testListarSaldoCartaoInexistente() throws NotFoundException {
		
    	when(repository.findByNumeroCartao(NUMERO)).thenReturn(Optional.ofNullable(null));
    	
        Exception exception = assertThrows(NotFoundException.class, () -> {
        	service.listarSaldoCartao(NUMERO);
        });
        
        String msg = "Not found";
        assertEquals(msg, exception.getMessage());

    }

	@Test
    void testSalvarSaldoCartao() throws NotFoundException, ValidacaoException {
		
		Cartoes cartaoSave = Cartoes.builder()
				.numeroCartao(request.getNumeroCartao())
				.senha(request.getSenha())
				.saldo(SALDO_INICIAL).build();
		
    	when(repository.findByNumeroCartao(NUMERO)).thenReturn(Optional.ofNullable(null));
    	when(repository.save(cartaoSave)).thenReturn(cartao);
    	
    	Cartoes ret = service.salvarCartao(request);
    	
    	assertEquals(cartao.getId(), ret.getId());
    	assertEquals(cartao.getNumeroCartao(), ret.getNumeroCartao());
    	assertEquals(cartao.getSenha(), ret.getSenha());
    	assertEquals(cartao.getSaldo(), ret.getSaldo());
    }

	@Test
    void testSalvarCartaoExistente() throws NotFoundException {
		
    	when(repository.findByNumeroCartao(NUMERO)).thenReturn(Optional.of(cartao));
    	
        Exception exception = assertThrows(ValidacaoException.class, () -> {
        	service.salvarCartao(request);
        });
        
        assertEquals(TransactionErrorEnum.CARTAO_INEXISTENTE.toString(), exception.getMessage());
    }
}