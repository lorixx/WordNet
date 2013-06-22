package practice;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.TreeMap;

public class Solution {

    private int bound;
    private int size;
    private TreeMap<String, Node> table;
    private Node head;
    private Node tail;

    public Solution() {
        this.table = new TreeMap<String, Node>();
        bound = 1; //default bound
        size = 0;
        head = tail = null;
    }

    public void setBound(int boundSize) {
        if (boundSize < 1) {
            //throw new IllegalArgumentException("Bound size should not be less than 1.");
            return;
        }

        bound = boundSize;
        if (size > bound) {
            while (size > bound) {
                Node nodeToDelete = tail;
                tail.prev.next = null;
                tail = tail.prev;
                size--;

                table.remove(nodeToDelete.key);
            }
        }
    }

    public void set(String key, String value) {
        if (key.length() > 10)
            //throw new IllegalArgumentException("Key string's size should not exceed 10 characters.");
            return;

        if (value.length() > 10)
            //throw new IllegalArgumentException("Value string's size should not exceed 10 characters.");
            return;

        // if exist then update
        if (this.table.containsKey(key)) {
            Node node = this.table.get(key);
            node.value = value;
            moveToHead(key);
        } else
            createNewNode(key, value);
    }

    public String get(String key) {
        if (!table.containsKey(key)) {
            System.out.println("NULL");
            return "NULL";
        }

        String result = table.get(key).value;
        moveToHead(key);
        System.out.println(result);
        return result;
    }

    public String peek(String key) {
        if (!table.containsKey(key)) {
            System.out.println("NULL");
            return "NULL";
        }
        System.out.println(table.get(key).value);
        return table.get(key).value;
    }

    public void dump() {

        for (String key : table.keySet()) {
            System.out.println(key + " " + table.get(key).value);
        }

//        Node start = head;
//        while(start != null) {
//            System.out.print(start.key + ", ");
//            start = start.next;
//        }

    }

    private void createNewNode(String key, String value) {
        Node newNode = new Node();
        newNode.key = key;
        newNode.value = value;
        newNode.next = head;

        if (size == 0) {
            head = tail = newNode;
            table.put(key, newNode); //add the new node to table
            size++; // update the size
            return;
        }

        head.prev = newNode;
        head = newNode;
        table.put(key, newNode); //add the new node to table

        if (size < bound) {
            size++; //add the size
        } else if (size == bound) {

            Node oldTail = tail;
            tail = tail.prev;
            tail.next = null;
            table.remove(oldTail.key); // remove from table
            oldTail = null; //set to null
        }
    }

    private void moveToHead(String key) {
        Node targetNode = table.get(key);
        if (targetNode == head) return;
        if (targetNode == tail) {
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

    public static void main(String[] args) {

            Solution solution = new Solution();
            while (true) {

                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                try {
                    String line = br.readLine();
                    String [] result = line.split(" ");
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

                } catch (Exception e) {
                    System.out.println(e);
                }


            } //end of while
    }
}



/*
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
System.out.println("==================");*/
