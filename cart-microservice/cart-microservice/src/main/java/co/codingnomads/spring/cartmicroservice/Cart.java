package co.codingnomads.spring.cartmicroservice;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cart {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long userId;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items;

    public void addCartItem(CartItem item) {
        items.add(item);
    }

    public void removeCartItem(Long cartItemId) {
        items.removeIf(ci -> ci.getId().equals(cartItemId));
    }
}
