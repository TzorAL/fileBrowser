package FileBrowser;

/*
 * Extension of JFrame class used to implement the entirety of the File Browser
 */

/**
 *
 * @author altzortzis <altzortzis@uth.gr>
 */

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Browser extends JFrame{
    public int WIDTH = 800;
    public int HEIGHT = 600;
    private File curDir;
    
    public Browser() throws IOException {
        super();
        setSize(WIDTH, HEIGHT);
        setLayout(new BorderLayout());
        SearchBar searchBar = new SearchBar();/* create an object to establish
                                                 connection with Menu bar;*/
        
        /*Window is going to be split into  two main columns, one is 
            > right_main: panel which contains searchBar,breadCrumb and contents
                        of current directory
            > favouritesPanel: panel which coontains a vertical bar of favourite
                             files and directories
        */

        /*panel that contains breadcurmb and dirContent
        (AKA dirPanel AKA every panel that is changed/refreshed when clicking
         a directory)*/
        JPanel dirCard = new JPanel(new CardLayout());  
        JPanel favCard = new JPanel(new CardLayout());  
        JPanel favouritesPanel = new FavouritesPanel(dirCard,favCard);
        JPanel main_panel = new JPanel();/*this is main_panel we are going to use*/
        JPanel right_main = new JPanel();
        
        /*curDir: file pointer to home directory*/
        curDir = new File(System.getProperty("user.home"));
        
        //Setting up the container ready for the components to be added.
    	Container pane = getContentPane();
    	setContentPane(pane);       
        right_main.setLayout(new BorderLayout());
        main_panel.setLayout(new GridBagLayout());
        
        this.setJMenuBar(new MenuBar(searchBar,this,curDir));        
        
        /*mainpanel(
            favouritesPanel,
            right_main(
                searchBar
                dirCard(dirPanel)
            )
        )*/
        GridBagConstraints favConstraints = new GridBagConstraints();
        favConstraints.weightx = 0.3;
        favConstraints.weighty = 1.0;
        
        favConstraints.fill = GridBagConstraints.BOTH;

        favCard.add(favouritesPanel);
        main_panel.add(favCard,favConstraints);
        
        GridBagConstraints dirConstraints = new GridBagConstraints();
        dirConstraints.weightx = 0.7;
        dirConstraints.weighty = 1.0;
        dirConstraints.fill = GridBagConstraints.BOTH;

        dirCard.add(new DirPanel(curDir,dirCard));
        right_main.add(searchBar,BorderLayout.NORTH);
        right_main.add(dirCard,BorderLayout.CENTER);
        main_panel.add(right_main,dirConstraints);
        pane.add(main_panel);
    }
    
    public static void main(String[] args){
        javax.swing.SwingUtilities.invokeLater(() -> {
            Browser fBrowser = null;
            try {
                fBrowser = new Browser();
            } catch (IOException ex) {
                System.out.println("IO Exception at fBrowser.main");
            }
            fBrowser.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            fBrowser.setVisible(true);
        });
    }
}
