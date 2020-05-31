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
	@Column(name = "interest_id")
	private Long id;

	private String first;
	private String second;
	private String third;

	@OneToOne
	@JoinColumn(name = "user_num")
	private User user;
	
	@Builder
	public Interest(String first, String second, String third) {
		this.first = first;
		this.second = second;
		this.third = third;
	}
	
}
