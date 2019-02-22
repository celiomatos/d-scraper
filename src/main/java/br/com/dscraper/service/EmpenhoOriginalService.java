package br.com.dscraper.service;

import br.com.dscraper.dao.EmpenhoOriginalRepository;
import br.com.dscraper.dto.NeReforcoAnulacaoDto;
import br.com.dscraper.model.Empenho;
import br.com.dscraper.model.EmpenhoOriginal;
import br.com.dscraper.model.Orgao;
import br.com.dscraper.util.NumberUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class EmpenhoOriginalService {

    @Autowired
    private EmpenhoOriginalRepository empenhoOriginalRepository;

    @Autowired
    private EmpenhoService empenhoService;

    /**
     * @param lst
     * @param empenho
     * @param orgao
     */
    public void setReforcoAnulacao(List<NeReforcoAnulacaoDto> lst, Empenho empenho, Orgao orgao) {

        long idNeOriginal = empenho.getId();

        lst.forEach((NeReforcoAnulacaoDto bean) -> {

            Optional<Empenho> optEmpenho = empenhoService.findByNotaAndOrgaoId(
                    bean.getNeReforcadaAnulada(), orgao.getId());

            if (optEmpenho.isPresent()) {

                long idNeReforco = optEmpenho.get().getId();

                Optional<EmpenhoOriginal> optEmpenhoOriginal = empenhoOriginalRepository
                        .findByOriginalIdAndReforcoId(idNeOriginal, idNeReforco);

                EmpenhoOriginal eo = optEmpenhoOriginal.orElseGet(EmpenhoOriginal::new);
                eo.setDescricao(bean.getDescricao());
                eo.setEvento(bean.getEvento());
                eo.setReforco(optEmpenho.get());
                eo.setOriginal(empenho);
                eo.setValor(NumberUtil.strToBigDecimal(bean.getValor()));

                empenhoOriginalRepository.save(eo);
            }
        });
    }
}
