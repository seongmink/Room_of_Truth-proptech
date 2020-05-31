package com.roomoftruth.rot.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Auth {

    GENERAL("AUTH_GENERAL", "일반사용자"),
	AGENT("AUTH_AGENT", "공인중개사");

    private final String key;
    private final String title;
}
