import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Bookmark {
    private String name="";
    private String url="";
    private String time="";
    private String groupName;
    private String memo="";
    private String bookmarks="";
    public int included = 0;

    Bookmark(String[] data){

        this.name=data[0];
        this.url =data[1];
        this.time = data[2];
        this.groupName = data[3];
        this.memo = data[4];

    }
    Bookmark(String name, String url, String time, String groupName, String memo){

        this.name=name;
        this.url =url;
        this.time = time;
        this.groupName = groupName;
        this.memo = memo;

    }


    Bookmark(String url){

        String addTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm"));
        this.time = addTime;
        this.url = url;

    }

    public String printFile() {
        bookmarks = this.name +","+this.time +","+this.url +","+this.groupName +","+ this.memo;
        return bookmarks;

    }
    public void print() {
        bookmarks = this.name +","+this.time +","+this.url +","+this.groupName +","+ this.memo;
        System.out.println(bookmarks);
    }

    public String getName() {
        return this.name;
    }
    public String getUrl() {
        return this.url;
    }
    public String getTime() {
        return this.time;
    }
    public String getGroupName() {
        return this.groupName;
    }
    public String getMemo() {
        return this.memo;
    }
}
