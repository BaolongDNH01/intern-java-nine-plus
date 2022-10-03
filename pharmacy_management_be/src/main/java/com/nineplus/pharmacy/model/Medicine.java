package com.nineplus.pharmacy.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "medicine")
public class Medicine {
	private long id;
	private String medicineName;
	// Công ty sản xuất
	private String medicineCompany;
	// Xuất xứ
	private String origin;
	// Ngày sản xuất
	private Date manufactureDate;
	private Date expireDate;
	private long amount;
	private String unit;
	private String status;

	public Medicine() {
		
	}

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "medicine_name", nullable = false)
	public String getMedicineName() {
		return medicineName;
	}

	public void setMedicineName(String medicineName) {
		this.medicineName = medicineName;
	}

	@Column(name = "medicine_company", nullable = false)
	public String getMedicineCompany() {
		return medicineCompany;
	}

	public void setMedicineCompany(String medicineCompany) {
		this.medicineCompany = medicineCompany;
	}
	@Column(name = "origin", nullable = false)
	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	@Column(name = "manufacture_date", nullable = false)
	public Date getManufactureDate() {
		return manufactureDate;
	}

	public void setManufactureDate(Date manufactureDate) {
		this.manufactureDate = manufactureDate;
	}

	@Column(name = "expire_date", nullable = false)
	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	@Column(name = "amount", nullable = false)
	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	@Column(name = "unit", nullable = false)
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column(name = "status", nullable = false)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Medicine [id=" + id + ", medicineName=" + medicineName + ", medicineCompany=" + medicineCompany
				+ ", origin=" + origin + ", manufactureDate=" + manufactureDate + ", expireDate=" + expireDate
				+ ", amount=" + amount + ", unit=" + unit + ", status=" + status + "]";
	}

	
	
}
