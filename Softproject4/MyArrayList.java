public class MyArrayList<T>{
    T[] elements;//여기에 private 을 쓴다고 생각!
    public static final int CAPACITY = 5;
    int size;

    MyArrayList(){
        this(CAPACITY);
    }

    MyArrayList(int capacity){

        elements = (T[]) new Object[capacity]; //type-casting
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        if(size == 0){
            return true;
        }
        else{
            return false;
        }
    }

    public T get(int index){
        return elements[index];
    }

    public void add(int index, T t){
        size ++;
        if (index >size){
            System.out.println("Error");
        }
        else {
            if (size >= elements.length) {
                T[] temp = (T[])new Object[elements.length * 2];
                System.arraycopy(elements, 0, temp, 0, elements.length);
                elements = temp;
            }
            for (int i = size - 1; i > index; i--) {
                elements[i] = elements[i - 1];
            }
            elements[index] = t;
        }
    }
    public void add(T t){

        if (size >= elements.length) {
            T[] temp = (T[])new Object[elements.length * 2];
            System.arraycopy(elements, 0, temp, 0, elements.length);
            elements = temp;
        }
        elements[size++] = t;
    }

    public T remove(int index){
        if (isEmpty()){
            return null;
        }
        else {
            --size;
            T value = elements[index];
            elements[index] = null;
            for (int i=index; i<size; i++){
                elements[i] = elements[i+1];
            }
            elements[size] =  null;
            return (T) value;
        }
    }

    public void set(int index, T t){
        elements[index] = t;
    }

    public void clear(){
        for (int i =0; i<size ; i++){
            elements[i] = null;
        }
        size =0;
    }
}
