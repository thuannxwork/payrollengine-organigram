package lu.vallis.controller;

import lu.vallis.common.URI;
import lu.vallis.entity.bean.OrganizationalPosition;
import lu.vallis.service.OrgPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class OrgPositionController {

    @Autowired
	OrgPositionService orgPositionService;

	@CrossOrigin("*")
	@ResponseBody
	@GetMapping(value = URI.PAYROLL_ORGANIGRAM_SERVICE + URI.POSITION + URI.ID, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<OrganizationalPosition> getOrgPosition(
			@PathVariable(name = "id") String id) throws Exception {
		try {
			OrganizationalPosition position = orgPositionService.getById(id);
			return ResponseEntity.ok(position);
		} catch (NullPointerException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@CrossOrigin(origins = "*")
	@PostMapping(value = URI.PAYROLL_ORGANIGRAM_SERVICE + URI.POSITION)
	public ResponseEntity<Void> createPosition(@RequestBody OrganizationalPosition organizationalPosition) {
		orgPositionService.create(organizationalPosition);
		return ResponseEntity.ok().build();
	}

	@CrossOrigin(origins = "*")
	@PutMapping(value = URI.PAYROLL_ORGANIGRAM_SERVICE + URI.POSITION + URI.ID)
	public ResponseEntity<Void> updatePosition(@PathVariable(name = "id") String id,
											   @RequestBody OrganizationalPosition newOrganizationalPosition) {
		orgPositionService.update(id, newOrganizationalPosition);
		return ResponseEntity.ok().build();
	}

	@CrossOrigin(origins = "*")
	@DeleteMapping(value = URI.PAYROLL_ORGANIGRAM_SERVICE + URI.POSITION + URI.ID)
	public ResponseEntity<Void> deletePosition(@PathVariable("id") String id) {
		orgPositionService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
