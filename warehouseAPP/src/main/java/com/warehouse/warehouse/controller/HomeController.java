package com.warehouse.warehouse.controller;

import com.warehouse.warehouse.model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {
    private static final List<Product> PRODUCTS = new ArrayList(List.of(
            new Product("Mleko", "W-155", 500, 1.23f, "Mleko krowie pasteryzowane."),
            new Product("Ser Kozi Mlekpol", "W-1", 322, 10.55f, "Ser z mleka koziego, tłusty, hermetycznie zapakowany."),
            new Product("Śmietana 18% Warmia", "W-765", 200, 4.00f, "Śmietana 18% z mleka krowiego, pasteryzowana.")
    ));

    public static void updateProductQuantity (String serialNumber, String action) {
        PRODUCTS.stream()
                .filter(it -> it.getSerialNumber().equals(serialNumber))
                .forEach(it -> {
                    if (action.equals("increase")) {
                        Integer updatedQuantity = it.getQuantity()+1;
                        it.setQuantity(updatedQuantity);
                    } else if(action.equals("decrease")){
                        Integer updatedQuantity = it.getQuantity()-1;
                        if(updatedQuantity < 0) {
                            it.setQuantity(0);
                        } else {
                            it.setQuantity(updatedQuantity);
                        }
                    }
        });
    }

    public static Optional<Product> getProduct (String serialNumber) {
        return PRODUCTS.stream()
                .filter(it -> it.getSerialNumber().equals(serialNumber))
                .findAny();
    }

    @RequestMapping(path = "/updateQuantity/{serialNumber}/{action}", method = RequestMethod.POST)
    public String updateQuantity(@PathVariable("serialNumber") String serialNumber, @PathVariable("action") String action, Model model) {
        updateProductQuantity(serialNumber, action);
        model.addAttribute("products", PRODUCTS);
        model.addAttribute("updateQuantity", new String());
        model.addAttribute("newProduct", new Product());
        return "index";
    }

    @RequestMapping(path = "/")
    public String showProducts(Model model) {
        model.addAttribute("products", PRODUCTS);
        model.addAttribute("updateQuantity", new String());
        model.addAttribute("newProduct", new Product());
        return "index";
    }

    @RequestMapping(value = "/productDetails/{serialNumber}", method = RequestMethod.GET)
    public String showProductDetails(@PathVariable("serialNumber") String serialNumber, Model model) {
        Product product = getProduct(serialNumber).orElseThrow();
        model.addAttribute("product", product);
        return "productDetails";
    }

    @RequestMapping(path = "/addProduct", method = RequestMethod.POST)
    public String addProduct(Product newProduct, Model model) {
        PRODUCTS.add(newProduct);
        model.addAttribute("newProduct", newProduct);
        return "newProduct";
    }
}