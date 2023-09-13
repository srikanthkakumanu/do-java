package generics;

class GMPair<K, V> {
    private K key;
    private V value;
    
    public GMPair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }
    public V getValue() {
        return value;
    }
    public void setKey(K key) {
        this.key = key;
    }
    public void setValue(V value) {
        this.value = value;
    }
}
public class GenericMethods2 {
    public static void main(String[] args) {
        GMPair<Integer, String> p1 = new GMPair<>(19, "Hello");
        GMPair<Integer, String> p2 = new GMPair<>(19, "Hello");
        System.out.println(GenericMethods2.<Integer, String>compare(p1, p2));
        System.out.println(GenericMethods2.compare(p1, p2)); // compiler use type inference
    }

    public static <K, V> boolean compare(GMPair<K, V> p1, GMPair<K, V> p2) {
        return (p1.getKey() == p2.getKey() && p1.getValue() == p2.getValue());
    }
}
