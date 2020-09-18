package bot.tool.parsers;

import bot.tool.records.Record;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.LinkedList;

public class GamesParser extends Parser {

    public GamesParser() {
        url = "https://playisgame.com/khalyava";
    }

    public LinkedList<Record> gamesList() {
        LinkedList<Record> games = new LinkedList<>();

        Document page = getPage();
        if(page == null) return games;
        Elements news = page.selectFirst("div[class=items]").select("div[class=teaser-item]");

        for(Element game: news) {
            //String date = game.child(0).child(0).text();
            String date = game.selectFirst("li[class=element element-itemcreated first last]").text();
            String title = game.selectFirst("a").attr("title");
            String ref = "https://playisgame.com/" + game.selectFirst("a").attr("href");
            games.add(new Record(title, ref, date));
        }

        return games;
    }

}
