package co.codingnomads.spring.cartmicroservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    CartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getCartByUserId(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(cartService.getCartByUserId(userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("cartItems/{userId}")
    public ResponseEntity<?> getCartItems(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(cartService.getAllCartItemsInCart(userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{userId}")
    public ResponseEntity<?> addNewCart(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(cartService.addNewCart(userId));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PostMapping("/{userId}/{itemId}")
    public ResponseEntity<?> addNewCartItem(@PathVariable("itemId") Long itemId, @PathVariable("userId") Long userId) {
        try {
            return ResponseEntity.ok(cartService.addCartItem(itemId, userId));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @DeleteMapping("/{userId}/{cartItemId}")
    public ResponseEntity<?> removeCartItem(@PathVariable("cartItemId") Long cartItemId,
                                            @PathVariable("userId") Long userId) {
        try {
            return ResponseEntity.ok(cartService.removeCartItem(cartItemId, userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{userId}/{cartItemId}")
    public ResponseEntity<?> updateItemAmount(@PathVariable("userId") Long userId,
                                              @PathVariable("cartItemId") Long cartItemId,
                                              @RequestParam("amount") Integer amount) {
        try {
            return ResponseEntity.ok(cartService.updateAmount(userId, cartItemId, amount));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
