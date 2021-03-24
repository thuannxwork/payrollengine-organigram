package lu.vallis.service;

import lu.vallis.entity.OrganizationalUnit;
import lu.vallis.repository.OrgUnitRepository;
import lu.vallis.document.OrganizationalUnitDoc;
import lu.vallis.exception.NotFoundException;
import lombok.extern.java.Log;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Log
@Service
public class OrgUnitServiceImpl implements OrgUnitService {

    private final OrgUnitRepository nodeRepository;

    public OrgUnitServiceImpl(OrgUnitRepository nodeRepository) {
    	this.nodeRepository = nodeRepository;
    }

    @Override
    public OrganizationalUnit getFullOrganigram(int rootId) {
        List<OrganizationalUnitDoc> orgCharts = nodeRepository.findDistinctByRootId(rootId).orElseThrow(NotFoundException::new);

        List<OrganizationalUnit> organizationalUnits = new ArrayList<>();
        for (OrganizationalUnitDoc chartNode : orgCharts) {
            OrganizationalUnit organizationalUnit = new OrganizationalUnit();
            BeanUtils.copyProperties(chartNode, organizationalUnit, "id", "children");

			organizationalUnits.add(organizationalUnit);
        }

        return OrgUnitService.assembleTree(organizationalUnits, DEFAULT_ROOT_NODE_ID);
    }

    @Override
	@Transactional(readOnly = true)
    public OrganizationalUnit getSubOrganigram(int rootId, int orgUnitId, Long maxDepth) {
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
    @Transactional(rollbackFor = Exception.class)
	public void deleteOrgUnitNodes(int rootId, int orgUnitId)  {
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
		if (OrganizationalUnit.getOrgUnitId() < 0) {
			node.setOrgUnitId(OrganizationalUnit.getOrgUnitId());
		}
		else {
			int nextOrgUnitId = new Random().nextInt() & Integer.MAX_VALUE;
			node.setOrgUnitId(nextOrgUnitId); // set a unique orgUnitId based on your policy
		}

    	nodeRepository.save(node);
    	// iterate children and persist them as well...
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void move(int rootId, int orgUnitId, int newParentNodeId) {
		// ... perform validations etc.
		var node = nodeRepository.findDistinctByRootIdAndOrgUnitId(rootId, orgUnitId).orElseThrow(NotFoundException::new);
		node.setParentOrgUnitId(List.of(newParentNodeId));
		nodeRepository.save(node);
	}

}
