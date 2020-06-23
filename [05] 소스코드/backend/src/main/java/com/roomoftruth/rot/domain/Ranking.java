package com.roomoftruth.rot.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class Ranking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long rankingId;

	private long userNum;
	private String nickname;
	private String agentName;
	private String userPicture;
	private String agentPicture;
	private int rnk;
	private int point;

	@Builder
	public Ranking(long userNum, String nickname, String agentName, String userPicture, String agentPicture, int rnk, int point) {
		this.userNum = userNum;
		this.nickname = nickname;
		this.agentName = agentName;
		this.userPicture = userPicture;
		this.agentPicture = agentPicture;
		this.rnk = rnk;
		this.point = point;
	}
	
}
