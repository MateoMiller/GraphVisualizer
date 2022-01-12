package TransitionTableStuff;

import Infrastructure.StateMachine;
import Infrastructure.TransitionChar;

public class TransitionTableToGraphConverter {
    public StateMachine convert(TransitionTable table){
        var alphabet = table.getAlphabet();
        var nodes = table.getNodes();
        var machine = new StateMachine();
        for (var node: nodes){
            for (var symbol: alphabet){
                var nextPosition = table.getNextCondition(node, symbol);
                if (nextPosition != null)
                    machine.addTransition(node, nextPosition, new TransitionChar(symbol));
            }
        }

        return machine;
    }
}
