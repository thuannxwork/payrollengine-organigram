package lu.vallis.repository;

import lu.vallis.document.Node;

import java.util.List;
import java.util.Optional;

public interface NodeGraphLookupRepository {

	Optional<List<Node>> getSubTree(int treeId, int nodeId, Long maxDepth);

}
