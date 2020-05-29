package com.roomoftruth.rot.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Auth {
	
    NORMAL("ROLE_USER", "일반사용자"),
	AGENT("ROLE_AGENT", "공인중개사");

    private final String key;
    private final String title;
}
