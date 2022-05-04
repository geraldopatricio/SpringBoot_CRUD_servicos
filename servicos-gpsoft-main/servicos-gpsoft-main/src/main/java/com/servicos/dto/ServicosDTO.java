package com.servicos.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.servicos.entity.Laboratorio;
import com.servicos.entity.Propriedade;
import com.servicos.entity.Servicos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ServicosDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	@NotEmpty(message = "Preenchimento obrigatório")
	private String nome;

	private String observacoes;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@NotNull(message = "Preenchimento obrigatório")
	private Date dataInicial;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@NotNull(message = "Preenchimento obrigatório")
	private Date dataFinal;

	@NotNull(message = "Preenchimento obrigatório")
	private InfosPropriedadeDTO infosPropriedade;

	@NotNull(message = "Preenchimento obrigatório")
	private LaboratorioDTO laboratorio;

	public ServicosDTO(Servicos obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.observacoes = obj.getObservacoes();
		this.dataInicial = obj.getDataInicial();
		this.dataFinal = obj.getDataFinal();
		this.infosPropriedade = propriedadeToinfosPropriedade(obj.getPropriedade());
		this.laboratorio = laboratorioToLaboratorioDTO(obj.getLaboratorio());
	}

	private InfosPropriedadeDTO propriedadeToinfosPropriedade(Propriedade propriedade) {
		InfosPropriedadeDTO result = new InfosPropriedadeDTO();
		result.setId(propriedade.getId());
		result.setNome(propriedade.getNome());
		return result;
	}

	private LaboratorioDTO laboratorioToLaboratorioDTO(Laboratorio laboratorio) {
		LaboratorioDTO result = new LaboratorioDTO();
		result.setId(laboratorio.getId());
		result.setNome(laboratorio.getNome());
		return result;

	}

}
