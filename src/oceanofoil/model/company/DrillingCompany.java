
package oceanofoil.model.company;

/**
 *
 * @author 
 */
public class DrillingCompany {
    
   private CompanyOwner owner;
   private String companyName;

   private int bankAccount = 20000;

   public DrillingCompany(CompanyOwner owner, String companyName) {
       this.owner = owner;
       this.companyName = companyName;
   }

   public CompanyOwner getCompanyOwner() {
       return owner;
   }

   public String getCompanyName() {
       return companyName;
   }

   public int getBankAccount() {
       return bankAccount;
   }
}
