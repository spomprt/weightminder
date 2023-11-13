package com.spomprt.weightminder.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spomprt.weightminder.cache.UrlShortenerLinksCache;
import com.spomprt.weightminder.domain.Person;
import com.spomprt.weightminder.external.ShortenerClient;
import com.spomprt.weightminder.external.ShortenerRequest;
import com.spomprt.weightminder.external.ShortenerResult;
import com.spomprt.weightminder.external.model.Chart;
import com.spomprt.weightminder.service.mapper.ChartMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChartService {

    @Value("${external.quickchart.url}")
    private String quickChartApi;

    private final ObjectMapper objectMapper;

    private final ShortenerClient shortenerClient;

    private final UrlShortenerLinksCache urlShortenerLinksCache;

    private final PersonService personService;

    //todo причесать код
    public String getChart(Long userId) {
        Person person = personService.get(userId);

        if (person.isNoRecords() || person.getRecordsCount() == 1) {
            return "Недостаточно данных для построения графика.";
        }

        String cachedShortUrl = urlShortenerLinksCache.get(userId);

        if (cachedShortUrl != null) {
            return cachedShortUrl;
        } else {
            Chart chart = ChartMapper.map(person.getLastN(100));

            String chartAsString;
            try {
                chartAsString = objectMapper.writeValueAsString(chart);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            String encodedChart = URLEncoder.encode(chartAsString, StandardCharsets.UTF_8);

            String chartUrl = quickChartApi + encodedChart;

            try {
                log.info("Try to get short url from external service");
                ShortenerResult shortUrlResponse = shortenerClient.getShortLink(new ShortenerRequest(chartUrl));

                String shortUrl = shortUrlResponse.resultUrl();

                urlShortenerLinksCache.put(userId, shortUrl);

                return shortUrl;
            } catch (Exception e) {
                log.error("Cannot get short link.\n{}", e.getMessage());

                return chartUrl;
            }
        }
    }

}
