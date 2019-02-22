package br.com.dscraper.dao;

import br.com.dscraper.model.Empenho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpenhoRepository extends JpaRepository<Empenho, Long> {

    Optional<Empenho> findByNotaAndOrgaoId(String nota, long idOrgao);
}
