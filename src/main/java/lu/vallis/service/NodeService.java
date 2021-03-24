package lu.vallis.service;

import lu.vallis.entity.OrganizationalUnit;
import org.springframework.util.CollectionUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface NodeService {

    int DEFAULT_ROOT_NODE_ID = -1;

    OrganizationalUnit getFullOrganigram(int rootId);

    OrganizationalUnit getSubOrganigram(int rootId, int orgUnitId, Long maxDepth);

    void deleteNodes(int treeId, int nodeId);

	void create(OrganizationalUnit OrganizationalUnit);

	void move(int treeId, int nodeId, int newParentNodeId);

	static OrganizationalUnit assembleTree(final List<OrganizationalUnit> nodes, final int rootNodeId) {
        final Map<Integer, OrganizationalUnit> mapTmp = new LinkedHashMap<>();
        // Save all nodes to a map
        for (final OrganizationalUnit current : nodes) {
            mapTmp.put(current.getOrgUnitId(), current);
        }
        // Loop and assign parent/child relationships
        for (final OrganizationalUnit current : nodes) {
            final List<Integer> parents = current.getParentOrgUnitId();

            if (!CollectionUtils.isEmpty(parents)) {
                for (final Integer pid : parents) {
                    final OrganizationalUnit parent = mapTmp.get(pid);
                    if (parent != null) {
                        parent.addChild(current);
                        current.addParent(parent);
                    }
                }
            }
        }
        return mapTmp.get(rootNodeId);
    }

}
