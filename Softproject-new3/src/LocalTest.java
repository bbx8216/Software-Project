public class LocalTest {
    public static void main(String[] args) {
        BookmarkList bList = new BookmarkList();


        for (int i=0 ; i< bList.numBookmarks; i++) {
            bList.getBookmark(i).print();
        }
    }
}