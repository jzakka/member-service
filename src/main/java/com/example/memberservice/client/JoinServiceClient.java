package com.example.memberservice.client;

import com.example.memberservice.vo.TokenJoinAuthority;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient("join-service")
public interface JoinServiceClient {
    @GetMapping(path = "/joins/{memberId}", headers = {})
    List<TokenJoinAuthority> getJoinedGather(@PathVariable String memberId, @RequestHeader(name = "Authorization") String bearerToken);
}
