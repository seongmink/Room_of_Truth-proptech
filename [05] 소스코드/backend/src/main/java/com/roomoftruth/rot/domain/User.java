package com.roomoftruth.rot.domain;

import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "user")
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

	private String birth;
	private Gender gender;
	private String address;
	private String picture;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "entered_at")
	private LocalDateTime enteredAt;
	
//	@OneToMany(mappedBy = "user")
//	private List<Search> searches = new ArrayList<>();
//
//	@OneToOne(mappedBy = "user")
//	private Interest interest;
//
//	@OneToOne(mappedBy = "user")
//	private Agent agent;
//

	@Builder
	public User(long num, String nickname, Auth auth, String picture) {
		this.num = num;
		this.nickname = nickname;
		this.auth = auth;
		this.picture = picture;
		this.createdAt = LocalDateTime.now();
		this.enteredAt = LocalDateTime.now();
	}
	
	public String getAuthKey() {
		return this.auth.getKey();
	}

	public String getGenderKey() { return this.gender.getKey(); }

}
