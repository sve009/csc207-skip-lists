import java.util.Iterator;

public class SkipListExpt {
    public static void main(String[] args) {
        SkipList<Integer, String> strings = new SkipList<Integer, String>();

        strings.set(5, "Hello");
        printSL(strings);
        strings.set(7, "World");
        printSL(strings);
        strings.set(0, "Ayy");
        printSL(strings);
    }

    public static <K, V> void printSL(SkipList<K, V> lst) {
        Iterator<SLNode<K, V>> iter = lst.nodes();

        while (iter.hasNext()) {
            SLNode<K, V> node = iter.next();

            System.out.print("[" + node.key + ":" + node.value + ":" + node.next.size() + "] ");
        }
        System.out.println();
    }
}


