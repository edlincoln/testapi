package com.testapi.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
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
import com.testapi.exceptions.ValidacaoException;
import com.testapi.repository.CartoesRepository;
import com.testapi.request.TransacaoRequest;
import com.testapi.service.TransacaoServiceImpl;

@ExtendWith(MockitoExtension.class)
class TransacaoServiceImplTest {

	@Mock
	CartoesRepository repository = mock(CartoesRepository.class);

	@InjectMocks
	TransacaoServiceImpl service;

	Cartoes cartao;

	TransacaoRequest request;

	private static final Long ID = 1l;
	private static final Long NUMERO = 12340987l;
	private static final String SENHA = "1234";
	private static final BigDecimal SALDO_INICIAL = new BigDecimal(500l);
	private static final String SENHA_ERRADA = "4321";
	private static final BigDecimal SALDO_MAIOR = new BigDecimal(700l);

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		cartao = Cartoes.builder()
				.id(ID)
				.numeroCartao(NUMERO)
				.senha(SENHA)
				.saldo(SALDO_INICIAL).build();

		request = new TransacaoRequest();
		request.setNumeroCartao(NUMERO);
		request.setSenha(SENHA);
		request.setValor(SALDO_INICIAL);
	}

    @Test
    void processarCompra() throws ValidacaoException {
    	when(repository.findByNumeroCartao(NUMERO)).thenReturn(Optional.of(cartao));
    	when(repository.save(cartao)).thenReturn(cartao);
    	
    	Cartoes ret = service.processarCompra(request);
    	
    	assertEquals(cartao.getId(), ret.getId());
    	assertEquals(cartao.getNumeroCartao(), ret.getNumeroCartao());
    	assertEquals(cartao.getSenha(), ret.getSenha());
    	assertEquals(cartao.getSaldo(), ret.getSaldo());
    }

    @Test
    void processarCompraCartaoInexistente() throws ValidacaoException {
    	when(repository.findByNumeroCartao(NUMERO)).thenReturn(Optional.ofNullable(null));  
    	
        Exception exception = assertThrows(ValidacaoException.class, () -> {
        	service.processarCompra(request);
        });
        
        assertEquals(TransactionErrorEnum.CARTAO_INEXISTENTE.toString(), exception.getMessage());
    }
    
    @Test
    void processarCompraSenhaCartaoInvalida() throws ValidacaoException {
    	when(repository.findByNumeroCartao(NUMERO)).thenReturn(Optional.of(cartao));  
    	request.setSenha(SENHA_ERRADA);
        Exception exception = assertThrows(ValidacaoException.class, () -> {
        	service.processarCompra(request);
        });
        
        assertEquals(TransactionErrorEnum.SENHA_INVALIDA.toString(), exception.getMessage());
    }
    
    @Test
    void processarCompraSaldoMaior() throws ValidacaoException {
    	when(repository.findByNumeroCartao(NUMERO)).thenReturn(Optional.of(cartao));  
    	request.setValor(SALDO_MAIOR);
        Exception exception = assertThrows(ValidacaoException.class, () -> {
        	service.processarCompra(request);
        });
        
        assertEquals(TransactionErrorEnum.SALDO_INSUFICIENTE.toString(), exception.getMessage());
    }
}