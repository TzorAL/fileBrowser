package FileBrowser;

/*
 * Implementation of MouseListener inteface used to detect and seperate 
 * between single,double or right click of a button.This can be achieved by
 * starting a timer when an event is detected.
 * if the time it takes for a second event to appear is longer than
 * the time the system considers it a double-click (time difference is stored in 
 * variable "clickInterval") then the two clicks are considered independent 
 * single (always left) clicks.But if that' not the case,then we identify the two
 * clicks as a single double-click.
 * Disclaimer: We never start (but restart) the timer so that the timer does not 
 * constantly fire action events,but rather start running only at the specific
 * timeframe where clicks are detected
*/

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 *
 * @author altzortzis <altzortzis@uth.gr>
 */
public class FavouritesMouseListener implements MouseListener{
    private String fullPath;
    boolean rightClick;
    private final static int clickInterval = (Integer)Toolkit.getDefaultToolkit().getDesktopProperty("awt.multiClickInterval");
    private JButton btnPressed;
    private MouseEvent lastEvent;
    private Timer timer;
    private JPanel dirCard;
    private JPopupMenu popupMenu;
    private String btnName;
    
    public FavouritesMouseListener(String fullPath,JPopupMenu popupMenu,JPanel dirCard) {
        this.dirCard = dirCard;
        this.fullPath = fullPath;
        this.popupMenu = popupMenu;
        
        timer = new Timer( clickInterval, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.stop(); //(3.1: if clickInterval milliseconds have passed,stop)
                if(rightClick == false){
                    btnPressed.setBackground(Color.GRAY);
                    System.out.println("Clicked: \""+btnPressed.getText()+"\" button!");
                    
                    MenuBar mbar = new MenuBar();
                    mbar.enableEdit(true);
                }
            }
        });
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        btnPressed = (JButton) e.getSource();
        btnPressed.setBackground(Color.GRAY);
       
        if(e.getClickCount() > 2){return;}
        btnPressed = (JButton) e.getSource();
        btnName = new String(btnPressed.getText());

        if (SwingUtilities.isRightMouseButton(e) && e.getClickCount() == 1) {
            System.out.println("RightCLicked1: "+btnPressed.getText());
            popupMenu.show(e.getComponent(), e.getX(), e.getY());
            FavouritesPanel.btnPressed = btnPressed;
            rightClick = true;//avoid left-click option to act
            btnPressed.setBackground(Color.GRAY);
        }
        if(SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 1){
            rightClick = false;
        }
                
        // 3.2: if second click detected and 3.1 hasnt happened yet
        if (timer.isRunning()){
            timer.stop();
            if(SwingUtilities.isLeftMouseButton(e)){
                System.out.println("Double-clicked: \""+btnPressed.getText()+"\" button"); 
                File newDir = new File(fullPath); 
                if(newDir.isDirectory()){
                    dirCard.removeAll();
                    dirCard.add(new DirPanel(newDir,dirCard));
                    CardLayout cl =  (CardLayout) dirCard.getLayout();
                    cl.addLayoutComponent(dirCard, fullPath);
                    cl.show(dirCard,fullPath);                  
                }
                else{
                    if(Desktop.isDesktopSupported()) {
                        try {
                            Desktop.getDesktop().open(new File(fullPath));
                        } catch (IOException ex) {
                            System.out.println("IO Exception when opening file");
                            System.exit(1);
                        }
                    }
                    else{
                        System.out.println("Desktop not supported");
                    }
                }
            }
        }
        else{//First click (1)
            timer.restart(); //start timer (2)
        }
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        JButton btn = (JButton) e.getSource();
        btn.setBackground(Color.LIGHT_GRAY);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        JButton btn = (JButton) e.getSource();
        btn.setBackground(new Color(238, 238, 238)); //reset colour
    }
}
