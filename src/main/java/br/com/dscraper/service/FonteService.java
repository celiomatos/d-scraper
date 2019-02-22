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

    /**
     * @param codigo
     * @return
     */
    public Optional<Fonte> findByCodigo(String codigo) {
        return fonteRepository.findById(codigo);
    }

    /**
     * @param fonte
     * @return
     */
    public Fonte save(Fonte fonte) {
        return fonteRepository.save(fonte);
    }

    /**
     * @param strFonte
     * @return
     */
    public Fonte getOrCreateFonte(String strFonte) {

        Fonte fonte = null;

        String frVt[] = strFonte.split("[-]");

        if (frVt.length > 0) {

            String codigo = frVt[0].trim();

            Optional<Fonte> optFonte = findByCodigo(codigo);
            if (optFonte.isPresent()) {
                fonte = optFonte.get();
            } else {
                fonte = new Fonte();
                fonte.setId(codigo);
                fonte.setNome("XXX");
                if (frVt.length > 1) {
                    String descricao = frVt[1].trim();
                    fonte.setNome(descricao);
                }
                fonte = save(fonte);
            }
        }
        return fonte;
    }
}