package lu.vallis;

import lu.vallis.document.Node;
import lu.vallis.entity.OrganizationalUnit;
import lu.vallis.enumeration.EntityType;
import lu.vallis.repository.NodeRepository;
import lu.vallis.service.NodeService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@SpringBootTest
@TestInstance(PER_CLASS)
class GraphLookupTestsIT {

    @Autowired
    NodeService nodeService;

    @Autowired
	NodeRepository nodeRepository;

    @BeforeAll
	@Transactional
	void populate() {
		Node SectionOrganization = new Node();
		SectionOrganization.setRootId(1001);
		SectionOrganization.setOrgUnitId(0);
		SectionOrganization.setName("oak");
		SectionOrganization.setParentOrgUnitId(List.of(-1));
		SectionOrganization.setVersionId(new Random().nextInt());
		SectionOrganization.setEntityType(EntityType.type_1);

		nodeRepository.save(SectionOrganization);

		Node leafNode = new Node();
		leafNode.setRootId(1001);
		leafNode.setOrgUnitId(5);
		leafNode.setName("leaf");
		leafNode.setParentOrgUnitId(List.of(0));
		leafNode.setVersionId(new Random().nextInt());
		leafNode.setEntityType(EntityType.type_5);

		nodeRepository.save(leafNode);
	}

	@DisplayName(value = "given an existing tree and orgUnitId, retrieve its descendants")
    @Test
    void testSubTreeRetrieval() {
        OrganizationalUnit node = nodeService.getSubOrganigram(1001, 0, null);
        assertThat(node).isNotNull();
    }

    // add your integration tests (IT) here

}
