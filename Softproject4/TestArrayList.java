import java.util.ArrayList;

public class TestArrayList {
    public static void main(String[] args){
       MyArrayList list = new MyArrayList();
       System.out.println("--Information--\n"+"Size : "+list.size() + "\tisEmpty? "+list.isEmpty() );
       System.out.println("--Test Start--");

       list.add(0,"String1");
       System.out.println(list.get(0));

        System.out.println("-- 메소드 test--");
        list.set(0,2);
        list.add("String3");
        System.out.println("0번째 set 이후: "+list.get(0));
        System.out.println("1번째: "+list.get(1));

        list.clear();
        System.out.println("Size : "+list.size() + "\tisEmpty ? "+list.isEmpty());
        System.out.println("--Test Finish--");
    }
}
/*


*  MyArrayList<String> list = new MyArrayList<String>();
        System.out.println("--Information--\n"+"Size : "+list.size() + "\tisEmpty? "+list.isEmpty());
        System.out.println("--Test Start--");

        System.out.println("--add 메소드 test--");
        list.add(0,"String1");
        list.add("String2");
        System.out.println(list.get(0));
        System.out.println(list.get(1));
        System.out.println("Size : "+list.size());

        System.out.println("--remove 메소드 test--");
        list.remove(0);
        System.out.println(list.get(0));
        list.remove(0);
        System.out.println("Size : "+list.size() + "\tisEmpty ? "+list.isEmpty());
        System.out.println(list.get(1));

        System.out.println("--Capacity test--");
        list.add(0,"String3");
        list.add(1,"String4");
        list.add(2,"String5");
        list.add(3,"String6");
        list.add(4,"String7");
        System.out.println("Size : "+list.size() + "\tisEmpty ? "+list.isEmpty());
        list.add(5,"String8");
        list.add(6,"String9");
        list.add(7,"String10");
        list.add(8,"String11");
        System.out.println("Size : "+list.size() + "\tisEmpty ? "+list.isEmpty());

        System.out.println("--set 메소드 test--");
        System.out.println(list.get(1));
        list.set(1, "String12");
        System.out.println(list.get(1));
        System.out.println(list.get(2));

        System.out.println("--clear 메소드 test--");
        list.clear();
        System.out.println("Size : "+list.size() + "\tisEmpty ? "+list.isEmpty());


*/