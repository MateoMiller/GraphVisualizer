package Algorithms.Minimalizing;

import java.util.List;

public class MinimalizingGroup {
    public List<String> states;
    public String groupName;

    public MinimalizingGroup(List<String> nodes, String groupName){
        this.states = nodes;
        this.groupName = groupName;
    }
}
