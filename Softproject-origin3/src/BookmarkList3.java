import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class BookmarkList {

    ArrayList<Bookmark> bookmarks = new ArrayList<>();


    BookmarkList(){
        this("bookmark.txt");
        this.mergeByGroup();
    }

    BookmarkList(String filename) {
        try {
            File file = new File(filename);

            FileReader fr = new FileReader(file);
            BufferedReader bufReader = new BufferedReader(fr);
            String line = "";
            while ((line = bufReader.readLine())!=null) {
                if(!line.isBlank()&&!line.startsWith("//")) {
                    createBookmark(line);
                }
            }
            bufReader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Unknown BookmarkList data File");
        }
        catch(IOException e){
            System.out.println(e);
        }
    }

    public void createBookmark(String line) {
        String[] token = line.split("[,;]",-1);
        for(int i=0; i<5;i++) {
            token[i]=token[i].trim();
        }

        String pattern = "[\\d]{4}[-][\\d]{2}[-][\\d]{2}[_][\\d]{2}[:][\\d]{2}";
        boolean regex = Pattern.matches(pattern,token[1]);
        if (token[2].isBlank()) {
            System.out.printf("MalformedURLException: wrong URL - No URL ; invalid Bookmark info line:%s \n",line);
        }
        if (regex==false) {
            System.out.printf("Date Format Error -> No Created Time invalid Bookmark info line:%s\n",line);
        }
        Bookmark tempBookmark =new Bookmark(token);
        bookmarks.add(tempBookmark);
    }

    public void writeFile(String filename) {
        try {
            File file = new File(filename);
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file);
            PrintWriter writer = new PrintWriter(fw);

            for (int i =0; i<bookmarks.size();i++) {
                writer.println(bookmarks.get(i).printFile());
            }
            writer.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public int numBookmarks() {
        return bookmarks.size();
    }

    public Bookmark getBookmark(int num) {
        return bookmarks.get(num);
    }

    ArrayList<Group> groups = new ArrayList<>();
    int groupNameIndex;
    ArrayList<String> groupNames = new ArrayList<>();
    //groupName이 빈칸인 북마크를 정의해준다.
    ArrayList<Group> blankNameGroup = new ArrayList<>();

    public boolean isExistGroup(String groupName){
        for(int i=0; i<groupNames.size(); i++){
            if(groupNames.get(i).equals(groupName)){
                return true;
            }
        }
        return false;
    }

    //그룹이름으로 그룹 어레이리스트에 접근하고자 한다. 즉, 값으로 어레이리스트 접근.
    //arraylist의 indexOf() 메소드는 객체의 구성물 전체를 비교해야함.
    public int getIndexByName(String groupName){
        for(int i=0; i<groupNames.size(); i++){
            if(groupNames.get(i).equals(groupName)){
                return i;
            }
        }
        return -1;
    }

    public void mergeByGroup() {
        ArrayList<Bookmark> mergedBookmark = new ArrayList<>();
        //그룹이름 종류 수집
        for(int i=0; i<bookmarks.size();i++){
            String groupName = bookmarks.get(i).getGroupName();

            //group이 존재하지 않는다면 group arraylist 에 새로운 그룹 이름 추가해준다.
            if(!isExistGroup(groupName)){
                groups.add(new Group(groupName));
                groupNames.add(groupName);
            }

            //하나의 그룹에 속한 북마크 리스트 채워주기
            int groupIndex = getIndexByName(groupName);
            groups.get(groupIndex).addBookmark(bookmarks.get(i));
        }


        // 넣고자 하는 어레이리스트에 차례로 넣어준다.
        for(int i=0; i<groupNames.size(); i++){
            if(!"".equals(groups.get(i).groupName)){
                ArrayList<Bookmark> bookmarks = groups.get(i).bookmarks;
                for(int j=0; j<groups.get(i).bookmarksSize(); j++){
                    mergedBookmark.add(bookmarks.get(j));
                }
            }
            else{
                blankNameGroup = groups.get(i);
            }
        }

        if(blankNameGroup != null){
            for (int j=0; j<blankNameGroup.size() ;j++){
                mergedBookmark.add(blankNameGroup.bookmarks.get(j));
            }
        }

        //안전성 문제 있을 수 있음.
        bookmarks = mergedBookmark;
    }
}

class Group{
    String groupName;
    ArrayList<Bookmark> bookmarks = new ArrayList<>();
    public Group(String groupName){
        this.groupName = groupName;
    }
    public void addBookmark(Bookmark bookmark){
        bookmarks.add(bookmark);
    }
    public String getGroupName(){
        return this.groupName;
    }
    public int bookmarksSize(){
        return this.bookmarks.size();
    }

}
