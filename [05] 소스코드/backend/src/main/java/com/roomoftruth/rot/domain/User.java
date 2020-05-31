package com.roomoftruth.rot.domain;

import com.roomoftruth.rot.dto.UserUpdateRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "user")
@NoArgsConstructor
public class User extends BaseTimeEntity {
	
	@Id
	private long num;
	private String nickname;
	
	@Enumerated(EnumType.STRING)
	private Auth auth;

	private String phoneNum;

	private String birth;
	private Gender gender;
	private String address;
	private String picture;

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
	}

	public void update(UserUpdateRequestDto updateRequestDto) {
		this.phoneNum = updateRequestDto.getPhoneNum();
		this.birth = updateRequestDto.getBirth();
		this.gender = updateRequestDto.getGender();
		this.address = updateRequestDto.getAddress();
	}
	
	public String getAuthKey() {
		return this.auth.getKey();
	}

	public String getGenderKey() { return this.gender.getKey(); }

}
