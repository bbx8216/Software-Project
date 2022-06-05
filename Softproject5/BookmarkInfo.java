import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BookmarkInfo extends JFrame implements MouseListener, KeyListener {
    final String[] labels = {"Group", "Name","URL","MEMO"};
    final JTextField[] fields = new JTextField[4];
    JButton inputBtn;

    public BookmarkInfo(){
        super("Input New Bookmark");
        super.setSize(400,200);
        JPanel p1 = new JPanel(new GridLayout(2,4));
        for(int i=0;i<4;i++) {
            p1.add(new JLabel(labels[i]));
            fields[i]=new JTextField(30);
            p1.add(fields[i]);
        }
        inputBtn = new JButton("Input");
        inputBtn.addMouseListener(this);
        for (int i=0 ; i<4; i++){
            fields[i].addKeyListener(this);
        }

        JPanel p2 = new JPanel(new BorderLayout());
        add(inputBtn,BorderLayout.EAST);
        add(p1, BorderLayout.CENTER);
        setVisible(true);

    }
    //BookmarkListPanel 클래스에 직접적으로 접근해주어서 값을 변화시켜야함!
    BookmarkListPanel bookmarkListPanel = new BookmarkListPanel();
    public void addBookmark() {
        DefaultTableModel model=(DefaultTableModel)bookmarkListPanel.table.getModel();
        String []record=new String[4];
        for(int i=0;i<4;i++) {
            record[i]=fields[i].getText();
        }
        model.addRow(record);

        //모든 TextField 비우기
        for(int i=0;i<4;i++)
            fields[i].setText("");
        fields[0].requestFocus();
    }
    //MouseListener Overrides
    @Override
    public void mouseClicked(MouseEvent e) {
        Object src=e.getSource();
        if(src==inputBtn)
            addBookmark();
    }
    //MouseListener Overrides
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}

    //KeyListener Overrides
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {

    }
}

//main method
/*
public static void main(String[] args){
    BookmarkInfo frame = new BookmarkInfo();
    frame.setSize(400, 80);
    frame.setLocationRelativeTo(null); // Center the frame
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
}

 */