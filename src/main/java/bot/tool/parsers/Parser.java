package bot.tool.parsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;

public abstract class Parser {

    protected String url;
    protected static final int waitingDownloadTime_ms = 10000;

    protected Document getPage() {
        if(url == null) {
            System.out.println("Empty url");
            return null;
        }
        try {
            Document page = Jsoup.parse(new URL(url), waitingDownloadTime_ms);
            return page;
        } catch(IOException e){
            System.out.println("Can't download page form " + url);
            e.printStackTrace();
        }
        return null;
    }

    //abstract public LinkedList<EventRecord> CoursesList();

}
