package FileBrowser;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author altzortzis <altzortzis@uth.gr>
 */

public class DirPanel extends javax.swing.JPanel{

    public DirPanel(File curDir,JPanel dirCard) {
        super();
        
        /*Panel that shows contents of currently open directory*/
        JPanel dirContent = new JPanel();
        
        /*We basically encapsulate the content panel in JScrollPane
          so that it becomes scrollable*/
        JScrollPane scrollPane = new JScrollPane(new DirContentPanel(curDir,dirCard));
                
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        dirContent.setLayout(new WrapLayout());
        this.setLayout(new BorderLayout());
        /*dirPanel(
            breadCrumbPanel
            ScrollPane(
                dirContent
            )
        )
        */
        
        /*  We modify static variable curDir which is is a string
            containing the full path of current directory
        */
        MenuActionListener.setCurDir(curDir.getAbsolutePath());
        this.add(new BreadCrumbPanel(curDir,dirCard),BorderLayout.NORTH);
        this.add(scrollPane,BorderLayout.CENTER);
    }
}
