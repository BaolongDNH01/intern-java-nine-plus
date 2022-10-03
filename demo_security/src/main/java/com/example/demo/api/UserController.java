package com.example.demo.api;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.dto.UserDto;
import com.example.demo.exception.BussinessException;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.service.EmailSenderService;
import com.example.demo.service.UserService;
import com.example.demo.utils.CommonConstants;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin
public class UserController extends BaseController {
	@Autowired
	private UserService userService;

	@Autowired
	private EmailSenderService emailSenderService;

	@PostMapping("/register")
	public ResponseEntity<? extends Object> register(@Valid @RequestBody UserDto userDto, BindingResult bindingResult)
			throws BussinessException {

		List<User> existsUsers = this.userService.getUsers();
		for (User user : existsUsers) {
			if (user.getUsername().equals(userDto.getUsername())) {
				bindingResult.rejectValue("username", "ExistedUsername", "Username already exists.");
			}
		}

		userDto.validate(userDto, bindingResult);

		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult);
			return failed(CommonConstants.MessageCode.S1X0002, bindingResult.getFieldErrors().toArray());
		}

		User user = new User();
		User createUser = new User();
		try {
			BeanUtils.copyProperties(userDto, user);
			user.setCreatedDate(LocalDateTime.now());
			createUser = this.userService.saveUser(user);

			String emailSubject = "Register account";
			String emailContent = "Hi " + user.getFullname() + "!" + "\nYour new account is successfully registered.";
			this.emailSenderService.sendEmail(user.getEmail(), emailSubject, emailContent);

		} catch (BussinessException ex) {

			return failed(ex.getMsgCode(), bindingResult.getFieldErrors().toArray());
		}
		return success(CommonConstants.MessageCode.S1X0001, createUser, null);
	}

	@GetMapping("/users")
	public ResponseEntity<List<User>> getUsers() {
		return ResponseEntity.ok().body(userService.getUsers());
	}

	@PostMapping("/role/save")
	public ResponseEntity<Role> saveRole(@RequestBody Role role) {
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/api/role/save").toUriString());
		return ResponseEntity.created(uri).body(userService.saveRole(role));
	}

	@PostMapping("/role/addToUser")
	public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form) {
		userService.addRoleToUSer(form.getUsername(), form.getRolename());
		return ResponseEntity.ok().build();
	}
}

@Data
class RoleToUserForm {
	private String username;
	private String rolename;

}
