package lu.vallis.service;

import lu.vallis.common.Constants;
import lu.vallis.entity.OrganizationalUnit;
import org.springframework.util.CollectionUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface OrgUnitService {
    OrganizationalUnit getFullOrganigram(int rootId);

    OrganizationalUnit getFullEmployeeOrganigram(int rootId);

    OrganizationalUnit getSubOrganigram(int rootId, String orgUnitId, Long maxDepth);

    OrganizationalUnit getSubEmployeeOrganigram(int rootId, String orgUnitId, Long maxDepth);

    OrganizationalUnit getRootOrgUnit(String parentId);

    void deleteOrgUnitNodes(int rootId, String orgUnitId);

	void create(OrganizationalUnit OrganizationalUnit);

	void move(int rootId, String orgUnitId, String newParentNodeId);

    void delete(String id);

	static OrganizationalUnit assembleTree(final List<OrganizationalUnit> orgUnitNodes, final String rootNodeId) {
        final Map<String, OrganizationalUnit> mapTmp = new LinkedHashMap<>();
        // Save all orgUnitNodes to a map
        for (final OrganizationalUnit current : orgUnitNodes) {
            mapTmp.put(current.getId(), current);
        }
        // Loop and assign parent/child relationships
        for (final OrganizationalUnit current : orgUnitNodes) {
            final List<String> parents = current.getParentOrgUnitId();

            if (!CollectionUtils.isEmpty(parents)) {
                for (final String pid : parents) {
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
