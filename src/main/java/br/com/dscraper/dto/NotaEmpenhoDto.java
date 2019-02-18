package br.com.dscraper.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NotaEmpenhoDto {

    private String dataEmissao;
    private String vaDocumento;
    private String coCredor;
    private String tipoEmpenho;
    private String programaTrabalho;
    private String funcao;
    private String subFuncao;
    private String naturezaDespesa;
    private String fonte;
    private String licitacao;
    private String referenciaLicitacao;
    private String nuProcesso;
    private String descricaoEmpenho;
    private String nuEmpenho;
    private String ano;
    private String orgao;
    private List<NeReforcoAnulacaoDto> anuladaReforcada;

}
