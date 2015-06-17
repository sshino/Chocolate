package ac.tsuda;

//import java.net.URL;
import java.util.Date;

import javax.jdo.annotations.*;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class LinkData {
  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  private Long id;
   
  @Persistent
  private String username;
   
  @Persistent
  private String address;
  
  @Persistent
  private String item;
  
  @Persistent
  private String price;
   
  @Persistent
  private Date datetime;

  public LinkData(String title,String address, String item, String price, Date datetime) {
      super();
      this.username = title;
      this.address = address;
      this.item = item;
      this.price = price;
      this.datetime = datetime;
  }

  public Long getId() {
      return id;
  }

  public void setId(Long id) {
      this.id = id;
  }

  public String getTitle() {
      return username;
  }

  public void setTitle(String title) {
      this.username = title;
  }

  public String getAddress() {
      return address;
  }

  public void setAddress(String address) {
      this.address = address;
  }
  
  public String getItem() {
      return item;
  }

  public void setItem(String item) {
      this.item = item;
  }
  
  public String getPrice() {
      return price;
  }

  public void setPrice(String price) {
      this.price = price;
  }

  public Date getDatetime() {
      return datetime;
  }

  public void setDatetime(Date datetime) {
      this.datetime = datetime;
  }
}
