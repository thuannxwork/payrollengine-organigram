package lu.vallis.controller;

import lu.vallis.common.URI;
import lu.vallis.entity.OrganizationalEmployee;
import lu.vallis.entity.OrganizationalUnit;
import lu.vallis.service.OrgEmployeeService;
import lu.vallis.service.OrgUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class OrgEmployeeController {

    @Autowired
	OrgEmployeeService orgEmployeeService;

	@CrossOrigin("*")
	@ResponseBody
	@GetMapping(value = URI.PAYROLL_ORGANIGRAM_SERVICE + URI.EMPLOYEE + URI.ID, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<OrganizationalEmployee> getOrgEmployee(
			@PathVariable(name = "id") String id) throws Exception {
		try {
			OrganizationalEmployee employee = orgEmployeeService.getById(id);
			return ResponseEntity.ok(employee);
		} catch (NullPointerException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@CrossOrigin(origins = "*")
	@PostMapping(value = URI.PAYROLL_ORGANIGRAM_SERVICE + URI.EMPLOYEE)
	public ResponseEntity<Void> createEmployee(@RequestBody OrganizationalEmployee organizationalEmployee) {
		orgEmployeeService.create(organizationalEmployee);
		return ResponseEntity.ok().build();
	}

	@CrossOrigin(origins = "*")
	@PutMapping(value = URI.PAYROLL_ORGANIGRAM_SERVICE + URI.EMPLOYEE + URI.ID)
	public ResponseEntity<Void> updateEmployee(@PathVariable(name = "id") String id,
											   @RequestBody OrganizationalEmployee newOrganizationalEmployee) {
		orgEmployeeService.update(id, newOrganizationalEmployee);
		return ResponseEntity.ok().build();
	}

	@CrossOrigin(origins = "*")
	@DeleteMapping(value = URI.PAYROLL_ORGANIGRAM_SERVICE + URI.EMPLOYEE + URI.ID)
	public ResponseEntity<Void> deleteEmployee(@PathVariable("id") String id) {
		orgEmployeeService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
