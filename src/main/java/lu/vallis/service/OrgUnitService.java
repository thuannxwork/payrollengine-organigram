package lu.vallis.service;

import lu.vallis.entity.bean.OrganizationalEmployee;
import lu.vallis.entity.bean.OrganizationalUnit;
import org.springframework.util.CollectionUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface OrgUnitService {
    OrganizationalUnit getFullOrganigram(int rootId);

    OrganizationalUnit getFullEmployeeOrganigram(int rootId);

    OrganizationalEmployee getEmployeeOrganigram(int rootId) throws Exception;

    OrganizationalUnit getSubOrganigram(int rootId, String orgUnitId, Long maxDepth);

    OrganizationalUnit getSubEmployeeOrganigram(int rootId, String orgUnitId, Long maxDepth);

    OrganizationalUnit getRootOrgUnit(String parentId);

    void deleteOrgUnitNodes(int rootId, String orgUnitId);

	void create(OrganizationalUnit OrganizationalUnit);

	void move(int rootId, String orgUnitId, String newParentNodeId);

    void delete(String id);

    void update(OrganizationalUnit request);

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

//    static OrganizationalEmployee assembleEmployeeTree(final List<OrganizationalUnit> orgUnitNodes, final List<OrganizationalEmployee> allEmployees,
//                                                       final String rootEmployeeId) {
//        final Map<String, OrganizationalEmployee> mapEmployeeTmp = new LinkedHashMap<>();
//        final Map<String, OrganizationalUnit> mapOrgUnitTmp = new LinkedHashMap<>();
//        for (final OrganizationalEmployee employee : allEmployees) {
//            mapEmployeeTmp.put(employee.getId(), employee);
//        }
//
//        for (final OrganizationalUnit orgUnit : orgUnitNodes) {
//            mapOrgUnitTmp.put(orgUnit.getId(), orgUnit);
//        }
//
//
//        for (final OrganizationalEmployee employee : allEmployees) {
//            String orgUnitId = employee.getOrgUnitId();
//            OrganizationalUnit orgUnit = mapOrgUnitTmp.get(orgUnitId);
////            employee.setOrgUnit(orgUnit);
//            for (OrganizationalUnit parentOrgUnit : orgUnit.getParents()) {
//                if (parentOrgUnit.getHeadOfUnit() != null) {
//                    OrganizationalEmployee manager = mapEmployeeTmp.get(parentOrgUnit.getHeadOfUnit().getId());
//                    employee.addParent(manager);
//                }
//            }
//
//            if (orgUnit.getHeadOfUnit() != null) {
//                OrganizationalEmployee headOfUnit = mapEmployeeTmp.get(orgUnit.getHeadOfUnit().getId());
//                headOfUnit.setOrgUnitName(orgUnit.getName());
//                for (OrganizationalUnit childOrgUnit : orgUnit.getChildren()) {
//                    for (OrganizationalEmployee employee1 : childOrgUnit.getEmployees()) {
//                        employee1.setOrgUnitName(childOrgUnit.getName());
//                        headOfUnit.addChild(employee1);
//                    }
//                }
////                mapEmployeeTmp.put(headOfUnit.getId(), headOfUnit);
//            }
//        }
//
//        return mapEmployeeTmp.get(rootEmployeeId);
//    }

    static OrganizationalEmployee assembleEmployeeTree(final List<OrganizationalUnit> orgUnitNodes, final List<OrganizationalEmployee> allEmployees,
                                                       final String rootEmployeeId) throws Exception {
	    try {
            final Map<String, OrganizationalEmployee> mapEmployeeTmp = new LinkedHashMap<>();
            final Map<String, OrganizationalUnit> mapOrgUnitTmp = new LinkedHashMap<>();
            for (final OrganizationalEmployee employee : allEmployees) {
                mapEmployeeTmp.put(employee.getId(), employee);
            }

            for (final OrganizationalUnit orgUnit : orgUnitNodes) {
                mapOrgUnitTmp.put(orgUnit.getId(), orgUnit);
            }


            for (final OrganizationalUnit currentUnit : orgUnitNodes) {
                final List<String> parents = currentUnit.getParentOrgUnitId();

                if (!CollectionUtils.isEmpty(parents)) {
                    for (final String pid : parents) {
                        final OrganizationalUnit parentUnit = mapOrgUnitTmp.get(pid);

                        if (parentUnit != null && parentUnit.getHeadOfUnit() != null) {
                            OrganizationalEmployee parentUnitManager = parentUnit.getHeadOfUnit();

                            for (OrganizationalEmployee employee : currentUnit.getEmployees()) {
                                if (parentUnitManager != null) {
                                    parentUnitManager.addChild(employee);
                                    employee.addParent(parentUnitManager);
                                    mapEmployeeTmp.put(parentUnitManager.getId(), parentUnitManager);
                                    mapEmployeeTmp.put(employee.getId(), employee);
                                }
                            }
                        }
                    }
                }
            }

            return mapEmployeeTmp.get(rootEmployeeId);
        }
	    catch (Exception e) {
	        e.printStackTrace();
	        throw e;
        }
    }

}
