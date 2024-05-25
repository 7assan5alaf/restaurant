package com.hsn.restaurant.service;

import java.util.ArrayList;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hsn.restaurant.entity.Bill;
import com.hsn.restaurant.entity.CartItem;
import com.hsn.restaurant.entity.Order;
import com.hsn.restaurant.entity.OrderItem;
import com.hsn.restaurant.entity.User;
import com.hsn.restaurant.excpetion.EntityNotFound;
import com.hsn.restaurant.excpetion.OperationPermittedException;
import com.hsn.restaurant.mail.EmailService;
import com.hsn.restaurant.repository.BillRepository;
import com.hsn.restaurant.repository.OrderItemRepository;
import com.hsn.restaurant.repository.OrderRepository;
import com.hsn.restaurant.repository.UserRepository;
import com.hsn.restaurant.request.OrderRequest;
import com.hsn.restaurant.response.BillResponse;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderItemRepository orderItemRepository;
	private final OrderRepository orderRepository;
	private final CartService cartService;
	private final UserRepository userRepository;
	private final BillRepository billRepository;
	private final EmailService emailService;

	@Transactional
	public BillResponse createOrder(OrderRequest request, Authentication auth) {
		var user = (User) auth.getPrincipal();
		var order = Order.builder().address(request.getAddress()).user(user).orderStatus("PENDING").build();
		long totalAmount=0;
		var cart = cartService.findCartByUserId(auth);
		List<String> orderDetails = new ArrayList<>();
		List<OrderItem> items = new ArrayList<>();

		
		for (CartItem item : cart.getCartItems()) {
			totalAmount+=item.getQuantity();
			var orderItem = OrderItem.builder().product(item.getProduct()).quantity(item.getQuantity())
					.totalPrice(item.getTotalPrice()).build();
			orderDetails.add(item.getProduct().getName()+" and quantity is"+ item.getQuantity());
			orderItemRepository.save(orderItem);
			items.add(orderItem);
		}
		order.setItems(items);
		order.setTotalItem(cart.getCartItems().size());
		order.setTotalPrice(cart.getTotal());
        order.setTotalAmount(totalAmount);
		user.getOrders().add(order);
		
		var bill = Bill.builder().address(request.getAddress()).fullName(user.getFullName())
				.orderDetails(orderDetails)
				.phoneNumber(request.getPhoneNumber()).totalPrice(cart.getTotal()).user(user).build();
		order.setBill(bill);
		orderRepository.save(order);
		billRepository.save(bill);
		return BillResponse
				.builder()
				.address(request.getAddress())
				.amount(order.getTotalAmount())
				.name(order.getUser().getFullName())
				.orderDetails(orderDetails)
				.totalPrice(cart.getTotal())
				.orderNumber(order.getId())
				.phoneNumber(request.getPhoneNumber())
				.build();
	}
	
	public Order findOrderById(Long id) {
		return orderRepository
				.findById(id).orElseThrow(()->new EntityNotFound("order not found"));
	}
	
	public String cancelOrder(Long orderId,Authentication auth) {
		var order=findOrderById(orderId);
		var user = (User) auth.getPrincipal();
		if(order.getOrderStatus().equals("COMPLETED"))
			throw new OperationPermittedException("You can not cancel ordeer because it completed");
		
		if(!user.getEmail().equals(order.getUser().getEmail()))
			throw new OperationPermittedException("You  not have any order");
		
		Long billId=order.getBill().getId();
		orderRepository.delete(order);
		billRepository.deleteById(billId);
		return "Cancel successfully";
	}
	
	public List<Order>findByUser(Authentication auth){
		var user = (User) auth.getPrincipal();
		return orderRepository.findByUserId(user.getId());
	}
	
	public Order updateOrderStatus(Long orderId) throws MessagingException {
		var order=findOrderById(orderId);
		order.setOrderStatus("COMPLETED");
		var user=userRepository.findByEmail(order.getUser().getEmail())
				.orElseThrow(()->new EntityNotFound("User Not Found"));
		emailService.sendEmail(user.getEmail(), "Order is completed.Delivery is headed to you");
	 return	orderRepository.save(order);
	}
}
