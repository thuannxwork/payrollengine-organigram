package lu.vallis.entity;

//organizationPosition correspond to role

import lombok.Data;
import lombok.ToString;
import org.javers.core.metamodel.annotation.DiffIgnore;
import org.javers.core.metamodel.annotation.Id;
import org.javers.core.metamodel.annotation.TypeName;

@TypeName("organizational_employee")
@Data
@ToString
public class OrganizationalEmployee {
    @Id
    private String id;
    private String orgUnitId;
    private String orgPosId;
    private String name;
    private String imgUrl;
    private String status;
    private String employeeId;

    @DiffIgnore
    private OrganizationalPosition postion;
}
