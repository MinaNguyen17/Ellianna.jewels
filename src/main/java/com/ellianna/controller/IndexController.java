package com.ellianna.controller;

import com.ellianna.model.Product;
import com.ellianna.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
public class IndexController {
    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String index(){
        return "index";
    }
    @GetMapping("/product-page")
    public String product(Model model){
        List<Product> productList = productService.getProduct();
        model.addAttribute("productList", productList);
        return "product";
    }
    @GetMapping("/admin-page")
    public String admin(Model model) {
        List<Product> productList = productService.getProduct();
        model.addAttribute("productList", productList);
        return "admin";
    }
    
    @GetMapping("/signin-page")
    public String login() {
        return "login";
    }
}