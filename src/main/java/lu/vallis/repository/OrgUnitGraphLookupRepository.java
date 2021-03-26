package lu.vallis.repository;

import lu.vallis.document.OrganizationalUnitDoc;

import java.util.List;
import java.util.Optional;

public interface OrgUnitGraphLookupRepository {

	Optional<List<OrganizationalUnitDoc>> getSubOrganigram(int rootId, String orgUnitId, Long maxDepth);

}
