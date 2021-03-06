package cn.itcast.bos.domain.auth;

// Generated 2017-7-29 22:27:44 by Hibernate Tools 3.2.2.GA

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Function generated by hbm2java
 */
@Entity
@Table(name = "auth_function", catalog = "maven_ssh", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Function implements java.io.Serializable {

	private String id;
	private String name;
	private String code;
	private String description;
	private Set<Role> roles = new HashSet<Role>(0);

	public Function() {
	}

	public Function(String name, String code, String description,
			Set<Role> roles) {
		this.name = name;
		this.code = code;
		this.description = description;
		this.roles = roles;
	}

	@GenericGenerator(name = "generator", strategy = "uuid")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length = 32)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "name", unique = true)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "code")
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "description")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "role_function", catalog = "maven_ssh", joinColumns = { @JoinColumn(name = "function_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "role_id", nullable = false, updatable = false) })
	@JSONField(serialize = false)
	public Set<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

}
