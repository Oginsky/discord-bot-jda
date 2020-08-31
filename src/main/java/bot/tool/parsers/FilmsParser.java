package bot.tool.parsers;

import bot.tool.records.FilmRecord;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.LinkedList;

public class FilmsParser extends Parser {

    private LinkedList<FilmRecord> films;
    private static final String urlPostfix = "#subMenuScrollTo";

    static {
        url = "https://tver.kinoafisha.info/movies/";
    }

    private Document getPage(String siteURL) {
        String tmp = url;
        url = siteURL;
        Document page = getPage();
        url = tmp;
        return page;
    }

    private void parseFilm(Document moviePage, FilmRecord film) {
        Elements theatreRows = moviePage.select("div[class=showtimes_item]");

        for(Element theatreRow: theatreRows) {
            int j = 0, i = 0;
            String theatreName = theatreRow.selectFirst("a.theater_name").text();

            Elements prices = theatreRow.select("span.sessionV2_price");
            Elements seans = theatreRow.select("span.sessionV2_time");
            String[] theatreSeans = new String[seans.size()];
            int[] theatrePrices = new int[prices.size()];

            for(Element sean: seans) {
                theatreSeans[i++] = sean.text();
                if(prices != null && prices.size() > j) {
                    String p = prices.get(j).text();
                    p = p.substring(0, p.length()-2);
                    theatrePrices[j] = Integer.parseInt(p);
                }
            }
            FilmRecord.Theatre theatre = new FilmRecord.Theatre(theatreName, theatreSeans, theatrePrices);
            film.addTheatre(theatre);
        }
    }


    public LinkedList<FilmRecord> filmsList() {
        LinkedList<FilmRecord> films = new LinkedList<>();

        Document page = getPage();
        Elements movies = page.select("div[class=films_content]");

        for(Element movie: movies) {
            FilmRecord film = new FilmRecord();
            film.cinema = movie.selectFirst("span.link_border").text();

            String movieURL = movie.selectFirst("a.films_iconFrame").attr("href");
            String movieTimeURL = movieURL + urlPostfix;
            Document movieTimePage = getPage(movieURL);
            parseFilm(movieTimePage, film);

            films.add(film);
        }

        return films;
    }

}
