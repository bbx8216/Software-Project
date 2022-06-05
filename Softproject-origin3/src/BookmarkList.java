import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

public class BookmarkList {

    Bookmark[] bookmarks = new Bookmark[100];

    public int numBookmarks=0;

    BookmarkList(String filename) {
        try {
            File file = new File(filename);

            FileReader fr = new FileReader(file);
            BufferedReader bufReader = new BufferedReader(fr);
            String line = "";
            while ((line = bufReader.readLine())!=null) {
                if(!line.isBlank()&&!line.startsWith("//")) {
                    createBookmark(line, numBookmarks);
                    numBookmarks++;
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

    public void createBookmark(String line, int num) {
        line = " "+line;
        String[] token = line.split("[,;]");
        String[] data = new String[5];
        for(int i=0; i<5;i++) {
            if(token.length<=i) {
                data[i] = "";
            }
            else {
                data[i]=token[i].trim();
            }
        }

        String pattern = "[\\d]{4}[-][\\d]{2}[-][\\d]{2}[_][\\d]{2}[:][\\d]{2}";
        boolean regex = Pattern.matches(pattern,data[1]);
        if (data[2].isBlank()) {
            System.out.printf("MalformedURLException: wrong URL - No URL ; invalid Bookmark info line:%s \n",line);
        }
        if (regex==false) {
            System.out.printf("Date Format Error -> No Created Time invalid Bookmark info line:%s\n",line);
        }

        bookmarks[num]= new Bookmark(data);
    }

    public void writeFile(String filename) {
        try {
            File file = new File(filename);
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file);
            PrintWriter writer = new PrintWriter(fw);

            for (int i =0; i<numBookmarks;i++) {
                writer.println(bookmarks[i].printFile());
            }
            writer.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public int numBookmarks() {
        return numBookmarks;
    }

    public Bookmark getBookmark(int num) {
        return bookmarks[num];
    }

    public void mergeByGroup() {

        int index = 0;
        Bookmark[] mergedBookmarks = new Bookmark[100];

        for (int i=0 ;i<numBookmarks; i++) {
            if (bookmarks[i].included == 0&&!("".equals(bookmarks[i].getGroupName()))) {
                mergedBookmarks[index++] = bookmarks[i];
                bookmarks[i].included =1;
            }
            for(int j=i+1 ; j<numBookmarks; j++) {

                if (bookmarks[i].getGroupName().equals(bookmarks[j].getGroupName())&&!("".equals(bookmarks[i].getGroupName()))){
                    mergedBookmarks[index++] = bookmarks[j];
                    bookmarks[j].included = 1;
                }
            }
        }
        for(int i= 0;i<numBookmarks;i++) {
            if("".equals(bookmarks[i].getGroupName())&&bookmarks[i].included == 0) {
                mergedBookmarks[index++] = bookmarks[i];
                bookmarks[i].included =1;
            }
        }

        bookmarks = mergedBookmarks;


    }


}
