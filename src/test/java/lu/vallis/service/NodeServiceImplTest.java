package lu.vallis.service;

import lu.vallis.document.OrganizationalUnitDoc;
import lu.vallis.entity.OrganizationalUnit;
import lu.vallis.repository.OrgUnitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.from;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NodeServiceImplTest {

    @InjectMocks
    private OrgUnitServiceImpl nodeService;

    @Mock
    private OrgUnitRepository nodeRepository;

    @Test
    void getFullTree() throws Exception {
        final OrganizationalUnitDoc node = new OrganizationalUnitDoc();
        node.setRootId(1);
        node.setOrgUnitId(OrgUnitService.DEFAULT_ROOT_NODE_ID);
        node.setName("name1");

        when(nodeRepository.findDistinctByRootId(1)).thenReturn(Optional.of(Collections.singletonList(node)));

        final OrganizationalUnit fullTree = nodeService.getFullOrganigram(1);
        assertThat(fullTree)
                .isNotNull()
                .returns(1, from(OrganizationalUnit::getRootId))
                .returns("name1", from(OrganizationalUnit::getName));
    }

    // add your unit tests here
}
