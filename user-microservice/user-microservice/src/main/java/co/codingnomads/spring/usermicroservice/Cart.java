package co.codingnomads.spring.usermicroservice;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cart {
    private Long id;
    private Long userId;
}
