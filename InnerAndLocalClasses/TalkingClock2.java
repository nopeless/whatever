package InnerAndLocalClasses;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.Timer;

public class TalkingClock2 {

    private int interval;
    private boolean beep;

    public TalkingClock2(int interval, boolean beep){
        this.interval = interval;
        this.beep = beep;
    }    

    public void start(){
        class TimePrinter implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e){
                System.out.println("At the tone the time is " + new Date());
                if (TalkingClock2.this.beep){
                    Toolkit.getDefaultToolkit().beep();
                }
            }

        }
        Timer t = new Timer(interval, new TimePrinter());
        t.start();

    }

    public static void main(String[] args){
        TalkingClock clock = new TalkingClock(2000, true);
        //TalkingClock.TimePrinter printer = clock.new TimePrinter();

        clock.start();

        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
