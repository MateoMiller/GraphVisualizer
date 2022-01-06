import Infrastructure.StateMachine;
import TransitionTableStuff.TransitionTable;
import TransitionTableStuff.TransitionTableToGraphConverter;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TransitionTableToGraphConverter_Should {
    public final TransitionTableToGraphConverter converter = new TransitionTableToGraphConverter();

    @Test
    public void convert_ReturnEmpty_TableIsEmpty(){
        var table = new TransitionTable();

        var expected = new StateMachine();
        var actual = converter.convert(table);

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    public void convert_ReturnMachineWithStateFromTable(){
        var table = new TransitionTable();
        table.addNode("node1");
        table.addNode("node2");
        table.addNode("node3");

        var expectedMachine = new StateMachine();
        expectedMachine.addNode("node1");
        expectedMachine.addNode("node2");
        expectedMachine.addNode("node3");

        var actualMachine= converter.convert(table);

        assertThat(actualMachine)
                .usingRecursiveComparison()
                .isEqualTo(expectedMachine);
    }

    @Test
    public void convert_ContainsEdgesThatWereInTransitionTable(){
        var table = new TransitionTable();
        table.addNode("node1");
        table.addNode("node2");
        table.addNode("node3");
        table.addEdge("edge1");
        table.addEdge("edge2");
        table.addTransition("node1", "edge1","node2");
        table.addTransition("node2", "edge2","node3");

        var expectedMachine = new StateMachine();
        expectedMachine.addNode("node1");
        expectedMachine.addNode("node2");
        expectedMachine.addNode("node3");
        expectedMachine.addEdge("node1", "node2", "edge1");
        expectedMachine.addEdge("node2", "node3", "edge2");

        var actualMachine= converter.convert(table);

        assertThat(actualMachine)
                .usingRecursiveComparison()
                .isEqualTo(expectedMachine);
    }
}
