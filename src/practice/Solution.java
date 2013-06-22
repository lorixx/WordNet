package practice;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.TreeMap;


/**
 * Basic idea is use double-linked list to keep track of order, the head is the most recent used/updated node,
 * the tail is the least used/updated node; We also use TreeMap to keep tracks for fast access and enumeration.
 */
public class Solution {

    private int bound;
    private int size;
    private HashMap<String, Node> map;
    private TreeMap<String, Node> table;
    private Node head;
    private Node tail;

    public Solution() {
        this.map = new HashMap<String, Node>();
        this.table = new TreeMap<String, Node>();
        bound = 0;
        size = 0;
        head = tail = null;
    }

    public void setBound(int boundSize) {

        bound = boundSize;
        if (bound == 0) {
            map.clear();
            table.clear();
            head = tail = null;
            size = 0;
        } else {
            while (size > bound) {
                Node nodeToDelete = tail;
                tail.prev.next = null;
                tail = tail.prev;
                size--;
                map.remove(nodeToDelete.key);
                table.remove(nodeToDelete.key);
            }
        }
    }

    public void set(String key, String value) {
        if (bound == 0 || key == null || value == null || key.length() > 10 || value.length() > 10) return;

        // if exist then update
        if (this.map.containsKey(key)) {
            Node node = this.map.get(key);
            node.value = value;
            moveToHead(key);
        } else
            createNewNode(key, value);
    }

    public String get(String key) {
        if (!map.containsKey(key)) {
            System.out.println("NULL");
            return "NULL";
        }

        String result = map.get(key).value;
        moveToHead(key);
        System.out.println(result);
        return result;
    }

    public String peek(String key) {
        if (!map.containsKey(key)) {
            System.out.println("NULL");
            return "NULL";
        }
        System.out.println(map.get(key).value);
        return map.get(key).value;
    }

    public void dump() {

        for (String key : table.keySet()) {
            System.out.println(key + " " + map.get(key).value);
        }
    }

    private void createNewNode(String key, String value) {
        Node newNode = new Node();
        newNode.key = key;
        newNode.value = value;
        newNode.next = head;

        if (size == 0) {
            head = tail = newNode;
            map.put(key, newNode); //add the new node to map
            table.put(key, newNode);
            size++; // update the size
            return;
        }

        head.prev = newNode;
        head = newNode;
        map.put(key, newNode); //add the new node to map
        table.put(key, newNode);


        if (size < bound) {
            size++; //add the size
        } else if (size == bound) {

            Node oldTail = tail;
            tail = tail.prev;
            tail.next = null;
            map.remove(oldTail.key); // remove from map
            table.remove(oldTail.key);
        }
    }

    private void moveToHead(String key) {
        Node targetNode = map.get(key);
        if (targetNode == head) return;
        if (targetNode == tail) {
            tail = targetNode.prev;
            targetNode.prev.next = null;
            targetNode.prev = null;
            targetNode.next = head;
            head.prev = targetNode;
            head = targetNode;
        } else {
            targetNode.prev.next = targetNode.next;
            targetNode.next.prev = targetNode.prev;
            targetNode.prev = null;
            targetNode.next = head;
            head.prev = targetNode;
            head = targetNode;
        }
    }

    private class Node {
        public String key;
        public String value;
        public Node prev;
        public Node next;
    }

    public void test() {
        Solution lruCache = new Solution();
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

        System.out.println("Peek f is " + lruCache.peek("f"));
        lruCache.dump();
        System.out.println("==================");


        // test 6, get the key should change the order
        System.out.println("Peek f is " + lruCache.get("f"));
        lruCache.dump();
        System.out.println("==================");

        //test 7, set bound size to 0 should remove all
        System.out.println("Set bound to 0:");
        lruCache.setBound(0);
        lruCache.dump();
        System.out.println("==================");

    }

    public static void main(String[] args) {

        Solution solution = new Solution();
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
