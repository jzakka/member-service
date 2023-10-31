package com.example.memberservice.filter;

import com.example.memberservice.client.JoinServiceClient;
import com.example.memberservice.dto.MemberDto;
import com.example.memberservice.service.MemberService;
import com.example.memberservice.vo.RequestLogin;
import com.example.memberservice.vo.TokenJoinAuthority;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private MemberService memberService;
    private JoinServiceClient joinServiceClient;
    private Environment env;

    public AuthenticationFilter(AuthenticationManager authenticationManager,
                                MemberService memberService,
                                JoinServiceClient joinServiceClient,
                                Environment env) {
        super(authenticationManager);
        this.memberService = memberService;
        this.joinServiceClient = joinServiceClient;
        this.env = env;
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        ObjectMapper objectMapper = new ObjectMapper();

        RequestLogin credential = objectMapper.readValue(request.getInputStream(), RequestLogin.class);

        return getAuthenticationManager()
                .authenticate(new UsernamePasswordAuthenticationToken(
                        credential.getEmail(),
                        credential.getPassword(),
                        new ArrayList<>()
                ));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        // 스프링 시큐리티 프레임워크 상 변수이름이 username으로 돼있으나 실제 값은 사용자 email임
        String username = ((User) authResult.getPrincipal()).getUsername();
        MemberDto userDetails = memberService.getMemberByEmail(username);

        String token = Jwts.builder()
                .setSubject(userDetails.getMemberId())
                .setExpiration(new Date(System.currentTimeMillis()
                        + Long.parseLong(env.getProperty("token.expiration_time"))))
                .signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret"))
                .compact();

        List<TokenJoinAuthority> joinedGather = joinServiceClient.getJoinedGather(userDetails.getMemberId(), "Bearer " + token);

        /**
         * 사용자가 참여한 모임정보를 얻어오고 토큰을 한번 더 생성
         */
        token = Jwts.builder()
                .setSubject(userDetails.getMemberId())
                .setExpiration(new Date(System.currentTimeMillis()
                        + Long.parseLong(env.getProperty("token.expiration_time"))))
                .claim("gatherIds", joinedGather)
                .signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret"))
                .compact();

        response.addHeader("token", token);
        response.addHeader("userId", userDetails.getMemberId());
    }
}
