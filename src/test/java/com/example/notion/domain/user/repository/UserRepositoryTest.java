package com.example.notion.domain.user.repository;

import com.example.notion.domain.user.entitiy.User;
import com.example.notion.global.config.TestConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(TestConfig.class)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("이메일로 사용자 찾기 테스트")
    void findByEmailTest() {
        // given : 테스트 데이터 준비
        String email = "test@test.com";
        String password = "password";
        String name = "tester";

        User user = User.builder()
                .email(email)
                .password(password)
                .name(name)
                .build();
        userRepository.save(user);

        // when : 실제 테스트하고 싶은 동작 실행
        Optional<User> foundUser = userRepository.findByEmail(email);

        // then : 결과 검증
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo(email);
        assertThat(foundUser.get().getName()).isEqualTo(name);
    }

    @Test
    @DisplayName("이메일 중복 체크 테스트")
    void existsByEmailTest() {
        // given
        String email = "test@test.com";
        User user = User.builder()
                .email(email)
                .password("password")
                .name("tester")
                .build();

        userRepository.save(user);

        // when
        boolean exists = userRepository.existsByEmail(email);

        // then
        assertThat(exists).isTrue();
    }
}
