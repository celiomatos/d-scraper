package br.com.dscraper.service;

import br.com.dscraper.dao.CredorRepository;
import br.com.dscraper.model.Credor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class CredorService {

    @Autowired
    private CredorRepository credorRepository;

    public Optional<Credor> findByNome(String nome) {
        return credorRepository.findByNome(nome);
    }

    public Credor save(Credor credor) {
        return credorRepository.save(credor);
    }
}
