package lnu.asd.messenger.web.mapper;

import lnu.asd.messenger.domain.dbentity.User;
import lnu.asd.messenger.web.entity.register.response.Attributes;
import lnu.asd.messenger.web.entity.register.response.Data;
import lnu.asd.messenger.web.entity.register.response.UserInfoResponse;
import org.springframework.stereotype.Component;

import static lnu.asd.messenger.web.utils.EntityTypes.USER;

@Component
public class UserInfoResponseMapper implements BaseMapper<User, UserInfoResponse> {

    @Override
    public UserInfoResponse map(User entity) {
        Attributes attributes = new Attributes();
        attributes.setUserName(entity.getUserName());

        Data data = new Data();
        data.setType(USER);
        data.setId(entity.getId());
        data.setAttributes(attributes);

        UserInfoResponse response = new UserInfoResponse();
        response.setData(data);

        return response;
    }
}
