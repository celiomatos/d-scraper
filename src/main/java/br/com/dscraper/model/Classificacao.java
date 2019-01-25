package br.com.dscraper.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@Entity
@AllArgsConstructor
@Table(name = "classificacao", schema = "scraper")
public class Classificacao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cla_id", nullable = false)
    private Long id;

    @Column(name = "cla_codigo", length = 10)
    private String codigo;

    @Column(name = "cla_nome", length = 100)
    private String nome;
}
