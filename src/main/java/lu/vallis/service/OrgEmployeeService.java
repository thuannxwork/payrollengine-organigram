package lu.vallis.service;

import lu.vallis.entity.OrganizationalEmployee;

import java.util.List;

public interface OrgEmployeeService {
	OrganizationalEmployee getById(String id);
	List<OrganizationalEmployee> getAllByOrgUnitId(String orgUnitId);
	void create(OrganizationalEmployee OrganizationalEmployee);
	void update(String id, OrganizationalEmployee newOrganizationalEmployee);
    void delete(String id);
}
