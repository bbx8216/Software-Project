import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

/**
 * 1. 변수명은 camelCase
 * 2. method는 camelCase
 * 3. class는 CameCase
 */
public class BookmarkList {

    Bookmark[] bookmarks = new Bookmark[100];

    Group[] groups = new Group[100];
    int groupNameIndex = 0;
    String[] groupNames = new String[100];

    public int numBookmarks=0;
    public int numLine = 0;

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
                    CreateBookmark(line, numBookmarks);
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

    public void CreateBookmark(String line, int num) {
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

        bookmarks[num]= new Bookmark(Data);
    }

    public void WriteFile(String filename) {
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


    // 거슬림.
    public boolean isExistGroup(String groupName) {
        for(int i=0; i<groupNameIndex; i++) {
            if(groupNames[i].equals(groupName)) {
                return true;
            }
        }
        return false;
    }

    public int getIndexByName(String groupName) {
        for(int i=0; i<groupNameIndex; i++) {
            if(groups[i].groupName.equals(groupName)) {
                return i;
            }
        }
        return -1;
    }

    public void mergeByGroup() {
        /**
         * class이름은 대문자로 시작
         * method/ 변수이름은 소문자로 시작해서 중간에 단어가 붙으면 대문자
         */

        /**
         * class안에 있는 변수에 접근할 때 getter를 만들어서
         * bookmark의 included 제거를 판단해볼것
         */


        /**
         * portal -> 특정 함수(해쉬 함수)를 거쳐서 -> 123
         * "portal" -> 2  list[2]에 true가 저장돼있다!! => O(1)
         * Map 자료 구조를 쓰면 ("portal", true) 이런 형태로 저장이 가능한데, 얘가 "portal"을 이용해서 존재여부인 true를 찾는데 걸리는 시간이 O(1)
         */

        int index = 0;
        Bookmark[] mergedBookmark = new Bookmark[100];

        for(int i=0; i<numBookmarks; i++) {
            String groupName = bookmarks[i].getGroupName();

            if(!isExistGroup(groupName)) {
                groups[groupNameIndex] = new Group(groupName);
                groupNames[groupNameIndex] = groupName;
                groupNameIndex++;
            }

            int groupIndex = getIndexByName(groupName);
            groups[groupIndex].addBookmark(bookmarks[i]);
        }

        Group blankNameGroup = null;
        for(int i=0; i<groupNameIndex; i++) {

            if(!"".equals(groups[i].groupName)) {
                Bookmark[] bookmarks = groups[i].bookmarks;

                for(int j=0; j<groups[i].size ; j++) {
                    mergedBookmark[index] = bookmarks[j];
                    index++;
                }
            } else {
                blankNameGroup = groups[i];
            }

        }

        if(blankNameGroup != null) {
            for(int j=0; j<blankNameGroup.size ; j++) {
                mergedBookmark[index] = blankNameGroup.bookmarks[j];
                index++;
            }
        }

        bookmarks = mergedBookmark;

//
//		for (int i=0 ;i<numBookmarks; i++) {
//			if (bookmarks[i].included == 0 && !(bookmarks[i].getGroupName() == "")) {
//				mergedBookmark[index++] = bookmarks[i];
//				bookmarks[i].included =1;
//			}
//			for(int j=i+1 ; j<numBookmarks; j++) {
//
//				if (bookmarks[i].getGroupName().equals(bookmarks[j].getGroupName())&&!(bookmarks[i].getGroupName().equals(""))){
//					mergedBookmark[index++] = bookmarks[j];
//					bookmarks[j].included = 1;
//				}
//			}
//		}
//		for(int i= 0;i<numBookmarks;i++) {
//			if(bookmarks[i].getGroupName()==""&& bookmarks[i].included == 0) {
//				mergedBookmark[index++] = bookmarks[i];
//				bookmarks[i].included =1;
//			}
//		}
//
//		for (int i=0; i<numBookmarks;i++) {
//			bookmarks[i] = mergedBookmark[i];
//		}

    }





}
