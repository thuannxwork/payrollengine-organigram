package lu.vallis.repository;

import lu.vallis.document.OrganizationalEmployeeDoc;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrgEmployeeRepository extends MongoRepository<OrganizationalEmployeeDoc, Object> {
	OrganizationalEmployeeDoc findById(ObjectId id);
	List<OrganizationalEmployeeDoc> findByOrgUnitId(String orgUnitId);
}
