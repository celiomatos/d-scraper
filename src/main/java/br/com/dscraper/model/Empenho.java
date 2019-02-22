package br.com.dscraper.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "empenho", schema = "scraper")
public class Empenho implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id", nullable = false)
    private long id;

    @Column(name = "emp_nota", length = 11, nullable = false)
    private String nota;

    @Column(name = "emp_data", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date data;

    @Column(name = "emp_tipo" ,length = 250)
    private String tipo;

    @Column(name = "emp_programa", length = 250)
    private String programa;

    @Column(name = "emp_funcao", length = 250)
    private String funcao;

    @Column(name = "emp_sub_funcao", length = 250)
    private String subFuncao;

    @Column(name = "emp_licitacao", length = 250)
    private String licitacao;

    @Column(name = "emp_referencia", length = 250)
    private String referencia;

    @Column(name = "emp_processo", length = 100)
    private String processo;

    @Column(name = "emp_descricao", length = 100000)
    private String descricao;

    @JoinColumn(name = "emp_cre_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Credor credor;

    @JoinColumn(name = "emp_fon_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Fonte fonte;

    @JoinColumn(name = "emp_org_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Orgao orgao;

}
