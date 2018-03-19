package lnu.asd.messenger.web.controllers;

import lnu.asd.messenger.domain.dbentity.Group;
import lnu.asd.messenger.domain.dbentity.Participates;
import lnu.asd.messenger.domain.dbentity.ParticipatesID;
import lnu.asd.messenger.domain.dbentity.User;
import lnu.asd.messenger.repository.GroupRepository;
import lnu.asd.messenger.repository.ParticipatesRepository;
import lnu.asd.messenger.repository.UserRepository;
import lnu.asd.messenger.web.entity.addmember.request.AddMemberRequest;
import lnu.asd.messenger.web.entity.group.request.CreateGroupRequest;
import lnu.asd.messenger.web.entity.group.response.GroupResponse;
import lnu.asd.messenger.web.entity.groupslist.response.GroupsResponse;
import lnu.asd.messenger.web.exceptions.UserNotFoundException;
import lnu.asd.messenger.web.mapper.GroupResponseMapper;
import lnu.asd.messenger.web.mapper.UserGroupsResponseMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private GroupRepository groupRepository;

    private ParticipatesRepository participatesRepository;

    private UserRepository userRepository;

    private GroupResponseMapper groupResponseMapper;

    private UserGroupsResponseMapper userGroupsResponseMapper;

    @Inject
    public void setGroupRepository(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Inject
    public void setParticipatesRepository(ParticipatesRepository participatesRepository) {
        this.participatesRepository = participatesRepository;
    }

    @Inject
    public void setGroupResponseMapper(GroupResponseMapper groupResponseMapper) {
        this.groupResponseMapper = groupResponseMapper;
    }

    @Inject
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Inject
    public void setUserGroupsResponseMapper(UserGroupsResponseMapper userGroupsResponseMapper) {
        this.userGroupsResponseMapper = userGroupsResponseMapper;
    }

    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GroupResponse> createGroup(@RequestBody CreateGroupRequest request) throws UserNotFoundException {
        User creator = userRepository.findOne(request.getCreatorId());

        if (creator == null) {
            throw new UserNotFoundException(request.getCreatorId());
        }

        Group group = new Group();
        group.setGroupName(request.getName());

        group = groupRepository.save(group);

        ParticipatesID participatesID = new ParticipatesID();
        participatesID.setGroup(group);
        participatesID.setUser(creator);
        Participates participates = new Participates();
        participates.setCreator(true);
        participates.setId(participatesID);
        participates = participatesRepository.save(participates);

        return new ResponseEntity<>(groupResponseMapper.map(group), HttpStatus.OK);
    }

    @RequestMapping(value = "/{userId}",
            method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GroupsResponse> getUserGroups(@PathVariable("userId") Long userId) throws UserNotFoundException {
        User user = userRepository.findOne(userId);

        if (user == null) {
            throw new UserNotFoundException(userId);
        }

        List<Participates> participates = participatesRepository.findById_User(user);

        return new ResponseEntity<>(userGroupsResponseMapper.map(participates), HttpStatus.OK);
    }

    @RequestMapping(value = "/{groupId}/member",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addMember(@RequestBody AddMemberRequest request,
                                    @PathVariable("groupId") Long groupId) {
        Group group = groupRepository.findOne(groupId);
        User user = userRepository.findOne(request.getMemberId());

        if (group == null || user == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        if (participatesRepository.findById_GroupAndId_User(group, user) != null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        ParticipatesID participatesID = new ParticipatesID();
        participatesID.setGroup(group);
        participatesID.setUser(user);
        Participates participates = new Participates();
        participates.setCreator(false);
        participates.setId(participatesID);
        participates = participatesRepository.save(participates);

        return new ResponseEntity(HttpStatus.CREATED);
    }
}
