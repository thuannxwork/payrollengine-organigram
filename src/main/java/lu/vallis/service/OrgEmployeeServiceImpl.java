package lu.vallis.service;

import lombok.extern.java.Log;
import lu.vallis.common.Constants.Status;
import lu.vallis.document.OrganizationalEmployeeDoc;
import lu.vallis.entity.OrganizationalEmployee;
import lu.vallis.repository.OrgEmployeeRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Log
@Service
public class OrgEmployeeServiceImpl implements OrgEmployeeService {

	@Autowired
    private OrgEmployeeRepository repository;

	@Override
	public OrganizationalEmployee getById(String id) {
		OrganizationalEmployeeDoc employeeDoc = repository.findById(new ObjectId(""
				+ id));
		Objects.requireNonNull(employeeDoc, "employee not found");
		OrganizationalEmployee employee = new OrganizationalEmployee();
		BeanUtils.copyProperties(employeeDoc, employee);
		return employee;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void create(OrganizationalEmployee organizationalEmployee) {
		OrganizationalEmployeeDoc newEmployeeDoc = new OrganizationalEmployeeDoc();
		BeanUtils.copyProperties(organizationalEmployee, newEmployeeDoc);
		repository.save(newEmployeeDoc);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(String id, OrganizationalEmployee newOrganizationalEmployee) {
		OrganizationalEmployeeDoc currentOrgEmployeeDoc = repository.findById(new ObjectId("" + id));
		Objects.requireNonNull(currentOrgEmployeeDoc, "OrganizationalEmployeeDoc not found");
		BeanUtils.copyProperties(newOrganizationalEmployee, currentOrgEmployeeDoc, "id");
		repository.save(currentOrgEmployeeDoc);
	}

	@Override
	public void delete(String id) throws NullPointerException {
		OrganizationalEmployeeDoc entity = repository.findById(new ObjectId(""
				+ id));
		Objects.requireNonNull(entity, "OrganizationalEmployeeDoc not found");

		entity.setStatus(Status.INACTIVE.name());

		repository.save(entity);
	}
}
