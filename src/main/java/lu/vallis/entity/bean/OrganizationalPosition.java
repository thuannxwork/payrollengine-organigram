package lu.vallis.entity.bean;

//organizationPosition correspond to role

import lombok.Data;
import lombok.ToString;
import org.javers.core.metamodel.annotation.Id;
import org.javers.core.metamodel.annotation.TypeName;

@TypeName("organizational_position")
@Data
@ToString
public class OrganizationalPosition {
    @Id
    private String id;
    private String name;
    private Boolean isManager;
    private String status;
    private int level;
}
