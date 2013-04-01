public class SAP {

    private Digraph graph;

    private boolean[] markedByV;
    private boolean[] markedByW;

    private int[] minStepsFromV;
    private int[] minStepsFromW;

    private Queue<Integer> changedForV; //caching the changed 
    private Queue<Integer> changedForW; //caching the changed 

    private int shortestLengthSoFar;
    private int ancestor;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        this.graph = new Digraph(G);

        markedByV = new boolean[G.V()];
        markedByW = new boolean[G.V()];

        minStepsFromV = new int[G.V()];
        minStepsFromW = new int[G.V()];

        changedForV = new Queue<Integer>();
        changedForW = new Queue<Integer>();

        shortestLengthSoFar = -1;
        ancestor = -1;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {

        this.calculateResults(v, w);

        return shortestLengthSoFar;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {

        this.calculateResults(v, w);

        return ancestor;

    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {

        // saved all target vertices into stacks
        Stack<Integer> stackForV = new Stack<Integer>();
        Stack<Integer> stackForW = new Stack<Integer>();
        int first;
        int second;
        int ancestorForV;
        int ancestorForW;
        int totalLengthForV = 0;
        int totalLengthForW = 0;

        for (int a : v) {
            stackForV.push(a);
        }
        for (int b : w) {
            stackForW.push(b);
        }

        if (stackForV.size() < 1 || stackForW.size() < 1)
            throw new IndexOutOfBoundsException();

        // 1. calculate ancestor for stackForV

        while (stackForV.size()!= 1) {
            first = stackForV.pop();
            second = stackForV.pop();
            calculateResults(first, second);
            if (shortestLengthSoFar == -1)
                return -1;
            else {
                stackForV.push(ancestor);
                totalLengthForV += shortestLengthSoFar;
            }
        }

        ancestorForV = stackForV.pop();

        // 2. calculate ancestor for stackForW
        while (stackForW.size()!= 1) {
            first = stackForW.pop();
            second = stackForW.pop();
            calculateResults(first, second);
            if (shortestLengthSoFar == -1)
                return -1;
            else {
                stackForW.push(ancestor);
                totalLengthForW += shortestLengthSoFar;
            }
        }

        ancestorForW = stackForW.pop();

        // 3. calculate the last two ancestors for two sub-graphs
        calculateResults(ancestorForV, ancestorForW);

        if (shortestLengthSoFar != -1) {

            StdOut.println("The ancestor is " + ancestor);
            return shortestLengthSoFar + totalLengthForV + totalLengthForW;
        }

        return shortestLengthSoFar;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        // saved all target vertices into stacks
        Stack<Integer> stackForV = new Stack<Integer>();
        Stack<Integer> stackForW = new Stack<Integer>();
        int first;
        int second;
        int ancestorForV;
        int ancestorForW;
        int totalLengthForV = 0;
        int totalLengthForW = 0;

        for (int a : v) {
            stackForV.push(a);
        }
        for (int b : w) {
            stackForW.push(b);
        }

        if (stackForV.size() < 1 || stackForW.size() < 1)
            throw new IndexOutOfBoundsException();

        // 1. calculate ancestor for stackForV

        while (stackForV.size()!= 1) {
            first = stackForV.pop();
            second = stackForV.pop();
            calculateResults(first, second);
            if (shortestLengthSoFar == -1)
                return -1;
            else {
                stackForV.push(ancestor);
                totalLengthForV += shortestLengthSoFar;
            }
        }

        ancestorForV = stackForV.pop();

        // 2. calculate ancestor for stackForW
        while (stackForW.size()!= 1) {
            first = stackForW.pop();
            second = stackForW.pop();
            calculateResults(first, second);
            if (shortestLengthSoFar == -1)
                return -1;
            else {
                stackForW.push(ancestor);
                totalLengthForW += shortestLengthSoFar;
            }
        }

        ancestorForW = stackForW.pop();

        // 3. calculate the last two ancestors for two sub-graphs
        calculateResults(ancestorForV, ancestorForW);

        if (ancestor != -1) {

            StdOut.println("The ancestor is " + ancestor);
            return ancestor;
        }

        return ancestor;
    }

    private void cleanup() {
        //cleanup for V
        while(!changedForV.isEmpty()) {
            int index = changedForV.dequeue();
            markedByV[index] = false;
            minStepsFromV[index] = 0;
        }

        // cleanup for W
        while(!changedForW.isEmpty()) {
            int index = changedForW.dequeue();
            markedByW[index] = false;
            minStepsFromW[index] = 0;
        }
        shortestLengthSoFar = -1;
    }

    private void calculateResults(int v, int w) {

        if (v < 0 || w < 0 || v >= graph.V() || w >= graph.V())
            throw new java.lang.IndexOutOfBoundsException();
        if (v == w) {
            shortestLengthSoFar = 0;
            ancestor = v;
            return;
        }

        BreadthFirstDirectedPaths bfsForV = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths bfsForW = new BreadthFirstDirectedPaths(graph, w);


        this.cleanup();
        Queue<Integer> queueForV = new Queue<Integer>();
        queueForV.enqueue(v);
        markedByV[v] = true;
        minStepsFromV[v] = 0;
        changedForV.enqueue(v);

        Queue<Integer> queueForW = new Queue<Integer>();
        queueForW.enqueue(w);
        markedByW[w] = true;
        minStepsFromW[w] = 0;
        changedForW.enqueue(w);

        int currentStep = 0;

        while (!queueForV.isEmpty() || !queueForW.isEmpty()) {

            currentStep++;
            if (!queueForV.isEmpty()) {
                int currentV = queueForV.dequeue();
                for (int a : graph.adj(currentV)) {

                    if (!markedByV[a]) {
                        //StdOut.println("a: " + a);
                        changedForV.enqueue(a);
                        markedByV[a] = true;
                        minStepsFromV[a] = currentStep; // update current step
                        queueForV.enqueue(a);

                        //check whether it is marked by w
                        if (markedByW[a]) {
                            if (shortestLengthSoFar == -1) {
                                shortestLengthSoFar = minStepsFromV[a] + minStepsFromW[a];
                                ancestor = a;
                            } else if ( minStepsFromV[a] + minStepsFromW[a] < shortestLengthSoFar) {
                                shortestLengthSoFar = minStepsFromV[a] + minStepsFromW[a];
                                ancestor = a;
                            } else if (shortestLengthSoFar!= -1 && minStepsFromV[a] > shortestLengthSoFar) {
                                //stop searching
                                while(!queueForV.isEmpty()) queueForV.dequeue();
                                break;
                            }
                        }
                    }
                } // end of for
            }

            if (!queueForW.isEmpty()) {
                int currentW = queueForW.dequeue();
                for (int b : graph.adj(currentW)) {

                    if (!markedByW[b]) {
                        //StdOut.println("b: " + b);
                        changedForW.enqueue(b);
                        markedByW[b] = true;
                        minStepsFromW[b] = currentStep; // update current step
                        queueForW.enqueue(b);

                        //check whether it is marked by w
                        if (markedByV[b]) {

                            if (shortestLengthSoFar == -1) {
                                shortestLengthSoFar = minStepsFromV[b] + minStepsFromW[b];
                                ancestor = b;
                            } else if ( minStepsFromV[b] + minStepsFromW[b] < shortestLengthSoFar) {
                                shortestLengthSoFar = minStepsFromV[b] + minStepsFromW[b];
                                ancestor = b;
                            } else if (shortestLengthSoFar!= -1 && minStepsFromW[b] > shortestLengthSoFar) {
                                //stop searching
                                while(!queueForW.isEmpty()) queueForW.dequeue();
                                break;
                            }
                        }
                    }
                } // end of for
            }
        }
    }


    public void test1() {
        Bag<Integer> listV = new Bag<Integer>();
        Bag<Integer> listW = new Bag<Integer>();

        listV.add(7); listV.add(8);listV.add(4);
        listW.add(9); listW.add(11);


        StdOut.println(this.length(listV, listW));
        assert this.length(listV, listW) == 8;
    }

    public void test2()  {
        Bag<Integer> listV = new Bag<Integer>();
        Bag<Integer> listW = new Bag<Integer>();

        listV.add(9); listV.add(1);listV.add(2);
        listW.add(9); listW.add(5);


        StdOut.println(this.length(listV, listW));
        assert this.length(listV, listW) == 4 : "This should be 4";
    }

    public void test3()  {
        Bag<Integer> listV = new Bag<Integer>();
        Bag<Integer> listW = new Bag<Integer>();

        listV.add(7); listV.add(1);listV.add(1);
        listW.add(1); listW.add(7);


        StdOut.println(this.length(listV, listW));
        assert this.length(listV, listW) == 2 : "This should be 2";
    }

    // for unit testing of this class (such as the one below)
    public static void main(String[] args) {


        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);

        sap.test1(); sap.test2();sap.test3();

        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}