import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.function.BiConsumer;

/**
 * An implementation of skip lists.
 */
public class SkipList<K, V> implements SimpleMap<K, V> {

  // +-----------+---------------------------------------------------
  // | Constants |
  // +-----------+

  /**
   * The initial height of the skip list.
   */
  static final int INITIAL_HEIGHT = 16;

  // +---------------+-----------------------------------------------
  // | Static Fields |
  // +---------------+

  static Random rand = new Random();

  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * Pointers to all the front elements.
   */
  ArrayList<SLNode<K, V>> front;

  /**
   * The comparator used to determine the ordering in the list.
   */
  Comparator<K> comparator;

  /**
   * The number of values in the list.
   */
  int size;

  /**
   * The current height of the skiplist.
   */
  int height;
  int actualCurrentHeight;

  /**
   * The probability used to determine the height of nodes.
   */
  double prob = 0.5;

  /**
   * An operations counter to check complexity
   */
  int operations = 0;

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new skip list that orders values using the specified comparator.
   */
  public SkipList(Comparator<K> comparator) {
    this.front = new ArrayList<SLNode<K, V>>(INITIAL_HEIGHT);
    for (int i = 0; i < INITIAL_HEIGHT; i++) {
      front.add(null);
    } // for
    this.comparator = comparator;
    this.size = 0;
    this.height = INITIAL_HEIGHT;
    this.actualCurrentHeight = 0;
  } // SkipList(Comparator<K>)

  /**
   * Create a new skip list that orders values using a not-very-clever default comparator.
   */
  public SkipList() {
    this((k1, k2) -> k1.toString().compareTo(k2.toString()));
  } // SkipList()

  // +-------------+-------------------------------------------------
  // | Main Method |
  // +-------------+

  public static void main(String[] args) {

  } // main(string[])

  // +-------------------+-------------------------------------------
  // | SimpleMap methods |
  // +-------------------+

  private V addNode(K key, V value, ArrayList<ArrayList<SLNode<K, V>>> update) {
    int newLevel = this.randomHeight();

    if (newLevel > this.height) {
      int diff = newLevel - this.height;
      for (int i = 0; i < diff; i++) {
        this.front.add(null);
      } // for
      this.height = newLevel;
    } // if the new level is greater than the full height

    if (this.actualCurrentHeight < newLevel) {
      for (int i = 0; i < newLevel - this.actualCurrentHeight; i++) {
        update.add(this.front);
      } // for
      this.actualCurrentHeight = newLevel;
    } // if

    SLNode<K, V> newNode = new SLNode<K, V>(key, value, newLevel);
    for (int i = 0; i < newLevel; i++) {
      newNode.next.set(i, update.get(i).get(i));
      update.get(i).set(i, newNode);
    } // for

    this.size++;
    return value;
  } // addNode(K, V, ArrayList<ArrayList<SLNode<K, V>>>)

  @Override
  public V set(K key, V value) {

    ArrayList<ArrayList<SLNode<K, V>>> update = new ArrayList<ArrayList<SLNode<K, V>>>();
    for (int i = 0; i < this.actualCurrentHeight; i++) {
      update.add(this.front);
    } // for

    // If the skip list is currently empty
    if (this.size == 0) {
      return this.addNode(key, value, update);
    } // if the skip list is currently empty

    // If list is not empty
    // Create an ArrayList of references to the first nodes of lesser value for each height

    // Create a reference for the front
    ArrayList<SLNode<K, V>> node = this.front;
    for (int i = this.actualCurrentHeight - 1; i >= 0; i--) {
      while (node.get(i) != null && this.comparator.compare(node.get(i).key, key) < 0) {
        this.operations++;
        node = node.get(i).next;
      } // while the next node doesn't have a null value and the key of the next node is less than
      // key
      update.set(i, node);
    } // for

    // Advance one along the bottom (to the next node)
    SLNode<K, V> finalNode = node.get(0);

    if (finalNode != null && this.comparator.compare(finalNode.key, key) == 0) {
      finalNode.value = value;
      return value;
    } // if you found the same key, change the value at that key
    else {
      return this.addNode(key, value, update);
    } // else
  } // set(K,V)

  @Override
  public V get(K key) {
    if (key == null) {
      throw new NullPointerException("null key");
    } // if

    ArrayList<SLNode<K, V>> node = this.front;
    for (int i = this.actualCurrentHeight - 1; i >= 0; i--) {
      while (node.get(i) != null && this.comparator.compare(node.get(i).key, key) < 0) {
        this.operations++;
        node = node.get(i).next;
      } // while
    } // for

    SLNode<K, V> finalNode = node.get(0);

    if (finalNode == null || finalNode.key != key) {
      return null;
    } // if
    else {
      return finalNode.value;
    } // else
  } // get(K,V)

  @Override
  public int size() {
    return this.size;
  } // size()

  @Override
  public boolean containsKey(K key) {
    ArrayList<SLNode<K, V>> node = this.front;
    for (int i = this.actualCurrentHeight - 1; i >= 0; i--) {
      while (node.get(i) != null && this.comparator.compare(node.get(i).key, key) < 0) {
        node = node.get(i).next;
      } // while
    } // for

    SLNode<K, V> finalNode = node.get(0);

    if (finalNode == null) {
      return false;
    } // if

    return this.comparator.compare(finalNode.key, key) == 0;
  } // containsKey(K)

  @Override
  public V remove(K key) {
    if (this.size == 0) {
      return null;
    } // if
    // If list is not empty
    // Create an ArrayList of references to the first nodes of lesser value for each height
    ArrayList<ArrayList<SLNode<K, V>>> update = new ArrayList<ArrayList<SLNode<K, V>>>();
    for (int i = 0; i < this.actualCurrentHeight; i++) {
      update.add(null);
    } // for

    // Create a reference for the front
    ArrayList<SLNode<K, V>> node = this.front;
    for (int i = this.actualCurrentHeight - 1; i >= 0; i--) {
      while (node.get(i) != null && this.comparator.compare(node.get(i).key, key) < 0) {
        this.operations++;
        node = node.get(i).next;
      } // while the next node doesn't have a null value and the key of the next node is less than
      // key
      update.set(i, node);
    } // for

    // Advance one along the bottom (to the next node)
    SLNode<K, V> finalNode = node.get(0);
    this.operations++;

    if (finalNode != null) {
      if (finalNode != null && this.comparator.compare(finalNode.key, key) == 0) {
        for (int i = 0; i < finalNode.next.size(); i++) {
          if (update.get(i) != null) {
            update.get(i).set(i, finalNode.next.get(i));
          } // if
        } // for
        this.size--;
        return finalNode.value;
      } // if
    } // if

    return null;
  } // remove(K)

  @Override
  public Iterator<K> keys() {
    return new Iterator<K>() {
      Iterator<SLNode<K, V>> nit = SkipList.this.nodes();

      @Override
      public boolean hasNext() {
        return nit.hasNext();
      } // hasNext()

      @Override
      public K next() {
        return nit.next().key;
      } // next()

      @Override
      public void remove() {
        nit.remove();
      } // remove()
    };
  } // keys()

  @Override
  public Iterator<V> values() {
    return new Iterator<V>() {
      Iterator<SLNode<K, V>> nit = SkipList.this.nodes();

      @Override
      public boolean hasNext() {
        return nit.hasNext();
      } // hasNext()

      @Override
      public V next() {
        return nit.next().value;
      } // next()

      @Override
      public void remove() {
        nit.remove();
      } // remove()
    };
  } // values()

  @Override
  public void forEach(BiConsumer<? super K, ? super V> action) {
    Iterator<SLNode<K, V>> iter = this.nodes();
    while (iter.hasNext()) {
      SLNode<K, V> node = iter.next();
      action.accept(node.key, node.value);
    }
  } // forEach

  // +----------------------+----------------------------------------
  // | Other public methods |
  // +----------------------+

  /**
   * Dump the tree to some output location.
   */
  public void dump(PrintWriter pen) {
    // Forthcoming
  } // dump(PrintWriter)

  // +---------+-----------------------------------------------------
  // | Helpers |
  // +---------+

  /**
   * Pick a random height for a new node.
   */
  int randomHeight() {
    int result = 1;
    while (rand.nextDouble() < prob) {
      result = result + 1;
    }
    return result;
  } // randomHeight()

  /**
   * Get an iterator for all of the nodes. (Useful for implementing the other iterators.)
   */
  Iterator<SLNode<K, V>> nodes() {
    return new Iterator<SLNode<K, V>>() {

      /**
       * A reference to the next node to return.
       */
      SLNode<K, V> next = SkipList.this.front.get(0);

      @Override
      public boolean hasNext() {
        return this.next != null;
      } // hasNext()

      @Override
      public SLNode<K, V> next() {
        if (this.next == null) {
          throw new IllegalStateException();
        }
        SLNode<K, V> temp = this.next;
        this.next = this.next.next.get(0);
        return temp;
      } // next();
    }; // new Iterator
  } // nodes()

  // +---------+-----------------------------------------------------
  // | Helpers |
  // +---------+

  public int getOperations() {
    return this.operations;
  } // getOperations()

  public void refreshOperations() {
    this.operations = 0;
  } // refreshOperations()

} // class SkipList


/**
 * Nodes in the skip list.
 */
class SLNode<K, V> {

  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The key.
   */
  K key;

  /**
   * The value.
   */
  V value;

  /**
   * Pointers to the next nodes.
   */
  ArrayList<SLNode<K, V>> next;

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new node of height n with the specified key and value.
   */
  public SLNode(K key, V value, int n) {
    this.key = key;
    this.value = value;
    this.next = new ArrayList<SLNode<K, V>>(n);
    for (int i = 0; i < n; i++) {
      this.next.add(null);
    } // for
  } // SLNode(K, V, int)

  // +---------+-----------------------------------------------------
  // | Methods |
  // +---------+

} // SLNode<K,V>
