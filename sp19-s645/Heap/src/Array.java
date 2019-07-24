public class Array<E> { //E  ---本质 类型  现在是声明了数组中要使用这个类型e,,盛放的类型是 大E这种数据类型
    private E[] data;// 这个E 是静态数组data 的类型           但是数组逻辑中还没有使用这个
    private int size;
    public Array(int capacity) {
//        data = new E [capacity];
        data = (E[])new Object[capacity];
        size = 0;
    }
    public Array(E[] arr) {
    data = (E[])new Object[arr.length];
    for(int i = 0; i < arr.length; i++) {
        data[i] = arr[i];
        size = arr.length;   //动态数组现在的size就等于传进来数组的length
    }

}
    public Array() {
        this(10);

    }
    public int getCapacity() {
        return data.length;
    }
    public int getSize() {
        return size;
    }
    public boolean isEmpty() {
        return size == 0;
    }
    public void addfirst(E a) {
//        for( int i = size - 1; i >= 0;i--) {
//            data[i + 1] = data[i];
//
//        }
//        data[0] = a;
        insert(0,a);
    }
    public void insert(int index, E a) {
        if (size == data.length) {
            throw new IllegalArgumentException();
        }
        if (index < 0 || index > size) {
            throw new IllegalArgumentException();
        }

        for ( int i = size - 1; i >= index; i--) {
            data[i + 1] = data[i];
        }
        data[index] = a;
        size++;
    }
    public void addLast(E a) {
//        if (size == data.length) {
//            resize();
//        }
//        data[size] = a;
//        size ++;
        insert(size,a);
    }

     E get(int index) { //获取index索引位置的元素
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException();
        }
        return data[index];
    }

    public boolean contains(E e) {
        for (int i = 0; i < size; i++) {
            if (data[i] == e) {
                return true;
            }

        }
            return false;

    }
    public int find(E e) {        //获取元素e所在的索引
        for (int i = 0; i < size; i++) {
            if (data[i] == e) {
                return i;
            }

        }
        return -1;
    }
    public E remove(int index) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException();
        }
        E a = data[index];
        for ( int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }
        data[size - 1] = null;
        size--;
        return a;

    }
    public void removeLast() {
        remove(size - 1);
    }

    public void removeElement(E e) {
        int index = find(e);
        if(index != -1) {
            remove(index);
        }
    }
    public void set(int index, E e) {
//        替换元素
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException();
        }
        data[index] = e;

    }


    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(String.format("Array: size = %d, capacity = %d\n", size, data.length));
        res.append('[');
        for (int i = 0; i < size; i++) {
            res.append(data[i]);

            if (i != size - 1)
                res.append(",");
        }
        res.append(']');
        return res.toString();//toString() 方法返回此对象本身（它已经是一个字符串）。
    }
    public void swap(int i,int j) {
        if(i < 0|| i >= size || j < 0 || j >= size) {
            throw new IllegalArgumentException();
        }
        E t = data[i];
        data[i] = data[j];
        data[j] = t;
    }


public static void main(String[] args) {
        Array arr = new Array(20);
        for(int i = 0; i < 10; i++) {
//            arr[i] = i;
            arr.addLast(i);
            System.out.println(arr);
        }
}



}
