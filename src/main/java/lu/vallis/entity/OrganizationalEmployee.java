package lu.vallis.entity;

//organizationPosition correspond to role

import lombok.Data;
import lombok.ToString;
import org.javers.core.metamodel.annotation.Id;
import org.javers.core.metamodel.annotation.TypeName;

@TypeName("organizational_employee")
@Data
@ToString
public class OrganizationalEmployee {
    @Id
    private int id;
    private int orgUnitId;
    private int orgPosId;
    private String name;
    private String imgUrl;
}
