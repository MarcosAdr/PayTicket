package com.tesis.payticket.controllers;

import java.math.BigDecimal;
import java.util.Arrays;

import com.tesis.payticket.models.entity.Compra;
import com.tesis.payticket.models.entity.Evento;
import com.tesis.payticket.models.entity.Localidad;
import com.tesis.payticket.models.entity.Usuario;
import com.tesis.payticket.models.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.braintreegateway.CreditCard;
import com.braintreegateway.Customer;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.TransactionRequest;
import com.braintreegateway.ValidationError;
import com.braintreegateway.Transaction.Status;

import java.util.logging.Logger;


@Controller
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @Autowired
    IUsuarioService usuarioService;

    @Autowired
    IEventoService eventoService;

    @Autowired
    ICompraService compraService;

    @Autowired
    ILocalidadService localidadService;

    //private static final Logger logger = Logger.getLogger();

    private final Status[] TRANSACTION_SUCCESS_STATUSES = new Status[]{
            Transaction.Status.AUTHORIZED,
            Transaction.Status.AUTHORIZING,
            Transaction.Status.SETTLED,
            Transaction.Status.SETTLEMENT_CONFIRMED,
            Transaction.Status.SETTLEMENT_PENDING,
            Transaction.Status.SETTLING,
            Transaction.Status.SUBMITTED_FOR_SETTLEMENT
    };

    @RequestMapping(value = "/checkouts", method = RequestMethod.GET)
    public String checkout(Model model, @RequestParam("total") float total,
                           @RequestParam("localidadId") Long idLocalidad,
                           @RequestParam("cantidad") int cantidad
                           ) {
        String clientToken = paymentService.getToken();
        model.addAttribute("clientToken", clientToken);
        model.addAttribute("total", total);
        model.addAttribute("localidadId", idLocalidad);
        model.addAttribute("cantidad", cantidad);
        return "checkouts/new";
    }

    @RequestMapping(value = "/checkouts", method = RequestMethod.POST)
    public String postForm(Model model,
                           @RequestParam("total") String amount,
                           @RequestParam("payment_method_nonce") String nonce,
                           @RequestParam("localidadId") Long idLocalidad,
                           @RequestParam("cantidad") int cantidad,
                           final RedirectAttributes redirectAttributes) {

        //logger.info("Received total amount: " + amount);
        BigDecimal decimalAmount;
        try {
            decimalAmount = new BigDecimal(amount);
        } catch (NumberFormatException e) {
            redirectAttributes.addFlashAttribute("errorDetails", "Error: 81503: Amount is an invalid format.");
            return "redirect:/checkouts";
        }

        TransactionRequest request = new TransactionRequest()
                .amount(decimalAmount)
                .paymentMethodNonce(nonce)
                .options()
                .submitForSettlement(true)
                .done();

        Result<Transaction> result = paymentService.getGateway().transaction().sale(request);

        if (result.isSuccess()) {
            Transaction transaction = result.getTarget();

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();

            Usuario usuario = usuarioService.findByUsername(username);

            Localidad localidad = localidadService.findOne(idLocalidad);

            if (Arrays.asList(TRANSACTION_SUCCESS_STATUSES).contains(transaction.getStatus())) {
                Compra compra = new Compra();
                compra.setTransaccion(transaction.getId());
                compra.setUsuario(usuario);
                compra.setMonto(transaction.getAmount().floatValue());
                compra.setLocalidad(localidad);
                compra.setFechaTransaccion(transaction.getCreatedAt().getTime());
                compra.setCantidad(cantidad);
                compraService.save(compra);
            }

            return "redirect:/checkouts/" + transaction.getId();
        } else if (result.getTransaction() != null) {
            Transaction transaction = result.getTransaction();
            return "redirect:/checkouts/" + transaction.getId();
        } else {
            StringBuilder errorString = new StringBuilder();
            for (ValidationError error : result.getErrors().getAllDeepValidationErrors()) {
                errorString.append("Error: ").append(error.getCode()).append(": ").append(error.getMessage()).append("\n");
            }
            redirectAttributes.addFlashAttribute("errorDetails", errorString.toString());
            return "redirect:/checkouts";
        }
    }


    @RequestMapping(value = "/checkouts/{transactionId}")
    public String getTransaction(@PathVariable String transactionId, Model model) {

        Transaction transaction;
        CreditCard creditCard;
        Customer customer;

        try {
            transaction = paymentService.getGateway().transaction().find(transactionId);
            creditCard = transaction.getCreditCard();
            customer = transaction.getCustomer();
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return "redirect:/checkouts";
        }

        model.addAttribute("isSuccess", Arrays.asList(TRANSACTION_SUCCESS_STATUSES).contains(transaction.getStatus()));
        model.addAttribute("transaction", transaction);
        model.addAttribute("creditCard", creditCard);
        model.addAttribute("customer", customer);

        return "checkouts/show";
    }


}
