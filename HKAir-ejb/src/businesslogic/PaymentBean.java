/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businesslogic;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.xml.ws.WebServiceRef;
import webservice.BankWS_Service;

/**
 *
 * @author Lokesh
 */
@Stateless
@LocalBean
public class PaymentBean {

    @WebServiceRef(wsdlLocation = "localhost:8080/BankAppWS/BankWS?wsdl")
    private BankWS_Service service;

    private String debitAccount(java.lang.String firstname, java.lang.String lastname, java.lang.String cardnumber, java.lang.String security, java.lang.String expires, double amount) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        webservice.BankWS port = service.getBankWSPort();
        return port.debitAccount(firstname, lastname, cardnumber, security, expires, amount);
    }

    
    

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    public String debit(String firstname, String lastname, String card, String security, String expires, double amount) {
        return debitAccount(firstname, lastname, card, security, expires, amount);
    }
    
}
