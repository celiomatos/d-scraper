package br.com.dscraper.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Setter
@Getter
@Entity
@AllArgsConstructor
@Table(name = "fonte", schema = "scraper")
public class Fonte implements Serializable {

    @Id
    @Column(name = "fon_id", nullable = false)
    private Long id;

    @Column(name = "fon_nome", nullable = false, length = 100)
    private String nome;

}
