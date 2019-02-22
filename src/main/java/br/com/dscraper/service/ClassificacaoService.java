package br.com.dscraper.service;

import br.com.dscraper.dao.ClassificacaoRepository;
import br.com.dscraper.model.Classificacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClassificacaoService {

    @Autowired
    private ClassificacaoRepository classificacaoRepository;

    Optional<Classificacao> findByCodigo(String codigo) {
        return classificacaoRepository.findByCodigo(codigo);
    }

    public Classificacao save(Classificacao classificacao) {
        return classificacaoRepository.save(classificacao);
    }
}