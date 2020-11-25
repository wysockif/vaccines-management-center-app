package pl.wysockif.optimizer.algorithms.flow;

import pl.wysockif.optimizer.structures.graph.Graph;

public interface MaxFlow {
    Graph findMaxFlow(Graph residualGraph);
}
