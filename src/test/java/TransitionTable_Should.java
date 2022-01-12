import TransitionTableStuff.TransitionTable;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TransitionTable_Should {

//    @Test
//    public void getTransition_Throws_When_NodeWithGivenNameDoesNotExists(){
//        var table = new TransitionTable();
//        table.addNode("node1");
//        table.addEdge("edge1");
//
//        assertThrows(Exception.class, () -> table.getNextCondition("node2", "edge2"));
//    }
//
//    @Test
//    public void addTransition_Throws_When_NodeWithGivenNameDoesNotExists(){
//        var table = new TransitionTable();
//        table.addNode("node1");
//        table.addEdge("edge1");
//
//        assertThrows(Exception.class, () -> table.addTransition("node2", "edge2", "node33"));
//    }
//
//    @Test
//    public void getTransition_ReturnNull_WhenTransitionWasNotInitialized(){
//        var table = new TransitionTable();
//        table.addNode("node1");
//        table.addEdge("edge1");
//
//        var nextCondition = table.getNextCondition("node1", "edge1");
//
//        assertThat(nextCondition).isNull();
//    }
//
//    @Test
//    public void getTransition_ReturnNewPosition_WhenTransitionExists(){
//        var table = new TransitionTable();
//        table.addNode("node1");
//        table.addNode("node2");
//        table.addEdge("edge1");
//        table.addTransition("node1", "edge1", "node2");
//
//        var nextCondition = table.getNextCondition("node1", "edge1");
//
//        assertThat(nextCondition).isEqualTo("node2");
//    }
}
