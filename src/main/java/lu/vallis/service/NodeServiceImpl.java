package lu.vallis.service;

import lu.vallis.entity.OrganizationalUnit;
import lu.vallis.repository.NodeRepository;
import lu.vallis.document.Node;
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
public class NodeServiceImpl implements NodeService {

    private final NodeRepository nodeRepository;

    public NodeServiceImpl(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }

    @Override
    public OrganizationalUnit getFullTree(int treeId) {
        List<Node> nodes = nodeRepository.findDistinctByRootId(treeId).orElseThrow(NotFoundException::new);

        List<OrganizationalUnit> organizationalUnits = new ArrayList<>();
        for (Node node : nodes) {
            OrganizationalUnit organizationalUnit = new OrganizationalUnit();
            BeanUtils.copyProperties(node, organizationalUnit, "id", "children");

			organizationalUnits.add(organizationalUnit);
        }

        return NodeService.assembleTree(organizationalUnits, DEFAULT_ROOT_NODE_ID);
    }

    @Override
	@Transactional(readOnly = true)
    public OrganizationalUnit getSubTree(int treeId, int nodeId, Long maxDepth) {
        List<Node> nodes = nodeRepository.getSubTree(treeId, nodeId, null).orElseThrow(NotFoundException::new);

        List<OrganizationalUnit> flatList = nodes.stream()
                .map(Node::getDescendants)
                .flatMap(Collection::stream)
                .map(node -> {
                    OrganizationalUnit tr = new OrganizationalUnit();
                    BeanUtils.copyProperties(node, tr, "id");
                    return tr;
                })
                .collect(Collectors.toList());

        OrganizationalUnit root = new OrganizationalUnit();
        BeanUtils.copyProperties(nodes.get(0), root, "id", "children");
        flatList.add(root);

        return (NodeService.assembleTree(flatList, nodeId));
    }

	@Override
    @Transactional(rollbackFor = Exception.class)
	public void deleteNodes(int treeId, int nodeId)  {
		// ... perform validations etc.
		List<Node> nodes = nodeRepository.getSubTree(treeId, nodeId, 1L).orElseThrow(NotFoundException::new);
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
    	Node node = new Node();
    	node.setRootId(OrganizationalUnit.getRootId());
    	node.setParentOrgUnitId(OrganizationalUnit.getParentOrgUnitId());
    	node.setName(OrganizationalUnit.getName());
//    	node.setVersionId(OrganizationalUnit.getVersionId());
    	node.setEntityType(OrganizationalUnit.getEntityType());
    	node.setOrgUnitId(new Random().nextInt()); // set a unique orgUnitId based on your policy

    	nodeRepository.save(node);
    	// iterate children and persist them as well...
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void move(int treeId, int nodeId, int newParentNodeId) {
		// ... perform validations etc.
		var node = nodeRepository.findDistinctByRootIdAndOrgUnitId(treeId, nodeId).orElseThrow(NotFoundException::new);
		node.setParentOrgUnitId(List.of(newParentNodeId));
		nodeRepository.save(node);
	}

}
