// SPDX-FileCopyrightText: 2020 Salif Mehmed <mail@salif.eu>
// SPDX-License-Identifier: MIT

package eu.salif.qa.service.impl;

import eu.salif.qa.constants.Roles;
import eu.salif.qa.constants.Users;
import eu.salif.qa.model.entity.Answer;
import eu.salif.qa.model.entity.Role;
import eu.salif.qa.model.entity.User;
import eu.salif.qa.model.view.UserViewModel;
import eu.salif.qa.model.view.QuestionPreviewViewModel;
import eu.salif.qa.model.view.RolesViewModel;
import eu.salif.qa.repository.AnswerRepository;
import eu.salif.qa.repository.QuestionRepository;
import eu.salif.qa.repository.RoleRepository;
import eu.salif.qa.repository.UserRepository;
import eu.salif.qa.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final DateTimeFormatter dateTimeFormatter;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           QuestionRepository questionRepository, AnswerRepository answerRepository,
                           PasswordEncoder passwordEncoder, ModelMapper modelMapper,
                           DateTimeFormatter dateTimeFormatter) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.dateTimeFormatter = dateTimeFormatter;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Username not found!"));
    }

    @Override
    public void initUsers() {
        if(this.userRepository.count() == 0){
                User admin = new User();
                admin.set(Users.ADMIN_USERNAME,
                        this.passwordEncoder.encode(Users.ADMIN_PASSWORD),
                        new HashSet<Role>(
                                Set.of(
                                        this.roleRepository.findByAuthority(Roles.USER),
                                        this.roleRepository.findByAuthority(Roles.ADMIN))),
                        LocalDateTime.now());
                this.userRepository.saveAndFlush(admin);
        }
    }

    @Override
    public void register(String username, String password) {
        User user = new User();
        user.set(username,
                this.passwordEncoder.encode(password),
                new HashSet<Role>(Set.of(this.roleRepository.findByAuthority(Roles.USER))),
                LocalDateTime.now());
        this.userRepository.saveAndFlush(user);
    }

    @Override
    public boolean exists(String username) {
        return this.userRepository.findByUsername(username).isPresent();
    }

    @Override
    public String getUserId(String username) {
        return this.userRepository.findByUsername(username).orElseThrow().getId();
    }

    @Override
    public UserViewModel getProfile(String id) {
        User user = this.userRepository.findById(id).orElseThrow();
        UserViewModel userViewModel = new UserViewModel();
        userViewModel.setUsername(user.getUsername());

        userViewModel.setRoles(user.getAuthorities().stream()
                .map(role -> this.modelMapper.map(role, RolesViewModel.class))
                .collect(Collectors.toList()));

        userViewModel.setQuestions(this.questionRepository.findAllByAuthorIdOrderByCreatedOn(id).stream()
                .map(question -> this.modelMapper.map(question, QuestionPreviewViewModel.class))
                .collect(Collectors.toList()));

        userViewModel.setAnsweredQuestions(this.answerRepository.findAllByAuthorIdOrderByCreatedOn(id).stream()
                .map(Answer::getQuestion)
                .distinct()
                .map(question -> this.modelMapper.map(question, QuestionPreviewViewModel.class))
                .collect(Collectors.toList()));
        userViewModel.setCreatedOn(user.getCreatedOn().format(dateTimeFormatter));

        return userViewModel;
    }
}
