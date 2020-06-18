import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

  private Node<Item> head;
  private int size;

  // construct an empty randomized queue
  public RandomizedQueue() {
    head = null;
    size = 0;
  }

  // is the randomized queue empty?
  public boolean isEmpty() {
    return size == 0;
  }

  // return the number of items on the randomized queue
  public int size() {
    return size;
  }

  // add the item
  public void enqueue(Item item) {
    if (item == null) {
      throw new IllegalArgumentException("must not be null");
    }
    Node<Item> node = new Node<>(item);
    if (head == null) {
      head = node;
    } else {
      int position = StdRandom.uniform(size);
      Node<Item> after = head;
      for (int i = 0; i < position; i++) {
        after = after.next;
      }
      node.next = after.next;
      after.next = node;
    }
    size++;
  }

  // remove and return a random item
  public Item dequeue() {
    if (head == null) {
      throw new NoSuchElementException();
    }
    Item value;
    if (head.next == null) {
      value = head.value;
      head = null;
    } else {
      int position = StdRandom.uniform(size);
      Node<Item> previous = null;
      Node<Item> current = head;
      for (int i = 0; i < position; i++) {
        previous = current;
        current = current.next;
      }
      value = current.value;
      if (previous == null) {
        head = current.next;
      } else {
        previous.next = current.next;
      }
    }
    size--;
    return value;
  }

  // return a random item (but do not remove it)
  public Item sample() {
    if (head == null) {
      throw new NoSuchElementException();
    }
    int position = StdRandom.uniform(size);
    Node<Item> node = head;
    for (int i = 0; i < position; i++) {
      node = node.next;
    }
    return node.value;
  }

  // return an independent iterator over items in random order
  public Iterator<Item> iterator() {
    return new Iterator<>() {

      private final int[] order = StdRandom.permutation(size);
      private int index = 0;

      @Override
      public boolean hasNext() {
        return index != size;
      }

      @Override
      public Item next() {
        if (index >= size) {
          throw new NoSuchElementException();
        }
        Node<Item> node = head;
        for (int i = 0; i < order[index]; i++) {
          node = node.next;
        }
        index++;
        return node.value;
      }
    };
  }

  // unit testing (required)
  public static void main(String[] args) {
    RandomizedQueue<String> queue = new RandomizedQueue<>();
    StdOut.println("RandomizedQueue isEmpty?");
    StdOut.println(queue.isEmpty());

    queue.enqueue("one");
    queue.enqueue("two");
    queue.enqueue("three");

    StdOut.println("RandomizedQueue size is");
    StdOut.println(queue.size());

    StdOut.println("RandomizedQueue elements are");
    for (String item: queue) {
      StdOut.println(item);
    }

    StdOut.println("Sample is");
    StdOut.println(queue.sample());

    StdOut.println("RandomizedQueue removed first element");
    StdOut.println(queue.dequeue());
    StdOut.println("RandomizedQueue removed last element");
    StdOut.println(queue.dequeue());

    StdOut.println("RandomizedQueue size is");
    StdOut.println(queue.size());
    StdOut.println("RandomizedQueue elements are");
    for (String item: queue) {
      StdOut.println(item);
    }
    queue.dequeue();
    queue.enqueue("four");
    StdOut.println("RandomizedQueue size is");
    StdOut.println(queue.size());
    StdOut.println("RandomizedQueue elements are");
    for (String item: queue) {
      StdOut.println(item);
    }
  }

  private static class Node<Item> {
    final Item value;
    Node<Item> next;
    Node(Item value) {
      this.value = value;
      next = null;
    }
  }

}