package Tuples;

public class Pair<T, K> {

	public T element1;
	public K element2;
	
	public Pair(T element1, K element2) {
		
		this.element1 = element1;
		this.element2 = element2;
		
	}
	
	public Pair() {
		this(null, null);
	}
	
}
