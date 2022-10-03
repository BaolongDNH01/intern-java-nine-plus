package com.nineplus.pharmacy.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity(name = "TUser")
@Table(name = "t_user")
@Data
public class TUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false, precision = 19)
    private long id;

    @Column(name = "USER_NM", nullable = false)
    private String userNm;
    
    @Column(name = "PASSWORD", nullable = false)
    private String Password;
    
    @Column(name = "EMAIL", nullable = false)
    private String email;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;
    
    @OneToOne
    @JoinColumn(name = "t_role_id", referencedColumnName = "id")
    private TRole tRole;

}
