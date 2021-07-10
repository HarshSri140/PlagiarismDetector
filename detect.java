package sample;

import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class detect {
    static String finalres="";
    static long n=0;
    static long count=0;
    static double res;
    public static void detector(String file) throws Exception {
        Vector<String> substitute = extractor(file);
        n=substitute.size();
        for (String x : substitute) {
            googletest(x);
            //  parser(link);

        }
        System.out.println(finalres);
       // System.out.println((double)(count*100)/n);
        res =(count*100)/n;
    }

    public static Vector<String> extractor(String text) throws IOException {


       /* File file = new File(path);
        PDDocument document = PDDocument.load(file);*/


        //PDFTextStripper pdfStripper = new PDFTextStripper();

        //  text = text.replaceAll("\\s", "");
        /* String str = pdfStripper.getText(text);*/
        char dl = '.';
        return splitStrings(text, dl);
    }

    static @NotNull
    Vector<String> splitStrings(String str, char dl) {
        StringBuilder word = new StringBuilder();
        str = str + dl;
        int l = str.length();
        Vector<String> substr_list = new Vector<>();
        for (int i = 0; i < l; i++) {
            if (str.charAt(i) != dl) {
                word.append(str.charAt(i));
            } else {
                if (word.length() != 0) {
                    substr_list.add(word.toString());
                }
                word = new StringBuilder();
            }
        }
        return substr_list;
    }

    public static void googletest(String searchTerm) throws Exception {
        System.out.println("Searching for: " + searchTerm);
        String query = "https://www.google.com/search?q=" + URLEncoder.encode(searchTerm+" -youtube", StandardCharsets.UTF_8) + "&num=5";
        String page = getSearchContent(query);
        List<String> links = parseLinks(page);
        System.out.println();
        System.out.println("Results:");
        for (int i = 0,freq=0; i < 5; i++) {
            String link = links.get(i);
            String parsed = parser(link);
            Vector<String> check = splitStrings(parsed, '.');
            for (String y : check) {
                double p = dame_lev(searchTerm, y);
                if(p>60.0) {
                    freq += 1;
                    if (freq == 1) {
                        count += 1;
                        System.out.println(p);
                        finalres += searchTerm + "-" + link + "\n\n";
                    }
                }
            }

        }
    }

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
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static String getSearchContent(String googleSearchQuery) throws Exception {
        final String agent = "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)";
        URL url = new URL(googleSearchQuery);
        final URLConnection connection = url.openConnection();
        connection.setRequestProperty("User-Agent", agent);
        final InputStream stream = connection.getInputStream();
        return getString(stream);
    }

    public static List<String> parseLinks(final String html) {
        List<String> result = new ArrayList<>();
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
            result.add(relHref);
        }
        return result;
    }

    public static String parser(String page) throws IOException {
        //Connection conn = Jsoup.connect(page);
        Document doc = null;
        try{
            doc = Jsoup.connect(page).ignoreHttpErrors(true).ignoreContentType(true).ignoreHttpErrors(true).get();
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
        assert doc != null;
        return doc.body().text();
    }

    public static double dame_lev(String s, String t){
        int n = s.length(); // length of s
        int m = t.length(); // length of t
        if (n == 0) {
            return m;
        }
        else if (m == 0){
            return n;
        }
        int[] p = new int[n+1];
        int[] d = new int[n+1];
        int[] _d;
        int i;
        int j;
        char t_j;
        int cost;
        for (i = 0; i<=n; i++) {
            p[i] = i;
        }
        for (j = 1; j<=m; j++) {
            t_j = t.charAt(j-1);
            d[0] = j;
            for (i=1; i<=n; i++) {
                cost = s.charAt(i-1)==t_j ? 0 : 1;
                d[i] = Math.min(Math.min(d[i-1]+1, p[i]+1), p[i-1]+cost);
            }
            _d = p;
            p = d;
            d = _d;
        }
        var i1 = (s.length() - p[n]) * 100 / s.length();
        return i1; }
}



