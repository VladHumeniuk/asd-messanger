package lnu.asd.messenger.web.mapper;

import lnu.asd.messenger.domain.dbentity.Participates;
import lnu.asd.messenger.web.entity.groupslist.response.Attributes;
import lnu.asd.messenger.web.entity.groupslist.response.Data;
import lnu.asd.messenger.web.entity.groupslist.response.GroupsResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserGroupsResponseMapper implements BaseMapper<List<Participates>, GroupsResponse> {

    @Override
    public GroupsResponse map(List<Participates> entity) {
        List<Data> groups = new ArrayList<>();

        for (Participates participates : entity) {
            Attributes attributes = new Attributes();
            attributes.setName(participates.getId().getGroup().getGroupName());
            attributes.setCreator(participates.getCreator());
            Data data = new Data();
            data.setAttributes(attributes);
            data.setId(participates.getId().getGroup().getId());
            groups.add(data);
        }

        GroupsResponse response = new GroupsResponse();
        response.setGroups(groups);
        return response;
    }
}
