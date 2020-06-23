package com.roomoftruth.rot.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
	@JsonBackReference
	private List<Search> searches = new ArrayList<>();

	@OneToOne(mappedBy = "user")
	@JsonBackReference
	private Interest interest;

	@OneToOne(mappedBy = "user")
	@JsonBackReference
	private Agent agent;

	@OneToMany(mappedBy = "user")
	@JsonBackReference
	private List<Favorite> favorites = new ArrayList<>();

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

	public void updateAuth() {
		this.auth = "AGENT";
	}

}
