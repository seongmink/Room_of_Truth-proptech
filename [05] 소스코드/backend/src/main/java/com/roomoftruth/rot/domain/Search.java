package com.roomoftruth.rot.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "search")
@Getter
@NoArgsConstructor
public class Search extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long searchId;

	private String keyword;

	@ManyToOne(cascade = CascadeType.ALL)
	private User user;

	@Builder
	public Search(User user, String keyword) {
		this.user = user;
		this.keyword = keyword;
	}
}
