package br.com.dscraper.service;

import br.com.dscraper.dto.NeReforcoAnulacaoDto;
import br.com.dscraper.dto.NotaEmpenhoDto;
import br.com.dscraper.model.Credor;
import br.com.dscraper.model.Empenho;
import br.com.dscraper.model.Orgao;
import br.com.dscraper.util.DateUtil;
import br.com.dscraper.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class EmpenhoService {

    @Autowired
    private ParametroService parametroService;

    @Autowired
    private EmpenhoSiteService empenhoSiteService;

    @Autowired
    private OrgaoService orgaoService;

    @Autowired
    private CredorService credorService;

    /**
     * @param pr
     */
    public void updateBySchedule(long pr) {
        String p = parametroService.getParametroEmpenho(pr);
        if (p != null) {
            String ano = p.substring(0, 4);
            String orgao = p.substring(4);
            List<NotaEmpenhoDto> lst = empenhoSiteService.getNotasEmpenho(ano, orgao, pr);
            if (!lst.isEmpty()) {
                persistEmpenho(lst);
            }
        }
    }

    /**
     * @param nes
     */
    private void persistEmpenho(List<NotaEmpenhoDto> nes) {
        // verificando a existencia do orgao
        Optional<Orgao> optOrgao = orgaoService.findByCodigo(nes.get(0).getOrgao());

        if (optOrgao.isPresent()) {
            for (NotaEmpenhoDto ne : nes) {

                Empenho empenho = getTbempenho(ne, optOrgao.get());

                if (empenho != null) {
                    setTbempenhoDespesa(ne, empenho);

                    List<NeReforcoAnulacaoDto> list = ne.getAnuladaReforcada();

                    if (list != null && !list.isEmpty()) {
                        setReforcoAnulacao(list, empenho, optOrgao.get());
                    }
                }
            }
        }
    }

    /**
     *
     * @param ne
     * @param o
     * @return
     */
    private Empenho getTbempenho(NotaEmpenhoDto ne, Orgao o) {

        long idOrgao = o.getId();
        Empenho empenho = null;

        // credores
        Credor credor = getCredor(ne.getCoCredor());
        if (credor != null) {
            // fontes
            Integer idfr = getIdFonte(ne.getFonte());
            if (idfr != null) {
                try {
                    empenho = ef.getEmpenhoByNeAndOrgao(ne.getNuEmpenho(), idOrgao);
                    if (tbempenho == null) {
                        tbempenho = new Empenho();
                    }
                    empenho.setData(DateUtil.strToDate(ne.getDataEmissao()));
                    empenho.setDescricao(ne.getDescricaoEmpenho());
                    empenho.setFuncao(ne.getFuncao());
                    empenho.setLicitacao(ne.getLicitacao());
                    empenho.setNota(ne.getNuEmpenho());
                    empenho.setProcesso(ne.getNuProcesso());
                    empenho.setProgramaTrabalho(ne.getProgramaTrabalho());
                    empenho.setReferencia(ne.getReferenciaLicitacao());
                    empenho.setSubFuncao(ne.getSubFuncao());
                    empenho.setTipo(ne.getTipoEmpenho());
                    empenho.setOrgao(o);
                    empenho.setCredor(credor);
                    empenho.setFonte(new Fonte(idfr));

                    if (tbempenho.getId() == null) {
                        ef.create(tbempenho);
                        tbempenho = ef.getEmpenhoByNeAndOrgao(ne.getNuEmpenho(), idOrgao);
                    } else {
                        ef.edit(tbempenho);
                    }
                } catch (Exception ex) {
                    tbempenho = null;
                    ex.printStackTrace(System.err);
                }
            }
        }

        return tbempenho;
    }

    private void setTbempenhoDespesa(NotaEmpenhoDto ne, Empenho e) {

        long idempenho = e.getId();

        try {
            EmpenhosDespesas ed = edf.getTbempenhoDespesaByEmpenhoAndAno(idempenho, ne.getAno());

            if (ed == null) {
                ed = new EmpenhosDespesas();
            }
            ed.setAnoDespesa(String.valueOf(ne.getAno()));
            ed.setNaturezaDespesa(ne.getNaturezaDespesa());
            ed.setEmpenhos(e);
            ed.setValorDespesa(NumberUtils.strToBigDecimal(ne.getVaDocumento()));
            ed.setDtlancamento(new Date());

            if (ed.getIddespesa() == null) {
                edf.create(ed);
            } else {
                edf.edit(ed);
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }


    private void setReforcoAnulacao(List<NeReforcoAnulacaoDto> lst, Empenho e, Orgao o) {

        long idNeOriginal = e.getId();
        long idorgao = o.getId();

        lst.forEach((bean) -> {
            try {
                Empenho tbempenho = ef.getEmpenhoByNeAndOrgao(
                        bean.getNeReforcadaAnulada(), idorgao);

                if (tbempenho != null) {

                    int idNeReforco = tbempenho.getIdempenho();

                    EmpenhosOriginais eo = eof.getEmpenhoOriginal(idNeOriginal, idNeReforco);

                    if (eo == null) {
                        eo = new EmpenhosOriginais();
                    }

                    eo.setDescricao(bean.getDescricao());
                    eo.setEvento(bean.getEvento());
                    eo.setEmpenhos(tbempenho);
                    eo.setEmpenhoOriginal(e);
                    eo.setValor(NumberUtils.strToBigDecimal(bean.getValor()));

                    if (eo.getIdoriginal() == null) {
                        eof.create(eo);
                    } else {
                        eof.edit(eo);
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }
        });
    }

    /**
     *
     * @param strCredor
     * @return
     */
    private Credor getCredor(String strCredor) {

        Credor credor = null;
        String vtcredor[] = strCredor.split("[-]");

        if (vtcredor.length > 0) {

            String codigo = vtcredor[0].trim();

            Optional<Credor> optCodigo = credorService.findByCodigo(codigo);

            if (!optCodigo.isPresent() && vtcredor.length > 1) {
                String nome = StringUtil.removerAcento(vtcredor[1]).toUpperCase();

                Optional<Credor> optNome = credorService.findByNome(nome);
                if (!optNome.isPresent()) {
                    credor = new Credor();
                }
                credor.setCodigo(codigo);
                credor.setNome(nome);

                credor = credorService.save(credor);
            }

        }

        return credor;
    }

}
