
package lu.vallis.document;

import lu.vallis.enumeration.EntityType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "OrganizationalUnits")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Node {

  @Id
  private String id;

  private int orgUnitId;

  private int versionId;

  private String name;

  private EntityType entityType;

  private int rootId;

  private List<Integer> parentOrgUnitId;

  private List<Node> descendants;

}
