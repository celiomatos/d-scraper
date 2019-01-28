package br.com.dscraper.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/scraper")
public class ScraperController {

    @GetMapping
    public void start(){
        System.out.println("aqui deste lado");
    }
}
