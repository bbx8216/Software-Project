import java.util.ArrayList;
//같은 패키지 내에서만 접근할 수 있도록 modifier를 default로 설정하였음.
class Group {
    String groupName;
    ArrayList<Bookmark> bookmarks= new ArrayList<>();

    public Group(String groupName) {
        this.groupName = groupName;
    }

    public void addBookmark(Bookmark bookmark) {
        bookmarks.add(bookmark);
    }

}

