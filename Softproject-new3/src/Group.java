public class Group {
    String groupName;
    int size = 0;
    Bookmark[] bookmarks =new Bookmark[100];

    public Group(String groupName) {
        this.groupName = groupName;
    }

    public void addBookmark(Bookmark bookmark) {
        bookmarks[size] = bookmark;
        size++;
    }

}
