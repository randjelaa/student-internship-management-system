package com.example.internships.controller;

import com.example.internships.rss.RssService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rss/internships")
@RequiredArgsConstructor
public class RssController {

    private final RssService rssService;

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public String getInternshipsRss() {
        return rssService.generateInternshipsFeed();
    }
}
