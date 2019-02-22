package br.com.dscraper.dao;

import br.com.dscraper.model.EmpenhoOriginal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpenhoOriginalRepository extends JpaRepository<EmpenhoOriginal, Long> {

    Optional<EmpenhoOriginal> findByOriginalIdAndReforcoId(long idOriginal, long idReforco);
}
