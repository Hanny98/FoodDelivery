/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc;
import java.sql.*;
/**
 *
 * @author User
 */
public class JdbcUtility {
    
    //database credential
    Connection con = null;
    String driver;
    String url;
    String username;
    String password;
    
    //user table
    //admin
    PreparedStatement psInsertAdmin = null;
    PreparedStatement psSelectAdmin = null;
    PreparedStatement psUpdateAdminProfilePic = null;
    //user
    PreparedStatement psInsertUser = null;
    PreparedStatement psSelectUserByLogin = null;
    PreparedStatement psUpdateUserById = null;
    PreparedStatement psUpdateUserProfilePic = null;
    PreparedStatement psSelectAllUser = null;
    
    //dish table
    PreparedStatement psSelectAllDishes = null;
    PreparedStatement psInsertDish = null;
    PreparedStatement psUpdateDishById = null;
    PreparedStatement psUpdateDishStatusById = null;
    PreparedStatement psDeleteDishById = null;
    PreparedStatement psUpdateDishPicById = null;
    PreparedStatement psSelectActiveDish = null;
    
    //order table
    PreparedStatement psInsertOrder = null;
    PreparedStatement psSelectOrderByUserId = null;
    PreparedStatement psSelectAllOrder = null;
    PreparedStatement psUpdateOrderStatusById = null;
    
    public JdbcUtility(String driver, String url, String username, String password)
    {
        this.driver = driver;
        this.url = url;
        this.username = username;
        this.password = password;
    }
    
    public void jdbcConnect()
    {
        try
        {
            Class.forName(driver);
            con = DriverManager.getConnection(url, username, password);
            DatabaseMetaData dma = con.getMetaData();
            System.out.println("\nConnected to " + dma.getURL());
            System.out.println("Driver " + dma.getDriverName());
            System.out.println("Version " + dma.getDriverVersion());
            System.out.println();
        }
        catch(SQLException ex)
        {
            while(ex != null)
            {
                System.out.println("SQL State: " + ex.getSQLState());
                System.out.println("Message: " + ex.getMessage());
                System.out.println("Vendor: " + ex.getErrorCode());
                ex.getNextException();
                System.out.println();
            }
            
            System.out.println("Connection to database error");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }      
    }
    
    public void jdbcConClose()
    {
        try
        {
            con.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    //user table
    //admin
    public void prepareSQLStatementInsertAdmin()
    {
        try
        {
            String sqlInsertAdmin = "INSERT INTO user(login, password, salt, usertype, fullname, address) VALUES(?, ?, ?, 'admin', ?, '-')";
            
            psInsertAdmin = con.prepareStatement(sqlInsertAdmin);
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    
    public PreparedStatement getPsInsertAdmin()
    {
        return psInsertAdmin;
    }
    
    public void prepareSQLStatementSelectAdmin()
    {
        try
        {
            String sqlSelectAdmin = "SELECT * FROM user WHERE login=? AND usertype='admin'";
            
            psSelectAdmin = con.prepareStatement(sqlSelectAdmin);
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    
    public PreparedStatement getPsSelectAdmin()
    {
        return psSelectAdmin;
    }
    
    public void prepareSQLStatementUpdateAdminProfilePic()
    {
        try
        {
            String sqlUpdateAdminProfilePic = "UPDATE user set photo = ? WHERE id = ?";
            
            psUpdateAdminProfilePic = con.prepareStatement(sqlUpdateAdminProfilePic);
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    
    public PreparedStatement getPsUpdateAdminProfilePic()
    {
        return psUpdateAdminProfilePic;
    }
    
    //user
    public void prepareSQLStatementInsertUser()
    {
        try
        {
            String sqlInsertUser = "INSERT INTO user(login, password, salt, usertype, fullname, address) VALUES(?, ?, ?, 'user', ?, ?)";
            
            psInsertUser = con.prepareStatement(sqlInsertUser);
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    
    public PreparedStatement getPsInsertUser()
    {
        return psInsertUser;
    }
    
    public void prepareSQLStatementSelectUserByLogin()
    {
        try
        {
            String sqlSelectUserByLogin = "SELECT * FROM user WHERE login = ? AND usertype = 'user'";
            
            psSelectUserByLogin = con.prepareStatement(sqlSelectUserByLogin);
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    
    public PreparedStatement getPsSelectUserByLogin()
    {
        return psSelectUserByLogin;
    }
    
        public void prepareSQLStatementUpdateUserById()
    {
        try
        {
            String sqlUpdateUserById = "UPDATE user set password = ?, fullname = ?, address = ? WHERE id = ?";
            
            psUpdateUserById = con.prepareStatement(sqlUpdateUserById);
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    
    public PreparedStatement getPsUpdateUserById()
    {
        return psUpdateUserById;
    }
    
    public void prepareSQLStatementUpdateUserProfilePic()
    {
        try
        {
            String sqlUpdateUserProfilePic = "UPDATE user set photo = ? WHERE id = ?";
            
            psUpdateUserProfilePic = con.prepareStatement(sqlUpdateUserProfilePic);
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    
    public PreparedStatement getPsUpdateUserProfilePic()
    {
        return psUpdateUserProfilePic;
    }
    
    public void prepareSQLStatementSelectAllUser()
    {
        try
        {
            String sqlSelectAllUser = "SELECT * FROM user WHERE usertype = 'user'";
            
            psSelectAllUser = con.prepareStatement(sqlSelectAllUser);
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    
    public PreparedStatement getPsSelectAllUser()
    {
        return psSelectAllUser;
    }
    
    //dish table
    public void prepareSQLStatementSelectAllDishes()
    {
        try
        {
            String sqlSelectAllDishes = "SELECT * FROM dish";
            
            psSelectAllDishes = con.prepareStatement(sqlSelectAllDishes);
            
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    
    public PreparedStatement getPsSelectAllDishes()
    {
        return psSelectAllDishes;
    }
    
    public void prepareSQLStatementInsertDish()
    {
        try
        {
            String sqlInsertDish = "INSERT INTO dish(dishcode, dishname, price, type, description) VALUES(?, ?, ?, ?, ?)";
            
            psInsertDish = con.prepareStatement(sqlInsertDish);
            
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    
    public PreparedStatement getPsInsertDish()
    {
        return psInsertDish;
    }
    
    public void prepareSQLStatementUpdateDishById()
    {
        try
        {
            String sqlUpdateDishById = "UPDATE dish set dishname = ?, price = ?, type =?, description = ? WHERE id = ?";
            
            psUpdateDishById = con.prepareStatement(sqlUpdateDishById);
            
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    
    public PreparedStatement getPsUpdateDishById()
    {
        return psUpdateDishById;
    }
    
    public void prepareSQLStatementUpdateDishStatusById()
    {
        try
        {
            String sqlUpdateDishStatusById = "UPDATE dish set status = ? WHERE id = ?";
            
            psUpdateDishStatusById = con.prepareStatement(sqlUpdateDishStatusById);
            
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    
    public PreparedStatement getPsUpdateDishStatusById()
    {
        return psUpdateDishStatusById;
    }
    
    public void prepareSQLStatementDeleteDishById()
    {
        try
        {
            String sqlDeleteDishById = "DELETE FROM dish WHERE id = ?";
            
            psDeleteDishById = con.prepareStatement(sqlDeleteDishById);
            
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    
    public PreparedStatement getPsDeleteDishById()
    {
        return psDeleteDishById;
    }
    
    public void prepareSQLStatementUpdateDishPicById()
    {
        try
        {
            String sqlUpdateDishPicById = "UPDATE dish set photo = ? WHERE id = ?";
            
            psUpdateDishPicById = con.prepareStatement(sqlUpdateDishPicById);
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    
    public PreparedStatement getPsUpdateDishPicById()
    {
        return psUpdateDishPicById;
    }
    
    public void prepareSQLStatementSelectActiveDish()
    {
        try
        {
            String sqlSelectActiveDish = "SELECT * FROM dish WHERE status = 'active'";
            
            psSelectActiveDish = con.prepareStatement(sqlSelectActiveDish);
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    
    public PreparedStatement getPsSelectActiveDish()
    {
        return psSelectActiveDish;
    }
    
    //orders table
    public void prepareSQLStatementInsertOrder()
    {
        try
        {
            String sqlInsertOrder = "INSERT INTO orders(orderID, userID, dishCodeList, quantity, status, lastModifiedDate, totalPrice, deliveryAddress) VALUES(?, ?, ?, ?, 'Paid', NOW(), ?, ?)";
        
            psInsertOrder = con.prepareStatement(sqlInsertOrder);
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }
        
    public PreparedStatement getPsInsertOrder()
    {
        return psInsertOrder;
    }
    
    public void prepareSQLStatementSelectOrderByUserId()
    {
        try
        {
            String sqlSelectOrderByUserId = "SELECT * FROM orders WHERE userID = ? ORDER BY lastModifiedDate DESC";
        
            psSelectOrderByUserId = con.prepareStatement(sqlSelectOrderByUserId);
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    
    public PreparedStatement getPsSelectOrderByUserId()
    {
        return psSelectOrderByUserId;
    }
    
    public void prepareSQLStatementSelectAllOrder()
    {
        try
        {
            String sqlSelectAllOrder = "SELECT * FROM orders ORDER BY lastModifiedDate DESC";
            
            psSelectAllOrder = con.prepareStatement(sqlSelectAllOrder);
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    
    public PreparedStatement getPsSelectAllOrder()
    {
        return psSelectAllOrder;
    }
    
    public void prepareSQLStatementUpdateOrderStatusById()
    {
        try
        {
            String sqlUpdateOrderStatusById = "UPDATE orders set status = ?, lastModifiedDate = NOW() WHERE orderID = ?";
            
            psUpdateOrderStatusById = con.prepareStatement(sqlUpdateOrderStatusById);
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    
    public PreparedStatement getPsUpdateOrderStatusById()
    {
        return psUpdateOrderStatusById;
    }
}
