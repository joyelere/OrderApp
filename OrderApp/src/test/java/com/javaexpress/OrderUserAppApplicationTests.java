package com.javaexpress;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.javaexpress.models.Order;
import com.javaexpress.repositories.OrderRepository;
import com.javaexpress.services.OrderService;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class OrderUserAppApplicationTests {

	@Mock
	private OrderRepository orderRepository;
	
	@InjectMocks
	private OrderService orderService;
	
    private Order order;
    
    @BeforeEach
    void setUp() {
        order = new Order();
        order.setId(1L);
        order.setCustomerName("John Oye");
        order.setStatus("Pending");
        order.setOrderDate("2024-07-8");
        order.setTotalAmount(19.99);
    }

	
	@Test
    void testGetAllOrders() {

        List<Order> mockOrders = new ArrayList<>();
        mockOrders.add(order);
		
        when(orderRepository.findAll()).thenReturn(mockOrders);
        
        List<Order> orders = orderService.getAllOrders();
        assertEquals(1, orders.size());
        assertEquals("John Oye", orders.get(0).getCustomerName());
  
	}

	
	@Test
    void testGetOrderById() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Optional<Order> foundOrder = orderService.getOrderById(1L);
        assertTrue(foundOrder.isPresent());
        assertEquals("John Oye", foundOrder.get().getCustomerName());
    }
	
    @Test
    void testCreateOrder() {
        when(orderRepository.save(order)).thenReturn(order);

        Order createdOrder = orderService.createOrder(order);
        assertNotNull(createdOrder);
        assertEquals("John Oye", createdOrder.getCustomerName());
    }

    @Test
    void testUpdateOrder() {
        Order updatedDetails = new Order();
        updatedDetails.setStatus("Completed");
        updatedDetails.setCustomerName("John Oye");
        updatedDetails.setOrderDate("2024-07-8");
        updatedDetails.setTotalAmount(19.99);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);

        Order updatedOrder = orderService.updateOrder(1L, updatedDetails);
        assertEquals("Completed", updatedOrder.getStatus());
    }

    @Test
    void testDeleteOrder() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        doNothing().when(orderRepository).delete(order);

        orderService.deleteOrder(1L);
        verify(orderRepository, times(1)).delete(order);
    }
}
