package TransitionTableStuff;

import Infrastructure.StateMachine;

public class GraphToTransitionTableConverter {
    public TransitionTable convert(StateMachine machine) {
        var alphabet = machine.getAlphabet();
        var nodes = machine.getNodes();
        var edges = machine.getEdges();
        var table = new TransitionTable();

        for (var symbol : alphabet) {
            table.addTransitionChar(symbol.character);
        }

        for (var node : nodes) {
            table.addNode(node.name);
        }

        for (var edge : edges) {
            for (var symbol : edge.transitionChars) {
                table.addTransition(edge.from.name, symbol.character, edge.to.name);
            }
        }

        return table;
    }
}
