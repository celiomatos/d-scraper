package br.com.dscraper.controller;

import br.com.dscraper.service.PagamentoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("scraper")
@RestController
@RequestMapping("/scraper")
public class ScraperController {

    @Autowired
    private PagamentoService pagamentoService;

    @ApiOperation("Update actual payment ")
    @GetMapping("/pagamento-mes-atual")
    public void pagamentoMesAtual() {
        pagamentoService.updateBySchedule(true);
    }

    @ApiOperation("Update lasts payment ")
    @GetMapping("/pagamento-mes-anterior")
    public void pagamentoMesAnterior() {
        pagamentoService.updateBySchedule(false);
    }
}
