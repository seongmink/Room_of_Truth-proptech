package com.roomoftruth.rot.service;

import com.fasterxml.jackson.databind.JsonNode;

public interface IKaKaoAPIService {

    public JsonNode getKaKaoUserInfo(String access_Token);
    public String redirectToken(JsonNode json);

}
