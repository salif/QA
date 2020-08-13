package com.salifm.qa.service.impl;

import com.salifm.qa.constants.Roles;
import com.salifm.qa.constants.Users;
import com.salifm.qa.model.entity.Answer;
import com.salifm.qa.model.entity.Role;
import com.salifm.qa.model.entity.User;
import com.salifm.qa.model.view.ProfileViewModel;
import com.salifm.qa.model.view.QuestionPreviewViewModel;
import com.salifm.qa.model.view.RolesViewModel;
import com.salifm.qa.repository.AnswerRepository;
import com.salifm.qa.repository.QuestionRepository;
import com.salifm.qa.repository.RoleRepository;
import com.salifm.qa.repository.UserRepository;
import com.salifm.qa.service.UserService;
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
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, QuestionRepository questionRepository, AnswerRepository answerRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper, DateTimeFormatter dateTimeFormatter) {
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
        return this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
    }

    @Override
    public void initUsers() {
        if(this.userRepository.count() == 0){
            register(Users.ADMIN_USERNAME, Users.ADMIN_PASSWORD);
            if (this.userRepository.findByUsername(Users.ADMIN_USERNAME).isPresent()) {
                User admin = this.userRepository.findByUsername(Users.ADMIN_USERNAME).get();
                admin.addAuthority(this.roleRepository.findByAuthority(Roles.ADMIN));
                this.userRepository.saveAndFlush(admin);
            }
        }
    }

    @Override
    public void register(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(this.passwordEncoder.encode(password));
        user.setAuthorities(new HashSet<Role>(Set.of(this.roleRepository.findByAuthority("USER"))));
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        user.setCreatedOn(LocalDateTime.now());
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
    public ProfileViewModel getProfile(String id) {
        User user = this.userRepository.findById(id).orElseThrow();
        ProfileViewModel profileViewModel = new ProfileViewModel();
        profileViewModel.setUsername(user.getUsername());

        profileViewModel.setRoles(user.getAuthorities().stream()
                .map(role -> this.modelMapper.map(role, RolesViewModel.class))
                .collect(Collectors.toList()));

        profileViewModel.setQuestions(this.questionRepository.findAllByAuthorIdOrderByCreatedOn(id).stream()
                .map(question -> this.modelMapper.map(question, QuestionPreviewViewModel.class))
                .collect(Collectors.toList()));

        profileViewModel.setAnsweredQuestions(this.answerRepository.findAllByAuthorIdOrderByCreatedOn(id).stream()
                .map(Answer::getQuestion)
                .distinct()
                .map(question -> this.modelMapper.map(question, QuestionPreviewViewModel.class))
                .collect(Collectors.toList()));
        profileViewModel.setCreatedOn(user.getCreatedOn().format(dateTimeFormatter));

        return profileViewModel;
    }
}
