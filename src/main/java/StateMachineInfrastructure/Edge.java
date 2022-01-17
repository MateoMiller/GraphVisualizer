package StateMachineInfrastructure;

import java.util.HashSet;
import java.util.stream.Collectors;

public class Edge {
    public Node from, to;
    public HashSet<TransitionChar> transitionChars;

    public Edge(Node from, Node to) {
        this.from = from;
        this.to = to;
        this.transitionChars = new HashSet<>();
    }

    public void addTransitionChar(TransitionChar transitionChar) {
        if (!transitionChars.add(transitionChar))
            throw new RuntimeException("Данный переход уже существует");
    }

    public void removeTransitionChar(TransitionChar transitionChar) {
        if (!transitionChars.remove(transitionChar)){
            throw new RuntimeException("Такого перехода не существует");
        }
    }

    @Override
    public String toString() {
        return "from:= " + from.name + ", to:= " + to.name + ", transitions:=" +
                transitionChars.stream()
                        .map(x -> Character.toString(x.character))
                        .collect(Collectors.joining(","));
    }
}
