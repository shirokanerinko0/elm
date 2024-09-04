package com.neusoft.elmboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.neusoft.elmboot.po.Cart;
import com.neusoft.elmboot.service.CartService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
public class CartController {

	@Autowired
	private CartService cartService;
	
	@RequestMapping("/Carts")
	public List<Cart> listCart(Cart cart) throws Exception{
		return cartService.listCart(cart);
	}
	
	@PostMapping("/Cart")
	public int saveCart(Cart cart) throws Exception{
		return cartService.saveCart(cart);
	}
	
	@PutMapping("/Cart")
	public int updateCart(Cart cart) throws Exception{
		return cartService.updateCart(cart);
	}
	
	@DeleteMapping("/Cart")
	public int removeCart(Cart cart) throws Exception{
		return cartService.removeCart(cart);
	}
}
