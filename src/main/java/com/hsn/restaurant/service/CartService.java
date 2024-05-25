package com.hsn.restaurant.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.hsn.restaurant.entity.Cart;
import com.hsn.restaurant.entity.CartItem;
import com.hsn.restaurant.entity.Product;
import com.hsn.restaurant.entity.User;
import com.hsn.restaurant.excpetion.EntityNotFound;
import com.hsn.restaurant.excpetion.OperationPermittedException;
import com.hsn.restaurant.repository.CartItemRepository;
import com.hsn.restaurant.repository.CartRepository;
import com.hsn.restaurant.repository.ProductRepository;
import com.hsn.restaurant.request.CartItemRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {

	private final CartItemRepository itemRepository;
	private final CartRepository cartRepository;
	private final ProductRepository productRepository;
	
	
	public CartItem addItemToCart(CartItemRequest request,Authentication auth) {
		
		Product product=productRepository.findById(request.getProductId())
				.orElseThrow(()->new EntityNotFound("Product not found"));
		var user=(User)auth.getPrincipal();
		var cart=cartRepository.findByCustomerId(user.getId())
				.orElseThrow(()->new EntityNotFound("Cart not found"));
		
		if(!product.isAvailabilty()) {
			throw new OperationPermittedException("this item no availability now");
		}
		
		for(CartItem item:cart.getCartItems()) {
			if(item.getProduct().equals(product)) {
				int newQuantity=item.getQuantity()+request.getQuantity();
				return updateCartItemQuantity(item.getId(), newQuantity);
			}
		}
		var cartItem=CartItem
				.builder().product(product)
				.cart(cart)
				.quantity(request.getQuantity())
				.totalPrice(product.getPrice()*request.getQuantity())
				.build();
		
		   var item=itemRepository.save(cartItem);
		      cart.getCartItems().add(item);
		
		 return item;
	}
	
	public CartItem updateCartItemQuantity(Long itemId,int quantity) {
		var item=itemRepository.findById(itemId)
				.orElseThrow(()->new EntityNotFound("Item not found"));
		var cartItme=CartItem
				.builder()
				.quantity(quantity)
				.totalPrice(item.getProduct().getPrice()*quantity)
				.build();
      return itemRepository.save(cartItme);		
	}
	
	public Cart removeItemFromCart(Long itemId,Authentication auth) {
		var user=(User)auth.getPrincipal();
		Cart cart=cartRepository.findByCustomerId(user.getId())
				.orElseThrow(()->new EntityNotFound("Cart not found"));
		var item=itemRepository.findById(itemId)
				.orElseThrow(()->new EntityNotFound("Item not found"));
		cart.getCartItems().remove(item);
		cart.setTotal(calcTotalFromCart(cart));
		return cart;
	}
	
	public double calcTotalFromCart(Cart cart) {
		double totalPrice=0;
	   for(CartItem item:cart.getCartItems()) {
		   totalPrice+=item.getProduct().getPrice()*item.getQuantity();
	   }
	   cart.setTotal(totalPrice);
	   cartRepository.save(cart);
		return totalPrice;
		
	}
	
	public Cart findCartById(Long cartId) {
		
		return cartRepository.findById(cartId)
				.orElseThrow(()->new EntityNotFound("Cart not found"));
	}
	public Cart findCartByUserId(Authentication auth) {
		var user=(User)auth.getPrincipal();
		var cart= cartRepository.findByCustomerId(user.getId())
				.orElseThrow(()->new EntityNotFound("You not have a cart"));
		cart.setTotal(calcTotalFromCart(cart));
		cartRepository.save(cart);
		return cart;
		
	}
	public Cart clearCart(Authentication auth) {
		var user=(User)auth.getPrincipal();
		var cart= cartRepository.findByCustomerId(user.getId())
				.orElseThrow(()->new EntityNotFound("You not have a cart"));
		
		cart.getCartItems().clear();
		cart.setTotal(0);
		return cartRepository.save(cart);
	}
}
