package cn.itcast.bos.domain.bc;

// Generated 2017-7-27 14:55:42 by Hibernate Tools 3.2.2.GA

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import cn.itcast.bos.domain.qp.NoticeBill;
import cn.itcast.bos.domain.qp.WorkBill;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Staff generated by hbm2java
 */
@Entity
@Table(name = "bc_staff", catalog = "maven_ssh")
public class Staff implements java.io.Serializable {

	private String id;
	private String name;
	private String telephone;
	private String haspda;
	private Integer deltag = 1;
	private String station;
	private String standard;
	private String newtelephone;
	private Set<WorkBill> workBills = new HashSet<WorkBill>(0);
	private Set<NoticeBill> noticeBills = new HashSet<NoticeBill>(0);
	private Set<DecidedZone> decidedZones = new HashSet<DecidedZone>(0);

	public Staff() {
	}

	public Staff(String name, String telephone, String haspda, Integer deltag,
			String station, String standard, String newtelephone,
			Set<WorkBill> workBills, Set<NoticeBill> noticeBills,
			Set<DecidedZone> decidedZones) {
		this.name = name;
		this.telephone = telephone;
		this.haspda = haspda;
		this.deltag = deltag;
		this.station = station;
		this.standard = standard;
		this.newtelephone = newtelephone;
		this.workBills = workBills;
		this.noticeBills = noticeBills;
		this.decidedZones = decidedZones;
	}

	@GenericGenerator(name = "generator", strategy = "uuid")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, length = 32)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "NAME", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "TELEPHONE", length = 20)
	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Column(name = "HASPDA", length = 20)
	public String getHaspda() {
		return this.haspda;
	}

	public void setHaspda(String haspda) {
		this.haspda = haspda;
	}

	@Column(name = "DELTAG")
	public Integer getDeltag() {
		return this.deltag;
	}

	public void setDeltag(Integer deltag) {
		this.deltag = deltag;
	}

	@Column(name = "STATION", length = 40)
	public String getStation() {
		return this.station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	@Column(name = "STANDARD", length = 100)
	public String getStandard() {
		return this.standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	@Column(name = "newtelephone")
	public String getNewtelephone() {
		return this.newtelephone;
	}

	public void setNewtelephone(String newtelephone) {
		this.newtelephone = newtelephone;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "staff")
	@JSONField(serialize = false)
	public Set<WorkBill> getWorkBills() {
		return this.workBills;
	}

	public void setWorkBills(Set<WorkBill> workBills) {
		this.workBills = workBills;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "staff")
	@JSONField(serialize = false)
	public Set<NoticeBill> getNoticeBills() {
		return this.noticeBills;
	}

	public void setNoticeBills(Set<NoticeBill> noticeBills) {
		this.noticeBills = noticeBills;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "staff")
	@JSONField(serialize = false)
	public Set<DecidedZone> getDecidedZones() {
		return this.decidedZones;
	}

	public void setDecidedZones(Set<DecidedZone> decidedZones) {
		this.decidedZones = decidedZones;
	}

}
