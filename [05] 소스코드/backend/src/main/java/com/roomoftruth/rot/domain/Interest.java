package com.roomoftruth.rot.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "interest")
@Getter
@NoArgsConstructor
public class Interest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long interestId;

	private String sd;
	private String sgg;
	private String first;
	private String second;
	private String third;
	private String birth;
	private String gender;

	@OneToOne(cascade = CascadeType.ALL)
	private User user;

	@Builder
	public Interest(User user, String sd, String sgg, String first, String second, String third, String gender, String birth) {
		this.user = user;
		this.sd = sd;
		this.sgg = sgg;
		this.first = first;
		this.second = second;
		this.third = third;
		this.birth = birth;
		this.gender = gender;
	}


}
