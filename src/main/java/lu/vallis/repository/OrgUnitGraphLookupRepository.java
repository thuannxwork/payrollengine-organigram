package lu.vallis.repository;

import lu.vallis.entity.mongobo.OrganizationalUnitDoc;

import java.util.List;
import java.util.Optional;

public interface OrgUnitGraphLookupRepository {

	Optional<List<OrganizationalUnitDoc>> getSubOrganigram(int rootId, String orgUnitId, Long maxDepth);

}
