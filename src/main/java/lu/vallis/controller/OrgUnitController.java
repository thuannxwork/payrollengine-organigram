package lu.vallis.controller;

import lu.vallis.common.URI;
import lu.vallis.entity.OrganizationalUnit;
import lu.vallis.service.OrgUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrgUnitController {

    @Autowired
	OrgUnitService organizationalService;

	@CrossOrigin(origins = "*")
    @GetMapping(value = URI.PAYROLL_ORGANIGRAM_SERVICE + URI.UNIT + "/{rootId}")
    public ResponseEntity<OrganizationalUnit> getFullOrganization(@PathVariable("rootId") int rootId) {
		System.out.println("rootId " + rootId);
		OrganizationalUnit result = organizationalService.getFullOrganigram(rootId);
		return ResponseEntity.ok(result);
    }

	@CrossOrigin(origins = "*")
	@GetMapping(value = URI.PAYROLL_ORGANIGRAM_SERVICE + URI.UNIT_EMPLOYEE + "/{rootId}")
	public ResponseEntity<OrganizationalUnit> getFullEmployeeOrganization(@PathVariable("rootId") int rootId) {
		System.out.println("rootId " + rootId);
		return ResponseEntity.ok(organizationalService.getFullEmployeeOrganigram(rootId));
	}

	@CrossOrigin(origins = "*")
    @GetMapping(value = URI.PAYROLL_ORGANIGRAM_SERVICE + URI.UNIT + "/{rootId}/st/{orgUnitId}")
    public ResponseEntity<OrganizationalUnit> getSubOrganization(@PathVariable("rootId") int rootId, @PathVariable("orgUnitId") String orgUnitId) {
        return ResponseEntity.ok(organizationalService.getSubOrganigram(rootId, orgUnitId, null));
    }

	@CrossOrigin(origins = "*")
	@GetMapping(value = URI.PAYROLL_ORGANIGRAM_SERVICE + URI.UNIT_EMPLOYEE + "/{rootId}/st/{orgUnitId}")
	public ResponseEntity<OrganizationalUnit> getSubEmployeeOrganization(@PathVariable("rootId") int rootId, @PathVariable("orgUnitId") String orgUnitId) {
		return ResponseEntity.ok(organizationalService.getSubEmployeeOrganigram(rootId, orgUnitId, null));
	}

	@CrossOrigin(origins = "*")
    @DeleteMapping(value = URI.PAYROLL_ORGANIGRAM_SERVICE + URI.UNIT + "/{rootId}/{orgUnitId}")
	public ResponseEntity<Void> deleteOrgUnitNodes(@PathVariable("rootId") int rootId, @PathVariable("orgUnitId") String orgUnitId) {
    	organizationalService.deleteOrgUnitNodes(rootId, orgUnitId);
    	return ResponseEntity.noContent().build();
	}

	@CrossOrigin(origins = "*")
	@PostMapping(value = URI.PAYROLL_ORGANIGRAM_SERVICE + URI.UNIT)
	public ResponseEntity<Void> create(@RequestBody OrganizationalUnit OrganizationalUnit) {
		organizationalService.create(OrganizationalUnit);
		return ResponseEntity.ok().build();
	}

	@CrossOrigin(origins = "*")
	@PutMapping(value = URI.PAYROLL_ORGANIGRAM_SERVICE + URI.UNIT + "/{rootId}/{orgUnitId}")
	public ResponseEntity<Void> move(@PathVariable("rootId") int rootId, @PathVariable("orgUnitId") String orgUnitId,
			@RequestParam String newParentOrgUnitId) {
		organizationalService.move(rootId, orgUnitId , newParentOrgUnitId);
		return ResponseEntity.ok().build();
	}

	@CrossOrigin(origins = "*")
	@DeleteMapping(value = URI.PAYROLL_ORGANIGRAM_SERVICE + URI.UNIT + URI.ID)
	public ResponseEntity<Void> deleteUnit(@PathVariable("id") String id) {
		organizationalService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@CrossOrigin(origins = "*")
	@PostMapping(value = URI.PAYROLL_ORGANIGRAM_SERVICE + URI.UNIT + URI.UPDATE)
	public ResponseEntity<Void> update(@RequestBody OrganizationalUnit request) {
		organizationalService.update(request);
		return ResponseEntity.ok().build();
	}
}
