package br.com.dscraper.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@Api("scraper")
@RestController
@RequestMapping("/scraper")
public class ScraperController {

    @ApiOperation("get mehodo")
    @GetMapping
    public void start() {
        log.info("as {} aqui deste lado", new Date());
    }
}
