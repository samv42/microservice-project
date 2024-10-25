package co.codingnomads.spring.usermicroservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserById(User user);
    User findByUsername(String username);
}
