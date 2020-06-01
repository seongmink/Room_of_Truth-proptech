package com.roomoftruth.rot.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class User extends BaseTimeEntity {
	
	@Id
	private long num;

	private String nickname;
	private String auth;
	private String picture;

	@OneToMany(mappedBy = "user")
	private List<Search> searches = new ArrayList<>();

	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
	private Interest interest;
//
//	@OneToOne(mappedBy = "user")
//	private Agent agent;
//
	// 찜
	// 본것도

	@Builder
	public User(long num, String nickname, String auth, String picture, Interest interest) {
		this.num = num;
		this.nickname = nickname;
		this.auth = auth;
		this.picture = picture;
		this.interest = interest;
	}

	public void update(String nickname, String picture) {
		this.nickname = nickname;
		this.picture = picture;
	}
	
}
