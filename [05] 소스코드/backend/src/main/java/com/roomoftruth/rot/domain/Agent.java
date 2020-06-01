package com.roomoftruth.rot.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Agent extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long attentionId;

	private String name;
	private String representative;
	private String license;
	private String address;
	private String picture;
	private String phoneNum;
	private int count;
	private int point;

	@OneToOne(cascade = CascadeType.ALL)
	@JsonManagedReference
	private User user;

//	@OneToMany(mappedBy = "agent")
//	@JsonBackReference
//	private List<Building> buildings = new ArrayList<>();

	@Builder
	public Agent(String name, String representative, String license, String address,
				 String picture, String phoneNum, int count, int point, User user) {
		this.name = name;
		this.representative = representative;
		this.license = license;
		this.address = address;
		this.picture = picture;
		this.phoneNum = phoneNum;
		this.count = count;
		this.point = point;
		this.user = user;
	}

}
