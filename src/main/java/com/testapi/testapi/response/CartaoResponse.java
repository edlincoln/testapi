package com.testapi.testapi.response;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartaoResponse implements Serializable {

    private static final long serialVersionUID = -3472965826794926154L;

    private Long numeroCartao;

    private String senha;
}
