import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * 1. 변수명은 camelCase
 * 2. method는 camelCase
 * 3. class는 CameCase
 */
public class BookmarkList {

    ArrayList<Bookmark> bookmarks = new ArrayList<>();
    ArrayList<Group> groups = new ArrayList<>();
    ArrayList<String> groupNames = new ArrayList<>();

    public int numLine = 0;
    public int blankNameGroupSize;

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

    public int numBookmarks() {
        return bookmarks.size();
    }

    public Bookmark getBookmark(int num) {
        return bookmarks.get(num);
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
    Group blankNameGroup = null;
    public void mergeByGroup() {

        int index = 0;
        ArrayList<Bookmark> mergedBookmark = new ArrayList<>();

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
