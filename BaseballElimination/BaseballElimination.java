
public class BaseballElimination {

    private Queue<String> teams;

    private String[] teamNames; // this is used to index team name from index

    private ST<String, Integer> st; // team name to team number

    private int[] wins;

    private int[] losses;

    private int[] remaining;

    private int[][] againstTable;



    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {

        st = new ST<String, Integer>();
        teams = new Queue<String>();

        In in = new In(filename);
        int numOfTeams = in.readInt();

        teamNames = new String[numOfTeams];
        wins = new int[numOfTeams];
        losses = new int[numOfTeams];
        remaining = new int[numOfTeams];
        againstTable = new int[numOfTeams][numOfTeams];

        for (int i = 0; i < numOfTeams; i++) {
            String teamName = in.readString();
            st.put(teamName, i);
            teams.enqueue(teamName);
            teamNames[i] = teamName;
            wins[i] = in.readInt();
            losses[i] = in.readInt();
            remaining[i] = in.readInt();

            for (int j = 0; j < numOfTeams; j++) {
                againstTable[j][i] = in.readInt();
            }
        }
    }

    // number of teams
    public int numberOfTeams() {
        return teams.size();
    }


    // all teams
    public Iterable<String> teams() {
        return teams;
    }

    // number of wins for given team
    public int wins(String team) {

        if (!st.contains(team))
            throw new IllegalArgumentException();
        else
            return wins[st.get(team)];
    }


    // number of losses for given team
    public int losses(String team) {
        if (!st.contains(team))
            throw new IllegalArgumentException();
        else
            return losses[st.get(team)];      }

    // number of remaining games for given team
    public int remaining(String team) {
        if (!st.contains(team))
            throw new IllegalArgumentException();
        else
            return remaining[st.get(team)];      }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {

        if (!st.contains(team1) || !st.contains(team2))
            throw new IllegalArgumentException();

        else
            return againstTable[st.get(team1)][st.get(team2)];
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {

        if (!st.contains(team))
            throw new IllegalArgumentException();

        // trivial eliminated check
        int targetIndex = st.get(team);
        Queue<String> eliminatedTeams =  new Queue<String>();
        for (int i = 0; i < teams.size(); i++) {
            if (i != targetIndex) {
                if (wins(team) + remaining(team) < wins[i]) {
                     eliminatedTeams.enqueue(teamNames[i]);
                }
            }
        }
        if (eliminatedTeams.size() > 0) {
            //StdOut.println("This is trivial.");
            return true;
        }


        int networkSize = 2 + (teams.size() - 1) + ((teams.size() - 2) * (teams.size() - 1) / 2);
        int sourceIndex = networkSize - 2;
        int sinkIndex = networkSize - 1;

        FlowNetwork fn = new FlowNetwork(networkSize);

        String[] restTeams = new String[teams.size() - 1];
        for (int i = 0, j = 0; i < teams.size(); i++) {
            if (teamNames[i].equals(team))
                continue;
            else {
                restTeams[j] = teamNames[i];
                fn.addEdge(new FlowEdge(j, sinkIndex, wins(team) + remaining(team) - wins(teamNames[i])));
                j++; //update index for restTeam

            }

        }

        int indexForGames = restTeams.length;
        int maxCapacity = 0;
        for (int i = 0; i < restTeams.length; i++) {
            for (int j = i + 1; j < restTeams.length; j++) {
                String leftTeam = restTeams[i];
                String rightTeam = restTeams[j];

                int currentAgainst = against(leftTeam, rightTeam);
                maxCapacity += currentAgainst;
                fn.addEdge(new FlowEdge(sourceIndex, indexForGames, currentAgainst));
                fn.addEdge(new FlowEdge(indexForGames, i, Double.POSITIVE_INFINITY));
                fn.addEdge(new FlowEdge(indexForGames, j, Double.POSITIVE_INFINITY));
                indexForGames++;
            }
        }


        FordFulkerson ff = new FordFulkerson(fn, sourceIndex, sinkIndex);

        if (ff.value() < maxCapacity)
            return true;

        return false;
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {

        if (!st.contains(team))
            throw new IllegalArgumentException();

        // trivial eliminated check
        int targetIndex = st.get(team);
        Queue<String> eliminatedTeams =  new Queue<String>();
        for (int i = 0; i < teams.size(); i++) {
            if (i != targetIndex) {
                if (wins(team) + remaining(team) < wins[i]) {
                    eliminatedTeams.enqueue(teamNames[i]);
                }
            }
        }
        if (eliminatedTeams.size() > 0) return eliminatedTeams;



        int networkSize = 2 + (teams.size() - 1) + ((teams.size() - 2) * (teams.size() - 1) / 2);
        int sourceIndex = networkSize - 2;
        int sinkIndex = networkSize - 1;

        FlowNetwork fn = new FlowNetwork(networkSize);

        String[] restTeams = new String[teams.size() - 1];
        for (int i = 0, j = 0; i < teams.size(); i++) {
            if (teamNames[i].equals(team))
                continue;
            else {
                restTeams[j] = teamNames[i];
                fn.addEdge(new FlowEdge(j, sinkIndex, wins(team) + remaining(team) - wins(teamNames[i])));
                j++; //update index for restTeam

            }

        }

        int indexForGames = restTeams.length;
        int maxCapacity = 0;
        for (int i = 0; i < restTeams.length; i++) {
            for (int j = i + 1; j < restTeams.length; j++) {
                String leftTeam = restTeams[i];
                String rightTeam = restTeams[j];

                int currentAgainst = against(leftTeam, rightTeam);
                maxCapacity += currentAgainst;
                fn.addEdge(new FlowEdge(sourceIndex, indexForGames, currentAgainst));
                fn.addEdge(new FlowEdge(indexForGames, i, Double.POSITIVE_INFINITY));
                fn.addEdge(new FlowEdge(indexForGames, j, Double.POSITIVE_INFINITY));
                indexForGames++;
            }
        }


        FordFulkerson ff = new FordFulkerson(fn, sourceIndex, sinkIndex);

        Queue<String> result = new Queue<String>();
        for (int i = 0; i < restTeams.length; i++) {
            if (ff.inCut(i)) {
                result.enqueue(restTeams[i]);
            }
        }

        if (result.size() > 0) return result;
        else return null;
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);

        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team))
                    StdOut.print(t + " ");
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }


}
