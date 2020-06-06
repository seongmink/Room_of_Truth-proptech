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
public class Favorite {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long favoriteId;

	private int score;

	@ManyToOne(cascade = CascadeType.ALL)
	@JsonBackReference
	private Around around;

	@ManyToOne(cascade = CascadeType.ALL)
	@JsonManagedReference
	private User user;

	@Builder
	public Favorite(User user, int score, Around around) {
		this.user = user;
		this.score = score;
		this.around = around;
	}



}
