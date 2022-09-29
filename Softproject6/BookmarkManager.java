import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

//나중에 main 함수에서 new JFrame("Bookmark Manager");해주기
public class BookmarkManager extends JFrame implements MouseListener{

    JButton addBt;
    JButton delBt;
    JButton upBt;
    JButton downBt;
    JButton saveBt;

    BookmarkListPanel bookmarkListPanel;
    BookmarkList bml;

    JTable table;
    DefaultTableModel model;
    JFrame frame;


    public BookmarkManager(){

        super("Bookmark Manager");
        this.bookmarkListPanel = new BookmarkListPanel();
        this.bml = BookmarkListPanel.bookmarkList;
        this.table = bookmarkListPanel.table;
        this.model = bookmarkListPanel.model;

        JPanel buttonSetPanel = new JPanel();
        addBt = new JButton("ADD");
        delBt = new JButton("DELETE");
        upBt = new JButton("UP");
        downBt = new JButton("DOWN");
        saveBt = new JButton("SAVE");

        buttonSetPanel.setLayout(new GridLayout(5,1));

        buttonSetPanel.add(addBt);
        buttonSetPanel.add(delBt);
        buttonSetPanel.add(upBt);
        buttonSetPanel.add(downBt);
        buttonSetPanel.add(saveBt);

        //button에 eventListener 붙이기
        addBt.addMouseListener(this);
        delBt.addMouseListener(this);
        upBt.addMouseListener(this);
        downBt.addMouseListener(this);
        saveBt.addMouseListener(this);

        //table에 eventListener 붙이기
        table.addMouseListener(this);

        add(buttonSetPanel, BorderLayout.EAST);
        add(bookmarkListPanel, BorderLayout.CENTER);
    }

    //MouseListener 관리
    @Override
    public void mouseClicked(MouseEvent e){
        Object src = e.getSource();
        int selectedRow = table.getSelectedRow();
        int selectedCol = table.getSelectedColumn();
        String check = null;
        try{
            check = (String)table.getValueAt(selectedRow, selectedCol);

        } catch(ArrayIndexOutOfBoundsException err){
            System.out.println(err);
        }
        if (src == addBt){
            BookmarkListPanel.BookmarkInfo bookmarkInfo = bookmarkListPanel.new BookmarkInfo();
            bookmarkInfo.setLocationRelativeTo(null);
            model.fireTableDataChanged();

        }
        if(src == delBt){

            deleteRow(selectedRow);

            //bookmarkListPanel.refreshTable();
            //model.fireTableDataChanged();

        }
        if(src ==upBt){

            moveLoc(selectedRow, 0);

        }
        if(src == downBt){
            int selectedIndex = table.getSelectedRow();
            moveLoc(selectedIndex,1);

        }
        if(src == saveBt){
            //지금까지 변경된 정보들 저장
            bml.WriteFile("bookmark-org.txt");
            //BookmarkList.delBookmarkInList(index);
        }
        if(">".equals(check) && src == table){
            //토글 메서드를 실행해서 해당 그룹의 멤버들을 아래의 테이블에 추가해준다.
            //만약 다시 토글 체크 버튼을 누르면 원래대로 돌아가야한다.
            //bookmarkListPanel.toggleOpenPanel();
            table.setValueAt("V", selectedRow, 0);
            toggleOpen(selectedRow,selectedCol);
        }
        if("V".equals(check) && src == table){
            //토글을 닫는 경우엔 그냥 해당 그룹이름이랑 같은 북마크들을 테이블에서 삭제해주는 걸로
            //얘는 데이터베이스인 북마크리스트와는 연동될필요 없다.
            toggleClose(selectedRow,selectedCol);
            table.setValueAt(">", selectedRow, 0);

        }
    }

    //기능 구현


    //행 삭제
    public void deleteRow(int index){
        if(index < 0){
            showMessage("삭제할 행을 선택해 주세요.");
        }
        else{
            String name = (String)table.getValueAt(index, 2);
            String url = (String)table.getValueAt(index, 3);
            String time = (String)table.getValueAt(index, 4);

            //그룹이 있고 이름이 있는 경우라면 북마크 리스트 -그룹 클래스 내부에서 북마크를 삭제 해준다.
            int delThis =-1;
            for(int i=0; i<bml.numBookmarks(); i++){
                System.out.println(i);
                System.out.println(bml.bookmarks.get(i).getName()+","+bml.bookmarks.get(i).getUrl()+","+bml.bookmarks.get(i).getTime());

                if(bml.bookmarks.get(i).getName().equals(name)&&bml.bookmarks.get(i).getUrl().equals(url)&&bml.bookmarks.get(i).getTime().equals(time)){
                    delThis = i;
                    System.out.println(i);
                }
            }

            if(delThis == -1){
                System.out.println("delThis값이 없음");
            }
            else{
                bml.delBookmarkInList(delThis);
                model.removeRow(index);
            }

        }
    }
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(frame, message, "Warning", JOptionPane.INFORMATION_MESSAGE);
    }

    //행 이동
    public void moveLoc(int index, int dir){

        if(index < 0){
            showMessage("이동시킬 행을 선택해 주세요.");
        }else{
            //up
            if (dir == 0){
                if (index > 0) {
                    model.moveRow(index, index, index - 1);
                    table.setRowSelectionInterval(index - 1, index - 1);
                }
            }
            //down
            else if (dir ==1 ){
                if (index < model.getRowCount()-1) {
                    model.moveRow(index, index, index + 1);
                    table.setRowSelectionInterval(index + 1, index + 1);
                }
            }
        }
    }

    //toggle open 기능
    public void toggleOpen(int row, int col){
        String groupName = (String)table.getValueAt(row, col+1);
        //여기서 북마크리스트의 그룹 어레이리스트를 가져오면서 같은 그룹이름이 있는 그룹 북마크들을 전체 테이블에 표시해준다.
        int groupNum = bml.getIndexByName(groupName);
        Group toggleGroup = bml.groups.get(groupNum);
        for(int i=0, j=row+1; i<toggleGroup.bookmarks.size();i++, j++){
            Bookmark toggleBookmark = toggleGroup.bookmarks.get(i);
            Vector<Object> temp = new Vector<>();
            temp.add("");
            temp.add(toggleBookmark.getGroupName());
            temp.add(toggleBookmark.getName());
            temp.add(toggleBookmark.getUrl());
            temp.add(toggleBookmark.getTime());
            temp.add(toggleBookmark.getMemo());
            model.insertRow(j, temp);
        }
    }

    //toggle close 기능
    public void toggleClose(int row, int col){
        //만약 그룹 내부의 북마크가 삭제되었을 경우를 대비해서 지금까지의 토글 내부 그룹의 내용을 새로운 그룹에 저장을 하고

        String groupname = (String)table.getValueAt(row, 1);
        for(int i=row+1; i<table.getRowCount()+1;i++){
            String pointedGroup = (String)table.getValueAt(row+1,1);
            if(groupname.equals(pointedGroup)){
                model.removeRow(row+1);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}

}

