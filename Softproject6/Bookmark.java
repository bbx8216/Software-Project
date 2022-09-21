import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Bookmark {
    private String name="";
    private String url="";
    private String time="";
    private String groupName="";
    private String memo="";
    private String bookmark_object="";

    Bookmark(String[] Data){

        this.name=Data[0];
        this.time =Data[1];
        this.url = Data[2];
        this.groupName = Data[3];
        this.memo = Data[4];

    }
    Bookmark(String groupName, String name, String url, String memo){
        String add_time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd_HH:mm"));
        this.time = add_time;
        this.groupName = groupName;
        this.name = name;
        this.url = url;
        this.memo = memo;
    }

    Bookmark(String url){

        String add_time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd_HH:mm"));
        this.time = add_time;
        this.url = url;

    }

    public String printFile() {
        bookmark_object = this.name +","+this.time +","+this.url +","+this.groupName +","+ this.memo;
        return bookmark_object;

    }
    public void print() {
        bookmark_object = this.name +","+this.time+","+this.url+","+this.groupName +","+ this.memo;
        System.out.println(bookmark_object);
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
