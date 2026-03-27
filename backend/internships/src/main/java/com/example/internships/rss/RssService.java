package com.example.internships.rss;

import com.example.internships.entity.Internship;
import com.example.internships.repository.InternshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RssService {

    private final InternshipRepository internshipRepository;

    @Value("${app.base-url}")
    private String baseUrl;

    public String generateInternshipsFeed() {

        List<Internship> internships = internshipRepository.findAll();

        StringBuilder rss = new StringBuilder();

        rss.append("""
        <?xml version="1.0" encoding="UTF-8" ?>
        <rss version="2.0">
          <channel>
            <title>Internship Opportunities</title>
            <description>Latest available internships</description>
        """);

        rss.append("<link>")
                .append(baseUrl)
                .append("/api/internships</link>");

        for (Internship i : internships) {

            rss.append("<item>");
            rss.append("<title>").append(escapeXml(i.getTitle())).append("</title>");
            rss.append("<description>").append(escapeXml(i.getDescription())).append("</description>");
            rss.append("<guid>").append(i.getId()).append("</guid>");

            DateTimeFormatter formatter = DateTimeFormatter.RFC_1123_DATE_TIME;

            rss.append("<pubDate>")
                    .append(i.getCreatedAt().atZone(ZoneId.systemDefault()).format(formatter))
                    .append("</pubDate>");

            rss.append("</item>");
        }

        rss.append("""
                  </channel>
                </rss>
                """);

        return rss.toString();
    }

    private String escapeXml(String input) {
        if (input == null) return "";

        return input
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }
}