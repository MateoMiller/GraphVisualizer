
import Algorithms.AlgorithmResult;
import Algorithms.Traversing.BfsAlgorithm;
import Algorithms.Traversing.TraversingStep;
import Infrastructure.Edge;
import Infrastructure.StateMachine;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class DfsAlgorithm_Should {
    @Test
    public void solve_Throw_WhenStartNodeWasNotInitializedAndGraphIsNotEmpty(){
        var algorithm = new BfsAlgorithm();
        var machine = new StateMachine();
        algorithm.setMachine(machine);

        assertThrows(Exception.class, algorithm::solve);
    }

    @Test
    public void solve_Throw_WhenGraphWasNotInitialized(){

        var algorithm = new BfsAlgorithm();
        algorithm.setStartNode("1");

        assertThrows(Exception.class, algorithm::solve);
    }

    @Test
    public void solve_ReturnEdgesInLexicalOrder(){
        var algorithm = new BfsAlgorithm();
        algorithm.setStartNode("a");
        var machine = new StateMachine();

        machine.addNode("a");
        machine.addNode("b");
        machine.addNode("c");
        machine.addNode("d");
        machine.addNode("e");

        machine.addEdge("a", "c", "edge1");
        machine.addEdge("a", "b", "edge2");
        machine.addEdge("c", "d", "edge3");
        machine.addEdge("b", "e", "edge4");
        machine.addEdge("b", "d", "edge5");

        var nodes = machine.getNodes();
        var nodeA = nodes.stream().filter(x -> x.name.equals("a")).findFirst().get();
        var nodeB = nodes.stream().filter(x -> x.name.equals("b")).findFirst().get();
        var nodeC = nodes.stream().filter(x -> x.name.equals("c")).findFirst().get();
        var nodeD = nodes.stream().filter(x -> x.name.equals("d")).findFirst().get();
        var nodeE = nodes.stream().filter(x -> x.name.equals("e")).findFirst().get();

        var expectedTraversing = new ArrayList<Edge>();
        expectedTraversing.add(new Edge(nodeA, nodeB, "edge2"));
        expectedTraversing.add(new Edge(nodeB, nodeD, "edge5"));
        expectedTraversing.add(new Edge(nodeB, nodeE, "edge4"));
        expectedTraversing.add(new Edge(nodeA, nodeC, "edge1"));

        var expectedSteps = new ArrayList<TraversingStep>();
        expectedSteps.add(new TraversingStep(expectedTraversing.subList(0, 1)));
        expectedSteps.add(new TraversingStep(expectedTraversing.subList(0, 2)));
        expectedSteps.add(new TraversingStep(expectedTraversing.subList(0, 3)));
        expectedSteps.add(new TraversingStep(expectedTraversing.subList(0, 4)));

        var actualResult = algorithm.solve();

        assertThat(actualResult.steps).
                usingRecursiveComparison()
                .isEqualTo(expectedSteps);
    }
}
