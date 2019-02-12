package br.com.dscraper.service;

import br.com.dscraper.dto.OrgaoValorDto;
import br.com.dscraper.dao.OrgaoRepository;
import br.com.dscraper.model.Orgao;
import br.com.dscraper.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class OrgaoService {

    @Autowired
    private OrgaoRepository orgaoRepository;

    public List<OrgaoValorDto> getOrgaoValueByDate(Date dtInicial, Date dtFinal) {

        List<Object[]> lst = orgaoRepository.getOrgaoValueByDate(
                DateUtil.formatDateBr(dtInicial),
                DateUtil.formatDateBr(dtFinal));

        List<OrgaoValorDto> result = new LinkedList<>();

        lst.stream().map((obj) -> {
            OrgaoValorDto bean = new OrgaoValorDto();
            bean.setCodigo(obj[0].toString());
            bean.setNome(obj[1].toString());
            bean.setValor(obj[2].toString());
            return bean;
        }).forEachOrdered((bean) -> {
            result.add(bean);
        });
        return result;
    }

    public Optional<Orgao> findByCodigo(String codigo) {
        return orgaoRepository.findByCodigo(codigo);
    }

    public Orgao save(Orgao orgao) {
        return orgaoRepository.save(orgao);
    }

}
