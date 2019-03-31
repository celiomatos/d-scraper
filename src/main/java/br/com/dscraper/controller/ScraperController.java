package br.com.dscraper.controller;

import br.com.dscraper.service.EmpenhoService;
import br.com.dscraper.service.PagamentoService;
import br.com.dscraper.util.MyConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scraper")
public class ScraperController {

    @Autowired
    private PagamentoService pagamentoService;

    @Autowired
    private EmpenhoService empenhoService;

    @GetMapping("/pagamento-mes-atual")
    public void pagamentoMesAtual() {
        pagamentoService.updateBySchedule(true);
    }

    @GetMapping("/pagamento-mes-anterior")
    public void pagamentoMesAnterior() {
        pagamentoService.updateBySchedule(false);
    }

    @GetMapping("/empenho-ano-atual")
    public void empenhoAnoAtual() {
        empenhoService.updateBySchedule(MyConstant.EMPENHO_ANO_ATUAL);
    }

    @GetMapping("/empenho-ano-anterior")
    public void empenhoAnoAnterior() {
        empenhoService.updateBySchedule(MyConstant.EMPENHO_ANOS_ANTERIORES);
    }

}
