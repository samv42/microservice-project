package co.codingnomads.spring.itemmicroservice;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    @Autowired
    ItemRepository itemRepository;

    @Autowired
    @LoadBalanced
    RestTemplate restTemplate;

    public List<Item> getAllItems(){
        return itemRepository.findAll();
    }
    public Item getItemById(long id){
        Optional<Item> optional;
        if ((optional = Optional.ofNullable(itemRepository.findById(id))).isEmpty()) {
            return null;
        } else {
            return optional.get();
        }
    }
    public Item insertNewItem(Item item){
        /*ResponseEntity<User> UserResponse = restTemplate.getForEntity
                ("http://USER-MICROSERVICE/user", User.class);
        User user = UserResponse.getBody();
        ResponseEntity<Item> response = restTemplate.postForEntity
                ("http://CART-MICROSERVICE/cart/" + user.getId() + "/" + item.getItemId(), item, Item.class);*/
        return itemRepository.save(item);
    }
    public Item updateItem(Item updatedItem){
        Item item = itemRepository.findById(updatedItem.getItemId()).orElseThrow(RuntimeException::new);
        BeanUtils.copyProperties(updatedItem, item);
        return itemRepository.save(item);
    }
    public void deleteItemById(long id){
        itemRepository.deleteById(id);
    }
}
