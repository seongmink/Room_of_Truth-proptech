package com.roomoftruth.rot.domain;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "search")
@Getter
@NoArgsConstructor
public class Search extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "search_id")
	private long id;

//	@Column(updatable = false, insertable = false, name = "user_id")
//	private long userId;

	private String keyword;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
}
