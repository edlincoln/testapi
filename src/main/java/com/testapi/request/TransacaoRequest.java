package com.testapi.request;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import lombok.Data;

@Data
public class TransacaoRequest implements Serializable {

    private static final long serialVersionUID = 1320998842316586180L;

	@NotNull(message = "O número do cartão é obrigatório")
    private Long numeroCartao;

    @NotNull(message = "Senha é obrigatório")
    private String senhaCartao;
    
    @NumberFormat(style = Style.CURRENCY)
    private BigDecimal valor;
}
