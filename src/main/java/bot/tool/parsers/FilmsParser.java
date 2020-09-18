package bot.tool.parsers;

import bot.tool.records.FilmRecord;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Collections;
import java.util.LinkedList;

public class FilmsParser extends Parser {

    private LinkedList<FilmRecord> films;
    private static final String urlPostfix = "#subMenuScrollTo";
    private String filmName;
    private String day;

    public FilmsParser(String filmName, String day) {
        url = "https://tver.kinoafisha.info/movies/";
        this.filmName = filmName;
        this.day = day;
    }


    private Document getPage(String siteURL) {
        String tmp = url;
        url = siteURL;
        Document page = getPage();
        url = tmp;
        return page;
    }

    private FilmRecord parseFilm(Document moviePage) {
        FilmRecord film = new FilmRecord();
        Elements theatreRows = moviePage.select("div[class=showtimes_item]");

        for(Element theatreRow: theatreRows) {
            int j = 0, i = 0;
            String theatreName = theatreRow.selectFirst("a.theater_name").text();

            Elements prices = theatreRow.select("span.sessionV2_price");
            Elements seans = theatreRow.select("span.sessionV2_time");
            String[] theatreSeans = new String[seans.size()];
            int[] theatrePrices = new int[seans.size()];

            for(Element sean: seans) {
                theatreSeans[i++] = sean.text();
                if(prices != null && prices.size() > j) {
                    String p = prices.get(j).text();
                    p = p.substring(0, p.indexOf(" "));
                    theatrePrices[j++] = Integer.parseInt(p);
                }
                else if(prices == null || prices.isEmpty()) theatrePrices[j++] = -1;

            }
            FilmRecord.Theatre theatre = new FilmRecord.Theatre(theatreName, theatreSeans, theatrePrices);
            film.addTheatre(theatre);
        }
        return film;
    }


    public LinkedList<FilmRecord> filmsList() {

        LinkedList<FilmRecord> films = new LinkedList<>();
        Document page = getPage( (day.isEmpty()) ? url : (url + "?date=" + day) );
        if(page == null) return null;

        Elements movies = page.select("div[class=films_content]");

        for(Element movie: movies) {
            String cinema = movie.selectFirst("span.link_border").text();
            if(!filmName.isEmpty() && !filmName.equalsIgnoreCase(cinema)) continue; // filtred for name

            String movieURL = movie.selectFirst("a.films_iconFrame").attr("href");
            movieURL = "https://tver." + movieURL.substring(movieURL.indexOf("kino"));
            movieURL += ((day.isEmpty()) ?  urlPostfix : "?date="+day); // filtred for day
            Document movieTimePage = getPage(movieURL);

            FilmRecord film = parseFilm(movieTimePage);
            if(film != null) film.cinema = cinema;
            if(film != null) films.add(film);
        }

        return films;
    }

}
