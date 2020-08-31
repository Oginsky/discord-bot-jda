package bot.tool.parsers;

import bot.tool.records.CoursesRecord;
import bot.tool.records.Record;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.LinkedList;

public class TProgerParser extends Parser {

    static {
        url = "https://tproger.ru/events/";
        defaultNumbersOfCourses = 5;
    }

    public LinkedList<CoursesRecord> CoursesList() {
        LinkedList<CoursesRecord> coursesList = new LinkedList<>();

        Document page = getPage();
        Elements bar = page.select("div[id=0]");
        Elements articlesBar = bar.select("a[class!=show-more hoverable]");

        for(Element record: articlesBar) {
            String ref = record.attr("href");
            ref = ref.substring(0, ref.lastIndexOf("/"));
            String title = record.selectFirst("div[class=widget-list-item-caption]").text();
            String[] descriptions = record.selectFirst("div[class=widget-list-description]").text().split(",");
            String date = descriptions[0], place = descriptions[1], price = descriptions[2];
            CoursesRecord er = new CoursesRecord(new Record(title, price), date, place, ref);
            coursesList.add(er);
        }

        return coursesList;
    }

}
