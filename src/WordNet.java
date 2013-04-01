public class WordNet {
    
    private ST<String, Integer> nounTable;  // keep track of noun, lookup from String
    private ST<Integer, String> synset;     // keep track of synset from id

    private Digraph graph;
    private SAP sap;
    
    
    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        this.nounTable = new ST<String, Integer>();
        this.synset = new ST<Integer, String>();
        In in = new In(synsets);

        int size = 0;

        while (in.hasNextLine()) {
            size++;
            String currentLine = in.readLine();

            String [] allArgs = currentLine.split(",");
            Integer id = Integer.parseInt(allArgs[0]);

            synset.put(id, allArgs[1]);
            String [] nouns = allArgs[1].split(" ");
            for (String noun : nouns) {
                nounTable.put(noun, id);
            }
        }

        this.graph = new Digraph(size);

        in = new In(hypernyms);

        while (in.hasNextLine()) {
            String currentLine = in.readLine();
            String [] numbers = currentLine.split(",");
            Integer v = Integer.parseInt(numbers[0]);
            Integer w = Integer.parseInt(numbers[1]);

            if (v < 0 || w < 0 || v >= size || w >= size)
                throw new IndexOutOfBoundsException();

            this.graph.addEdge(v, w);
        }

        // check the graph is valid rooted DAG or not
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return this.nounTable;
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return this.nounTable.contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {

        if (!this.isNoun(nounA) || !this.isNoun(nounB))
            throw new IllegalArgumentException();

        return -1;
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!this.isNoun(nounA) || !this.isNoun(nounB))
            throw new IllegalArgumentException();

        return null;
    }

    // for unit testing of this class
    public static void main(String[] args) {
        
    }

    
}