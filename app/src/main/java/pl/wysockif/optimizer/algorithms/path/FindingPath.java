package pl.wysockif.optimizer.algorithms.path;

import pl.wysockif.optimizer.structures.graph.Graph;

import java.util.List;

public interface FindingPath {
    List<Integer> findPath(Graph residualGraph);
}
