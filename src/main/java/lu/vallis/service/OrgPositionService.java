package lu.vallis.service;

import lu.vallis.entity.bean.OrganizationalPosition;

public interface OrgPositionService {

    OrganizationalPosition getById(String id);
    void create(OrganizationalPosition organizationalPosition);
    void update(String id, OrganizationalPosition newOrganizationalPosition);
    void delete(String id);
}
