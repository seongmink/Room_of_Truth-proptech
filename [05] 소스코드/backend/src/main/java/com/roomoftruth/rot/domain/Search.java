package com.roomoftruth.rot.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Search extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long searchId;

	private String keyword;

	@ManyToOne(cascade = CascadeType.ALL)
	@JsonManagedReference
	private User user;

	@Builder
	public Search(User user, String keyword) {
		this.user = user;
		this.keyword = keyword;
	}

	public void updateTime(LocalDateTime now){
		this.updateTimeNow(now);
	}

}
