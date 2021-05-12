import dao.*;
import gui.MainMenu;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Finley
 */  



public class Main {
  public static void main(String[] args){
      final HallDAO Hdao = new HallJdbcDAO();
      final PaperDAO Pdao = new PaperJdbcDAO();
      final LibraryDAO Ldao = new LibraryJdbcDAO();
      final TutorDAO Tdao = new TutorCollectionsDAO();
      MainMenu frame = new MainMenu(Pdao,Tdao,Ldao,Hdao);
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
      System.out.println(Hdao.getHalls());
      
 }
    
}
