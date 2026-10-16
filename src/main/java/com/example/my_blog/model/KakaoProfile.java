package com.example.my_blog.model;

import lombok.Data;

import javax.annotation.processing.Generated;

// 왜 KakaoProfile을 모델 패키지에서 클래스로 생성했나?
// -> UserController에서 사용자 정보 조회를 위해 쓰이는
// kakao 응답 데이터를 오브젝트 형태로 쉽게 재사용하기 위해서
@Data // getter/setter
@Generated("jsonschema2pojo")
public class KakaoProfile {

    public Long id;
    public String connected_at;
    public Properties properties;
    public KakaoAccount kakao_account;

    // Properties, kakaoAccount 클래스를
    // KakaoProfile의 내부 클래스로
    @Data // getter/setter
    public static class Properties {

        public String nickname;
        public String profile_image;
        public String thumbnail_image;

    }

    @Data // getter/setter
    public static class KakaoAccount {

        public Boolean profile_nickname_needs_agreement;
        public Boolean profile_image_needs_agreement;
        public Profile profile;
        public Boolean has_email;
        public Boolean email_needs_agreement;
        public Boolean is_email_valid;
        public Boolean is_email_verified;
        public String email;


        // Profile 클래스는 KakaoAccount의 내부 클래스로
        @Data // getter/setter
        public static class Profile {

            public String nickname;
            public String thumbnail_image_url;
            public String profile_image_url;
            public Boolean is_default_image;
            public Boolean is_default_nickname;

        }

    }

}


