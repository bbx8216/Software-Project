public class ObjectArrayList extends ObjectArrayListLimitedCapacity {
    ObjectArrayList()
    {
        super();
    }
    public void add(int index, Object o){
        size ++;
        if (index >size){
            System.out.println("Error");
        }
        else {
            if (size >= elements.length) {
                Object[] temp = new Object[elements.length * 2];
                System.arraycopy(elements, 0, temp, 0, elements.length);
                elements = temp;
            }
            for (int i = size - 1; i > index; i--) {
                elements[i] = elements[i - 1];
            }
            elements[index] = o;
        }
    }
}
