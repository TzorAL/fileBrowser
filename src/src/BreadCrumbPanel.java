package FileBrowser;

/*
 * Extension of JPanel class used to implement the breadcrumb bar of 
 * the File Explorer.Each segment of the path,is a direct link to its respected
 * path,which when clicked the fileBrowser sets it as its current directory,as well
 * as displaying its contents.
 */

import java.awt.Color;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.util.regex.Pattern;

/**
 *
 * @author altzortzis <altzortzis@uth.gr>
 */
public class BreadCrumbPanel extends javax.swing.JPanel{
    private File curDir;
    private JPanel dirCard;
    
    public BreadCrumbPanel(File curDir, JPanel dirCard) {
        super();
        this.curDir = curDir;
        this.dirCard = dirCard;
        setBreadCrumb(this,dirCard);
    }
    
    private void setBreadCrumb(JPanel parent_panel,JPanel dirCard){
        JButton dirBtn;
        JLabel pathSeperator;

        this.setBackground(new Color(56, 56, 56));
        String fullPath = curDir.getAbsolutePath();
        String startOfFS = fullPath.substring(0,fullPath.indexOf(File.separator));
        if(startOfFS.length() == 0)
            startOfFS = "/";

        /*  Create an array of drectories that result to the path of 
            the current directory*/
        String fileSeperator = Pattern.quote(System.getProperty("file.separator"));
        String[] path = curDir.getAbsolutePath().split(fileSeperator);
        StringBuilder breadCrumbText = new StringBuilder();
                
        /*create root directory button*/
        dirBtn = new JButton(startOfFS);
        dirBtn.setBorderPainted(false);
        dirBtn.setBackground(new Color(249,249,249));
        dirBtn.addMouseListener(new BreadCrumbMouseListener(startOfFS,dirCard));
        this.add(dirBtn);
        
        /*  Based on path,array,for each slot of the array,create new Button
            in breadcrumb based on that slot*/
        for(int i = 1; i<path.length; i++){
            pathSeperator = new JLabel(" > ");
            pathSeperator.setForeground(new Color(248, 238, 226));
            dirBtn = new JButton(path[i]);
            dirBtn.setBorderPainted(false);
            dirBtn.setBackground(new Color(249,249,249));
            dirBtn.addMouseListener(new BreadCrumbMouseListener(curDir.getAbsolutePath(),dirCard ));
            breadCrumbText.append(path[i]+" > ");
            this.add(pathSeperator);
            this.add(dirBtn);
        }
    }

}
