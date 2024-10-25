package co.codingnomads.spring.usermicroservice;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    @LoadBalanced
    RestTemplate restTemplate;

    public User getUserById(Long id) {
        Optional<User> optional;
        if ((optional = userRepository.findById(id)).isEmpty()) {
            return null;
        } else {
            return optional.get();
        }
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User createNewUser(User newUser) {
        newUser.setPassword(newUser.getPassword());
        User user = userRepository.save(newUser);
        ResponseEntity<Cart> response = restTemplate.postForEntity
                ("http://CART-MICROSERVICE/cart/" + user.getId(), null, Cart.class);
        if(response.getStatusCode() != HttpStatus.OK){
            throw new RuntimeException("Cart object not returned.");
        }
        return user;
    }

    public User updateUser(User updatedUser) {
        User user = userRepository.findByUsername(updatedUser.getUsername());
        BeanUtils.copyProperties(updatedUser, user);
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
