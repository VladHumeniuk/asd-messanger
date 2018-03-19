package lnu.asd.messenger.web.mapper;

import lnu.asd.messenger.domain.dbentity.Group;
import lnu.asd.messenger.web.entity.group.response.Attributes;
import lnu.asd.messenger.web.entity.group.response.Data;
import lnu.asd.messenger.web.entity.group.response.GroupResponse;
import org.springframework.stereotype.Component;

@Component
public class GroupResponseMapper implements BaseMapper<Group, GroupResponse> {

    @Override
    public GroupResponse map(Group entity) {
        Attributes attributes = new Attributes();
        attributes.setName(entity.getGroupName());

        Data data = new Data();
        data.setAttributes(attributes);
        data.setId(entity.getId());

        GroupResponse response = new GroupResponse();
        response.setData(data);

        return response;
    }
}
