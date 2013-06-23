package practice;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;


public class LRUCache {

    private BoundableLinkedHashMap<String, String> map;    // hash map for fast search
    private TreeMap<String, String> table;

    public LRUCache() {
        this.map = new BoundableLinkedHashMap<String, String>();
        this.table = new TreeMap<String, String>();
    }

    public void setBound(int boundSize) {



        if (boundSize == 0) {
            this.map.clear();
            this.table.clear();
        }

        if (this.map.size() > boundSize) {
            //need to remove old
            int num = this.map.size() - boundSize;
            ArrayList<String> keysToDelete = new ArrayList<String>();

            //BoundableLinkedHashMap<String, String>newMap = new BoundableLinkedHashMap<String, String>();
            //newMap.bound = boundSize;
            Set<String> keys = this.map.keySet();
            for (String key : keys) {
                if (num == 0) {
                    //start adding the rest to new hashmap
                    //newMap.put(key, this.table.get(key));
                    break;

                } else {
                    this.table.remove(key);
                    keysToDelete.add(key);
                    num--;
                }
            }

            for (String key : keysToDelete) {
                this.map.remove(key);
            }
        }
        this.map.bound = boundSize;   //update the bound size
    }

    public void set(String key, String value) {
        if (map.bound == 0 || key == null || value == null || key.length() > 10 || value.length() > 10) return;

        map.put(key, value);
        table.put(key, value);

    }

    public void get(String key) {
        if (!map.containsKey(key)) {
            System.out.println("NULL");
        } else {
            System.out.println(map.get(key));
        }
    }

    public void peek(String key) {
        if (!map.containsKey(key)) {
            System.out.println("NULL");
        } else {
            System.out.println(table.get(key)); //use tree map for get without updating the eldest
        }

    }

    public void dump() {

        for (String key : table.keySet()) {
            System.out.println(key + " " + table.get(key));
        }
    }


    private class BoundableLinkedHashMap<KEY, VALUE> extends LinkedHashMap <KEY, VALUE>{

        public BoundableLinkedHashMap() {
            super(10, 43, true);
        }

        public int bound;
        @Override
        protected boolean removeEldestEntry(Map.Entry eldest) {
            if (this.size() > bound)
                table.remove(eldest.getKey()); //remove from the table

            return this.size() > bound;
        }
    }

    public void test() {
        LRUCache lruCache = new LRUCache();
        lruCache.setBound(1);

        //test 1, set bound to 1;
        lruCache.set("a", "1");
        lruCache.set("b", "2");
        lruCache.dump();
        System.out.println("==================");

        //test 2, set bound to 3, add more data and test lru order after get
        lruCache.setBound(3);
        lruCache.set("c", "3");
        lruCache.set("a", "1");
        lruCache.set("d", "4");

        lruCache.dump();
        System.out.println("==================");

        //test 3, if set bound less than the current size number, then we need to remove the extra;
        lruCache.setBound(5);
        lruCache.set("g", "7");
        lruCache.set("e", "5");
        lruCache.set("f", "6");

        lruCache.dump();
        System.out.println("==================");

        lruCache.setBound(3); //change the bound size, should remove a and d
        lruCache.dump();
        System.out.println("==================");

        //test 4, set value for the existing key and it should become the head
        lruCache.set("g", "9");
        lruCache.dump();
        System.out.println("==================");

        // test 5, peek the key should not change the order

        lruCache.peek("f");
        lruCache.dump();
        System.out.println("==================");


        // test 6, get the key should change the order
        lruCache.get("f");
        lruCache.dump();
        System.out.println("==================");

        //test 7, set bound size to 0 should remove all
        System.out.println("Set bound to 0:");
        lruCache.setBound(0);
        lruCache.dump();
        System.out.println("==================");

    }

    public static void main(String[] args) {

        LRUCache solution = new LRUCache();
        //solution.test();
        try {

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String line = br.readLine();
            int N = Integer.parseInt(line);
            for (int i = 0; i < N; i++) {
                line = br.readLine();
                String[] result = line.split(" ");
                String command = result[0];
                if (command.equals("BOUND")) {
                    int size = Integer.parseInt(result[1]);
                    solution.setBound(size);
                } else if (command.equals("SET")) {
                    String key = result[1];
                    String value = result[2];
                    solution.set(key, value);
                } else if (command.equals("GET")) {
                    String key = result[1];
                    solution.get(key);
                } else if (command.equals("PEEK")) {
                    String key = result[1];
                    solution.peek(key);
                } else if (command.equals("DUMP")) {
                    solution.dump();
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
