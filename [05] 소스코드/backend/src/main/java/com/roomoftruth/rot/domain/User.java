package com.roomoftruth.rot.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private long id;

	private long num;
	private String nickname;
	
	@Enumerated(EnumType.STRING)
	private Auth auth;
	
	private String phoneNum;
	private String birthYear;
	private Gender gender;
	private String address;
	private String picture;

	private LocalDateTime createdAt;
	private LocalDateTime enteredAt;
	
	@OneToMany(mappedBy = "user")
	private List<Search> searches = new ArrayList<>();
	
	@OneToOne(mappedBy = "user")
	private Interest interest;

//	@OneToOne(mappedBy = "user")
//	private Agent agent;
//
//	public User update(long num, String nickname, String picture) {
//		this.nickname = nickname;
//		this.picture = picture;
//
//		return this;
//	}
	
	public String getAuthKey() {
		return this.auth.getKey();
	}

	public String getGenderKey() { return this.gender.getKey(); }

}
