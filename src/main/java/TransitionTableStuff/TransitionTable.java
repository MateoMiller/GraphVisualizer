package TransitionTableStuff;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class TransitionTable {
    private final Map<Pair<String, Character>, String> transitions = new HashMap<>();
    private final Set<String> nodes = new HashSet<>();
    private final Set<Character> alphabet = new HashSet<>();

    public Set<String> getNodes() {
        return nodes;
    }
    public Set<Character> getAlphabet() {
        return alphabet;
    }

    public void addNode(String nodeName) {
        nodes.add(nodeName);
    }

    public void addTransition(String startNode, Character transitionChar, String nextPosition) {
        if (nodes.contains(startNode) && nodes.contains(nextPosition) && alphabet.contains(transitionChar)) {
            Pair<String, Character> pair = new ImmutablePair<>(startNode, transitionChar);
            transitions.put(pair, nextPosition);
        } else {
            throw new RuntimeException("Таких вершин или ребер не существует");
        }
    }

    public void addTransitionChar(Character charName){
        alphabet.add(charName);
    }

    public String getNextCondition(String startNode, Character transitionChar) {
        if (!nodes.contains(startNode) || !alphabet.contains(transitionChar))
            throw new RuntimeException("Таких вершин/символов не существует");
        Pair<String, Character> pair = new ImmutablePair<>(startNode, transitionChar);
        return transitions.getOrDefault(pair, null);
    }
}
