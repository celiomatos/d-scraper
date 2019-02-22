package br.com.dscraper.service;

import br.com.dscraper.dao.PagamentoRepository;
import br.com.dscraper.dto.OrgaoValorDto;
import br.com.dscraper.dto.PagamentoDto;
import br.com.dscraper.model.*;
import br.com.dscraper.util.DateUtil;
import br.com.dscraper.util.MyConstant;
import br.com.dscraper.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Service
public class PagamentoService {

    @Autowired
    private PagamentosSiteService pagamentosSiteService;

    @Autowired
    private ParametroService parametroService;

    @Autowired
    private OrgaoService orgaoService;

    @Autowired
    private CredorService credorService;

    @Autowired
    private FonteService fonteService;

    @Autowired
    private ClassificacaoService classificacaoService;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    /**
     * coleta de pagamentos do mes atual ou meses anteriores
     *
     * @param isMesAtual
     */
    public void updateBySchedule(boolean isMesAtual) {

        String mes = "";
        String ano = "";

        if (isMesAtual) {
            Calendar calAtual = Calendar.getInstance();
            int m = calAtual.get(Calendar.MONTH) + 1;
            mes = m < 10 ? "0" + m : "" + m;
            ano = String.valueOf(calAtual.get(Calendar.YEAR));
        } else {
            String mesAno = parametroService.getParametroPagamento();
            if (mesAno != null && mesAno.length() == 7) {
                mes = mesAno.substring(0, 2);
                ano = mesAno.substring(3);
            }
        }
        updateBySchedule(mes, ano);
    }

    /**
     * acesando site do portal de transparencia para coleta de dados
     * obtendo o inicio e fim do mes para busca de dados persistidos
     * setando o primeiro dia do mes
     * setando o ultimo dia do mes
     *
     * @param mes
     * @param ano
     */
    private void updateBySchedule(String mes, String ano) {

        List<OrgaoValorDto> orgaos = pagamentosSiteService.getOrgaoValue(mes, ano);

        if (!orgaos.isEmpty()) {
            Calendar cal = Calendar.getInstance();
            int a = Integer.parseInt(ano);
            int m = Integer.parseInt(mes);

            cal.set(a, (m - 1), 1);
            Date inicio = cal.getTime();
            cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            Date fim = cal.getTime();

            List<OrgaoValorDto> persistido = orgaoService.getOrgaoValueByDate(inicio, fim);
            List<OrgaoValorDto> dif = compara(orgaos, persistido);
            List<OrgaoValorDto> pagamentos = pagamentosSiteService.getPagamentos(dif, mes, ano);
            compara(pagamentos, inicio, fim);
        }
    }

    /**
     * compara os valores obtidos no site com os persitidos no banco
     *
     * @param orgaos
     * @param persistidos
     * @return
     */
    private List<OrgaoValorDto> compara(List<OrgaoValorDto> orgaos, List<OrgaoValorDto> persistidos) {

        List<OrgaoValorDto> result = new ArrayList<>();

        for (OrgaoValorDto bean : orgaos) {
            boolean achou = false;
            String value = bean.getValor().replaceAll("[,]", "");

            for (OrgaoValorDto persistido : persistidos) {
                if (bean.getCodigo().equalsIgnoreCase(persistido.getCodigo())) {
                    achou = true;
                    if (!value.equalsIgnoreCase(persistido.getValor())) {
                        result.add(bean);
                        break;
                    }
                }
            }
            if (!achou) {
                result.add(bean);
            }
        }
        return result;
    }

    /**
     * busca detalhada somente dos orgaos que tem divergencia de valores
     *
     * @param orgaos
     * @param inicio
     * @param fim
     */
    private void compara(List<OrgaoValorDto> orgaos, Date inicio, Date fim) {

        List<OrgaoValorDto> lstfinal = new ArrayList<>();
        List<Pagamento> removidos = new ArrayList<>();

        for (OrgaoValorDto orgao : orgaos) {

            List<Pagamento> p = pagamentoRepository.getPagamentos(orgao.getCodigo(), inicio, fim);

            List<PagamentoDto> pagamentos = new ArrayList<>();

            for (PagamentoDto b : orgao.getPagamentos()) {
                StringBuilder chave1 = new StringBuilder();
                chave1.append(StringUtil.removerAcento(b.getCredor()).toUpperCase());
                chave1.append(b.getData());
                chave1.append(b.getNrOb());
                chave1.append(b.getNrNl());
                chave1.append(b.getNrNe());
                chave1.append(b.getFonte());
                chave1.append(b.getClassificacao());
                String value = b.getValor().replaceAll("[,]", "");
                b.setValor(value);
                chave1.append(value);

                boolean achou = false;
                for (int i = 0; i < p.size(); i++) {
                    StringBuilder chave2 = new StringBuilder();
                    chave2.append(StringUtil.removerAcento(p.get(i).getCredor().getNome()).toUpperCase());
                    chave2.append(DateUtil.formatDateBr(p.get(i).getData()));
                    chave2.append(p.get(i).getNrOb());
                    chave2.append(p.get(i).getNrNl());
                    chave2.append(p.get(i).getNrNe());
                    chave2.append(p.get(i).getFonte().getId());
                    chave2.append(p.get(i).getClassificacao().getCodigo());
                    chave2.append(p.get(i).getValor().toString());

                    if (chave1.toString().equalsIgnoreCase(chave2.toString())) {
                        achou = true;
                        p.remove(i);
                        break;
                    }
                }
                if (!achou) {
                    pagamentos.add(b);
                }
            }
            if (!p.isEmpty()) {
                removidos.addAll(p);
            }
            if (!pagamentos.isEmpty()) {
                orgao.setPagamentos(pagamentos);
                lstfinal.add(orgao);
            }
        }

        persist(lstfinal, removidos);
    }

    /**
     *
     * @param pagamentos
     * @param removidos
     */
    private void persist(List<OrgaoValorDto> pagamentos, List<Pagamento> removidos) {

        if (!removidos.isEmpty()) {
            for (Pagamento r : removidos) {
                r.setRemovido(true);
                pagamentoRepository.save(r);
            }
        }

        for (OrgaoValorDto o : pagamentos) {
            Orgao orgao = getOrgao(o.getCodigo(), o.getOrgao(), o.getNome());
            if (orgao != null) {
                // limpar map
                for (PagamentoDto p : o.getPagamentos()) {
                    Credor credor = getCredor(p.getCredor());
                    if (credor != null) {
                        Fonte fonte = getFonte(p.getFonte());
                        if (fonte != null) {
                            Classificacao classificacao = getClassificacao(p.getClassificacao());
                            if (classificacao != null) {
                                Pagamento pagamento = Pagamento.builder()
                                        .orgao(orgao)
                                        .credor(credor)
                                        .data(DateUtil.strToDate(p.getData()))
                                        .nrOb(p.getNrOb())
                                        .nrNl(p.getNrNl())
                                        .nrNe(p.getNrNe())
                                        .fonte(fonte)
                                        .classificacao(classificacao)
                                        .valor(new BigDecimal(p.getValor()))
                                        .lancamento(new Date())
                                        .removido(false)
                                        .build();

                                log.info("save nota de empenho {}", p.getNrNe());
                                pagamentoRepository.save(pagamento);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * @param codigo
     * @param nome
     * @param orgNome
     * @return
     */
    private Orgao getOrgao(String codigo, String nome, String orgNome) {

        Optional<Orgao> optOrgao = orgaoService.findByCodigo(codigo);

        if (!optOrgao.isPresent()) {
            Orgao orgao = Orgao.builder()
                    .codigo(codigo)
                    .esfera(MyConstant.ESFERA_ESTADO)
                    .nome(StringUtil.inTrim(nome))
                    .orgName(StringUtil.inTrim(orgNome))
                    .build();

            return orgaoService.save(orgao);

        } else if ((!optOrgao.get().getNome().equalsIgnoreCase(nome))
                || (!optOrgao.get().getOrgName().equalsIgnoreCase(orgNome))) {

            optOrgao.get().setNome(StringUtil.inTrim(nome));
            optOrgao.get().setOrgName(StringUtil.inTrim(orgNome));

            orgaoService.save(optOrgao.get());
        }
        return optOrgao.get();
    }

    /**
     * @param nome
     * @return
     */
    private Credor getCredor(String nome) {

        Optional<Credor> optCredor = credorService.findByNome(nome);
        if (!optCredor.isPresent()) {
            Credor credor = Credor.builder()
                    .nome(StringUtil.removerAcento(nome).toUpperCase())
                    .build();

            return credorService.save(credor);
        }

        return optCredor.get();
    }

    /**
     * @param codigo
     * @return
     */
    private Fonte getFonte(String codigo) {

        Optional<Fonte> optFonte = fonteService.findByCodigo(codigo);
        if (!optFonte.isPresent()) {
            Fonte fonte = Fonte.builder()
                    .id(codigo)
                    .nome("X")
                    .build();

            return fonteService.save(fonte);

        }
        return optFonte.get();
    }

    /**
     * @param codigo
     * @return
     */
    private Classificacao getClassificacao(String codigo) {

        Optional<Classificacao> optClassificacao = classificacaoService.findByCodigo(codigo);
        if (!optClassificacao.isPresent()) {
            Classificacao classificacao = Classificacao.builder()
                    .codigo(codigo)
                    .nome("X")
                    .build();

            return classificacaoService.save(classificacao);
        }

        return optClassificacao.get();
    }
}
