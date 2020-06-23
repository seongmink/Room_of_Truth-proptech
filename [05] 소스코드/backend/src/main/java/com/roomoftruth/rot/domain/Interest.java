package com.roomoftruth.rot.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.roomoftruth.rot.dto.InterestSaveRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
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
	private int birth;
	private String gender;

	@OneToOne(cascade = CascadeType.ALL)
	@JsonManagedReference
	private User user;

	@Builder
	public Interest(User user, String sd, String sgg, String first, String second, String third, String gender, int birth) {
		this.user = user;
		this.sd = sd;
		this.sgg = sgg;
		this.first = first;
		this.second = second;
		this.third = third;
		this.birth = birth;
		this.gender = gender;
	}

	public void update(InterestSaveRequestDto requestDto) {
		this.sd = requestDto.getSd();
		this.sgg = requestDto.getSgg();
		this.first = requestDto.getFirst();
		this.second = requestDto.getSecond();
		this.third = requestDto.getThird();
		this.birth = requestDto.getBirth();
		this.gender = requestDto.getGender();
	}

}
