package bot.tool.parsers;

import bot.tool.records.CoursesRecord;
import bot.tool.records.Record;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.LinkedList;

public class CoursesParser extends Parser {

    protected LinkedList<CoursesRecord> coursesList;

    static {
        defaultNumbersOfCourses = 25;
    }

    private void parseTProger() {
        url = "https://tproger.ru/events/";

        Document page = getPage();
        if(page == null) return;
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
    }

    private void parseCoursesCalendar() {
        url = "https://all-events.ru/events/calendar/theme-is-informatsionnye_tekhnologii/";

        Document page = getPage();
        if(page == null) return;
        Elements eventList = page.select("div.event");

        for(Element event : eventList) {
            String title = event.selectFirst("a[class=event-name]").text();
            String type = event.selectFirst("div[class=event-type]").text();
            String date = event.selectFirst("div[class=event-date]").text();
            String place = event.selectFirst("div[class=event-place]").text();
            String ref = "https://all-events.ru" + event.selectFirst("a").attr("href");

            coursesList.add(new CoursesRecord(title, type, date, place, ref));
        }
    }

    public LinkedList<CoursesRecord> CoursesList() {
        coursesList = new LinkedList<>();
        parseTProger();
        parseCoursesCalendar();

        return coursesList;
    }
}
