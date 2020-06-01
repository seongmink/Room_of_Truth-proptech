package com.roomoftruth.rot.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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

	public void logined(LocalDateTime now){
		this.login(now);
	}

}
