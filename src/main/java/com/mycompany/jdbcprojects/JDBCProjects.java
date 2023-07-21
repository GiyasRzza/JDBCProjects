
package com.mycompany.jdbcprojects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JDBCProjects {
    private String userName = "root";
    private String pass = "searcher";
    private String dbName = "testdb";
    private String  host = "localhost";
    private int port = 3306; 
    
     private Connection conn = null;
    
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    
    public void preparedGetSqlStudent(int id){
        String query = "Select * from student where id >?";
        try {
         
            preparedStatement =conn.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet  rs = preparedStatement.executeQuery();
            while(rs.next()){
                String name = rs.getString("name");
                String username  = rs.getString("sur_name");
                int dbID = rs.getInt("id");
                System.out.println("Adiniz: "+name+" Soyadiniz: "+username+" ID: "+dbID);

            }
            
        } catch (SQLException ex) {
            Logger.getLogger(JDBCProjects.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    public void updatesqlStudent(){
        Scanner s = new  Scanner(System.in);
        try {
            statement =conn.createStatement();
            System.out.print("Adinizi yazin:");
            System.out.print("Adinizi yazin:");   String oldName = s.next();
            System.out.print("Ne olaraq deyissin?:");    
            String nameNew =s.next();
            String query = String.format("CALL updateStuden('%s','%s');",nameNew,oldName);
            statement.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(JDBCProjects.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    } 
    
    public void addSqlStudent(){
        Scanner scanner = new Scanner(System.in);
        
        try {
            statement =conn.createStatement();
            System.out.print("Adinizi daxil edin:");
            String name= scanner.next();
           System.out.print("Soy adinizi daxil edin:");
            String sur_name = scanner.next();
            String query =String.format(" CALL maxStudentID('%s','%s');",name,sur_name);
             statement.executeUpdate(query);
            //System.out.println(query);
        } catch (SQLException ex) {
            Logger.getLogger(JDBCProjects.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    public void getsqlStudent(){
      
        try {
            
            
            String query = "Select * from student";
            statement =conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {                
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String surname = rs.getString("sur_name");
                System.out.println("Id : "+id+" Name: "+name+" Surname: "+surname);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCProjects.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public JDBCProjects() {
        try {
            
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(JDBCProjects.class.getName()).log(Level.SEVERE, null, ex);
            }
            String url ="jdbc:mysql://" +host+":"+port+"/"+dbName;
            
            conn=DriverManager.getConnection(url,userName,pass);
            System.out.println("Commandingly!!!!");
        } catch (SQLException ex) {
                    ex.printStackTrace();
                System.out.println("Db ya baglana bilmedim!");
        }
    
    }
    public  void commitANDrollback(){
        Scanner scanner = new Scanner(System.in);
        try {
             conn.setAutoCommit(false);
             String query = "Delete  from student where id = 11";
             String query1 = "Update student set name='QiyasQiyas' where id = 1";
             System.out.println("Updateden once...");
             getsqlStudent();
             
             statement = conn.createStatement();
             statement.executeUpdate(query);
             statement.executeUpdate(query1);
             System.out.print("Yaddasa yazilsin?");
             String ans = scanner.next();
             if (ans.equals("yes")) {
                conn.commit();
             System.out.println("Guncellemeden sonra");
             getsqlStudent();

            }
             else{
             conn.rollback();
                 System.out.println("Update legv edildi!!");
             }
             
             
                    
           
        } catch (SQLException ex) {
            Logger.getLogger(JDBCProjects.class.getName()).log(Level.SEVERE, null, ex);
        }
             
    
    }
      public static void main(String[] args) {
        JDBCProjects jdbc = new JDBCProjects();
            jdbc.addSqlStudent();
            jdbc.getsqlStudent();

    }
   
}
