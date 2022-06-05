class ObjectArrayListLimitedCapacity{

    public static final int CAPACITY = 5;
    int size;
    Object[] elements;

    ObjectArrayListLimitedCapacity(){
        this(CAPACITY);
    }

    ObjectArrayListLimitedCapacity(int CAPACITY){
        elements = new Object[CAPACITY];
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

    public Object get(int index){
        return elements[index];
    }

    public void add(int index, Object o){
        size++;
        if (index >size){
            System.out.println("Error");
        }
        else {
            for(int i=size-1; i> index ; i--){
                elements[i] = elements[i-1];
            }
            elements[index] = o;
        }
    }

    public Object remove(int index){
        if (isEmpty()){
            return null;
        }
        else {
        --size;
        Object value = elements[index];
        elements[index] = null;
        for (int i=index; i<size; i++){
            elements[i] = elements[i+1];
        }
        elements[size] =  null;
        return value;
        }
    }
}