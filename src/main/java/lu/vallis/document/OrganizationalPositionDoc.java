package lu.vallis.document;

//organizationPosition correspond to role

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "organizational_position")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationalPositionDoc {
    @Id
    private int id;
    private String name;
    private boolean isManager;
    private String status;
}
