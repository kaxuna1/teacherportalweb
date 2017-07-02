package com.technonet.controlers;

import com.github.kevinsawicki.http.HttpRequest;
import com.technonet.Repository.PaymentsRepo;
import com.technonet.model.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by vakhtanggelashvili on 3/15/17.
 */
@Controller
public class PaymentsController {
    @RequestMapping("/confirmpayment")
    @ResponseBody
    public boolean confirmPayment(@RequestParam(value = "token", required = true, defaultValue = "") String token,
                                  @RequestParam(value = "id", required = true, defaultValue = "")String id){
        Payment payment = paymentsRepo.findByUuid(id);
        payment.confirm();
        paymentsRepo.save(payment);
        return true;
    }
    @RequestMapping("/paymentok")
    @ResponseBody
    public String payementOk(@RequestParam("trans_id")String trans_id){

        String sURL = "http://allwitz.com:88/check.php?id="+trans_id; //just a string
        String response = HttpRequest.get(sURL).body();
        return response;


        //return "redirect:/";
    }
    @Autowired
    PaymentsRepo paymentsRepo;
}
