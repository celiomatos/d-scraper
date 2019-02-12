package br.com.dscraper.service;

import br.com.dscraper.dao.FonteRepository;
import br.com.dscraper.model.Fonte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FonteService {

    @Autowired
    private FonteRepository fonteRepository;

    public Optional<Fonte> findByCodigo(String codigo) {
        return fonteRepository.findById(codigo);
    }

    public Fonte save(Fonte fonte) {
        return fonteRepository.save(fonte);
    }
}
