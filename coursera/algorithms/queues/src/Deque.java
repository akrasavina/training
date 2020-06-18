import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

  private Node<Item> head;
  private Node<Item> tail;
  private int size;

  // construct an empty deque
  public Deque() {
    head = null;
    tail = null;
    size = 0;
  }

  // is the deque empty?
  public boolean isEmpty() {
    return size == 0;
  }

  // return the number of items on the deque
  public int size() {
    return size;
  }

  // add the item to the front
  public void addFirst(Item item) {
    if (item == null) {
      throw new IllegalArgumentException("must not be null");
    }
    Node<Item> newNode = new Node<>(item);
    if (head == null) {
      tail = newNode;
    } else {
      head.prev = newNode;
      newNode.next = head;
    }
    head = newNode;
    size++;
  }

  // add the item to the back
  public void addLast(Item item) {
    if (item == null) {
      throw new IllegalArgumentException("must not be null");
    }
    Node<Item> newNode = new Node<>(item);
    if (tail == null) {
      head = newNode;
    } else {
      tail.next = newNode;
      newNode.prev = tail;
    }
    tail = newNode;
    size++;
  }

  // remove and return the item from the front
  public Item removeFirst() {
    if (head == null) {
      throw new NoSuchElementException("deque is empty");
    }
    Node<Item> deletedNode = head;
    if (head.next == null) {
      head = null;
      tail = null;
    } else {
      head = deletedNode.next;
      head.prev = null;
    }
    size--;
    return deletedNode.value;
  }

  // remove and return the item from the back
  public Item removeLast() {
    if (tail == null) {
      throw new NoSuchElementException("deque is empty");
    }
    Node<Item> deletedNode = tail;
    if (tail.prev == null) {
      head = null;
      tail = null;
    } else {
      tail = deletedNode.prev;
      tail.next = null;
    }
    size--;
    return deletedNode.value;
  }

  // return an iterator over items in order from front to back
  public Iterator<Item> iterator() {
    return new Iterator<>() {
      private Node<Item> current = head;

      @Override
      public boolean hasNext() {
        return current != null;
      }

      @Override
      public Item next() {
        if (current == null) {
          throw new NoSuchElementException();
        }
        Item currentValue = current.value;
        current = current.next;
        return currentValue;
      }
    };
  }

  // unit testing (required)
  public static void main(String[] args) {
    Deque<String> deque = new Deque<>();
    StdOut.println("Deque isEmpty?");
    StdOut.println(deque.isEmpty());

    deque.addFirst("one");
    deque.addFirst("two");
    deque.addLast("three");

    StdOut.println("Deque size is");
    StdOut.println(deque.size());

    StdOut.println("Deque elements are");
    for (String item: deque) {
      StdOut.println(item);
    }
    StdOut.println("Deque removed first element");
    StdOut.println(deque.removeFirst());
    StdOut.println("Deque removed last element");
    StdOut.println(deque.removeLast());

    StdOut.println("Deque size is");
    StdOut.println(deque.size());
    StdOut.println("Deque elements are");
    for (String item: deque) {
      StdOut.println(item);
    }
    deque.removeLast();
    deque.addFirst("four");
    StdOut.println("Deque size is");
    StdOut.println(deque.size());
    StdOut.println("Deque elements are");
    for (String item: deque) {
      StdOut.println(item);
    }

  }

  private static class Node<Item> {
    final Item value;
    Node<Item> next = null;
    Node<Item> prev = null;

    Node(Item value) {
      this.value = value;
      next = null;
      prev = null;
    }
  }

}