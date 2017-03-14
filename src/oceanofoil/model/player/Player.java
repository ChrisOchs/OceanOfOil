
package oceanofoil.model.player;

import oceanofoil.model.company.CompanyOwner;

/**
 *
 * @author 
 */
public class Player implements CompanyOwner {
    
    private String name;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
