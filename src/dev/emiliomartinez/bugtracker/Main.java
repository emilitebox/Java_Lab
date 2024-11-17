package dev.emiliomartinez.bugtracker;
import javax.swing.SwingUtilities;
import dev.emiliomartinez.bugtracker.gui.main.MainFrame;

public class Main {

	public static void main(String[] args) {
        System.out.println("LABI: BUG TRACKER");
        
        Integer USER_ID = 2; 
        
        // GUI
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame(USER_ID);
            mainFrame.setVisible(true);
        });


	}

}
