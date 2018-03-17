package lnu.asd.messenger.web.controllers;

import lnu.asd.messenger.domain.dbentity.User;
import lnu.asd.messenger.repository.UserRepository;
import lnu.asd.messenger.web.entity.login.request.Data;
import lnu.asd.messenger.web.entity.login.request.LoginRequest;
import lnu.asd.messenger.web.entity.register.response.UserInfoResponse;
import lnu.asd.messenger.web.exceptions.LoginException;
import lnu.asd.messenger.web.mapper.UserInfoResponseMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController("/api/login")
public class LoginController {

    private UserRepository userRepository;

    private UserInfoResponseMapper mapper;

    @Inject
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Inject
    public void setMapper(UserInfoResponseMapper mapper) {
        this.mapper = mapper;
    }

    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserInfoResponse> loginUser(@RequestBody LoginRequest request) throws LoginException {

        Data loginData = request.getData();
        User user = userRepository.findUserByUserName(loginData.getUserName());

        if (user == null || !user.getPassword().equals(loginData.getPassword())) {
            throw new LoginException();
        }

        //TODO add groups info to response
        return new ResponseEntity<>(mapper.map(user), HttpStatus.OK);
    }
}
