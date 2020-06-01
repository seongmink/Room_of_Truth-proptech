package com.roomoftruth.rot.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Attract extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long attentionId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JsonManagedReference
	private User user;

	@Builder
	public Attract(User user) {
		this.user = user;
	}
}
