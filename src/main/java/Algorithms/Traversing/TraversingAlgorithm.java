package Algorithms.Traversing;

import Algorithms.Algorithm;
import Infrastructure.StateMachine;

public abstract class TraversingAlgorithm implements Algorithm {
    protected String startNodeName;
    protected StateMachine stateMachine;

    public void setStartNode(String startNodeName) {
        this.startNodeName = startNodeName;
    }

    public void setMachine(StateMachine stateMachine){
        this.stateMachine = stateMachine;
    }
}
