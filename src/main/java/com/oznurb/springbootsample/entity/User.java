package com.oznurb.springbootsample.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Entity
@Table(name= "users", 
	uniqueConstraints = { @UniqueConstraint(columnNames = "userName"),
					 	  @UniqueConstraint(columnNames = "email")
})
@Getter
@Setter
@NoArgsConstructor
public class User implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@NotBlank(message = "Username is mandotory!")
	@Size(max = 20)
	@Column(name = "USERNAME", nullable = false, length = 30)
	private String userName;
	@NotBlank(message = "Email is mandotory!")
	@Size(max = 50)
	@Column(name = "EMAIL", nullable = false, length = 60)
	private String email;
	@NotBlank(message = "Password is mandotory!")
	@Size(max = 120)
	@Column(name = "PASSWORD", nullable = false, length = 200)
	private String password;
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "USER_ROLES",
				joinColumns = @JoinColumn(name = "USER_ID"),
				inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
	private Set<Role> roles  = new HashSet<Role>();
	
	
	public User(@NotBlank(message = "Username is mandotory!") @Size(max = 20) String userName,
			@NotBlank(message = "Email is mandotory!") @Size(max = 50) String email,
			@NotBlank(message = "Password is mandotory!") @Size(max = 120) String password) {
		super();
		this.userName = userName;
		this.email = email;
		this.password = password;
	}
	
	
	
	
	

	
}
