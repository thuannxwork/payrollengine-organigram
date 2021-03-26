package lu.vallis.repository;

import lu.vallis.entity.mongobo.OrganizationalUnitDoc;
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
public class OrgUnitRepositoryImpl implements OrgUnitGraphLookupRepository {

	private static final long MAX_DEPTH_SUPPORTED = 10000L;

	private final MongoTemplate mongoTemplate;

	public OrgUnitRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public Optional<List<OrganizationalUnitDoc>> getSubOrganigram(int rootId, String orgUnitId, Long maxDepth) {
		final Criteria byNodeId = new Criteria("orgUnitId").is(orgUnitId);
		final Criteria byTreeId = new Criteria("rootId").is(rootId);
		final MatchOperation matchStage = Aggregation.match(byTreeId.andOperator(byNodeId));

		GraphLookupOperation graphLookupOperation = GraphLookupOperation.builder()
				.from("organizational_unit")
				.startWith("$orgUnitId")
				.connectFrom("orgUnitId")
				.connectTo("parentOrgUnitId")
				.restrict(new Criteria("rootId").is(rootId))
				.maxDepth(maxDepth != null ? maxDepth : MAX_DEPTH_SUPPORTED)
				.as("descendants");

		Aggregation aggregation = Aggregation.newAggregation(matchStage, graphLookupOperation);

		List<OrganizationalUnitDoc> results = mongoTemplate.aggregate(aggregation, "organizational_unit", OrganizationalUnitDoc.class).getMappedResults();
		return CollectionUtils.isEmpty(results) ? Optional.empty() : Optional.of(results);
	}

}
