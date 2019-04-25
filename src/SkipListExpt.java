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
    
    for (int i = 0; i < 1000; i++) {
      integers.set(i, i + 1);
    } // for
    
    
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


