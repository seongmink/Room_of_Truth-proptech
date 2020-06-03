package com.roomoftruth.rot.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Agent extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long agentId;

	private String name;
	private String representative;
	private String license;
	private String address;
	private String picture;
	private int count;
	private int point;
	private int rnk;

	@OneToOne(cascade = CascadeType.ALL)
	@JsonManagedReference
	private User user;

//	@OneToMany(mappedBy = "agent")
//	@JsonBackReference
//	private List<Building> buildings = new ArrayList<>();

	@Builder
	public Agent(String name, String representative, String license, String address,
				 String picture, int count, int point, int rnk, User user) {
		this.name = name;
		this.representative = representative;
		this.license = license;
		this.address = address;
		this.picture = picture;
		this.count = count;
		this.point = point;
		this.user = user;
		this.rnk = rnk;
	}

	@Transactional
	public void updateRanking(int rnk) {
		this.rnk = rnk;
	}

	@Override
	public String toString() {
		return "Agent{" +
				"agentId=" + agentId +
				", name='" + name + '\'' +
				", representative='" + representative + '\'' +
				", license='" + license + '\'' +
				", address='" + address + '\'' +
				", picture='" + picture + '\'' +
				", count=" + count +
				", point=" + point +
				", rnk=" + rnk +
				'}';
	}
}
