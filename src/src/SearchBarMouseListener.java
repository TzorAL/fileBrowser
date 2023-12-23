package FileBrowser;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author altzortzis <altzortzis@uth.gr>
 */
public class SearchBarMouseListener implements MouseListener{
    JPanel searchBar;

    public SearchBarMouseListener(JPanel searchBar) {
        this.searchBar = searchBar;
    }

    @Override
    public void mouseClicked (MouseEvent e){
        /*Make searchBar appear or dissappear based on clicks*/
        if(searchBar.isVisible()){
            System.out.println("Search bar disappeared");
            searchBar.setVisible(false);
        }
        else{
            System.out.println("Search bar appeared");
            searchBar.setVisible(true);
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
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
