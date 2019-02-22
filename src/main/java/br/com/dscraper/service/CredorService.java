package br.com.dscraper.service;

import br.com.dscraper.dao.CredorRepository;
import br.com.dscraper.model.Credor;
import br.com.dscraper.util.StringUtil;
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

    public Optional<Credor> findByCodigo(String codigo) {
        return credorRepository.findByCodigo(codigo);
    }

    public Credor getOrCreateCredor(String strCredor) {

        Credor credor = null;
        String vtcredor[] = strCredor.split("[-]");

        if (vtcredor.length > 0) {

            String codigo = vtcredor[0].trim();

            Optional<Credor> optCodigo = findByCodigo(codigo);

            if (optCodigo.isPresent()) {
                credor = optCodigo.get();
            } else if (vtcredor.length > 1) {
                String nome = StringUtil.removerAcento(vtcredor[1]).toUpperCase();

                Optional<Credor> optNome = findByNome(nome);

                credor = optNome.orElseGet(Credor::new);
                credor.setCodigo(codigo);
                credor.setNome(nome);

                credor = save(credor);
            }
        }
        return credor;
    }
}
