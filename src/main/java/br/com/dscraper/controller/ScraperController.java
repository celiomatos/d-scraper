package br.com.dscraper.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("scraper")
@RestController
@RequestMapping("/scraper")
public class ScraperController {

    @ApiOperation("get mehodo")
    @GetMapping
    public void start() {
        System.out.println("aqui deste lado");
    }
}
