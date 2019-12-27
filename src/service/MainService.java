/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.ProdusDao;
import dao.UserDao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Produs;
import model.User;

/**
 *
 * @author cmihai
 */
public class MainService {
    private String user = "root";
    private String pass = "";
    private String url = "jdbc:mysql://localhost/java1pc8";
    private Connection con;
    
    private MainService(){
        try {
            con = DriverManager.getConnection(url,user,pass);
        }catch (Exception e){
            e.printStackTrace();
        
        }
    
    }
    
    private static final class SingletonHolder{
        private static final MainService INSTANCE = new MainService();
    }
    
    public static MainService getInstance(){
        return SingletonHolder.INSTANCE;
    }
    
    public boolean inregistrare(User user) {
        UserDao userDao = new UserDao(con); 
        boolean rez = false;
        
        try{
        Optional<User> optionalUser = userDao.findUser(user.getUsername());
        if(!optionalUser.isPresent()){
            userDao.adaugaUser(user);
            rez = true;
        
        }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return rez;
    }
    
    public Optional<User> login (String username, String parola){
        UserDao userDao = new UserDao(con);
        try{
            Optional<User> optionalUser = userDao.findUser(username);
            if (optionalUser.isPresent()){
                if (optionalUser.get().getParola().equals(parola)){
                    return optionalUser;
                }
            
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        return Optional.empty();
    }
    
    public void adaugaProdus (Produs p) {
        ProdusDao produsDao = new ProdusDao(con);
        
        try{
            produsDao.adaugaProdus(p);
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        
    }
    
    public List<Produs> getAllProducts(){
        ProdusDao produsDao = new ProdusDao(con);
        try{
            return produsDao.getAllProducts();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return Collections.emptyList();
    }
    
    public void stergeProdus (int id){
        ProdusDao produsDao = new ProdusDao(con);
        try {
            produsDao.stergeProdus(id);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public double sumaProduse(){
        ProdusDao produsDao = new ProdusDao(con);
        
        try {
            return produsDao.sumaProduse();
        
        }catch(SQLException e){
            e.printStackTrace();
        }
        return 0.0;
    }
}
