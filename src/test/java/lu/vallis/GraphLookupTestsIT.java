package lu.vallis;

import lu.vallis.document.OrganizationalUnitDoc;
import lu.vallis.entity.OrganizationalUnit;
import lu.vallis.repository.OrgUnitRepository;
import lu.vallis.service.OrgUnitService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@SpringBootTest
@TestInstance(PER_CLASS)
class GraphLookupTestsIT {

    @Autowired
    OrgUnitService nodeService;

    @Autowired
	OrgUnitRepository nodeRepository;

    @BeforeAll
	@Transactional
	void populate() {
		OrganizationalUnitDoc SectionOrganization = new OrganizationalUnitDoc();
		SectionOrganization.setRootId(1001);
//		SectionOrganization.setOrgUnitId("0");
		SectionOrganization.setName("oak");
		SectionOrganization.setParentOrgUnitId(List.of("-1"));

		nodeRepository.save(SectionOrganization);

		OrganizationalUnitDoc leafOrgUnitNode = new OrganizationalUnitDoc();
		leafOrgUnitNode.setRootId(1001);
//		leafOrgUnitNode.setOrgUnitId("5");
		leafOrgUnitNode.setName("leaf");
		leafOrgUnitNode.setParentOrgUnitId(List.of("0"));

		nodeRepository.save(leafOrgUnitNode);
	}

	@DisplayName(value = "given an existing tree and orgUnitId, retrieve its descendants")
    @Test
    void testSubTreeRetrieval() {
        OrganizationalUnit node = nodeService.getSubOrganigram(1001, "0", null);
        assertThat(node).isNotNull();
    }

    // add your integration tests (IT) here

}
