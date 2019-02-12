package br.com.dscraper.dao;

import br.com.dscraper.model.Credor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CredorRepository extends JpaRepository<Credor, Long> {

    Optional<Credor> findByNome(String nome);
}
