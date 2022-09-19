package com.nineplus.pharmacy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nineplus.pharmacy.model.Medicine;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long>{

}
