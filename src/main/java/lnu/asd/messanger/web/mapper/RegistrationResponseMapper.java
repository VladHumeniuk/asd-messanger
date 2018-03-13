package lnu.asd.messanger.web.mapper;

import lnu.asd.messanger.domain.User;
import lnu.asd.messanger.web.entity.register.response.Attributes;
import lnu.asd.messanger.web.entity.register.response.Data;
import lnu.asd.messanger.web.entity.register.response.RegisterResponse;
import org.springframework.stereotype.Component;

import static lnu.asd.messanger.web.utils.EntityTypes.USER;

@Component
public class RegistrationResponseMapper implements BaseMapper<User, RegisterResponse> {

    @Override
    public RegisterResponse map(User entity) {
        Attributes attributes = new Attributes();
        attributes.setUserName(entity.getUserName());

        Data data = new Data();
        data.setType(USER);
        data.setId(entity.getId());
        data.setAttributes(attributes);

        RegisterResponse response = new RegisterResponse();
        response.setData(data);

        return response;
    }
}
