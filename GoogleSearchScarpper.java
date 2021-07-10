package sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GoogleSearchScarpper {

    public static String getString(InputStream is) {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            /** finally block to close the {@link BufferedReader} */
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    public static String getSearchContent(String googleSearchQuery) throws Exception {
        //URL encode string in JAVA to use with google search
        //googleSearchQuery = URLEncoder
          //      .encode(googleSearchQuery, StandardCharsets.UTF_8.toString());
        final String agent = "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)";
        URL url = new URL(googleSearchQuery);
        final URLConnection connection = url.openConnection();
        /**
         * User-Agent is mandatory otherwise Google will return HTTP response
         * code: 403
         */
        connection.setRequestProperty("User-Agent", agent);
        final InputStream stream = connection.getInputStream();
        return getString(stream);
    }

    public static List<String> parseLinks(final String html) throws Exception {
        List<String> result = new ArrayList<String>();
        Document doc = Jsoup.parse(html);
        Elements results = doc.select("a > h3");
        for (Element link : results) {
            Elements parent = link.parent().getAllElements();
            String relHref = parent.attr("href");
            if (relHref.startsWith("/url?q=")) {
                relHref = relHref.replace("/url?q=", "");
            }
            String[] splittedString = relHref.split("&sa=");
            if (splittedString.length > 1) {
                relHref = splittedString[0];
            }
            //System.out.println(relHref);
            result.add(relHref);
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        //System.out.println("Google Search Parser Tutorial");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the search term.");
        String searchTerm = scanner.nextLine();
        System.out.println("Searching for: " + searchTerm);
        String query = "https://www.google.com/search?q=" + URLEncoder.encode(searchTerm,"UTF-8") + "&num=100";
        //System.out.println(query);
        String page = getSearchContent(query);
        List<String> links = parseLinks(page);
        System.out.println();
        System.out.println("Results:");
        for (int i = 0; i < links.size(); i++) {
            System.out.println(links.get(i));
        }
    }
}