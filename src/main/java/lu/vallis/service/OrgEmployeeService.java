package lu.vallis.service;

import lu.vallis.entity.bean.OrganizationalEmployee;

import java.util.List;

public interface OrgEmployeeService {
	OrganizationalEmployee getById(String id);
	OrganizationalEmployee getFullInfoById(String id);
	List<OrganizationalEmployee> getAllEmployees();
	List<OrganizationalEmployee> getAllByOrgUnitId(String orgUnitId);
	List<OrganizationalEmployee> getAllFullInfoByOrgUnitId(String orgUnitId);
	void create(OrganizationalEmployee OrganizationalEmployee);
	void update(String id, OrganizationalEmployee newOrganizationalEmployee);
    void delete(String id);
}
