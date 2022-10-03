package com.example.demo.dto;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.demo.model.Gender;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class UserDto implements Validator {

//	@Pattern(regexp = "^([A-Za-z]+ )*[A-Za-z]+$", message = "Enter letters only.")
	@NotBlank(message = "Enter your full name.")
	@Pattern(regexp = "^([A-ZÁÀẢÃẠĂẮẰẲẴẶÂẤẦẨẬẪÉÈẺẼẸÊẾỀỂỄỆÍÌỈĨỊÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢÚÙỦŨỤƯỨỪỬỮỰÝỲỶỸỴĐ]"
			+ "[a-záàảãạăắằẳẵặâấầẩậẫéèẻẽẹêếềểễệíìỉĩịóòỏõọôốồổỗộơớờởỡợúùủũụưứừửữựýỳỷỹỵđ]*"
			+ "( ))*([A-ZÁÀẢÃẠĂẮẰẲẴẶÂẤẦẨẬẪÉÈẺẼẸÊẾỀỂỄỆÍÌỈĨỊÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢÚÙỦŨỤƯỨỪỬỮỰÝỲỶỸỴĐ]"
			+ "[a-záàảãạăắằẳẵặâấầẩậẫéèẻẽẹêếềểễệíìỉĩịóòỏõọôốồổỗộơớờởỡợúùủũụưứừửữựýỳỷỹỵđ]*)$", message = "Enter letters only.")
	private String fullname;

	@NotBlank(message = "Enter your username.")
	private String username;

	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@-_])(?=\\S)[a-zA-Z0-9@\\-_]{8,20}$", message = "Enter from 8 to 20 characters, combine uppercase(s), lowercase(s), digit(s) and special character(s) (@-_).")
	private String password;

	private Date dateOfBirth;

	@Enumerated(EnumType.ORDINAL)
	private Gender gender;

	@NotBlank(message = "Enter your ID card number.")
	private String idCard;

	@Email(message = "Enter valid email.")
	private String email;

	@Pattern(regexp = "^\\+84[0-9]{9}$", message = "Enter your phone number in format: +84 and 9 digits following.")
	private String phone;

	@NotBlank(message = "Enter your address.")
	private String address;

	@Override
	public boolean supports(Class<?> clazz) {
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserDto userDto = (UserDto) target;
		LocalDate currentDate = LocalDate.now();
		LocalDate birthday = userDto.dateOfBirth.toLocalDate();
		int age = Period.between(birthday, currentDate).getYears();

		if (age < 18) {
			errors.rejectValue("dateOfBirth", "create.not18", "Not enough 18 years old.");
		}

	}

}
