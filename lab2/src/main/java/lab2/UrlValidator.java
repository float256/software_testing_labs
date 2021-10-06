package lab2;

import lab2.entities.WebPageValidationResult;
import lombok.SneakyThrows;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class UrlValidator {
    public static WebPageValidationResult validateUrlsInPage(String url) {
        List<String> allUrls = getAllUrlsInPage(url);
        Map<String, String> validUrls = new HashMap<>();
        Map<String, String> invalidUrls = new HashMap<>();
        allUrls.forEach(currUrl -> processOneUrl(currUrl, validUrls, invalidUrls));
        return WebPageValidationResult.builder()
                .validLinks(validUrls)
                .invalidLinks(invalidUrls).build();
    }

    @SneakyThrows
    private static List<String> getAllUrlsInPage(String baseUrl) {
        Document doc = Jsoup.connect(baseUrl).get();
        List<String> allUrls = getAllAttributeValues(doc, "href");
        allUrls.addAll(getAllAttributeValues(doc, "src"));

        return allUrls.stream().map(
                url -> url.startsWith("http") ? url : createAbsoluteUrlFromRelative(baseUrl, url)
        ).toList();
    }

    private static List<String> getAllAttributeValues(Document htmlDoc, String attribute) {
        Elements elementsWithAttribute = htmlDoc.select(
                String.format("[%s]", attribute)
        );
        return elementsWithAttribute.stream().map(
                element -> element.attr(attribute)
        ).collect(Collectors.toCollection(ArrayList::new));
    }

    @SneakyThrows
    private static String createAbsoluteUrlFromRelative(String baseUrl, String relativeUrl) {
        return new URL(new URL(baseUrl), relativeUrl).toString();
    }

    private static void processOneUrl(String url, Map<String, String> validUrls, Map<String, String> invalidUrls) {
        try {
            Connection.Response response = Jsoup
                    .connect(url)
                    .ignoreContentType(true)
                    .execute();
            if (response.statusCode() == HttpURLConnection.HTTP_OK) {
                validUrls.put(url, response.statusMessage());
            } else {
                invalidUrls.put(url, response.statusMessage());
            }
        } catch (Exception e) {
            invalidUrls.put(url, e.getMessage());
        }
    }
}
