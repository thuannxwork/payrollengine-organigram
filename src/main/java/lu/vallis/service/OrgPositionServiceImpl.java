package lu.vallis.service;

import lombok.extern.java.Log;
import lu.vallis.document.OrganizationalPositionDoc;
import lu.vallis.entity.OrganizationalPosition;
import lu.vallis.repository.OrgPositionRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lu.vallis.common.Constants.Status;

import java.util.*;

@Log
@Service
public class OrgPositionServiceImpl implements OrgPositionService {

	@Autowired
	private OrgPositionRepository repository;

	@Override
	public OrganizationalPosition getById(String id) {
		OrganizationalPositionDoc employeeDoc = repository.findById(new ObjectId(""
				+ id));
		Objects.requireNonNull(employeeDoc, "employee not found");
		OrganizationalPosition employee = new OrganizationalPosition();
		BeanUtils.copyProperties(employeeDoc, employee);
		return employee;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void create(OrganizationalPosition organizationalEmployee) {
		OrganizationalPositionDoc newEmployeeDoc = new OrganizationalPositionDoc();
		BeanUtils.copyProperties(organizationalEmployee, newEmployeeDoc);
		repository.save(newEmployeeDoc);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(String id, OrganizationalPosition newOrganizationalEmployee) {
		OrganizationalPositionDoc currentOrgEmployeeDoc = repository.findById(new ObjectId("" + id));
		Objects.requireNonNull(currentOrgEmployeeDoc, "OrganizationalPositionDoc not found");
		BeanUtils.copyProperties(newOrganizationalEmployee, currentOrgEmployeeDoc, "id");
		repository.save(currentOrgEmployeeDoc);
	}

	@Override
	public void delete(String id) throws NullPointerException {
		OrganizationalPositionDoc entity = repository.findById(new ObjectId(""
				+ id));
		Objects.requireNonNull(entity, "OrganizationalPositionDoc not found");

		entity.setStatus(Status.INACTIVE.name());

		repository.save(entity);
	}
}
