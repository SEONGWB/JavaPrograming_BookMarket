package org.example.repository;

import org.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

// User 엔티티의 ID 타입은 String(userId)이므로 두 번째 인자에 String을 넣습니다.
public interface UserRepository extends JpaRepository<User, String> {
}