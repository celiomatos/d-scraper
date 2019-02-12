package br.com.dscraper.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Builder
@Table(name = "fonte", schema = "scraper")
public class Fonte implements Serializable {

    @Id
    @Column(name = "fon_id", nullable = false, length = 8)
    private String id;

    @Column(name = "fon_nome", nullable = false, length = 100)
    private String nome;

    public Fonte(String id) {
        this.id = id;
    }
}
