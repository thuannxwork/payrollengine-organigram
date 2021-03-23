package lu.vallis.entity.bo;
//import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import java.io.Serializable;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public abstract class CommonBo implements Serializable {
  private static final long serialVersionUID = -4694906102496244622L;
  // protected boolean active = true;
  // Coming from import
  // FIXME This field should be removed and replaced by date in DateCommonBO refactored as java.util.Date
//  @ApiModelProperty(value = "Change date", example = "2020-08-01 13:06:35.518Z", dataType = "date")
  protected Date changeDate;
//  @ApiModelProperty(value = "Update date", example = "2020-08-01 13:06:35.518Z", dataType = "date")
  @LastModifiedDate
  protected Date updateDate;
//  @ApiModelProperty(value = "Change user identifier", example = "JTHAI", dataType = "string")
  protected String changeUserIdentifier;
//  @ApiModelProperty(value = "Creation date", example = "2020-08-01 13:06:35.518Z", dataType = "date")
  @CreatedDate
  protected Date creationDate;
  // protected String externalIdentifier;
//  @ApiModelProperty(value = "Technical id", example = "HNKLJKLJIJ", dataType = "string")
  @Id
  protected String id;
//  @ApiModelProperty(value = "EMPLOYEE", example = "00000999", dataType = "string", required = true)
  @NonNull
  protected String employee;
//  @ApiModelProperty(value = "company", example = "MYCOMPANY", dataType = "string", required = true)
  @NonNull
  protected String company;
}
