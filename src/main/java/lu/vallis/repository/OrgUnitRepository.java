package lu.vallis.repository;

import lu.vallis.document.OrganizationalUnitDoc;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface OrgUnitRepository extends MongoRepository<OrganizationalUnitDoc, Object>, OrgUnitGraphLookupRepository {
	OrganizationalUnitDoc findById(ObjectId id);
	OrganizationalUnitDoc findByParentOrgUnitId(String parentOrgUnitId);
	Optional<List<OrganizationalUnitDoc>> findDistinctByRootId(int rootId);
	Optional<OrganizationalUnitDoc> findDistinctByRootIdAndId(int rootId, String id);

}
