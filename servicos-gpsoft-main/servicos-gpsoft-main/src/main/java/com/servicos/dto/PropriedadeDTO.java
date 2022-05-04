package com.servicos.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import com.servicos.entity.Propriedade;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
public class PropriedadeDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	@NotEmpty(message="Preenchimento obrigatório")
	private String nome;
	@NotEmpty(message="Preenchimento obrigatório")
	private String cnpj;
	
	
	public PropriedadeDTO(Propriedade obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();	
		this.cnpj = obj.getCnpj();
	}

	
}
