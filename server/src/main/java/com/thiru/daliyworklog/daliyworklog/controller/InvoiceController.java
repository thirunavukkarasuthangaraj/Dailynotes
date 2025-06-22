package com.thiru.daliyworklog.daliyworklog.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.Stripe;
import com.stripe.model.Customer;
import com.stripe.model.Event;
import com.stripe.model.Invoice;
import com.stripe.model.InvoiceItem;
import com.stripe.net.Webhook;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.InvoiceCreateParams;
import com.stripe.param.InvoiceItemCreateParams;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class InvoiceController {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Value("${stripe.secret.key}")
    private String key;
    
 
    
    @PostMapping("/create-invoice")
    public Map<String, Object> createInvoice(@RequestBody Map<String, Object> request) {
        Stripe.apiKey = key ;

        try {
            String email = (String) request.get("email");
            Integer jobSlotTierId = (Integer) request.get("jobSlotTierId");
            Integer conversationTierId = (Integer) request.get("conversationTierId");
            
            // 1. Create Stripe customer
            Customer customer = Customer.create(
                CustomerCreateParams.builder()
                    .setEmail(email)
                    .build()
            );
            
            // 2. Create invoice
            Invoice invoice = Invoice.create(
                InvoiceCreateParams.builder()
                    .setCustomer(customer.getId())
                    .setCollectionMethod(InvoiceCreateParams.CollectionMethod.SEND_INVOICE)
                    .setDaysUntilDue(7L)
                    .build()
            );
            
            // 3. Add base plan
            InvoiceItem.create(
                InvoiceItemCreateParams.builder()
                    .setCustomer(customer.getId())
                    .setInvoice(invoice.getId())
                    .setDescription("Starter Plan")
                    .setQuantity(1L)
                    .setUnitAmount(99900L)
                    .build()
            );
            
            // 4. Add job slots if selected
            if (jobSlotTierId != null) {
                Map<Integer, String[]> jobSlotMap = Map.of(
                    1, new String[]{"10 Job Slots", "20000"},
                    2, new String[]{"25 Job Slots", "45000"},
                    3, new String[]{"50 Job Slots", "85000"}
                );
                
                String[] jobSlotInfo = jobSlotMap.get(jobSlotTierId);
                InvoiceItem.create(
                    InvoiceItemCreateParams.builder()
                        .setCustomer(customer.getId())
                        .setInvoice(invoice.getId())
                        .setDescription(jobSlotInfo[0])
                        .setQuantity(1L)
                        .setUnitAmount(Long.parseLong(jobSlotInfo[1]))
                        .build()
                );
            }
            
            // 5. Add conversations if selected
            if (conversationTierId != null) {
                Map<Integer, String[]> conversationMap = Map.of(
                    5, new String[]{"1000 Conversation Credits", "10000"},
                    6, new String[]{"2500 Conversation Credits", "23000"}
                );
                
                String[] convInfo = conversationMap.get(conversationTierId);
                InvoiceItem.create(
                    InvoiceItemCreateParams.builder()
                        .setCustomer(customer.getId())
                        .setInvoice(invoice.getId())
                        .setDescription(convInfo[0])
                        .setQuantity(1L)
                        .setUnitAmount(Long.parseLong(convInfo[1]))
                        .build()
                );
            }
            
            // 6. Finalize and send
            invoice = invoice.finalizeInvoice();
            invoice = invoice.sendInvoice();
            
            // 7. Save to PostgreSQL
            jdbcTemplate.update(
                "INSERT INTO invoices (email, stripe_invoice_id, amount, status) VALUES (?, ?, ?, ?)",
                email, invoice.getId(), request.get("total"), "sent"
            );
            
            // 8. Return invoice URL
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("invoiceUrl", invoice.getHostedInvoiceUrl());
            return response;
            
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("error", e.getMessage());
            return error;
        }
    }
    
    @PostMapping("/webhook")
    public String webhook(@RequestBody String payload, 
                         @RequestHeader("Stripe-Signature") String sigHeader) {
        try {
            // Use Webhook.constructEvent instead of Event.GSON
            String webhookSecret = "whsec_YOUR_WEBHOOK_SECRET"; // Get from Stripe dashboard
            Event event = Webhook.constructEvent(payload, sigHeader, webhookSecret);
            
            if ("invoice.payment_succeeded".equals(event.getType())) {
                Invoice invoice = (Invoice) event.getDataObjectDeserializer()
                    .getObject()
                    .orElse(null);
                
                if (invoice != null) {
                    // Update invoice status
                    jdbcTemplate.update(
                        "UPDATE invoices SET status = 'paid' WHERE stripe_invoice_id = ?",
                        invoice.getId()
                    );
                    
                    // Update organization quotas based on what they bought
                    // This is where you add the job slots and conversation credits
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "OK";
    }
}

 

