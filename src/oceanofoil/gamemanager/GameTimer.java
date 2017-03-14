
package oceanofoil.gamemanager;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import oceanofoil.util.Updateable;

/**
 *
 * @author 
 */
public class GameTimer implements Updateable {
    
    private long lastUpdate = System.currentTimeMillis();

    private Calendar currentDate;

    private int updates;

    public GameTimer() {
        currentDate = new GregorianCalendar(1900, Calendar.JANUARY, 1);
    }

    public void update() {
        if(System.currentTimeMillis() - lastUpdate >= 100) {
            lastUpdate = System.currentTimeMillis();
            updates++;

            currentDate.add(Calendar.MINUTE, 30);
        }
    }

    public int getUpdate() {
        return updates;
    }

    public Date getCurrentGameDate() {
        return currentDate.getTime();
    }
}
