package com.roomoftruth.rot.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Gender {
	
    MAN("GENDER_MAN", "남자"),
	WOMAN("GENDER_WOMAN", "여자");

    private final String key;
    private final String title;
}
