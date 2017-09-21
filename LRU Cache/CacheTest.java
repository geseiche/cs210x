import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

/**
 * Code to test an <tt>LRUCache</tt> implementation.
 */
public class CacheTest {
	
	/*
	 * A simple data provider that provides a string given an integer between 1 and 5
	 */
	private static class NumberGenerator implements DataProvider<Integer, String> {

		private HashMap<Integer, String> map;
		public NumberGenerator(){
			map = new HashMap<Integer, String>();
			map.put(1, "one");
			map.put(2, "two");
			map.put(3, "three");
			map.put(4, "four");
			map.put(5, "five");
		}
		
		public String get(Integer key){
			return map.get(key);
		}	
	}
	
	@Test
	public void leastRecentlyUsedIsCorrect () {
		DataProvider<Integer,String> provider = new NumberGenerator();
		Cache<Integer,String> cache = new LRUCache<Integer,String>(provider, 3);
		
		String one = cache.get(1);
		assertEquals(one, "one");
		assertEquals(cache.getNumMisses(), 1);
		
		String two = cache.get(2);
		assertEquals(two, "two");
		assertEquals(cache.getNumMisses(), 2);
		
		one = cache.get(1);
		assertEquals(one, "one");
		assertEquals(cache.getNumMisses(), 2);
		
		String three = cache.get(3);
		assertEquals(three, "three");
		assertEquals(cache.getNumMisses(), 3);
		
		String four = cache.get(4);
		assertEquals(four, "four");
		assertEquals(cache.getNumMisses(), 4);
		
		String five = cache.get(5);
		assertEquals(five, "five");
		assertEquals(cache.getNumMisses(), 5);
		
		two = cache.get(2);
		assertEquals(two, "two");
		assertEquals(cache.getNumMisses(), 6);
		
	}
}

