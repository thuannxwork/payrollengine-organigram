package lu.vallis.entity.bean;

public class SectionOrganization {
    private static final long serialVersionUID = 6953127036950571587L;
    private int orgId;
    //    @ApiModelProperty(value = " Cost center", example = "0000009003", dataType = "string")
    private String costCenter;
    //    @ApiModelProperty(value = " Employee category", example = "1", dataType = "string")
    private String categorization;
    //    @ApiModelProperty(value = " Employee group (or categorization subgroup)", example = "CA", dataType = "string")
    private String group;
    //    @ApiModelProperty(value = "Organizational unit", example = "50027975", dataType = "string")
//    private String organizationalUnit;
    //    @ApiModelProperty(value = "Business unit", example = "CHANGEME", dataType = "string")
    private String businessUnit;
    //    @ApiModelProperty(value = "Payroll group", example = "L0", dataType = "string")
    private String payrollGroup;
    //    @ApiModelProperty(value = "location", example = "1010", dataType = "string")
    private String location;
    //    @ApiModelProperty(value = "site", example = "ODO1", dataType = "string")
    private String site;
    //    @ApiModelProperty(value = "Organizational position", example = "50039638", dataType = "string")
    private String organizationalPosition;
}
