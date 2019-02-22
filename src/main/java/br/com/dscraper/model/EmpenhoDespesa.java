package br.com.dscraper.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "empenho_despesa", schema = "scraper")
public class EmpenhoDespesa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "des_id", nullable = false)
    private Integer id;

    @Column(name = "des_ano", length = 4, nullable = false)
    private String ano;

    @Column(name = "des_valor", nullable = false)
    private BigDecimal valor;

    @Column(name = "des_natureza", length = 250)
    private String natureza;

    @Column(name = "des_lancamento", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtlancamento;

    @JoinColumn(name = "des_emp_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Empenho empenho;

}
