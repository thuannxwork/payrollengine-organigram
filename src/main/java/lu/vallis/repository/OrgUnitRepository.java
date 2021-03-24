package lu.vallis.repository;

import lu.vallis.document.OrganizationalUnitDoc;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface OrgUnitRepository extends MongoRepository<OrganizationalUnitDoc, Object>, OrgUnitGraphLookupRepository {
	Optional<List<OrganizationalUnitDoc>> findDistinctByRootId(int rootId);
	Optional<OrganizationalUnitDoc> findDistinctByRootIdAndOrgUnitId(int rootId, int orgUnitId);

}
