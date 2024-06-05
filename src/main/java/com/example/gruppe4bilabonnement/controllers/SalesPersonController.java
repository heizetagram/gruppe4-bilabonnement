package com.example.gruppe4bilabonnement.controllers;

import com.example.gruppe4bilabonnement.models.*;

import com.example.gruppe4bilabonnement.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/salesperson")
public class SalesPersonController {
    @Autowired
    private SalesPersonService salesPersonService;
    @Autowired
    private LeaseAgreementService leaseAgreementService;
    @Autowired
    private CarService carService;
    @Autowired
    private MechanicService mechanicService;
    @Autowired
    private InvoiceService invoiceService;

    @GetMapping("/frontpage")
    public String frontpage() {
        return "salesperson/frontpage";
    }

    @GetMapping("/new_customer")
    public String insert() {
        return "salesperson/create_customer";
    }

    //Customer creation
    @PostMapping("/insert")
    public String insert(Model model,
                         @RequestParam String firstName, @RequestParam String lastName, @RequestParam String phoneNumber,
                         @RequestParam String email, @RequestParam String address, @RequestParam int zipCode) {
        boolean isEmailRegistered = salesPersonService.isEmailRegistered(email);
        boolean isZipCodeValid = salesPersonService.isZipCodeValid(zipCode);

        if (isEmailRegistered) {
            // Send an invalid message if e-mail is already registered in the system
            model.addAttribute("emailAlreadyRegistered", "E-mail er allerede i brug");
        } else if (!email.contains(".")) {
            // Send an invalid message if e-mail doesn't contain "."
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
    public String update(Model model, @RequestParam int id, @RequestParam String firstName, @RequestParam String lastName,
                         @RequestParam String phoneNumber, @RequestParam String email, @RequestParam String address,
                         @RequestParam int zipCode, @RequestParam String origin) {
        Customer customer = salesPersonService.getCustomerById(id);
        model.addAttribute("customer", customer);
        model.addAttribute("origin", origin);
        model.addAttribute("invalidEmailError", salesPersonService.getErrorMessageForInvalidEmail(email, customer));
        model.addAttribute("invalidZipCodeError", salesPersonService.getErrorMessageForInvalidZipCode(zipCode));

        //** The old and alternative solution (better to find the errors in the service layer **\\
        /*
        boolean isEmailRegistered = salesPersonService.isEmailRegistered(email);
        boolean isZipCodeValid = salesPersonService.isZipCodeValid(zipCode);
        boolean errorCaught = false;
        if (isEmailRegistered && !email.equalsIgnoreCase(customer.getEmail())) {
            // Send an invalid message if e-mail is already registered in the system
            model.addAttribute("emailAlreadyRegistered", "E-mail er allerede i brug");
            errorCaught = true;
        } else if (!email.contains(".")) {
            // Send an invalid message if e-mail doesn't contain "."
            model.addAttribute("invalidInfo", "E-mail skal indeholde \".\"");
            errorCaught = true;
        }
        if (!isZipCodeValid) {
            // Send an invalid message if the zip code doesn't exist
            model.addAttribute("invalidZipCode", "Postnummeret findes ikke");
            errorCaught = true;
        }*/

        // If no error is found, then update the customer
        if (model.getAttribute("invalidEmailError") == null && model.getAttribute("invalidZipCodeError") == null) {
            salesPersonService.update(id, firstName, lastName, phoneNumber, email, address, zipCode);

            if (origin.equals("overview")) {
                return "redirect:/salesperson/customer_overview";
            } else {
                return "redirect:/salesperson/customer_profile/" + id + "?origin=" + origin;
            }
        }
        return "salesperson/update_customer";
    }

    //Show all customers
    @GetMapping("/customer_overview")
    public String showCustomers(Model model, @CookieValue(name = "employeeRole") String cookieValue) {
        if (cookieValue.equals("SALESPERSON")) {
            List<Customer> customers = salesPersonService.getAllCustomers();
            model.addAttribute("customers", customers);
            return "salesperson/customer_overview";
        } else {
            return "redirect:/";
        }
    }

    //Prepare customer deletion
   /* @GetMapping("/confirm_delete_customer/{id}")
    public String confirmDelete(@PathVariable int id, @RequestParam String origin, Model model, @CookieValue(name = "employeeRole") String cookieValue) {
        if (cookieValue.equals("SALESPERSON")) {
            Customer customer = salesPersonService.getCustomerById(id);
            ZipCode zipCode = salesPersonService.getZipCodeByZipCode(customer.getZipCode());
            model.addAttribute("customer", customer);
            model.addAttribute("zipCode", zipCode);
            model.addAttribute("origin", origin);
            return "salesperson/delete_customer";
        } else {
            return "redirect:/";
        }
    } */
    // Bekræft sletning af kunde
    @GetMapping("/confirm_delete_customer/{id}")
    public String confirmDelete(@PathVariable int id, @RequestParam String origin, Model model, @CookieValue(name = "employeeRole") String cookieValue) {
        if (cookieValue.equals("SALESPERSON")) {
            Customer customer = salesPersonService.getCustomerById(id);
            ZipCode zipCode = salesPersonService.getZipCodeByZipCode(customer.getZipCode());
            LeaseAgreement leaseAgreement = salesPersonService.getLeaseAgreementByCustomerId(id);

            model.addAttribute("customer", customer);
            model.addAttribute("zipCode", zipCode);
            model.addAttribute("origin", origin);
            model.addAttribute("leaseAgreementExists", leaseAgreement != null);  // Tilføj denne linje

            return "salesperson/delete_customer";
        } else {
            return "redirect:/";
        }
    }

    // Slet kunde med leasingaftale check
    @PostMapping("/delete_customer")
    public String deleteCustomer(@RequestParam int id, @RequestParam String origin, Model model) {
        LeaseAgreement leaseAgreement = salesPersonService.getLeaseAgreementByCustomerId(id);

        if (leaseAgreement != null) {
            Customer customer = salesPersonService.getCustomerById(id);
            ZipCode zipCode = salesPersonService.getZipCodeByZipCode(customer.getZipCode());
            model.addAttribute("customer", customer);
            model.addAttribute("zipCode", zipCode);
            model.addAttribute("origin", origin);
            model.addAttribute("errorMessage", "Kunden kunne ikke slettes fordi de har leasingaftaler");
            return "salesperson/delete_customer";
        } else {
            salesPersonService.deleteCustomerById(id);
            if (origin.equals("overview")) {
                return "redirect:/salesperson/customer_overview";
            } else {
                return "redirect:/salesperson/customer_profile/" + id;
            }
        }
    }

    //Customer deletion
    @PostMapping("/delete_customer")
    public String delete(@RequestParam int id, @RequestParam String origin) {
        salesPersonService.deleteCustomerById(id);
        if (origin.equals("overview")) {
            return "redirect:/salesperson/customer_overview";
        } else {
            return "redirect:/salesperson/customer_profile/" + id;
        }
    }

    //Show selected customer profile
    @GetMapping("/customer_profile/{id}")
    public String customerProfile(@PathVariable int id, @RequestParam String origin, Model model, @CookieValue(name = "employeeRole") String cookieValue) {
        if (cookieValue.equals("SALESPERSON")) {
            Customer customer = salesPersonService.getCustomerById(id);
            model.addAttribute("customer", customer);
            ZipCode zipCode = salesPersonService.getZipCodeByZipCode(customer.getZipCode());
            model.addAttribute("zipCode", zipCode);

            if (origin.equals("overview")) {
                List<Customer> customers = salesPersonService.getAllCustomers();
                model.addAttribute("customers", customers);
                return "salesperson/customer_overview";
            } else {
                List<LeaseAgreement> leaseAgreements = leaseAgreementService.getAllLeaseAgreementsByCustomerId(id);
                model.addAttribute("leaseAgreements", leaseAgreements);

                model.addAttribute("carService", carService);
                return "salesperson/customer_profile";
            }
        } else {
            return "redirect:/";
        }
    }

    // Prepare new lease agreement
    @GetMapping("/new_lease_agreement")
    public String showNewLeaseAgreementForm(@RequestParam int customerId, Model model) {
        List<Car> cars = carService.getAllAvailableCars();
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

    // Create lease agreement
    @PostMapping("/create_lease_agreement")
    public String createLeaseAgreement(@RequestParam int customerId, @ModelAttribute LeaseAgreement leaseAgreement) {
        Car car = carService.getCarById(leaseAgreement.getCarId());
        long startKm = car.getKm();

        leaseAgreement.setStartKm(startKm);

        carService.rentCar(leaseAgreement.getCarId());

        leaseAgreementService.createLeaseAgreement(customerId, leaseAgreement);
        return "redirect:/salesperson/customer_overview";
    }

    // Show lease agreement
   @GetMapping("/show_lease_agreement_details/{leaseAgreementId}")
   public String showLeaseAgreementDetails(@PathVariable int leaseAgreementId, Model model) {
       LeaseAgreement leaseAgreement = leaseAgreementService.getLeaseAgreementById(leaseAgreementId);
       boolean doesInvoiceExist = invoiceService.checkIfInvoiceExists(leaseAgreementId);

       if (leaseAgreement != null) {
           Car car = carService.getCarById(leaseAgreement.getCarId());
           CarModel carModel = carService.getCarModelById(car.getCarModelId());
           boolean isCarInService = mechanicService.checkIfCarIsInService(leaseAgreement.getCarId());
           boolean isCarRented = carService.checkIfCarIsRented(leaseAgreement.getCarId());

           if (doesInvoiceExist) {
               model.addAttribute("invoiceExists", "En faktura er allerede blevet oprettet for denne leasingaftale.");
           } else if (isCarInService && isCarRented) {
               model.addAttribute("carInService", "Bilen er på værkstedet");
               model.addAttribute("carIsRented", "Bilen er udlejet");
           } else if (isCarRented) {
               model.addAttribute("carIsRented", "");
           }
           model.addAttribute("leaseAgreement", leaseAgreement);
           model.addAttribute("car", car);
           model.addAttribute("carModel", carModel);
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

    @PostMapping("/send_car_to_service")
    public String sendCarToService(@RequestParam int carId, @RequestParam int leaseAgreementId) {
        mechanicService.addCarToWorkshop(carId);
        return "redirect:/salesperson/show_lease_agreement_details/" + leaseAgreementId;
    }

}
