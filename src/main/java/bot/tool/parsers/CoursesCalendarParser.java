package bot.tool.parsers;

import bot.tool.records.CoursesRecord;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.LinkedList;

public class CoursesCalendarParser extends Parser {

    static {
        url = "https://all-events.ru/events/calendar/theme-is-informatsionnye_tekhnologii/";
        defaultNumbersOfCourses = 20;
    }

    public LinkedList<CoursesRecord> CoursesList() {
        LinkedList<CoursesRecord> coursesList = new LinkedList<>();

        Document page = getPage();
        Elements eventList = page.select("div.event");

        for(Element event : eventList) {
            String title = event.selectFirst("a[class=event-name]").text();
            String type = event.selectFirst("div[class=event-type]").text();
            String date = event.selectFirst("div[class=event-date]").text();
            String place = event.selectFirst("div[class=event-place]").text();
            String ref = "https://all-events.ru" + event.selectFirst("a").attr("href");

            coursesList.add(new CoursesRecord(title, type, date, place, ref));
        }

        return coursesList;
    }
}
