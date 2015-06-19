package by.academy.it.navigation.commands;

import by.academy.it.dao.exceptions.DaoException;
import by.academy.it.service.CreditCardService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static by.academy.it.resources.constant.Constants.*;

public class CreateCreditCardCommand implements Command{
    @Override
    public String execCommand(HttpServletRequest request, HttpServletResponse response) {
//        int creditCardPinCode = Integer.parseInt(request.getParameter(PARAM_CREDIT_CARD_PIN_CODE));

        try {
            CreditCardService creditCardService = new CreditCardService();
            int creditCardID = creditCardService.create();
        }catch (DaoException e){
            e.printStackTrace();
        }
        return PAGE_MAIN;
    }
}
