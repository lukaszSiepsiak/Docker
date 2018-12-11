import java.sql.*;
import java.util.Scanner;
import java.io.Console;

public class DockerfileSQL {
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://10.0.10.3:3306/database"; //IP: 10.0.10.3 ; PORT: 3306
   //nazwa użytkownika
   static final String USER = "kpiskorska";
   //hasło użytkownika
   static final String PASS = "haslo";
   
   public static void main(String[] args) {
   Connection conn = null;
   Statement stmt = null;
   try{
      Class.forName("com.mysql.jdbc.Driver");

      System.out.println("Łączenie z baza...");
      Boolean connect = false;
    
      //Pętla while - próby połączenia z bazą aż się połączy
      while(!connect){
         try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            connect = true;
         }
         catch(Exception e) {          
            System.out.println("Łącenie z bazą...");
            Thread.sleep(1000);
         }
      }
      stmt = conn.createStatement();
      String dropTable,createTable,initInsert,insert,sql;
      System.out.println("Usuń tabele"); 
      dropTable = "DROP TABLE IF EXISTS Osoby";   //Usun tabele if exists
      stmt.executeUpdate(dropTable);
     
      System.out.println("Utworz tabele...");
      createTable = "CREATE TABLE Osoby (IdOsoby int, imie varchar(255), nazwisko varchar(255));"; //Tworzenie tabeli Osoby z 3 polami
      stmt.executeUpdate(createTable);
     
      System.out.println("Wypelnij tabele...");
      initInsert = "INSERT INTO Osoby (IdOsoby, imie, nazwisko) VALUES (1,'Jan','Kowalski')"+
                    ",(2,'Piotr','Nowak'),(3,'Ktos','Ktos');";	      //Wypełnij tabele 3 rekordami
      stmt.executeUpdate(initInsert);
     
      sql = "SELECT * FROM Osoby";		     //wyszukaj wszystkie rekordy w tabeli osoby
      insert = "INSERT INTO Osoby (IdOsoby, imie, nazwisko) VALUES";
      
      Scanner input = new Scanner(System.in);
      
      int id;
      String imie,nazwisko;
           
      Boolean exit = false;
    
      while(!exit) { 
      System.out.println("Wybierz 1, 2 lub 3:");
      System.out.println("1. Wyświetl zawartość bazy");
      System.out.println("2. Wstaw");
      System.out.println("3. Wyjdź");
      
      int option = input.nextInt();
      
      switch(option) {
         case 1:   
            ResultSet rs = stmt.executeQuery(sql); //Zapytania SELECT - zmienna sql
               while(rs.next()){
                  System.out.println("ID: " + rs.getInt(1)+", Imie: " + rs.getString(2)+", Nazwisko: " + rs.getString(3));
               }
            rs.close();
            break;
         case 2:            
            System.out.println("Podaj IdOsoby:");
            id = input.nextInt();
            System.out.println("Podaj imie:");
            imie = input.next();
            System.out.println("Podaj nazwisko:");
            nazwisko = input.next();
            
            
            insert+=" ("+id+",'"+imie+"','"+nazwisko+"');";//Wstaw wprowadzone dane
            stmt.executeUpdate(insert);
            break;
         case 3:
            exit = true; //wyjdź z pętli
            break;
         default:
            System.out.println("Blad. Nie ma takiego numeru");
            }
      }
      stmt.close();
      conn.close();
   }catch(SQLException se){
      se.printStackTrace();
   }catch(Exception e){
      e.printStackTrace();
   }finally{
      try{
         if(stmt!=null)
            stmt.close();
      }catch(SQLException se2){
      }
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }
   }
 }
}
