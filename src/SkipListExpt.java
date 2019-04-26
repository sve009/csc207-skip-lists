import java.util.Iterator;
import java.util.Random;

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

    testOperations(100);
    testOperations(1000);
    testOperations(10000);
    testOperations(100000);
    testOperations(1000000);
    testOperations(10000000);
  }

  public static void testOperations(int n) {
    Random rand = new Random();
    SkipList<Integer, Integer> integers = new SkipList<Integer, Integer>();

    System.out.println("Testing size " + n);

    for (int i = 0; i < n; i++) {
      integers.set(i, i);
    }

    int total = 0;

    for (int i = 0; i < 25; i++) {
      integers.refreshOperations();
      int num = rand.nextInt(n); 
      integers.set(num, num);
      total += integers.getOperations();
    } 

    System.out.println("Average set operation number: " + total / 25); 

    total = 0;

    for (int i = 0; i < 25; i++) {
      integers.refreshOperations();
      int num = rand.nextInt(n); 
      integers.get(num);
      total += integers.getOperations();
    } 

    System.out.println("Average get operation number: " + total / 25); 

    total = 0;

    for (int i = 0; i < 25; i++) {
      integers.refreshOperations();
      int num = rand.nextInt(n); 
      integers.remove(num);
      total += integers.getOperations();
      integers.set(num, num);
    } 

    System.out.println("Average remove operation number: " + total / 25); 
  }


  public static <K, V> void printSL(SkipList<K, V> lst) {
    Iterator<SLNode<K, V>> iter = lst.nodes();
    while (iter.hasNext()) {
      SLNode<K, V> node = iter.next();
      System.out.print("[" + node.key + ":" + node.value + ":" + node.next.size() + "] ");
    } // while
    System.out.println();
  } // printSL(SkipList<K, V>)
} // class SkipListExpt


