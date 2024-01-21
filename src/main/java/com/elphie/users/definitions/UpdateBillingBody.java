package com.elphie.users.definitions;

@Data
public class UpdateBillingBody {
   // PROPERTIES ////////////////
   private Long userId;
   private String address_line_1;
   private String address_line_2;
   private String county;
   private String eircode;
   private String card_full_name;
   private String card_number;
   private String card_expiry;
   private String card_cvc;
   // GETTERS & SETTERS ////////////////
   public void setUserId(Long userId) { this.userId = userId; }
   public Long getUserId() { return userId; }
   public void setAddressLine1(String address_line_1) { this.address_line_1 = address_line_1; }
   public String getAddressLine1() { return address_line_1; }
   public void setAddressLine2(String address_line_2) { this.address_line_2 = address_line_2; }
   public String getAddressLine2() { return address_line_2; }
   public void setCounty(String county) { this.county = county; }
   public String getCounty() { return county; }
   public void setEircode(String eircode) { this.eircode = eircode; }
   public String getEircode() { return eircode; }
   public void setCardName(String card_full_name) { this.card_full_name = card_full_name; }
   public String getCardName() { return card_full_name; }
   public void setCardNumber(String card_number) { this.card_number = card_number; }
   public String getCardNumber() { return card_number; }
   public void setCardExpiry(String card_expiry) { this.card_expiry = card_expiry; }
   public String getCardExpiry() { return card_expiry; }
   public void setCardCVC(String card_cvc) { this.card_cvc = card_cvc; }
   public String getCardCVC() { return card_cvc; }
}
