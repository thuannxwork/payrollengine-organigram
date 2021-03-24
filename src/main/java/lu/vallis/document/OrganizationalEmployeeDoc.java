package lu.vallis.document;

//organizationPosition correspond to role

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "organizational_employee")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationalEmployeeDoc {
    @Id
    private int id;
    private int orgUnitId;
    private int orgPosId;
    private String name;
    private String imgUrl;
    private String status;
}
