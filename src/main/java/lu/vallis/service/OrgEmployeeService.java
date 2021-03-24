package lu.vallis.service;

import lu.vallis.entity.OrganizationalEmployee;

public interface OrgEmployeeService {
	OrganizationalEmployee getById(String id);
	void create(OrganizationalEmployee OrganizationalEmployee);
	void update(String id, OrganizationalEmployee newOrganizationalEmployee);
    void delete(String id);
}
