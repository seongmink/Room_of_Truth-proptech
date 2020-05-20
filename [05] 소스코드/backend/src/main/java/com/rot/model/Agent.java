package com.rot.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "agent")
public class Agent {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long num;

	private String name;
	private String representative;
	private String license;
	private String address;
	private String uPicture;
	private String Picture;
	private String phoneNum;
	private int rnk;
	private int count;
	private int point; // 포인트 받은 횟수
	private int delflag; // 0이면 활성화, 1이면 비활성화
	private LocalDateTime createdAt;
}
