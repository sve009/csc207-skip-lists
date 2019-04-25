import java.util.Iterator;

public class SkipListExpt {
  public static void main(String[] args) {
    SkipList<Integer, String> strings = new SkipList<Integer, String>();
    SkipList<Integer, Integer> integers = new SkipList<Integer, Integer>();

    strings.set(5, "Hello");
    printSL(strings);
    strings.set(7, "World");
    printSL(strings);
    strings.set(0, "Ayy");
    printSL(strings);
    strings.remove(5);
    printSL(strings);
    strings.remove(0);
    printSL(strings);
    strings.remove(7);
    printSL(strings);
    System.out.println(strings.getOperations());

    for (int j = 0; j < 5; j++) {
        for (int i = 0; i < 100; i++) {
            integers.set(i, i + 1);
        } // for
        System.out.println("Set 100: " + integers.getOperations());
        integers.refreshOperations();


        for (int i = 99; i >= 0; i--) {
            integers.remove(i);
        }
        System.out.println("Remove 100: " + integers.getOperations());
        integers.refreshOperations();
    }

    for (int j = 0; j < 5; j++) {
        for (int i = 0; i < 1000; i++) {
            integers.set(i, i + 1);
        } // for
        System.out.println("Set 1000: " + integers.getOperations());
        integers.refreshOperations();

        for (int i = 999; i >= 0; i--) {
            integers.remove(i);
        }
        System.out.println("Remove 1000: " + integers.getOperations());
        integers.refreshOperations();
    }

    for (int j = 0; j < 5; j++) {
        for (int i = 0; i < 10000; i++) {
            integers.set(i, i + 1);
        } // for
        System.out.println("Set 10000: " + integers.getOperations());
        integers.refreshOperations();

        for (int i = 9999; i >= 0; i--) {
            integers.remove(i);
        }
        System.out.println("Remove 10000: " + integers.getOperations());
    }

    for (int j = 0; j < 5; j++) {
        for (int i = 0; i < 100000; i++) {
            integers.set(i, i + 1);
        } // for
        System.out.println("Set 100000: " + integers.getOperations());
        integers.refreshOperations();

        for (int i = 99999; i >= 0; i--) {
            integers.remove(i);
        }
        System.out.println("Remove 100000: " + integers.getOperations());
    }

    for (int j = 0; j < 5; j++) {
        for (int i = 0; i < 1000000; i++) {
            integers.set(i, i + 1);
        } // for
        System.out.println("Set 1000000: " + integers.getOperations());
        integers.refreshOperations();

        for (int i = 999999; i >= 0; i--) {
            integers.remove(i);
        }
        System.out.println("Remove 1000000: " + integers.getOperations());
    }
    
  } // main(String[])

  public static <K, V> void printSL(SkipList<K, V> lst) {
    Iterator<SLNode<K, V>> iter = lst.nodes();
    while (iter.hasNext()) {
      SLNode<K, V> node = iter.next();
      System.out.print("[" + node.key + ":" + node.value + ":" + node.next.size() + "] ");
    } // while
    System.out.println();
  } // printSL(SkipList<K, V>)
} // class SkipListExpt


