package com.roomoftruth.rot.domain;

import javax.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "interest")
@Getter
@NoArgsConstructor
public class Interest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "interest_id")
	private Long id;

	@Column(name = "user_id")
	private Long userId;

	private String first;
	private String second;
	private String third;

	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;
	
//	@Builder
//	public Interest(Long num, String first, String second, String third) {
//		this.num = num;
//		this.first = first;
//		this.second = second;
//		this.third = third;
//	}
	
}
