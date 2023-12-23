package FileBrowser;

/*
 * Extension of JPopupMenu used to implement a popup menu when user right clicks
 * on a file/directory
 */

import javax.swing.JMenuItem;

/**
 *
 * @author altzortzis <altzortzis@uth.gr>
 */
public class PopupEditMenu extends javax.swing.JPopupMenu{
    private static boolean enabledPaste = false;
    private static boolean enabledFav = true;

    public PopupEditMenu() {
        super();
        JMenuItem miCut = new JMenuItem("Cut");
        JMenuItem miCopy = new JMenuItem("Copy");
        JMenuItem miPaste = new JMenuItem("Paste");
        JMenuItem miRename = new JMenuItem("Rename");
        JMenuItem miDelete = new JMenuItem("Delete");
        JMenuItem miAdd2Fav = new JMenuItem("Add to Favourites");
        JMenuItem miProperties = new JMenuItem("Properties");
        
        miCut.addActionListener(new MenuActionListener("Cut"));
        miCopy.addActionListener(new MenuActionListener("Copy"));
        miPaste.addActionListener(new MenuActionListener("Paste"));
        miRename.addActionListener(new MenuActionListener("Rename"));
        miDelete.addActionListener(new MenuActionListener("Delete"));
        miAdd2Fav.addActionListener(new MenuActionListener("Add to Favourites"));
        miProperties.addActionListener(new MenuActionListener("Properties"));
        
        miPaste.setEnabled(enabledPaste);
        miAdd2Fav.setEnabled(enabledFav);
        
        this.add(miCut);
        this.add(miCopy);
        this.add(miPaste);        
        this.add(miRename);
        this.add(miDelete);
        this.add(miAdd2Fav);
        this.add(miProperties);
    }
    
    /*  setters for enabling and disabling specific
        actions from the user.
        > Enable paste when user has already pressed
          copy or cut
        > Enable favourite if content button selected is a directory 
          and not a file
    */
    static void enablePaste(boolean choice){
        enabledPaste = choice;
    }
    
    static void enableFavourite(boolean choice){
        enabledFav = choice;
    }
}
