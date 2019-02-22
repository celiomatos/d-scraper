package br.com.dscraper.service;

import br.com.dscraper.dao.EmpenhoDespesaRepository;
import br.com.dscraper.dto.EmpenhoDto;
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

    public void save(EmpenhoDto empenhoDto, Empenho empenho) {

        Optional<EmpenhoDespesa> optEmpenhoDespesa = empenhoDespesaRepository.findByEmpenhoIdAndAno(
                empenho.getId(), empenhoDto.getAno());

        EmpenhoDespesa empenhoDespesa = optEmpenhoDespesa.orElseGet(EmpenhoDespesa::new);
        empenhoDespesa.setAno(String.valueOf(empenhoDto.getAno()));
        empenhoDespesa.setNatureza(empenhoDto.getNaturezaDespesa());
        empenhoDespesa.setEmpenho(empenho);
        empenhoDespesa.setValor(NumberUtil.strToBigDecimal(empenhoDto.getVaDocumento()));
        empenhoDespesa.setDtlancamento(new Date());

        empenhoDespesaRepository.save(empenhoDespesa);
    }
}
