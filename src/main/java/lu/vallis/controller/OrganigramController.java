package lu.vallis.controller;

import lu.vallis.common.URI;
import lu.vallis.entity.OrganizationalUnit;
import lu.vallis.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrganigramController {

    @Autowired
	NodeService nodeService;

	@CrossOrigin(origins = "*")
    @GetMapping(value = URI.PAYROLL_ORGANIGRAM_SERVICE + "/{rootId}")
    public ResponseEntity<OrganizationalUnit> getFullOrganization(@PathVariable("rootId") int rootId) {
		System.out.println("rootId " + rootId);
        return ResponseEntity.ok(nodeService.getFullTree(rootId));
    }

	@CrossOrigin(origins = "*")
    @GetMapping(value = URI.PAYROLL_ORGANIGRAM_SERVICE + "/{rootId}/st/{orgUnitId}")
    public ResponseEntity<OrganizationalUnit> getSubOrganization(@PathVariable("rootId") int rootId, @PathVariable("orgUnitId") int orgUnitId) {
        return ResponseEntity.ok(nodeService.getSubTree(rootId, orgUnitId, null));
    }

	@CrossOrigin(origins = "*")
    @DeleteMapping(value = URI.PAYROLL_ORGANIGRAM_SERVICE + "/{rootId}/{orgUnitId}")
	public ResponseEntity<Void> deleteNodes(@PathVariable("rootId") int rootId, @PathVariable("orgUnitId") int orgUnitId) {
    	nodeService.deleteNodes(rootId, orgUnitId);
    	return ResponseEntity.noContent().build();
	}

	@CrossOrigin(origins = "*")
	@PostMapping(value = URI.PAYROLL_ORGANIGRAM_SERVICE)
	public ResponseEntity<Void> create(@RequestBody OrganizationalUnit OrganizationalUnit) {
		nodeService.create(OrganizationalUnit);
		return ResponseEntity.ok().build();
	}

	@CrossOrigin(origins = "*")
	@PutMapping(value = URI.PAYROLL_ORGANIGRAM_SERVICE + "/{rootId}/{orgUnitId}")
	public ResponseEntity<Void> move(@PathVariable("rootId") int rootId, @PathVariable("orgUnitId") int orgUnitId,
			@RequestParam int newParentOrgUnitId) {
		nodeService.move(rootId, orgUnitId , newParentOrgUnitId);
		return ResponseEntity.ok().build();
	}

}
