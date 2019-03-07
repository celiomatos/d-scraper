package br.com.dscraper.service;

import br.com.dscraper.dao.EmpenhoDespesaRepository;
import br.com.dscraper.dto.EmpenhoDto;
import br.com.dscraper.model.Classificacao;
import br.com.dscraper.model.Empenho;
import br.com.dscraper.model.EmpenhoDespesa;
import br.com.dscraper.util.NumberUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
public class EmpenhoDespesaService {

    @Autowired
    private EmpenhoDespesaRepository empenhoDespesaRepository;

    @Autowired
    private ClassificacaoService classificacaoService;

    /**
     * @param empenhoDto
     * @param empenho
     */
    public void save(EmpenhoDto empenhoDto, Empenho empenho) {

        Optional<EmpenhoDespesa> optEmpenhoDespesa = empenhoDespesaRepository.findByEmpenhoIdAndAno(
                empenho.getId(), empenhoDto.getAno());

        EmpenhoDespesa empenhoDespesa = optEmpenhoDespesa.orElseGet(EmpenhoDespesa::new);
        empenhoDespesa.setAno(String.valueOf(empenhoDto.getAno()));
        empenhoDespesa.setEmpenho(empenho);
        empenhoDespesa.setValor(NumberUtil.strToBigDecimal(empenhoDto.getVaDocumento()));
        if (empenhoDespesa.getId() == 0L) {
            empenhoDespesa.setLancamento(new Date());
        }
        Classificacao classificacao = classificacaoService.getOrCreateClassificacao(empenhoDto.getNaturezaDespesa());
        empenhoDespesa.setNatureza(classificacao);

        empenhoDespesaRepository.save(empenhoDespesa);
    }
}
