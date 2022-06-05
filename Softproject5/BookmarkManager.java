import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Vector;

//나중에 main 함수에서 new JFrame("Bookmark Manager");해주기
public class BookmarkManager extends JFrame {
    public BookmarkManager(){
        super("Bookmark Manager");
        ButtonSetPanel p1 = new ButtonSetPanel();
        BookmarkListPanel bookmarkListPanel = new BookmarkListPanel();
        add(p1, BorderLayout.EAST);
        add(bookmarkListPanel, BorderLayout.CENTER);
    }
}
class ButtonSetPanel extends JPanel implements MouseListener{
    JButton addBt;
    JButton delBt;
    JButton upBt;
    JButton downBt;
    JButton saveBt;

    ButtonSetPanel(){
        addBt = new JButton("ADD");
        delBt = new JButton("DELETE");
        upBt = new JButton("UP");
        downBt = new JButton("DOWN");
        saveBt = new JButton("SAVE");
        setLayout(new GridLayout(5,1));
        add(addBt);
        add(delBt);
        add(upBt);
        add(downBt);
        add(saveBt);

        addBt.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                JFrame bookmarkInfo = new BookmarkInfo();
                bookmarkInfo.setLocationRelativeTo(null); // Center the frame
            }
        });
        //선택된 행 데이터 삭제
        delBt.addMouseListener();
        //현재 반영된 셀들 저장 후 BookmarkList 클래스에 반영 -> 반영은 BookmarkList 클래스에서 해주기

    }

    @Override
    public void mouseClicked(MouseEvent e){

    }



}
class BookmarkListPanel extends JPanel{
    DefaultTableModel model;
    JTable table;
    JScrollPane scrollPane;
    BookmarkList bml;

    BookmarkListPanel(){

        //모델의 헤더 선언
        String[] header = {"","Group","Name","URL","Created Time","Memo"};
        model = new DefaultTableModel(header, 0);

        table = new JTable(model);
        table.getColumn("").setPreferredWidth(8);

        scrollPane = new JScrollPane(table);

        //출력할 bookmarklist 객체 생성
        bml = new BookmarkList();

        for(int i=0; i<bml.groups.size(); i++){
            Group bookmarkGroup = null;
            bookmarkGroup = bml.groups.get(i);
            Vector<Object> temp = new Vector<>();

            if (bookmarkGroup.bookmarks.size() == 1){
                temp.add("");
                temp.add(bml.bookmarks.get(i).getGroupName());
                temp.add(bml.bookmarks.get(i).getName());
                temp.add(bml.bookmarks.get(i).getTime());
                temp.add(bml.bookmarks.get(i).getUrl());
                temp.add(bml.bookmarks.get(i).getMemo());
                model.addRow(temp);
            }
            else if ("".equals(bookmarkGroup.groupName)){
                continue;
            }
            else{
                temp.add(">");
                temp.add(bookmarkGroup.groupName);
                model.addRow(temp);

            }

        }
        for(int i=0 ; i< bml.blankNameGroup.bookmarks.size(); i++){
            Vector<Object> temp = new Vector<>();
            temp.add("");
            temp.add(bml.blankNameGroup.bookmarks.get(i).getGroupName());
            temp.add(bml.blankNameGroup.bookmarks.get(i).getName());
            temp.add(bml.blankNameGroup.bookmarks.get(i).getTime());
            temp.add(bml.blankNameGroup.bookmarks.get(i).getUrl());
            temp.add(bml.blankNameGroup.bookmarks.get(i).getMemo());
            model.addRow(temp);
        }
        add(scrollPane);
    }
}
