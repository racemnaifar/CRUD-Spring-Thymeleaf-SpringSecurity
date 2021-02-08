package tn.iit.entity;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.EqualsAndHashCode.Include;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "t_client")
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Include
    @Column(name = "id")
	public Long id;
	
	@Column(name = "cin",unique=true)
    @NotEmpty(message = "*Please provide a cin")
    public String cin;
	
    @Column(name = "nom")
    @NotEmpty(message = "*Please provide a first name")
    public String nom;
	
    @Column(name = "prenom")
    @NotEmpty(message = "*Please provide a last name")
    public String prenom;
  
    @Column(name = "email")
    @Email(message = "*Please provide a valid Email")
    @NotEmpty(message = "*Please provide an email")
    public String email;

    @JsonIgnore
    @OneToMany(mappedBy = "client", cascade = CascadeType.REMOVE,fetch = FetchType.EAGER)
    public List<Compte> comptes;
    
    @OneToOne(cascade = CascadeType.REMOVE)
    private User user;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Client(Long id,  String cin,
			String nom,
			String prenom,
		 String email) {
		super();
		this.id = id;
		this.cin = cin;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
	}

	public Client() {
		super();
	}
	

    
    
}
