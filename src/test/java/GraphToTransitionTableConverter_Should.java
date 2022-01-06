import Infrastructure.StateMachine;
import TransitionTableStuff.GraphToTransitionTableConverter;
import TransitionTableStuff.TransitionTable;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.not;

public class GraphToTransitionTableConverter_Should {
    private final GraphToTransitionTableConverter converter = new GraphToTransitionTableConverter();

    @Test
    public void convert_ReturnEmpty_WhenEmptyStateMachineGiven(){
        var machine = new StateMachine();
        var expected = new TransitionTable();

        var actual = converter.convert(machine);

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    public void convert_ContainNodesFromStateMachine(){
        var machine = new StateMachine();
        machine.addNode("node1");
        machine.addNode("node2");
        var expectedNodes = new HashSet<String>();
        expectedNodes.add("node1");
        expectedNodes.add("node2");

        var actualTable = converter.convert(machine);
        var actualNodes = actualTable.getNodes();

        assertThat(actualNodes)
                .usingRecursiveComparison()
                .isEqualTo(expectedNodes);
    }

    @Test
    public void convert_ContainEdgesFromStateMachine(){
        var machine = new StateMachine();
        machine.addNode("node1");
        machine.addNode("node2");
        machine.addNode("node3");
        machine.addEdge("node1", "node2", "edge1");
        machine.addEdge("node2", "node3", "edge2");

        var expectedEdges = new HashSet<String>();
        expectedEdges.add("edge1");
        expectedEdges.add("edge2");

        var actualTable = converter.convert(machine);
        var actualEdges = actualTable.getEdges();

        assertThat(actualEdges)
                .usingRecursiveComparison()
                .isEqualTo(expectedEdges);
    }

    @Test
    public void convert_ContainTransitionsFromStateMachine(){
        var machine = new StateMachine();
        machine.addNode("node1");
        machine.addNode("node2");
        machine.addNode("node3");
        machine.addEdge("node1", "node2", "edge1");
        machine.addEdge("node2", "node3", "edge2");

        var actualTable = converter.convert(machine);
        var firstTransition = actualTable.getNextCondition("node1", "edge1");
        var secondTransition = actualTable.getNextCondition("node2", "edge2");

        assertThat(firstTransition)
                .isEqualTo("node2");

        assertThat(secondTransition)
                .isEqualTo("node2");
    }

    @Test
    public void convert_NotContainNonExistingTransitions(){
        var machine = new StateMachine();
        machine.addNode("node1");
        machine.addNode("node2");
        machine.addNode("node3");
        machine.addEdge("node1", "node2", "edge1");
        machine.addEdge("node2", "node3", "edge2");

        var actualTable = converter.convert(machine);
        var nonExistingTransition = actualTable.getNextCondition("node1", "edge2");

        assertThat(nonExistingTransition)
                .isNull();
    }
}
