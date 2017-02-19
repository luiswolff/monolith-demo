package de.wolff.monolith.catalog;

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
public class CatalogController implements Converter{

    @Model
    @Produces
    private Item item = new Item();

    @Inject
    private CatalogBean catalogBean;

    private Long id;

    private String name;

    public void loadItem(){
        if (id == null){
            return;
        }
        item = catalogBean.findOne(id);
    }

    public void setId(Long id){
        this.id = id;
    }

    public Long getId(){
        return id;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Produces
    @Item.ItemList
    public List<Item> itemList(){
        List<Item> items;
        if (name != null)
            items = Collections.unmodifiableList(catalogBean.findByNameContaining(name));
        else
            items = Collections.unmodifiableList(catalogBean.findAll());
        return items;
    }

    public String save(){
        catalogBean.save(item);
        return "/success";
    }

    public String delete(Long id){
        catalogBean.delete(id);
        return "/success";
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Long id = Long.parseLong(value);
        return catalogBean.findOne(id);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Item i = (Item) value;
        return String.valueOf(i.getId());
    }
}
