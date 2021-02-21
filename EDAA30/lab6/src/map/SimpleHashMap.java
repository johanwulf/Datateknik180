package map;

import map.*;

import java.util.Random;
import java.util.Map.*;


public class SimpleHashMap<K,V> implements Map<K,V> {
    public Entry<K,V>[] table;
    
	public static void main(String[] args) {
        Random rand = new Random();
        SimpleHashMap<Integer, Integer> map = new SimpleHashMap<>(10);

        for (int i = 0; i < 500; i++) {
            int rndNumb = rand.nextInt(10000);
            map.put(rndNumb, rndNumb);
        }

        System.out.println(map.show());
	}

    public SimpleHashMap() {
        this.table = (Entry<K, V>[]) new Entry[16];
    }

    public SimpleHashMap(int capacity) {
        this.table = (Entry<K, V>[]) new Entry[capacity];
    }

    @Override
	public V get(Object object) {
        K key = (K) object;
		Entry<K, V> temp = find(index(key), key);

		if (temp != null) {
			return temp.getValue();
        }
        
		return null;
	}

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public V put(K key, V value) {
        Entry<K,V> temp = find(index(key), key);
        V returnValue = null;
        int index = index(key);

        if(temp != null) {
            returnValue = temp.getValue();
			temp.setValue(value);
        } else {
            Entry<K,V> entry = table[index];

            if(entry == null) {
                table[index] = new Entry<K, V>(key, value);
            } else {
                while(entry.next != null) {
                    entry = entry.next;
                }
                entry.next = new Entry<K, V>(key, value);
            }
        }

        if(table.length*0.75 < size()) {
            rehash();
        }

        return returnValue;
    }

    private void rehash() {
        Entry<K, V>[] oldTable = table;
        table = (Entry<K, V>[]) new Entry[table.length * 2];

        for(int i = 0; i < oldTable.length; i++) {
            Entry<K,V> temp = oldTable[i];

            if(temp != null) {
                put(temp.getKey(), temp.getValue());

                while(temp.next != null) {
                    put(temp.next.getKey(), temp.next.getValue());
                    temp = temp.next;
                }
            }
        }
    }

    @Override
    public V remove(Object key) {
        int index = index((K) key);
        Entry<K,V> entry = table[index];

        if(entry == null) {
            return null;
        } 

        Entry<K,V> nextEntry = entry.next;

        if(entry.getKey().equals((K) key)) {
            V value = entry.getValue();
            table[index] = nextEntry;

            return value;
        }

        while(nextEntry != null) {
            if(nextEntry.getKey().equals((K) key)) {
                V value = nextEntry.getValue();
                entry.next = nextEntry.next;
    
                return value;
            }
            entry = entry.next;
            nextEntry = nextEntry.next;
        }
        
        return null;
    }

    @Override
    public int size() {
        int noEntries = 0;
        for(int i = 0; i < table.length; i++) {
            Entry<K,V> entry = table[i];

            while(entry != null) {
                noEntries++;
                entry = entry.next;
            }
        }

		return noEntries;
    }
    
    public String show() {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < table.length; i++) {

            Entry<K,V> entry = table[i];
            sb.append(i + "    ");

            if(entry != null) {
                sb.append(entry.toString() + " ");

                Entry<K,V> temp = entry.next;
                while(temp != null) {
                    sb.append(temp.toString() + " ");
                    temp = temp.next;
                }
            }
            sb.append("\n");
        }

        return sb.toString();

    }

    private int index(K key) {
        return Math.abs(key.hashCode() % table.length);
    }

    private Entry<K,V> find(int index, K key) {
        Entry<K,V> temp = table[index];
        
        while (temp != null) {
            if(temp.getKey().equals(key)) {
                return temp;
            }
            temp = temp.next;
        }

        return null;
    }

    private static class Entry<K,V> implements Map.Entry<K,V> {
        private K key;
        private V value;
        private Entry<K,V> next;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        @Override
        public V setValue(V value) {
            this.value = value;
            return this.value;
        }
        
        @Override
        public String toString() {
            return this.key + "=" + this.value;
        }
    }
}