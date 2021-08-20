public class PriorityQueueTester {
    static Integer[] array;
    public static void main(String[] args) {
        array = new Integer[10];
        for(int i = 0; i < array.length; i++) {
            array[i] = new Integer(i);
        }
        OrderedArrayPriorityQueue<Integer> test = orderedArray();
        insertTest(test, true);
        removeTest(test);
        deleteTest(test);
        peekTest(test);
        containsTest(test);
    }

    private static void insertTest(OrderedArrayPriorityQueue test, boolean clear) {
        test.printArray();
        test.insert(array[1]);
        test.insert(array[0]);
        test.insert(array[5]);
        test.insert(array[4]);
        test.insert(array[2]);
        test.printArray();
        test.insert(array[2]);
        test.insert(array[3]);
        test.insert(array[8]);
        test.insert(array[2]);
        test.printArray();
        if (clear) {
            test.clear();
            test.printArray();
        }
    }
    
    private static void removeTest(OrderedArrayPriorityQueue test) {
        System.out.println(test.remove());
        insertTest(test, false);
        test.remove();
        test.printArray();
        test.remove();
        test.remove();
        test.remove();
        test.printArray();
        test.remove();
        test.remove();
        test.remove();
        test.printArray();
        test.remove();
        test.remove();
        test.remove();
        System.out.println(test.remove());
        test.printArray();
    }
    
    private static void deleteTest(OrderedArrayPriorityQueue test) {
        System.out.println(test.delete(array[2]));
        insertTest(test, false);
        test.delete(array[6]);
        test.printArray();
        test.delete(array[2]);
        test.printArray();
        test.delete(array[0]);
        test.delete(array[8]);
        test.printArray();
        test.delete(array[1]);
        test.delete(array[3]);
        test.delete(array[4]);
        test.delete(array[5]);
        test.printArray();
    }
    
    private static void peekTest(OrderedArrayPriorityQueue test) {
        System.out.println(test.peek());
        insertTest(test, false);
        System.out.println(test.peek());
    }
    
    private static void containsTest(OrderedArrayPriorityQueue test) {
        System.out.println(test.contains(array[2]));
        insertTest(test, false);
        System.out.println(test.contains(array[2]));
        System.out.println(test.contains(array[9]));
    }
    
    private static OrderedArrayPriorityQueue orderedArray() {
        return new OrderedArrayPriorityQueue(10);
    }
}