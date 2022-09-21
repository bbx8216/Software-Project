import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

class BookmarkListPanel extends JPanel {
    DefaultTableModel model;
    JTable table;
    JScrollPane scrollPane;

    //멤버변수로 생성된 객체를 이용하고 싶은 건데 이렇게 해도 되련지..
    //출력할 bookmarklist 객체 생성
    static BookmarkList bookmarkList = new BookmarkList();


    BookmarkListPanel() {

        //모델의 헤더 선언
        String[] header = {"", "Group", "Name", "URL", "Created Time", "Memo"};
        model = new DefaultTableModel(header, 0);

        table = new JTable(model);
        table.getColumn("").setPreferredWidth(8);

        scrollPane = new JScrollPane(table);

        for (int i = 0; i < bookmarkList.groups.size(); i++) {
            Group bookmarkGroup = bookmarkList.groups.get(i);
            Vector<Object> temp = new Vector<>();
            //System.out.println("북마크 그룹 출력 : " + bookmarkGroup.groupName);

            if (bookmarkGroup.bookmarks.size() == 1 && !"".equals(bookmarkGroup.groupName)) {
                temp.add("");
                temp.add(bookmarkGroup.groupName);
                temp.add(bookmarkGroup.bookmarks.get(0).getName());
                temp.add(bookmarkGroup.bookmarks.get(0).getUrl());
                temp.add(bookmarkGroup.bookmarks.get(0).getTime());
                temp.add(bookmarkGroup.bookmarks.get(0).getMemo());
                model.addRow(temp);
            } else if ("".equals(bookmarkGroup.groupName)) {
                continue;
            } else {
                temp.add(">");
                temp.add(bookmarkGroup.groupName);
                model.addRow(temp);

            }
        }
        for (int i = 0; i < bookmarkList.blankNameGroup.bookmarks.size(); i++) {
            Vector<Object> temp = new Vector<>();
            temp.add("");
            temp.add(bookmarkList.blankNameGroup.bookmarks.get(i).getGroupName());
            temp.add(bookmarkList.blankNameGroup.bookmarks.get(i).getName());
            temp.add(bookmarkList.blankNameGroup.bookmarks.get(i).getUrl());
            temp.add(bookmarkList.blankNameGroup.bookmarks.get(i).getTime());
            temp.add(bookmarkList.blankNameGroup.bookmarks.get(i).getMemo());
            model.addRow(temp);
        }
        add(scrollPane);
    }


    public class BookmarkInfo extends JFrame implements MouseListener {
        final String[] labels = {"Group", "Name", "URL", "MEMO"};
        final JTextField[] fields = new JTextField[4];
        JButton inputBtn;

        public BookmarkInfo() {
            super("Input New Bookmark");
            super.setSize(400, 200);
            JPanel p1 = new JPanel(new GridLayout(2, 4));
            //텍스트필드 위치시키기
            for (int i = 0; i < 4; i++) {
                p1.add(new JLabel(labels[i]));

            }
            for (int i = 0; i < 4; i++) {
                fields[i] = new JTextField(30);
                p1.add(fields[i]);
            }

            inputBtn = new JButton("Input");
            inputBtn.addMouseListener(this);

            JPanel p2 = new JPanel(new BorderLayout());
            add(inputBtn, BorderLayout.EAST);
            add(p1, BorderLayout.CENTER);
            setVisible(true);

        }

        public void addBookmarks() {
            //텍스트 필드 안에 값을 받아와서 바로 새로운 북마크 만들기
            String groupName = fields[0].getText();
            String name = fields[1].getText();
            String url = fields[2].getText();
            String memo = fields[3].getText();
            Bookmark bookmark = null;
            //아무것도 안 넣었을 땐 북마크 생성이 되지 않도록
            if (!("".equals(name)) && !("".equals(url))) {
                bookmark = new Bookmark(groupName, name, url, memo);

            }


            //만들어진 북마크 북마크리스트에 넣기
            if (bookmark != null) {
                bookmarkList.addBookmarkInList(bookmark);
                bookmarkList.mergeByGroup();
                refreshTable();

            }


            //모든 TextField 비우기
            for (int i = 0; i < 4; i++)
                fields[i].setText("");
            fields[0].requestFocus();

            //bookmarkInfo 프레임 종료하기
//        BookmarkManager.refreshTable();

        }

        //MouseListener Overrides
        @Override
        public void mouseClicked(MouseEvent e) {
            Object src = e.getSource();
            if (src == inputBtn) {
                addBookmarks();
            }
        }

        //MouseListener Overrides
        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

    }

    public void refreshTable() {

        DefaultTableModel tableModel = model;
        tableModel.setRowCount(0);
        //모델의 헤더 선언
        String[] header = {"", "Group", "Name", "URL", "Created Time", "Memo"};
        tableModel = new DefaultTableModel(header, 0);

        table = new JTable(tableModel);
        table.getColumn("").setPreferredWidth(8);

        //scrollPane = new JScrollPane(table);

        for (int i = 0; i < bookmarkList.groups.size(); i++) {
            Group bookmarkGroup = bookmarkList.groups.get(i);
            Vector<Object> temp = new Vector<>();

            if (bookmarkGroup.bookmarks.size() == 1 && !"".equals(bookmarkGroup.groupName)) {
                temp.add("");
                temp.add(bookmarkGroup.groupName);
                temp.add(bookmarkGroup.bookmarks.get(0).getName());
                temp.add(bookmarkGroup.bookmarks.get(0).getUrl());
                temp.add(bookmarkGroup.bookmarks.get(0).getTime());
                temp.add(bookmarkGroup.bookmarks.get(0).getMemo());
                model.addRow(temp);
            } else if ("".equals(bookmarkGroup.groupName)) {
                continue;
            } else {
                temp.add(">");
                temp.add(bookmarkGroup.groupName);
                model.addRow(temp);

            }

        }

        for (int i = 0; i < bookmarkList.blankNameGroup.bookmarks.size(); i++) {
            Vector<Object> temp = new Vector<>();
            temp.add("");
            temp.add(bookmarkList.blankNameGroup.bookmarks.get(i).getGroupName());
            temp.add(bookmarkList.blankNameGroup.bookmarks.get(i).getName());
            temp.add(bookmarkList.blankNameGroup.bookmarks.get(i).getUrl());
            temp.add(bookmarkList.blankNameGroup.bookmarks.get(i).getTime());
            temp.add(bookmarkList.blankNameGroup.bookmarks.get(i).getMemo());
            model.addRow(temp);
        }
        table.setModel(model);

    }
}