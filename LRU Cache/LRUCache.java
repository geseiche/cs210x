import java.util.HashMap;

/**
 * An implementation of <tt>Cache</tt> that uses a least-recently-used (LRU)
 * eviction policy.
 */
public class LRUCache<T, U> implements Cache<T, U> {
	
	
	/*
	 * A doubly linked list node
	 */
	private static class Node<T, U> {
		T key;
		U value;
		Node next;
		Node previous;
		
		public Node(U value){
			this.value = value;
		}
	}
	
	private HashMap<T, Node<T,U>> map;
	private DataProvider<T, U> provider;
	private int capacity;
	private int misses;
	
	private Node<T,U> head;
	private Node<T,U> tail;

	
	
	/**
	 * @param provider the data provider to consult for a cache miss
	 * @param capacity the exact number of (key,value) pairs to store in the cache
	 */
	public LRUCache (DataProvider<T, U> provider, int capacity) {
		map = new HashMap<T, Node<T,U>>();
		this.provider = provider;
		this.capacity = capacity;
		misses = 0;
		
		head = null;		
		tail = null;
			
	}
	/*
	 * Adds given key and value to cache
	 */
	private void set(T key, U value){
		// if map already contains key, update it
		if(map.containsKey(key)){
			map.get(key).value = value;
			map.get(key);
			return;
		}
		// if map is at maximum capacity, remove the oldest (least recently used) element
		else if(map.size() >= capacity){
			removeOldest();
		}
		Node<T, U> n = new Node<T, U>(value);
		n.key = key;
		map.put(key, n);
		setHead(n);
		if(tail==null){
			tail = n;
		}
	}
	
	/*
	 * Given a node, removes node from current location in list, and moves it to the head
	 */
	private void setHead(Node n){
		if(head == null){
			head = n;
		} else {
			if(n.previous != null){
				n.previous.next = n.next;
			}
			if(n.next != null){
				n.next.previous = n.previous;
			}
			n.next = head;
			head.previous = n;
			head = n;
		}
		
	}
	/*
	 * Removes least recently used element from the cache
	 */
	private void removeOldest(){
		if(tail == null){
			return;
		} else {
			map.remove(tail.key);
			tail = tail.previous;
			if(tail != null){
				tail.next = null;
			}
		}
	}

	/**
	 * Returns the value associated with the specified key.
	 * @param key the key
	 * @return the value associated with the key
	 */
	public U get (T key) {
		if(map.containsKey(key)){
			return map.get(key).value;
		} else {
		// if cache does not contain key, get it from provider
			misses++;
			U value = provider.get(key);
			set(key, value);
			return value;
		}
		
	}

	/**
	 * Returns the number of cache misses since the object's instantiation.
	 * @return the number of cache misses since the object's instantiation.
	 */
	public int getNumMisses () {
		return misses;
	}
}
