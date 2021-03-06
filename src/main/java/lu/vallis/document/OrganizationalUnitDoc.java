
package lu.vallis.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "organizational_unit")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationalUnitDoc {

  @Id
  private String id;

//  private String orgUnitId;

  private String name;

  private int rootId;

  private List<String> parentOrgUnitId;

  private List<OrganizationalUnitDoc> descendants;

  private Long managerId;

  private String status;

}
