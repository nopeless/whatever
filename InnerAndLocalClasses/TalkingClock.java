package InnerAndLocalClasses;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.Timer;
public class TalkingClock {

    private int interval;
    private boolean beep;

    public TalkingClock(int interval, boolean beep){
        this.interval = interval;
        this.beep = beep;
    }    
    public void start(){
        ActionListener listener = new TimePrinter();
        Timer t = new Timer(interval, listener);
        //starts timer in new thread
        t.start();
    }
    class TimePrinter implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            System.out.println("At the tone the time is " + new Date());
            if (TalkingClock.this.beep){
                Toolkit.getDefaultToolkit().beep();
            }
        }

        public void hello(){
            System.out.println("Hello");
        }
    }

    public static void main(String[] args){
        TalkingClock clock = new TalkingClock(2000, true);
        TalkingClock.TimePrinter printer = clock.new TimePrinter();
        printer.hello();
        //Just another way to call actionPerformed rather than using start()
        //printer.actionPerformed(null);
        clock.start();

        try {
            System.out.println("Hello");
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
