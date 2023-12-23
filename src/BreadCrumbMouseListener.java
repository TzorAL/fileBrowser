package FileBrowser;

/*
 * Implementation of MouseListener inteface used to detect if a button in the
 * breadcurmb was pressed and properly refresh File Browser to the new current
 * directory
*/

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.util.regex.Pattern;

/**
 *
 * @author altzortzis <altzortzis@uth.gr>
 */
public class BreadCrumbMouseListener  implements MouseListener {
    private JButton btnPressed;
    private String fullPath;
    private JPanel dirCard;
    
    public BreadCrumbMouseListener(String fullPath, JPanel dirCard) {
        this.fullPath = fullPath;
        this.dirCard = dirCard;
    }
 
    @Override
    public void mouseClicked (MouseEvent e){  
        String fileSeperator = Pattern.quote(System.getProperty("file.separator"));
        String startOfFS;
        btnPressed = (JButton) e.getSource();
        if(fullPath.indexOf(File.separator) != -1)
            startOfFS = fullPath.substring(0,fullPath.indexOf(File.separator));
        else
            startOfFS = fullPath;
        if(e.getClickCount() == 1){
            btnPressed.setBackground(Color.GRAY);
            
            /*If user clicked on root button*/
            if(btnPressed.getText().equals("C:") && startOfFS.equals("C:")){
                /*  > clear dirCard from all stored panels,
                    > add new dirPanel based on File.Seperator(first char
                    in string path and identifier for root of file system)
                    > create a cardlayout and add dirCard to it
                    > show this new Card where dirPanel is placed
                */
                dirCard.removeAll();
                dirCard.add(new DirPanel(new File("C:\\"),dirCard ));
                CardLayout cl =  (CardLayout) dirCard.getLayout();
                cl.addLayoutComponent(dirCard, File.separator);
                cl.show(dirCard,File.separator);
            }
            else if(btnPressed.getText().equals("/")){
            	/*  > clear dirCard from all stored panels,
                    > add new dirPanel based on File.Seperator(first char
                    in string path and identifier for root of file system)
                    > create a cardlayout and add dirCard to it
                    > show this new Card where dirPanel is placed
                */
            	dirCard.removeAll();
                dirCard.add(new DirPanel(new File(File.separator),dirCard ));
                CardLayout cl =  (CardLayout) dirCard.getLayout();
                cl.addLayoutComponent(dirCard, File.separator);
                cl.show(dirCard,File.separator);	
            }
            else if(btnPressed.getText().equals(startOfFS)){
            	/*  > clear dirCard from all stored panels,
                    > add new dirPanel based on File.Seperator(first char
                    in string path and identifier for root of file system)
                    > create a cardlayout and add dirCard to it
                    > show this new Card where dirPanel is placed
                */
            	dirCard.removeAll();
                dirCard.add(new DirPanel(new File(startOfFS),dirCard ));
                CardLayout cl =  (CardLayout) dirCard.getLayout();
                cl.addLayoutComponent(dirCard, File.separator);
                cl.show(dirCard,File.separator);
            }
            else{
                System.out.println("Clicked: \""+btnPressed.getText()+"\" button");
                /*create path based on the button user clicked*/
                String newPath = "";//startOfFS;
                
                for(String dir_path: fullPath.split(fileSeperator)){
                    if(dir_path.equals(btnPressed.getText())){
                        newPath += dir_path;
                        break;
                    }                        
                    newPath += dir_path + File.separator;
                }
                /*  > clear dirCard from all stored panels,
                    > add new dirPanel based on fileName
                    > create a cardlayout and add dirCard to it
                    > show this new Card where dirPanel is placed
                */
                dirCard.removeAll();
                dirCard.add(new DirPanel(new File(newPath),dirCard ));
                CardLayout cl =  (CardLayout) dirCard.getLayout();
                cl.addLayoutComponent(dirCard, newPath);
                cl.show(dirCard,newPath);
            }
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
        btn.setBackground(new Color(249,249,249));
   }
}