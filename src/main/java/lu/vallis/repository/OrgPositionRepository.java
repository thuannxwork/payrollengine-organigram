package lu.vallis.repository;

import lu.vallis.entity.mongobo.OrganizationalPositionDoc;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrgPositionRepository extends MongoRepository<OrganizationalPositionDoc, Object> {
    OrganizationalPositionDoc findById(ObjectId id);
}
