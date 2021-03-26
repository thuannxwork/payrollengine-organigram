package lu.vallis.repository;

import lu.vallis.document.Node;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GraphLookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

@Repository
public class NodeRepositoryImpl implements NodeGraphLookupRepository {

	private static final long MAX_DEPTH_SUPPORTED = 10000L;

	private final MongoTemplate mongoTemplate;

	public NodeRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public Optional<List<Node>> getSubTree(int treeId, int nodeId, Long maxDepth) {
		final Criteria byNodeId = new Criteria("orgUnitId").is(nodeId);
		final Criteria byTreeId = new Criteria("rootId").is(treeId);
		final MatchOperation matchStage = Aggregation.match(byTreeId.andOperator(byNodeId));

		GraphLookupOperation graphLookupOperation = GraphLookupOperation.builder()
				.from("nodes")
				.startWith("$orgUnitId")
				.connectFrom("orgUnitId")
				.connectTo("parentOrgUnitId")
				.restrict(new Criteria("rootId").is(treeId))
				.maxDepth(maxDepth != null ? maxDepth : MAX_DEPTH_SUPPORTED)
				.as("descendants");

		Aggregation aggregation = Aggregation.newAggregation(matchStage, graphLookupOperation);

		List<Node> results = mongoTemplate.aggregate(aggregation, "nodes", Node.class).getMappedResults();
		return CollectionUtils.isEmpty(results) ? Optional.empty() : Optional.of(results);
	}

}