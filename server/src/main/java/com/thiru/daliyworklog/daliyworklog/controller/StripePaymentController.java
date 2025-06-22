//package com.thiru.daliyworklog.daliyworklog.controller;
////--- StripePaymentController.java ---
//
//import com.stripe.Stripe;
//import com.stripe.exception.StripeException;
//import com.stripe.model.Customer;
//import com.stripe.model.Invoice;
//import com.stripe.model.InvoiceItem;
//import com.thiru.daliyworklog.daliyworklog.model.BenefitItemDTO;
//import com.thiru.daliyworklog.daliyworklog.model.PaymentRequestDTO;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/stripe")
//@CrossOrigin
//public class StripePaymentController {
//
//	@Value("${stripe.secret.key}")
//	private String stripeurl;
//
////	@PostMapping("/create-invoice")
////	public ResponseEntity<?> createInvoice(@RequestBody PaymentRequestDTO request) {
////		try {
////			Stripe.apiKey = stripeurl;
////
////			// Step 1: Create or get customer
////			Customer customer = Customer.create(Map.of("email", request.getCustomerEmail()));
////			System.out.println("✅ Created customer with ID: " + customer.getId());
////
////			double subtotal = 0;
////
////			// Step 2: Create invoice items and calculate subtotal
////			for (BenefitItemDTO item : request.getItems()) {
////				long amountInPaise = Math.round(item.getTotal() * 100);
////				subtotal += item.getTotal(); // 💡 Add to subtotal in rupees
////
////				InvoiceItem invoiceItem = InvoiceItem
////						.create(Map.of("customer", customer.getId(), "amount", amountInPaise, "currency", "inr",
////								"description", item.getBenefitName() + "(" + item.getSelectedTierId() + ")"));
////				System.out.println("✅ Created InvoiceItem with ID: " + invoiceItem.getId());
////			}
////
////			// Step 3: Apply discount as a negative invoice item
////			if (request.getDiscountPercent() > 0) {
////				double discountAmount = subtotal * request.getDiscountPercent(); // 💡 Correct subtotal now
////				long discountInPaise = Math.round(discountAmount * 100);
////
////				InvoiceItem.create(Map.of("customer", customer.getId(), "amount", -discountInPaise, // 💸 Negative for
////																									// discount
////						"currency", "inr", "description", "Discount (" + (request.getDiscountPercent() * 100) + "%)"));
////			}
////
////			// Step 3: Create invoice (IMPORTANT: include pending items)
////			Map<String, Object> invoiceParams = new HashMap<>();
////			invoiceParams.put("customer", customer.getId());
////			invoiceParams.put("collection_method", "send_invoice");
////			invoiceParams.put("days_until_due", 7);
////			invoiceParams.put("pending_invoice_items_behavior", "include"); // crucial!
////
////			Invoice invoice = Invoice.create(invoiceParams);
////
////			// Step 4: Finalize invoice (this sends the email)
////			Invoice finalized = invoice.finalizeInvoice();
////			System.out.println("📨 Finalized Invoice URL: " + finalized.getHostedInvoiceUrl());
////
////			return ResponseEntity
////					.ok(Map.of("invoiceUrl", finalized.getHostedInvoiceUrl(), "invoiceId", finalized.getId()));
////
////		} catch (Exception e) {
////			e.printStackTrace();
////			return ResponseEntity.status(500).body("Stripe Error: " + e.getMessage());
////		}
////	}
//
//}
