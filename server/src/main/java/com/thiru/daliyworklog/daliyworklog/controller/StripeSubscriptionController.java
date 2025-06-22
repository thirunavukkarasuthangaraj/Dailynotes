package com.thiru.daliyworklog.daliyworklog.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.CustomerCollection;
import com.stripe.model.Event;
import com.stripe.model.Invoice;
import com.stripe.model.InvoiceItem;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.model.Subscription;
import com.stripe.model.Coupon;
import com.stripe.net.Webhook;
import com.stripe.param.InvoiceCreateParams;
import com.stripe.param.InvoiceItemCreateParams;
import com.stripe.param.SubscriptionCreateParams;
import com.stripe.param.ProductCreateParams;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.CouponCreateParams;
import com.thiru.daliyworklog.daliyworklog.model.AddonItem;
import com.thiru.daliyworklog.daliyworklog.model.InvoiceRequest;
import com.thiru.daliyworklog.daliyworklog.model.SubscriptionRequestDTO;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/stripe")
public class StripeSubscriptionController {

	@Value("${stripe.secret.key}")
	private String stripeurl;

@PostMapping("/create-subscription")
public ResponseEntity<?> createSubscription(@RequestBody SubscriptionRequestDTO request) {
    try {
        Stripe.apiKey = stripeurl;

        // 1. Create or retrieve customer
        Customer customer = getOrCreateCustomer(request.getCustomerEmail());

        // 2. Create products and prices for base plan and add-ons
        List<SubscriptionCreateParams.Item> subscriptionItems = new ArrayList<>();

        // Find base plan and add-ons
        AddonItem basePlan = request.getItems().stream()
            .filter(item -> "Base Plan".equals(item.getBenefitName()))
            .findFirst()
            .orElse(null);

        List<AddonItem> addOns = request.getItems().stream()
            .filter(item -> !"Base Plan".equals(item.getBenefitName()))
            .collect(Collectors.toList());

        // 3. Create base plan subscription item
        if (basePlan != null) {
            String basePlanPriceId = createOrGetPrice(basePlan, "monthly");
            subscriptionItems.add(
                SubscriptionCreateParams.Item.builder()
                    .setPrice(basePlanPriceId)
                    .setQuantity(1L)
                    .build()
            );
        }

        // 4. Create add-on subscription items
        for (AddonItem addOn : addOns) {
            String addOnPriceId = createOrGetPrice(addOn, "monthly");
            subscriptionItems.add(
                SubscriptionCreateParams.Item.builder()
                    .setPrice(addOnPriceId)
                    .setQuantity(1L)
                    .build()
            );
        }

        // 5. Create subscription
        SubscriptionCreateParams subscriptionParams = SubscriptionCreateParams.builder()
            .setCustomer(customer.getId())
            .addAllItem(subscriptionItems)
            .setBillingCycleAnchor(System.currentTimeMillis() / 1000 + (30 * 24 * 60 * 60))
            .setCollectionMethod(SubscriptionCreateParams.CollectionMethod.SEND_INVOICE)
            .setDaysUntilDue(7L)
            .build();

        Subscription subscription = Subscription.create(subscriptionParams);

        // 6. Get the latest invoice BUT DON'T finalize yet
        Map<String, Object> invoiceListParams = new HashMap<>();
        invoiceListParams.put("customer", customer.getId());
        invoiceListParams.put("status", "draft");
        invoiceListParams.put("limit", 1);

        List<Invoice> invoices = Invoice.list(invoiceListParams).getData();
        String invoiceUrl = null;

        if (!invoices.isEmpty()) {
            Invoice invoice = invoices.get(0);
            
            // Add discount to the specific invoice BEFORE finalizing
            if (request.getDiscountPercentage() > 0) {
                long totalAmount = request.getItems().stream()
                    .mapToLong(item -> item.getAmount())
                    .sum();
                
                long discountAmount = (totalAmount * request.getDiscountPercentage()) / 100;
                
                InvoiceItem.create(
                    InvoiceItemCreateParams.builder()
                        .setCustomer(customer.getId())
                        .setInvoice(invoice.getId())
                        .setCurrency("usd")
                        .setAmount(-discountAmount)
                        .setDescription("Discount " + request.getDiscountPercentage() + "%")
                        .build()
                );
            }
            
            // NOW finalize the invoice
            invoice = invoice.finalizeInvoice();
            invoiceUrl = invoice.getHostedInvoiceUrl();
        }

        return ResponseEntity.ok(Map.of(
            "subscriptionId", subscription.getId(),
            "customerId", customer.getId(),
            "status", subscription.getStatus(),
            "currentPeriodStart", subscription.getCurrentPeriodStart(),
            "currentPeriodEnd", subscription.getCurrentPeriodEnd(),
            "invoiceUrl", invoiceUrl != null ? invoiceUrl : "No invoice available"
        ));

    } catch (StripeException e) {
        e.printStackTrace();
        return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
    }
}

	private String createOrGetPrice(AddonItem item, String interval) throws StripeException {
		// Create product first
		String productName = item.getBenefitName() + " - " + item.getDescription();

		ProductCreateParams productParams = ProductCreateParams.builder().setName(productName)
				.setDescription(item.getDescription()).build();

		Product product = Product.create(productParams);

		// Create price for the product
		PriceCreateParams.Recurring recurring = PriceCreateParams.Recurring.builder()
				.setInterval(PriceCreateParams.Recurring.Interval.MONTH).setIntervalCount(1L).build();

		PriceCreateParams priceParams = PriceCreateParams.builder().setProduct(product.getId())
				.setUnitAmount((long) item.getAmount()).setCurrency("usd").setRecurring(recurring).build();

		Price price = Price.create(priceParams);
		return price.getId();
	}

	@SuppressWarnings("unused")
	private String createDiscountCoupon(long discountPercentage) throws StripeException {
		CouponCreateParams couponParams = CouponCreateParams.builder()
				.setPercentOff(BigDecimal.valueOf(discountPercentage))
				.setDuration(CouponCreateParams.Duration.REPEATING) // Change from ONCE to REPEATING
				.setDurationInMonths(1L) // Add this line
				.setName("Subscription Discount " + discountPercentage + "%").build();

		Coupon coupon = Coupon.create(couponParams);
		return coupon.getId();
	}

	// Keep your existing invoice creation method
	@PostMapping("/create-invoice")
	public ResponseEntity<?> createInvoice(@RequestBody InvoiceRequest request) {
		try {
			Stripe.apiKey = stripeurl;

			// 1. Create or retrieve customer
			Customer customer = getOrCreateCustomer(request.getCustomerEmail());

			// 2. Create InvoiceItems
			for (AddonItem item : request.getItems()) {
				InvoiceItemCreateParams itemParams = InvoiceItemCreateParams.builder().setCustomer(customer.getId())
						.setCurrency("usd").setAmount((long) item.getAmount()).setDescription(item.getDescription())
						.build();

				InvoiceItem.create(itemParams);
			}

			// 3. Apply discount as a negative invoice item (if needed)
			if (request.getDiscountPercentage() > 0) {
				long totalBeforeDiscount = request.getItems().stream().mapToLong(AddonItem::getAmount).sum();

				long discountAmount = (totalBeforeDiscount * request.getDiscountPercentage()) / 100;

				// Optional safety: Prevent over-discount (negative invoice)
				if (discountAmount > totalBeforeDiscount) {
					discountAmount = totalBeforeDiscount;
				}

				InvoiceItem.create(InvoiceItemCreateParams.builder().setCustomer(customer.getId()).setCurrency("usd")
						.setAmount(-discountAmount).setDescription("Discount " + request.getDiscountPercentage() + "%")
						.build());
			}

			// Add discount as invoice item AFTER subscription creation
			if (request.getDiscountPercentage() > 0) {
				long totalAmount = request.getItems().stream().mapToLong(item -> item.getAmount()).sum();

				long discountAmount = (totalAmount * request.getDiscountPercentage()) / 100;

				InvoiceItem.create(InvoiceItemCreateParams.builder().setCustomer(customer.getId()).setCurrency("usd")
						.setAmount(-discountAmount).setDescription("Discount " + request.getDiscountPercentage() + "%")
						.build());
			}

			// 4. Create the invoice
			InvoiceCreateParams invoiceParams = InvoiceCreateParams.builder().setCustomer(customer.getId())
					.setCollectionMethod(InvoiceCreateParams.CollectionMethod.SEND_INVOICE).setDaysUntilDue(7L)
					.setPendingInvoiceItemsBehavior(InvoiceCreateParams.PendingInvoiceItemsBehavior.INCLUDE).build();

			Invoice invoice = Invoice.create(invoiceParams);

			// 5. Finalize the invoice (required for hosted URL)
			invoice = invoice.finalizeInvoice();

			return ResponseEntity.ok(Map.of("invoiceId", invoice.getId(), "invoiceUrl", invoice.getHostedInvoiceUrl()));

		} catch (StripeException e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
		}
	}

	private Customer getOrCreateCustomer(String email) throws StripeException {
		// Search for existing customer
		Map<String, Object> searchParams = new HashMap<>();
		searchParams.put("email", email);
		CustomerCollection customers = Customer.list(searchParams);

		if (!customers.getData().isEmpty()) {
			return customers.getData().get(0);
		}

		// Create if not found
		Map<String, Object> createParams = new HashMap<>();
		createParams.put("email", email);
		return Customer.create(createParams);
	}

	@PostMapping("/webhook")
	public ResponseEntity<String> handleStripeWebhook(@RequestBody String payload,
			@RequestHeader("Stripe-Signature") String sigHeader) {
		String endpointSecret = "your_webhook_secret";
		try {
			Event event = Webhook.constructEvent(payload, sigHeader, endpointSecret);

			switch (event.getType()) {
			case "invoice.paid":
				// Handle post-payment logic
				break;
			case "invoice.payment_failed":
				// Send alert
				break;
			case "customer.subscription.created":
				// Handle new subscription
				break;
			case "customer.subscription.updated":
				// Handle subscription changes
				break;
			case "customer.subscription.deleted":
				// Handle subscription cancellation
				break;
			}
			return ResponseEntity.ok("");
		} catch (Exception e) {
			return ResponseEntity.status(400).body("Webhook error: " + e.getMessage());
		}
	}

}