package com.nineplus.pharmacy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nineplus.pharmacy.exception.ResourceNotFoundException;
import com.nineplus.pharmacy.model.Employee;
import com.nineplus.pharmacy.model.Medicine;
import com.nineplus.pharmacy.repository.MedicineRepository;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class PharmacyStoreController {
	@Autowired
	private MedicineRepository medicineRepository;
	
	@GetMapping("/pharmacies")
	public List<Medicine> getAllMedicine() {
		return medicineRepository.findAll();
	}
	
	@GetMapping("/pharmacies/{id}")
	public ResponseEntity<Medicine> getEmployeeById(@PathVariable(value = "id") Long medicineId)
			throws ResourceNotFoundException {
		Medicine medicine = medicineRepository.findById(medicineId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + medicineId));
		return ResponseEntity.ok().body(medicine);
	}
	
	@PostMapping("/pharmacies")
	public Medicine createMedicine(@Valid @RequestBody Medicine medicine) {
		return medicineRepository.save(medicine);
	}

	/*
	 * @PutMapping("/pharmacies/{id}") public ResponseEntity<Medicine>
	 * updateEmployee(@PathVariable(value = "id") Long employeeId,
	 * 
	 * @Valid @RequestBody Employee employeeDetails) throws
	 * ResourceNotFoundException { Employee employee =
	 * employeeRepository.findById(employeeId) .orElseThrow(() -> new
	 * ResourceNotFoundException("Employee not found for this id :: " +
	 * employeeId));
	 * 
	 * employee.setEmailId(employeeDetails.getEmailId());
	 * employee.setLastName(employeeDetails.getLastName());
	 * employee.setFirstName(employeeDetails.getFirstName()); final Employee
	 * updatedEmployee = employeeRepository.save(employee); return
	 * ResponseEntity.ok(updatedEmployee); }
	 * 
	 * @DeleteMapping("/employees/{id}") public Map<String, Boolean>
	 * deleteEmployee(@PathVariable(value = "id") Long employeeId) throws
	 * ResourceNotFoundException { Employee employee =
	 * employeeRepository.findById(employeeId) .orElseThrow(() -> new
	 * ResourceNotFoundException("Employee not found for this id :: " +
	 * employeeId));
	 * 
	 * employeeRepository.delete(employee); Map<String, Boolean> response = new
	 * HashMap<>(); response.put("deleted", Boolean.TRUE); return response; }
	 */
	
}
