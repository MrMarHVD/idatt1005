package no.ntnu.idatt1002.demo;

import no.ntnu.idatt1002.demo.view.MyWindow;
import no.ntnu.idatt1002.model.Ingredient;
import no.ntnu.idatt1002.model.Recipe;

/**
 * Use this class to start the application
 * @author nilstes
 */
public class MyApp {

    /**
     * Main method for my application
     */
    public static void main(String[] args) throws Exception {
        MyWindow window = new MyWindow("The Window");
        window.setVisible(true);
        System.out.println("Hei");

      Ingredient eggs = new Ingredient("eggs");
      Ingredient flour = new Ingredient("flour");
      Ingredient milk = new Ingredient("milk");

      Recipe bread = new Recipe("Add the eggs and milk, then mix with flour.",
          flour, milk, eggs);

      System.out.println(bread.toString());
   }  
}
