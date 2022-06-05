public class LocalTest2 {
    public static void main(String[] args) {
        BookmarkList2 bList = new BookmarkList2("bookmark.txt");
        bList.mergeByGroup();
        for (int i=0 ; i< Bookmark2.numBookmarks; i++) {
            bList.getBookmark(i).print();
        }
    }
}
