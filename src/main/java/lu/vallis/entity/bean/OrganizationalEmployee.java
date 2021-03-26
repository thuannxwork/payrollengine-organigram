package lu.vallis.entity.bean;

//organizationPosition correspond to role

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import org.javers.core.metamodel.annotation.DiffIgnore;
import org.javers.core.metamodel.annotation.Id;
import org.javers.core.metamodel.annotation.TypeName;

import java.util.ArrayList;
import java.util.List;

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

    @DiffIgnore
    private String orgUnitName;

    private List<OrganizationalEmployee> children;

    public OrganizationalEmployee() {
        this.children = new ArrayList<>();
        this.parents = new ArrayList<>();
    }

    @DiffIgnore
    @JsonIgnore
    private List<OrganizationalEmployee> parents;

    // TODO: throws java.lang.NullPointerException: null and in many cases hierarchies are not fetched correctly
    public void addChild(OrganizationalEmployee child) {
        if (this.children!= null && !this.children.contains(child) && child != null)
            this.children.add(child);
    }

    public void addParent(OrganizationalEmployee parent) {
        if (this.parents!= null && !this.parents.contains(parent) && parent != null)
            this.parents.add(parent);
    }
}
