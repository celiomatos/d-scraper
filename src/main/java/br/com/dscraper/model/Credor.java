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
@Table(name = "credor", schema = "scraper")
public class Credor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cre_id", nullable = false)
    private Long id;

    @Column(name = "cre_nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "cre_codigo", length = 20)
    private String codigo;
}
