package br.com.dscraper.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "empenho_original", schema = "scraper")
public class EmpenhoOriginal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ori_id", nullable = false)
    private Integer id;

    @Column(name = "ori_evento", length = 15, nullable = false)
    private String evento;

    @Column(name = "ori_valor")
    private BigDecimal valor;

    @Column(name = "ori_descricao", length = 100000)
    private String descricao;

    @JoinColumn(name = "ori_emp_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Empenho reforco;

    @JoinColumn(name = "ori_emp_ori")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Empenho original;

}
