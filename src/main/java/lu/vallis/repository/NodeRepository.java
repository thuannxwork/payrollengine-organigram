package lu.vallis.repository;

import lu.vallis.document.Node;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface NodeRepository extends MongoRepository<Node, Object>, NodeGraphLookupRepository {

	Optional<List<Node>> findDistinctByRootId(int rootId);

	Optional<Node> findDistinctByRootIdAndOrgUnitId(int rootId, int orgUnitId);

}
