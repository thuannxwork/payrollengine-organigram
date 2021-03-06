package lu.vallis.service;

import lu.vallis.common.Constants;
import lu.vallis.entity.OrganizationalEmployee;
import lu.vallis.entity.OrganizationalPosition;
import lu.vallis.entity.OrganizationalUnit;
import lu.vallis.repository.OrgUnitRepository;
import lu.vallis.document.OrganizationalUnitDoc;
import lu.vallis.exception.NotFoundException;
import lombok.extern.java.Log;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import lu.vallis.common.Constants.Status;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Log
@Service
public class OrgUnitServiceImpl implements OrgUnitService {

	@Autowired
    private OrgUnitRepository nodeRepository;

	@Autowired
	private OrgEmployeeService employeeService;

	@Autowired
	private OrgPositionService orgPositionService;

    public OrgUnitServiceImpl(OrgUnitRepository nodeRepository) {
    	this.nodeRepository = nodeRepository;
    }

    @Override
    public OrganizationalUnit getFullOrganigram(int rootId) {
		List<OrganizationalUnit> organizationalUnits = new ArrayList<>();
		String rootOrgUnitId = "";
		try {
        List<OrganizationalUnitDoc> orgCharts = nodeRepository.findDistinctByRootId(rootId).orElseThrow(NotFoundException::new);

			for (OrganizationalUnitDoc orgUnitDoc : orgCharts) {
				OrganizationalUnit organizationalUnit = new OrganizationalUnit();
				BeanUtils.copyProperties(orgUnitDoc, organizationalUnit);

				// find staff list
				List<OrganizationalEmployee> employees = employeeService.getAllFullInfoByOrgUnitId(organizationalUnit.getId());
				organizationalUnit.setEmployees(employees);

				OrganizationalEmployee headOfUnit = null;
				List<OrganizationalEmployee> managers = new ArrayList<OrganizationalEmployee>();

				int managerLevel = 999999999;

				for (OrganizationalEmployee employee : employees) {
					// get position
					if (employee.getPostion() != null && employee.getPostion().getIsManager()) {
						managers.add(employee);
						if (managerLevel > employee.getPostion().getLevel()) {
							managerLevel = employee.getPostion().getLevel();
							headOfUnit = employee;
						}
					}
				}

				organizationalUnit.setManagers(managers);
				organizationalUnit.setHeadOfUnit(headOfUnit);

				organizationalUnits.add(organizationalUnit);
				rootOrgUnitId = orgCharts.get(0) != null ? orgCharts.get(0).getId() : Constants.DEFAULT_ROOT_NODE_ID;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

        return OrgUnitService.assembleTree(organizationalUnits, rootOrgUnitId);
    }

	@Override
	public OrganizationalUnit getFullEmployeeOrganigram(int rootId) {
		List<OrganizationalUnitDoc> orgCharts = nodeRepository.findDistinctByRootId(rootId).orElseThrow(NotFoundException::new);

		List<OrganizationalUnit> organizationalUnits = new ArrayList<>();
		for (OrganizationalUnitDoc chartNode : orgCharts) {
			OrganizationalUnit organizationalUnit = new OrganizationalUnit();
			BeanUtils.copyProperties(chartNode, organizationalUnit, "id", "children");

			organizationalUnits.add(organizationalUnit);
		}

		return OrgUnitService.assembleTree(organizationalUnits, Constants.DEFAULT_ROOT_NODE_ID);
	}

    @Override
	@Transactional(readOnly = true)
    public OrganizationalUnit getSubOrganigram(int rootId, String orgUnitId, Long maxDepth) {
        List<OrganizationalUnitDoc> orgUnitNodes = nodeRepository.getSubOrganigram(rootId, orgUnitId, null).orElseThrow(NotFoundException::new);

        List<OrganizationalUnit> flatList = orgUnitNodes.stream()
                .map(OrganizationalUnitDoc::getDescendants)
                .flatMap(Collection::stream)
                .map(node -> {
                    OrganizationalUnit tr = new OrganizationalUnit();
                    BeanUtils.copyProperties(node, tr, "id");
                    return tr;
                })
                .collect(Collectors.toList());

        OrganizationalUnit root = new OrganizationalUnit();
        BeanUtils.copyProperties(orgUnitNodes.get(0), root, "id", "children");
        flatList.add(root);

        return (OrgUnitService.assembleTree(flatList, orgUnitId));
    }

	@Override
	@Transactional(readOnly = true)
	public OrganizationalUnit getSubEmployeeOrganigram(int rootId, String orgUnitId, Long maxDepth) {
		List<OrganizationalUnitDoc> orgUnitNodes = nodeRepository.getSubOrganigram(rootId, orgUnitId, null).orElseThrow(NotFoundException::new);

		List<OrganizationalUnit> flatList = orgUnitNodes.stream()
				.map(OrganizationalUnitDoc::getDescendants)
				.flatMap(Collection::stream)
				.map(node -> {
					OrganizationalUnit tr = new OrganizationalUnit();
					BeanUtils.copyProperties(node, tr, "id");
					return tr;
				})
				.collect(Collectors.toList());

		OrganizationalUnit root = new OrganizationalUnit();
		BeanUtils.copyProperties(orgUnitNodes.get(0), root, "id", "children");
		flatList.add(root);

		return (OrgUnitService.assembleTree(flatList, orgUnitId));
	}

	@Override
	@Transactional(readOnly = true)
	public OrganizationalUnit getRootOrgUnit(String parentId) {
		OrganizationalUnitDoc orgUnitDoc = nodeRepository.findByParentOrgUnitId(parentId);
		Objects.requireNonNull(orgUnitDoc, "Unit not found");
		OrganizationalUnit orgUnit = new OrganizationalUnit();
		BeanUtils.copyProperties(orgUnitDoc, orgUnit);
		return orgUnit;
	}

	@Override
    @Transactional(rollbackFor = Exception.class)
	public void deleteOrgUnitNodes(int rootId, String orgUnitId)  {
		// ... perform validations etc.
		List<OrganizationalUnitDoc> nodes = nodeRepository.getSubOrganigram(rootId, orgUnitId, 1L).orElseThrow(NotFoundException::new);
		var target = nodes.get(0);
		if (!CollectionUtils.isEmpty(target.getDescendants())) {
			target.getDescendants().forEach(n -> n.setParentOrgUnitId(target.getParentOrgUnitId()));
			nodeRepository.saveAll(target.getDescendants());
		}

		nodeRepository.delete(target);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void create(OrganizationalUnit OrganizationalUnit) {
    	// ... check if parent exists etc.
    	OrganizationalUnitDoc node = new OrganizationalUnitDoc();
    	node.setRootId(OrganizationalUnit.getRootId());
    	node.setParentOrgUnitId(OrganizationalUnit.getParentOrgUnitId());
    	node.setName(OrganizationalUnit.getName());
//    	node.setVersionId(OrganizationalUnit.getVersionId());
//    	node.setEntityType(OrganizationalUnit.getEntityType());
//		if (OrganizationalUnit.getOrgUnitId() < 0) {
//			node.setOrgUnitId(OrganizationalUnit.getOrgUnitId());
//		}
//		else {
//			int nextOrgUnitId = new Random().nextInt() & Integer.MAX_VALUE;
//			node.setOrgUnitId(nextOrgUnitId); // set a unique orgUnitId based on your policy
//		}

    	nodeRepository.save(node);
    	// iterate children and persist them as well...
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void move(int rootId, String id, String newParentNodeId) {
		// ... perform validations etc.
		var node = nodeRepository.findDistinctByRootIdAndId(rootId, id).orElseThrow(NotFoundException::new);
		node.setParentOrgUnitId(List.of(newParentNodeId));
		nodeRepository.save(node);
	}


	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(String id) throws NullPointerException {
		try {
			OrganizationalUnitDoc entity = nodeRepository.findById(new ObjectId(""
					+ id));
			Objects.requireNonNull(entity, "OrganizationalUnitDoc not found");

			entity.setStatus(Status.INACTIVE.name());

			nodeRepository.save(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(OrganizationalUnit request) {
		var node = nodeRepository.findDistinctByRootIdAndId(request.getRootId(), request.getId()).orElseThrow(NotFoundException::new);
		node.setName(request.getName());
		nodeRepository.save(node);
	}

}
