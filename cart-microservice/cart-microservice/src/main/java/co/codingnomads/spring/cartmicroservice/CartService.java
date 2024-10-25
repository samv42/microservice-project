package co.codingnomads.spring.cartmicroservice;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CartService {
    @Autowired
    CartRepository cartRepository;
    @Autowired
    EurekaClient discoveryClient;

    @Autowired
    @LoadBalanced
    RestTemplate restTemplate;

    public String userServiceUrl() {
        InstanceInfo instance = discoveryClient.getNextServerFromEureka("ITEM-MICROSERVICE", false);
        return instance.getHomePageUrl();
    }

    public Cart addNewCart(Long userId) {
        Cart cart = new Cart();
        cart.setUserId(userId);
        return cartRepository.save(cart);
    }

    public Cart getCartByUserId(Long userId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            cart = Cart.builder().userId(userId).build();
            cart = cartRepository.save(cart);
        }
        return cart;
    }

    public List<CartItem> getAllCartItemsInCart(Long userId) {
        List<CartItem> items = cartRepository.findByUserId(userId).getItems();
        return items;
    }

    @Transactional
    public Cart addCartItem(Long itemId, Long userId) {
        Cart cart = getCartByUserId(userId);
        CartItem item = null;
        for(CartItem cartItem : cart.getItems()){
            if(cartItem.getItemId().equals(itemId)){
                item = cartItem;
                break;
            }
        }
        if(item == null){
            CartItem item2 = CartItem.builder().itemId(itemId).amount(1).build();
            cart.addCartItem(item2);
        }else{
            item.setAmount(item.getAmount() + 1);
        }
        /*if (!cart.getItems().isEmpty()) {
            for (CartItem item : cart.getItems()) {
                if (item.getItemId().equals(itemId)) {
                    item.setAmount(item.getAmount() + 1);
                    return cartRepository.save(cart);
                }
            }
        }else {
            CartItem item = CartItem.builder().itemId(itemId).amount(1).build();
            cart.addCartItem(item);
        }*/
        return cartRepository.save(cart);
    }

    @Transactional
    public Cart removeCartItem(Long cartItemId, Long userId) {
        Cart cart = cartRepository.findByUserId(userId);
        for (CartItem item : cart.getItems()) {
            if (item.getItemId().equals(cartItemId)) {
                item.setAmount(item.getAmount() - 1);
                if(item.getAmount() <= 0){
                    cart.removeCartItem(cartItemId);
                }
            }
        }
        return cartRepository.save(cart);
    }

    @Transactional
    public Cart updateAmount(Long userId, Long cartItemId, Integer amount) {
        Cart cart = cartRepository.findByUserId(userId);
        cart.getItems().stream().filter(i -> i.getId().compareTo(cartItemId) == 0)
                .findFirst().ifPresent(cartItem -> cartItem.setAmount(amount));
        return cart;
    }
}
