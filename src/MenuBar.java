package FileBrowser;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/**
 *
 * @author altzortzis <altzortzis@uth.gr>
 */
public class MenuBar extends javax.swing.JMenuBar{
    private JPanel searchBar;
    private JFrame frame;
    private static JMenu mEdit;
    private static JMenuItem miPaste;
    private static JMenuItem miAdd2Fav;
    
    public MenuBar(){}
    
    public MenuBar(JPanel searchBar, JFrame frame,File curDir) {
        this.searchBar = searchBar;
        this.frame = frame;
        this.setBackground(new Color(225,213,231));
        
    /*~~~~~~~~~~~~~~~~~ Init varialbes ~~~~~~~~~~~~~~~*/
        JMenu mFile = new JMenu("File");
        JMenuItem miNewWindow = new JMenuItem("New Window");
        JMenuItem miExit = new JMenuItem("Exit");
        
        mEdit = new JMenu("Edit");
        JMenuItem miCut = new JMenuItem("Cut");
        JMenuItem miCopy = new JMenuItem("Copy");
        miPaste = new JMenuItem("Paste");
        JMenuItem miRename = new JMenuItem("Rename");
        JMenuItem miDelete = new JMenuItem("Delete");
        miAdd2Fav = new JMenuItem("Add to Favourites");
        JMenuItem miProperties = new JMenuItem("Properties");
        
        mEdit.setEnabled(false);
        miPaste.setEnabled(false);
        JMenu mSearch = new JMenu("Search");
       
                
    /*~~~~~~~~~~~~~~ Set Action Listeners ~~~~~~~~~~~~*/
        miExit.addActionListener(new MenuActionListener("Exit",frame));
        miNewWindow.addActionListener(new MenuActionListener("New Window",frame));
        miCut.addActionListener(new MenuActionListener("Cut"));
        miCopy.addActionListener(new MenuActionListener("Copy"));
        miPaste.addActionListener(new MenuActionListener("Paste"));
        miRename.addActionListener(new MenuActionListener("Rename"));
        miDelete.addActionListener(new MenuActionListener("Delete"));
        miAdd2Fav.addActionListener(new MenuActionListener("Add to Favourites"));
        miProperties.addActionListener(new MenuActionListener("Properties"));
        mSearch.addMouseListener(new SearchBarMouseListener(searchBar));
        
    /*~~~~~~~~~~~~~~~ Make necessary additions~~~~~~~~~~~~~*/
        /*For JMenu: File*/
        mFile.add(miNewWindow);
        mFile.add(miExit);
        
        /*For JMenu: Edit*/
        mEdit.add(miCut);
        mEdit.add(miCopy);
        mEdit.add(miPaste);
        mEdit.add(miRename);
        mEdit.add(miDelete);
        mEdit.add(miAdd2Fav);
        mEdit.add(miProperties);
        
        /*For Jbar*/
        this.add(mFile);
        this.add(mEdit);
        this.add(mSearch);
    }
    
    /*  setters for enabling and disabling specific
        actions from the user.
        > Enable Edit actions only if a file or directory has been selected
        > Enable paste when user has already pressed
          copy or cut
        > Enable favourite if content button selected is a directory 
          and not a file
    */    
    
    static void enableEdit(boolean enabled){
        mEdit.setEnabled(enabled);
    }
    
    static void enableFavourite(boolean enabled){
        miAdd2Fav.setEnabled(enabled);
    }
    
    static void enablePaste(boolean enabled){
        miPaste.setEnabled(enabled);
    }
}
