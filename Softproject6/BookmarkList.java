import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.io.IOException;


public class BookmarkList {

    static ArrayList<Bookmark> bookmarks = new ArrayList<>();
    ArrayList<Group> groups;
    ArrayList<String> groupNames;
    Group blankNameGroup=null;

    public int numLine = 0;
    public int blankNameGroupSize;

    BookmarkList(){
        this("bookmark-org.txt");
        this.mergeByGroup();
    }
    BookmarkList(String filename) {
        try {
            File file = new File(filename);

            FileReader fr = new FileReader(file);
            BufferedReader bufReader = new BufferedReader(fr);
            String line = "";
            while ((line = bufReader.readLine())!=null) {
                ++numLine;
                if(!line.isBlank()&&!line.startsWith("//")) {
                    CreateBookmark(line);
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

    public void CreateBookmark(String line) {
        line = " "+line;
        String[] token = line.split("[,;]");
        String[] Data = new String[5];
        for(int i=0; i<5;i++) {
            if(token.length<=i) {
                Data[i] = "";
            }
            else {
                Data[i]=token[i].trim();
            }
        }

        String pattern = "[\\d]{4}[-][\\d]{2}[-][\\d]{2}[_][\\d]{2}[:][\\d]{2}";
        boolean regex = Pattern.matches(pattern,Data[1]);
        if (Data[2].isBlank()) {
            System.out.printf("MalformedURLException: wrong URL - No URL ; invalid Bookmark info line:%s \n",line);
        }
        if (!regex) {
            System.out.printf("Date Format Error -> No Created Time invalid Bookmark info line:%s\n",line);
        }

        bookmarks.add(new Bookmark(Data));
    }

    public void WriteFile(String filename) {
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

    //인스턴스를 생성하지 않고도 다른 클래스에서 쓸 수 있도록 static으로 해주었고 이에따라 어레이리스트 bookmarks 도 static으로 바꾸어주었다.
    public void addBookmarkInList(Bookmark bookmark){
        bookmarks.add(bookmark);
        mergeByGroup();

    }

    public void delBookmarkInList(int index){
        bookmarks.remove(index);
    }

    public int numBookmarks() {
        return bookmarks.size();
    }

    public boolean isExistGroup(String groupName) {
        for(int i=0; i< groupNames.size(); i++) {
            if(groupNames.get(i).equals(groupName)) {
                return true;
            }
        }
        return false;
    }

    public int getIndexByName(String groupName) {
        for(int i=0; i<groups.size(); i++) {
            if(groups.get(i).groupName.equals(groupName)) {
                return i;
            }
        }
        return -1;
    }
    public void mergeByGroup() {
        ArrayList<Bookmark> mergedBookmark = new ArrayList<>();
        groups = new ArrayList<>();
        groupNames = new ArrayList<>();
        blankNameGroup=null;

        for (int i = 0; i < numBookmarks(); i++) {
            String groupName = bookmarks.get(i).getGroupName();

            if (!isExistGroup(groupName)) {
                groups.add(new Group(groupName));
                groupNames.add(groupName);
            }

            int groupIndex = getIndexByName(groupName);
            groups.get(groupIndex).addBookmark(bookmarks.get(i));
        }

        for (int i = 0; i < groups.size(); i++) {
            //group이 있으면
            if (!"".equals(groups.get(i).groupName)) {
                ArrayList<Bookmark> bookmarks = groups.get(i).bookmarks;

                for (int j = 0; j < groups.get(i).bookmarks.size(); j++) {
                    mergedBookmark.add(bookmarks.get(j));
                }
            } else {
                blankNameGroup = groups.get(i);
            }
        }

        blankNameGroupSize = blankNameGroup.bookmarks.size();

        if (blankNameGroup != null) {
            for (int j = 0; j < blankNameGroup.bookmarks.size(); j++) {
                mergedBookmark.add(blankNameGroup.bookmarks.get(j));
            }
        }

        for(int i=0 ; i<bookmarks.size();i++){
            bookmarks.set(i,mergedBookmark.get(i));
        }
    }


}
