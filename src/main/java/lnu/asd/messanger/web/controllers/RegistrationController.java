package lnu.asd.messanger.web.controllers;

import lnu.asd.messanger.domain.User;
import lnu.asd.messanger.repository.UserRepository;
import lnu.asd.messanger.web.entity.register.request.RegisterRequest;
import lnu.asd.messanger.web.entity.register.response.RegisterResponse;
import lnu.asd.messanger.web.mapper.RegistrationResponseMapper;
import lnu.asd.messanger.web.utils.ErrorUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping("/api/register")
public class RegistrationController {

    private UserRepository userRepository;

    private RegistrationResponseMapper mapper;

    @Inject
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Inject
    public void setMapper(RegistrationResponseMapper mapper) {
        this.mapper = mapper;
    }

    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {

        User user = userRepository.findUserByUserName(request.getName());

        if (user != null) {
            new ResponseEntity<>(ErrorUtil.getConflictError(), HttpStatus.CONFLICT);
        }

        user = new User();
        user.setPassword(request.getPassword());
        user.setUserName(request.getName());
        user.setEncryptionKey(request.getKey());
        user = userRepository.save(user);

        return new ResponseEntity<>(mapper.map(user), HttpStatus.CREATED);
    }
}
