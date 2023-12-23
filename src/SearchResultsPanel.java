package FileBrowser;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author altzortzis <altzortzis@uth.gr>
 */
public class SearchResultsPanel extends javax.swing.JPanel implements MouseListener{
    
    public SearchResultsPanel() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }
    
    void addSearchResult(String path){
        JButton searchResult = new JButton(path);
        searchResult.setText(path);
        searchResult.addMouseListener(this);
        searchResult.setBackground(new Color(248,238,246));
        this.add(searchResult);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JButton btnPressed = (JButton) e.getSource();
        System.out.println("You clicked on search result: "+btnPressed.getText());
        if(new File(btnPressed.getText()).isDirectory()){
            JPanel dirCard = DirContentMouseListener.getDirCard();
            dirCard.removeAll();
            dirCard.add(new DirPanel(new File(btnPressed.getText()), dirCard));
            CardLayout cl =  (CardLayout) dirCard.getLayout();
            cl.addLayoutComponent(dirCard, "dirCard");
            cl.show(dirCard,"dirCard"); 
        }
        else{
            if(Files.isExecutable(Paths.get(btnPressed.getText()))){
                try {
                    Process process = Runtime.getRuntime().exec(btnPressed.getText());
                } catch (IOException ex) {
                    System.err.println("IO Exception: Attempt to run an executable");
                }
                return;
            }
            if(Desktop.isDesktopSupported()) {
                try {//open the file using corresponding tool
                    Desktop.getDesktop().open(new File(btnPressed.getText()));
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

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        JButton btnPressed = (JButton) e.getSource();
        btnPressed.setBackground(Color.GRAY);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        JButton btnPressed = (JButton) e.getSource();
        btnPressed.setBackground(new Color(248,238,246));
    }
}
