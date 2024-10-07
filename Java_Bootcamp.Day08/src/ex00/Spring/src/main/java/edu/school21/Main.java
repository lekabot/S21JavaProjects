package edu.school21;

import edu.school21.interfaces.Printer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
   public static void main(String[] args) {
       ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
       Printer printer = context.getBean("printerWithPrefix", Printer.class);
       printer.print("Hello!");
   }
}
