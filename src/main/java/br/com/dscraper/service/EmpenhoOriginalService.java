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

import java.util.Date;
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
     * @param original
     * @param orgao
     */
    public void setReforcoAnulacao(List<NeReforcoAnulacaoDto> lst, Empenho original, Orgao orgao) {


        for (NeReforcoAnulacaoDto bean : lst) {
            Optional<Empenho> optReforco = empenhoService.findByNotaAndOrgaoId(
                    bean.getNeReforcadaAnulada(), orgao.getId());

            if (optReforco.isPresent()) {

                Optional<EmpenhoOriginal> optEmpenhoOriginal = empenhoOriginalRepository
                        .findByOriginalIdAndReforcoId(original.getId(), optReforco.get().getId());

                EmpenhoOriginal eo = optEmpenhoOriginal.orElseGet(EmpenhoOriginal::new);
                eo.setDescricao(bean.getDescricao());
                eo.setEvento(bean.getEvento());
                eo.setReforco(optReforco.get());
                eo.setOriginal(original);
                eo.setValor(NumberUtil.strToBigDecimal(bean.getValor()));
                if (eo.getId() == 0L) {
                    eo.setLancamento(new Date());
                }
                empenhoOriginalRepository.save(eo);
            }
        }
    }
}
