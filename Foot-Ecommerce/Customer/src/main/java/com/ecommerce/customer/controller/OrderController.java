package com.ecommerce.customer.controller;

import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.ShoppingCart;
import com.ecommerce.library.service.CustomerService;
import com.ecommerce.library.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class OrderController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/check-out")
    public String checkout(Model model, Principal principal){
        if (principal == null){
            return "redirect:/login";
        }

        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);
        ShoppingCart shoppingCart = customer.getShoppingCart();
        model.addAttribute("shoppingCart", shoppingCart);
        model.addAttribute("subTotal", shoppingCart.getTotalPrices());
        return "checkout";
    }

    @GetMapping("/place-order")
    public String placeOrder(Model model, Principal principal){
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);
        ShoppingCart shoppingCart = customer.getShoppingCart();
        model.addAttribute("shoppingCart", shoppingCart);
        model.addAttribute("subTotal", shoppingCart.getTotalPrices());
        return "place-order";
    }
}
