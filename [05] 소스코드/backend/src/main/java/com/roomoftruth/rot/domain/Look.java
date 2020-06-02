//package com.roomoftruth.rot.domain;
//
//import com.fasterxml.jackson.annotation.JsonManagedReference;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//
//@Entity
//@Getter
//@NoArgsConstructor
//public class Look extends BaseTimeEntity {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long lookId;
//
//	@ManyToOne(cascade = CascadeType.ALL)
//	@JsonManagedReference
//	private User user;
//
//	@Builder
//	public Look(User user) {
//		this.user = user;
//	}
//}
