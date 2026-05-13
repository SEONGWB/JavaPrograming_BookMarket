package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.Cart;
import org.example.entity.Role;
import org.example.entity.User;
import org.example.repository.CartRepository;
import org.example.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuthProfile profile = OAuthProfile.from(registrationId, oAuth2User.getAttributes());

        User user = userRepository.findByLoginId(profile.loginId())
                .orElseGet(() -> userRepository.save(User.builder()
                        .loginId(profile.loginId())
                        .password(null)
                        .name(profile.name())
                        .role(Role.USER)
                        .provider(registrationId)
                        .build()));

        cartRepository.findByUser(user)
                .orElseGet(() -> cartRepository.save(Cart.builder().user(user).build()));

        Map<String, Object> attributes = new HashMap<>(oAuth2User.getAttributes());
        attributes.put("userId", user.getId());
        attributes.put("loginId", user.getLoginId());
        attributes.put("name", user.getName());

        return new DefaultOAuth2User(oAuth2User.getAuthorities(), attributes, profile.nameAttributeKey());
    }

    private record OAuthProfile(String loginId, String name, String nameAttributeKey) {
        @SuppressWarnings("unchecked")
        static OAuthProfile from(String registrationId, Map<String, Object> attributes) {
            if ("naver".equals(registrationId)) {
                Map<String, Object> response = (Map<String, Object>) attributes.get("response");
                String id = String.valueOf(response.get("id"));
                String name = String.valueOf(response.getOrDefault("name", "네이버 사용자"));
                return new OAuthProfile("naver_" + id, name, "response");
            }

            String id = String.valueOf(attributes.get("sub"));
            String name = String.valueOf(attributes.getOrDefault("name", "구글 사용자"));
            return new OAuthProfile("google_" + id, name, "sub");
        }
    }
}
