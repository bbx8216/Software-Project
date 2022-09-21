import javax.swing.*;
public class LocalTest {
    public static void main(String[] args) {
        //형변환을 frame으로 해주어야한다.

        JFrame frame = new BookmarkManager();
        frame.setSize(800,600);
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        for (int i = 0; i< BookmarkListPanel.bookmarkList.numBookmarks(); i++) {
            BookmarkListPanel.bookmarkList.bookmarks.get(i).print();
        }

    }
}
/**
import java.awt.*;
import java.util.ArrayList;

public class LocalTest {
    public static void main(String[] args) {
        BookmarkListPanel bList = new BookmarkListPanel();
        BookmarkList bml = BookmarkListPanel.bookmarkList;
        for (int i=0 ; i< bml.numBookmarks(); i++) {
            bml.bookmarks.get(i).print();
        }
    }
}
*/
/**
for(int i=0; i<bookmarkList.groups.size(); i++){
        Group bookmarkGroupI = bookmarkList.groups.get(i);
        Vector<Object> v = new Vector<>();
        if(bookmarkGroupI.bookmarks.size() == 1) {
        v.add("");
        v.add(bookmarkGroupI.bookmarks.get(i).getGroupName());
        v.add(bookmarkGroupI.bookmarks.get(i).getName());
        v.add(bookmarkGroupI.bookmarks.get(i).getTime());
        v.add(bookmarkGroupI.bookmarks.get(i).getUrl());
        v.add(bookmarkGroupI.bookmarks.get(i).getMemo());
        model.addRow(v);
        }
        else if ("".equals(bookmarkGroupI.groupName)){
        for(int j =0 ; j<bookmarkGroupI.bookmarks.size(); j++){
        v.add("");
        v.add(bookmarkGroupI.bookmarks.get(j).getGroupName());
        v.add(bookmarkGroupI.bookmarks.get(j).getName());
        v.add(bookmarkGroupI.bookmarks.get(j).getTime());
        v.add(bookmarkGroupI.bookmarks.get(j).getUrl());
        v.add(bookmarkGroupI.bookmarks.get(j).getMemo());
        model.addRow(v);
        }
        v = null;
        }
        else{
        v.add(">");
        v.add(bookmarkGroupI.groupName);
        model.addRow(v);
        }
        }*/