package com.nineplus.pharmacy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nineplus.pharmacy.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{

}
