package co.codingnomads.spring.cartmicroservice;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItem {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long itemId;

    @Column(nullable = false)
    private Integer amount;
}
