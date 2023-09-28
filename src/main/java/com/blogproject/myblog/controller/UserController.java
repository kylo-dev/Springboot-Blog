package com.blogproject.myblog.controller;

import com.blogproject.myblog.config.auth.PrincipalDetail;
import com.blogproject.myblog.dto.user.UpdateUserRequest;
import com.blogproject.myblog.entity.KakaoProfile;
import com.blogproject.myblog.entity.OAuthToken;
import com.blogproject.myblog.entity.User;
import com.blogproject.myblog.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @Value("${cos.key}")
    private String cosKey;

    /**
     * 인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/** 허용
     * 그냥 주소가 / 이면 index.html 허용
     * static 이하에 있는 /js/**, /css/**, /image/** 허용
     */
    @GetMapping("/auth/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    @GetMapping("/auth/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    @GetMapping("/user/updateForm")
    public String update(@AuthenticationPrincipal PrincipalDetail principal, Model model) {
        model.addAttribute("principal", principal);
        return "user/updateForm";
    }

    @GetMapping("/auth/kakao/callback")
    public String kakaoCallback(String code) { // Data를 return 하는 컨트롤러 함수
        // POST 방식으로 key=value 데이터를 요청 -> 카카오
        RestTemplate rt = new RestTemplate();

        // HttpHeader Object 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

        // HttpBody Object 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "1f0c8af976d2a64af21867e75e6198ea");
        params.add("redirect_uri", "http://localhost:8082/auth/kakao/callback");
        params.add("code", code);

        // HttpHeader와 HttpBody를 하나의 Object에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest
                = new HttpEntity<>(params, headers);

        // Http 요청하기 - POST방식으로 - 그리고 response 변수의 응답(토큰)을 받음
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // Gson, Json Simple, ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oAuthToken = null;
        try {
            oAuthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch(JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println("1. 카카오 액세스 토큰 = " + oAuthToken.getAccess_token());

        RestTemplate rt2 = new RestTemplate();

        // HttpHeader Object 생성
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer "+oAuthToken.getAccess_token()); // 추가
        headers2.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

        // HttpHeader와 HttpBody를 하나의 Object에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest
                = new HttpEntity<>(headers2);

        // Http 요청하기 - POST방식으로 - 그리고 response 변수의 응답(토큰)을 받음
        ResponseEntity<String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );
        System.out.println("2. 카카오 프로필 정보 받아오기 = " + response2.getBody());

        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
        } catch(JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // User Object : username, password, email
        System.out.println("카카오 아이디(번호) : " + kakaoProfile.getId());
        System.out.println("카카오 이메일 : " + kakaoProfile.getKakao_account().getEmail());

        System.out.println("블로그서버 username : " + kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId());
        System.out.println("블로그서버 email : " + kakaoProfile.getKakao_account().getEmail());
        // UUID tempPassword = UUID.randomUUID();
        String tempPassword = cosKey;
        System.out.println("블로그서버 password: " + tempPassword);

        User kakaoUser = User.builder()
                .username(kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId())
                .password(tempPassword)
                .email(kakaoProfile.getKakao_account().getEmail())
                .oauth("kakao")
                .build();

        // 가입자 혹은 비가입자 체크해서 처리
        User originUser = userService.validationDuplicator(kakaoUser);

        if (originUser.getUsername() == null) {
            System.out.println("기존 회원이 아니기에 자동 회원가입을 진행합니다.");
            userService.save(kakaoUser);
            System.out.println("카카오 아이디를 데이터베이스에 저장");
        }
        System.out.println("자동 로그인을 진행");

        // 로그인처리
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), tempPassword));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/";
    }
}
