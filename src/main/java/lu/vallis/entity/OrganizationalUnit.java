
package lu.vallis.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lu.vallis.enumeration.EntityType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.javers.core.metamodel.annotation.DiffIgnore;
import org.javers.core.metamodel.annotation.Id;
import org.javers.core.metamodel.annotation.TypeName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//organizationalUnit correspond to departement
@TypeName("OrganizationalUnit")
@Getter
@Setter
@ToString
public class OrganizationalUnit implements Serializable {

    @Id
    private int orgUnitId;

//    @DiffIgnore
//    private int orgUnitId;

//    private int versionId;

    private String name;

    private EntityType entityType;

    @DiffIgnore
    private int rootId;

    @DiffIgnore
    private List<Integer> parentOrgUnitId;

    private List<OrganizationalUnit> children;

    @DiffIgnore
    @JsonIgnore
    private List<OrganizationalUnit> parents;

    public OrganizationalUnit() {
        this.parentOrgUnitId = new ArrayList<>();
        this.children = new ArrayList<>();
        this.parents = new ArrayList<>();
    }

    // TODO: throws java.lang.NullPointerException: null and in many cases hierarchies are not fetched correctly
    public void addChild(OrganizationalUnit child) {
        if (this.children!= null && !this.children.contains(child) && child != null)
            this.children.add(child);
    }

    public void addParent(OrganizationalUnit parent) {
        if (this.parents!= null && !this.parents.contains(parent) && parent != null)
            this.parents.add(parent);
    }

}
