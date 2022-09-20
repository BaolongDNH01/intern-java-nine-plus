package com.example.demo.service;


import java.util.List;

import com.example.demo.domain.Role;
import com.example.demo.domain.User;



public interface UserService {
User saveUser(User user);
Role saveRole(Role role);
void addRoleToUSer(String username, String roleName);
User getUser(String username);
List<User> getUsers();
}
