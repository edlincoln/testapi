package com.testapi.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class CartaoRequest implements Serializable {


    private static final long serialVersionUID = 6241551438230474807L;

    @NotNull(message = "O número do cartão é obrigatório")
    private Long numeroCartao;

    @NotNull(message = "Senha é obrigatório")
    private String senha;
}
