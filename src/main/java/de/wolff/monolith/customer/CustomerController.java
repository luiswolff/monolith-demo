package de.wolff.monolith.customer;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

@RequestScoped
@Named
public class CustomerController implements Converter{

    @Model
    @Produces
    private Customer customer;

    @Inject
    private CustomerBean customerBean;

    @Inject
    private HttpServletRequest request;

    @PostConstruct
    public void init(){
        String id = request.getParameter("id");
        if (id == null){
            customer = new Customer();
        } else {
            customer = customerBean.findOne(Long.parseLong(id));
        }
    }

    @Produces
    @Customer.CustomerList
    public List<Customer> customerList(){
        return Collections.unmodifiableList(customerBean.findAll());
    }

    public String save(){
        customerBean.save(customer);
        return "/success";
    }

    public String delete(Long id){
        customerBean.delete(id);
        return "/success";
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Long id = Long.parseLong(value);
        return customerBean.findOne(id);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Customer c = (Customer) value;
        return String.valueOf(c.getId());
    }
}
