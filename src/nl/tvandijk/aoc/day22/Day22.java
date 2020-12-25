package nl.tvandijk.aoc.day22;

import nl.tvandijk.aoc.common.AoCCommon;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class Day22 extends AoCCommon {
    public static class Game {
        private final Set<State> seenStates = new HashSet<>();
        private State state;
        private int theWinner = 0;

        public Game(State state) {
            this.state = state;
        }

        public void playNormalGame() {
            while (theWinner == 0) {
                state = state.playRound();
                theWinner = state.winner();
            }
        }

        public void playRecursiveGame() {
            while (theWinner == 0) {
                if (seenStates.contains(state)) {
                    theWinner = 1;
                } else {
                    seenStates.add(state);
                    state = state.playRound();
                    theWinner = state.winner();
                }
            }
        }

        public int winner() {
            return theWinner;
        }

        public int score(int player) {
            return state.score(player);
        }
    }

    public abstract static class State {
        protected final List<Integer> deck1;
        protected final List<Integer> deck2;

        protected State(List<Integer> deck1, List<Integer> deck2) {
            this.deck1 = deck1;
            this.deck2 = deck2;
        }

        public abstract State playRound();

        public boolean gameEnded() {
            return winner() != 0;
        }

        public int winner() {
            return deck1.isEmpty() ? 2 : deck2.isEmpty() ? 1 : 0;
        }

        private int score(int player) {
            int res = 0;
            var list = player == 1 ? deck1 : deck2;
            for (int i = 0; i < list.size(); i++) {
                res += list.get(i) * (list.size() - i);
            }
            return res;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            State state = (State) o;
            return deck1.equals(state.deck1) && deck2.equals(state.deck2);
        }

        @Override
        public int hashCode() {
            return Objects.hash(deck1, deck2);
        }

        @Override
        public String toString() {
            return "Deck1 = " + deck1 + " Deck2 = " + deck2;
        }
    }

    public static class NormalGameState extends State {
        public NormalGameState(List<Integer> deck1, List<Integer> deck2) {
            super(deck1, deck2);
        }

        public NormalGameState playRound() {
            int top1 = deck1.get(0);
            int top2 = deck2.get(0);

            var next1 = new ArrayList<>(deck1.subList(1, deck1.size()));
            var next2 = new ArrayList<>(deck2.subList(1, deck2.size()));

            if (top1 > top2) {
                next1.add(top1);
                next1.add(top2);
            } else {
                next2.add(top2);
                next2.add(top1);
            }

            return new NormalGameState(next1, next2);
        }
    }

    public static class RecursiveGameState extends State {

        public RecursiveGameState(List<Integer> deck1, List<Integer> deck2) {
            super(deck1, deck2);
        }

        public RecursiveGameState playRound() {
            int top1 = deck1.get(0);
            int top2 = deck2.get(0);

            var next1 = new ArrayList<>(deck1.subList(1, deck1.size()));
            var next2 = new ArrayList<>(deck2.subList(1, deck2.size()));

            if (top1 <= next1.size() && top2 <= next2.size()) {
                // play recursive
                // only number!
                var sub1 = new ArrayList<>(next1.subList(0, top1));
                var sub2 = new ArrayList<>(next2.subList(0, top2));
                Game subgame = new Game(new RecursiveGameState(sub1, sub2));
                subgame.playRecursiveGame();
                int recursiveWinner = subgame.winner();
                if (recursiveWinner == 1) {
                    next1.add(top1);
                    next1.add(top2);
                } else {
                    next2.add(top2);
                    next2.add(top1);
                }
            } else {
                if (top1 > top2) {
                    next1.add(top1);
                    next1.add(top2);
                } else {
                    next2.add(top2);
                    next2.add(top1);
                }
            }

            return new RecursiveGameState(next1, next2);
        }
    }

    @Override
    public void process(InputStream stream) throws IOException {
        var lexer = new InputLexer(CharStreams.fromStream(stream));
        var parser = new InputParser(new CommonTokenStream(lexer));
        var tree = parser.players();

        var deck1 = tree.player(0).card().stream()
                .map(x -> Integer.parseInt(x.getText()))
                .collect(Collectors.toList());
        var deck2 = tree.player(1).card().stream()
                .map(x -> Integer.parseInt(x.getText()))
                .collect(Collectors.toList());

        Game normalGame = new Game(new NormalGameState(deck1, deck2));
        normalGame.playNormalGame();

        System.out.printf("normal scores: player 1 %d and player 2 %d\n", normalGame.score(1), normalGame.score(2));

        Game recursiveGame = new Game(new RecursiveGameState(deck1, deck2));
        recursiveGame.playRecursiveGame();

        System.out.printf("recursive scores: player 1 %d and player 2 %d\n", recursiveGame.score(1), recursiveGame.score(2));
    }

    public static void main(String[] args) {
        run(Day22::new, "example.txt", "input.txt");
    }
}