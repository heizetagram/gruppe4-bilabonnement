package com.example.gruppe4bilabonnement.controllers;

import com.example.gruppe4bilabonnement.models.Car;
import com.example.gruppe4bilabonnement.models.CarModel;
import com.example.gruppe4bilabonnement.models.Customer;

import com.example.gruppe4bilabonnement.models.LeaseAgreement;
import com.example.gruppe4bilabonnement.services.CarService;
import com.example.gruppe4bilabonnement.services.LeaseAgreementService;
import com.example.gruppe4bilabonnement.services.SalesPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Converter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;


import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Controller
@RequestMapping("/salesperson")
public class SalesPersonController {
    @Autowired
    private SalesPersonService salesPersonService;
    @Autowired
    private LeaseAgreementService leaseAgreementService;
    @Autowired
    private CarService carService;

    private static final int PAGE_SIZE = 5;

    // Initialize the customer queue in the session
    @ModelAttribute("customerQueue")
    public Queue<Customer> initializeCustomerQueue() {
        return new LinkedList<>();
    }

    // Show initial set of customers or more customers
    @GetMapping("/customer_overview")
    public String showCustomers(@RequestParam(name = "page", defaultValue = "1") int page,
                                @RequestParam(name = "origin", defaultValue = "overview") String origin,
                                Model model, @CookieValue(name = "employeeRole") String cookieValue,
                                @ModelAttribute("customerQueue") Queue<Customer> customerQueue) {
        if (cookieValue.equals("SALESPERSON")) {
            if (customerQueue.isEmpty()) {
                List<Customer> customers = salesPersonService.getAllCustomers();
                customerQueue.addAll(customers);  // Populate the queue with all customers initially
            }

            List<Customer> customers = getNextCustomers(customerQueue, page);
            model.addAttribute("customers", customers);
            model.addAttribute("page", page);
            model.addAttribute("origin", origin);
            model.addAttribute("moreCustomersAvailable", customerQueue.size() > PAGE_SIZE);

            return "salesperson/customer_overview";
        } else {
            return "redirect:/";
        }
    }

    // Get the next set of customers from the queue
    private List<Customer> getNextCustomers(Queue<Customer> customerQueue, int page) {
        List<Customer> customers = new LinkedList<>();
        for (int i = 0; i < PAGE_SIZE * (page - 1) && !customerQueue.isEmpty(); i++) {
            customerQueue.offer(customerQueue.poll());  // Move customers ahead to skip previous pages
        }
        for (int i = 0; i < PAGE_SIZE && !customerQueue.isEmpty(); i++) {
            Customer customer = customerQueue.poll();  // Dequeue customer
            customers.add(customer);
            customerQueue.offer(customer);  // Re-enqueue customer to the end
        }
        return customers;
    }

    @GetMapping("/new_customer")
    public String insert() {
        return "/salesperson/create_customer";
    }

    //Customer creation
    @PostMapping("/insert")
    public String insert(Model model,
                         @RequestParam String firstName, @RequestParam String lastName, @RequestParam String phoneNumber,
                         @RequestParam String email, @RequestParam String address, @RequestParam int zipCode) {
        boolean isEmailRegistered = salesPersonService.isEmailRegistered(email);
        boolean isZipCodeValid = salesPersonService.isZipCodeValid(zipCode);

        if (isEmailRegistered) {
            model.addAttribute("emailAlreadyRegistered", "E-mail er allerede i brug");
        } else if (!email.contains(".")) {
            model.addAttribute("invalidInfo", "E-mail skal indeholde \".\"");
        } else if (!isZipCodeValid) {
            model.addAttribute("invalidZipCode", "Postnummeret findes ikke");
        } else {
            salesPersonService.insert(firstName, lastName, phoneNumber, email, address, zipCode);
            Customer customer = salesPersonService.getCustomerByEmail(email);
            return "redirect:/salesperson/new_lease_agreement?customerId=" + customer.getId();
        }
        return "salesperson/create_customer";
    }

    // Prepare customer update
    @GetMapping("/prepare_update/{id}")
    public String prepareUpdate(@PathVariable int id, @RequestParam String origin, Model model, @CookieValue(name = "employeeRole") String cookieValue) {
        if (cookieValue.equals("SALESPERSON")) {
            model.addAttribute("customer", salesPersonService.prepareUpdate(id));
            model.addAttribute("origin", origin);
            return "salesperson/update_customer";
        } else {
            return "redirect:/";
        }
    }

    // Update customer
    @PostMapping("/update_customer")
    public String update(@RequestParam int id, @RequestParam String firstName, @RequestParam String lastName,
                         @RequestParam String phoneNumber, @RequestParam String email, @RequestParam String address, @RequestParam int zipCode, @RequestParam String origin) {
        salesPersonService.update(id, firstName, lastName, phoneNumber, email, address, zipCode);
        if (origin.equals("overview")) {
            return "redirect:/salesperson/customer_overview";
        } else {
            return "redirect:/salesperson/customer_profile/" + id;
        }
    }

    // Prepare customer deletion
    @GetMapping("/confirm_delete_customer/{id}")
    public String confirmDelete(@PathVariable int id, @RequestParam String origin, Model model, @CookieValue(name = "employeeRole") String cookieValue) {
        if (cookieValue.equals("SALESPERSON")) {
            Customer customer = salesPersonService.getCustomerById(id);
            model.addAttribute("customer", customer);
            model.addAttribute("origin", origin);
            return "salesperson/delete_customer";
        } else {
            return "redirect:/";
        }
    }

    // Customer deletion
    @PostMapping("/delete_customer")
    public String delete(@RequestParam int id, @RequestParam String origin) {
        salesPersonService.deleteCustomerById(id);
        if (origin.equals("overview")) {
            return "redirect:/salesperson/customer_overview";
        } else {
            return "redirect:/salesperson/customer_profile/" + id;
        }
    }

    // Show selected customer profile
    @GetMapping("/customer_profile/{id}")
    public String customerProfile(@PathVariable int id, @RequestParam String origin, Model model, @CookieValue(name = "employeeRole") String cookieValue) {
        if (cookieValue.equals("SALESPERSON")) {
            Customer customer = salesPersonService.getCustomerById(id);
            model.addAttribute("customer", customer);

            if (origin.equals("overview")) {
                List<Customer> customers = salesPersonService.getAllCustomers();
                model.addAttribute("customers", customers);
                return "/salesperson/customer_overview";
            } else {
                return "salesperson/customer_profile";
            }
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/new_lease_agreement")
    public String showNewLeaseAgreementForm(@RequestParam int customerId, Model model) {
        List<Car> cars = carService.getAllCars();
        Customer customer = salesPersonService.getCustomerById(customerId);
        model.addAttribute("cars", cars);
        model.addAttribute("carService", carService);
        model.addAttribute("customer", customer);
        return "salesperson/new_lease_agreement";
    }
// BRUG NEDENSTÅENDE SENERE, når vi sender id'et med
    /*@GetMapping("/new_lease_agreement")
    public String showNewLeaseAgreementForm(@RequestParam int customerId, Model model) {
        List<Car> cars = carService.getAllCars();
        Customer customer = salesPersonService.getCustomerById(customerId);
        model.addAttribute("cars", cars);
        model.addAttribute("carService", carService);
        model.addAttribute("customer", customer);
        return "salesperson/new_lease_agreement";
    } */

    @PostMapping("/create_lease_agreement")
    public String createLeaseAgreement(@RequestParam int customerId, @ModelAttribute LeaseAgreement leaseAgreement) {
        leaseAgreementService.createLeaseAgreement(customerId, leaseAgreement);
        return "redirect:/salesperson/customer_overview";
    }

   @GetMapping("/show_lease_agreement_details/{leaseAgreementIdStr}")
   public String showLeaseAgreementDetails(@PathVariable String leaseAgreementIdStr, Model model) {
       LeaseAgreement leaseAgreement = leaseAgreementService.getLeaseAgreementByCustomerId(Integer.parseInt(leaseAgreementIdStr));
       if (leaseAgreement != null) {
           model.addAttribute("leaseAgreement", leaseAgreement);
           return "salesperson/lease_agreement_details";
       } else {
           return "redirect:/salesperson/customer_overview";
       }
   }

    @GetMapping("/lease_agreement")
    public String showAllLeaseAgreements(Model model) {
        List<LeaseAgreement> leaseAgreements = leaseAgreementService.getAllLeaseAgreements();
        model.addAttribute("leaseAgreements", leaseAgreements);
        return "lease_agreement";
    }

    @GetMapping("/update_lease_agreement/{id}")
    public String showUpdateLeaseAgreementForm(@PathVariable int id, Model model) {
        LeaseAgreement leaseAgreement = leaseAgreementService.getLeaseAgreementById(id);
        if (leaseAgreement != null) {
            model.addAttribute("leaseAgreement", leaseAgreement);
            return "salesperson/update_lease_agreement";
        } else {
            return "redirect:/salesperson/customer_overview";
        }
    }

    @PostMapping("/update_lease_agreement")
    public String updateLeaseAgreement(@ModelAttribute LeaseAgreement leaseAgreement) {
        leaseAgreementService.updateLeaseAgreement(leaseAgreement);
        return "redirect:/salesperson/customer_overview";
    }

    @PostMapping("/delete_lease_agreement/{id}")
    public String deleteLeaseAgreement(@PathVariable int id) {
        leaseAgreementService.deleteLeaseAgreement(id);
        return "redirect:/salesperson/customer_overview";
    }
}
