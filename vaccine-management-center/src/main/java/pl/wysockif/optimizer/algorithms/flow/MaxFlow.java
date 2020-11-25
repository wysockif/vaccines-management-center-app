package pl.wysockif.optimizer.algorithms.flow;

import pl.wysockif.optimizer.structures.graph.Graph;

public interface MaxFlow {
    public Graph findMaxFlow(Graph residualGraph);
}
