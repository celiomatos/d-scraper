package br.com.dscraper.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
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

    public Credor(Long id) {
        this.id = id;
    }
}
