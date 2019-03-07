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

    /**
     *
     * @param codigo
     * @return
     */
    public Optional<Classificacao> findByCodigo(String codigo) {
        return classificacaoRepository.findByCodigo(codigo);
    }

    /**
     *
     * @param classificacao
     * @return
     */
    public Classificacao save(Classificacao classificacao) {
        return classificacaoRepository.save(classificacao);
    }

    /**
     *
     * @param strClassificacao
     * @return
     */
    public Classificacao getOrCreateClassificacao(String strClassificacao) {

        Classificacao classificacao = null;
        String vtclassificacao[] = strClassificacao.split("[-]");

        if (vtclassificacao.length > 0) {
            String codigo = vtclassificacao[0].trim();

            Optional<Classificacao> optClassificacao = findByCodigo(codigo);

            if (optClassificacao.isPresent()) {
                if (!optClassificacao.get().getNome().equalsIgnoreCase("X")) {
                    return optClassificacao.get();
                }
            }

            classificacao = optClassificacao.orElseGet(Classificacao::new);
            classificacao.setCodigo(codigo);
            classificacao.setNome(vtclassificacao.length > 1 ? vtclassificacao[1].trim() : "X");

            classificacao = save(classificacao);

        }
        return classificacao;
    }
}
