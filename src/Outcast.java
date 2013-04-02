public class Outcast {

    private WordNet wn;
    
    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        wn = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {

        int max = 0;
        String finalOutcast = "";
        for (String noun : nouns) {
            int currentOutcast = 0;
            for (String otherNoun : nouns) {
                if (otherNoun != noun) {
                    currentOutcast += wn.distance(noun, otherNoun);
                }
            }

            if (currentOutcast > max) {
                max = currentOutcast;
                finalOutcast = noun;
            }
        }

        return finalOutcast;
    }

    // for unit testing of this class (such as the one below)
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            String[] nouns = In.readStrings(args[t]);
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }  
}