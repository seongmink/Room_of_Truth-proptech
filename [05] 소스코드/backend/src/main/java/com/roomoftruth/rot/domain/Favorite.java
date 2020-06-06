package com.roomoftruth.rot.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Columns;

import javax.jws.soap.SOAPBinding;
import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Favorite extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long favoriteId;

	private int score;

	@OneToOne(cascade = CascadeType.ALL)
	@JsonBackReference
	private Around around;

	@ManyToOne(cascade = CascadeType.ALL)
	@JsonManagedReference
	private User user;

	@Builder
	public Favorite(int score, Around around) {
		this.score = score;
		this.around = around;
	}



}
