package com.example.demo.service;


import java.util.List;

import com.example.demo.exception.BussinessException;
import com.example.demo.model.Role;
import com.example.demo.model.User;



public interface UserService {
User saveUser(User user) throws BussinessException;
Role saveRole(Role role);
void addRoleToUSer(String username, String roleName);
User getUser(String username);
List<User> getUsers();
}
