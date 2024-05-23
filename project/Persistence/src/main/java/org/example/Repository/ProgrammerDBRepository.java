package org.example.Repository;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import org.example.Domain.Programmer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ProgrammerDBRepository implements ProgrammerRepository {

    private static JdbcUtils dbUtils;



    //private static final Logger logger= LogManager.getLogger();

    public ProgrammerDBRepository(Properties props) {
        //logger.info("Initializing ProgrammeriDBRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }


    @Override
    public void add(Programmer elem) {
        //logger.traceEntry("saving task {} ", elem);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStnt = con.prepareStatement("insert into Programmers values (?,?,?,?,?)")) {
            preStnt.setInt(1, elem.getId());
            preStnt.setString(2, elem.getName());
            preStnt.setString(3, elem.getUsername());
            preStnt.setString(4, elem.getPassword());
            preStnt.setString(5, elem.getEmail());

            int result = preStnt.executeUpdate();
            //logger.trace("Saved {} instances", result);

        } catch (SQLException e) {
            //logger.error(e);
            System.err.println("Error DB" + e);
        }
        //logger.traceExit();
    }

    @Override
    public void update(Integer integer, Programmer elem) {}

    @Override
    public List<Programmer> getAll(){
        //logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Programmer> data = new ArrayList<>();
        try(PreparedStatement preStmt = con.prepareStatement("select * from Programmers")){
            try(ResultSet rs = preStmt.executeQuery()) {
                while (rs.next()){
                    Programmer a = new Programmer(rs.getInt("id"), rs.getString("name"), rs.getString("username"), rs.getString("password"), rs.getString("email"));
                    data.add(a);
                }
            }
        } catch (SQLException e){
            //logger.error(e);
            System.err.println("Error DB" + e);
        }
        //logger.traceExit(data);
        return data;
    }

    public static Programmer find(Integer ID){
        //logger.traceEntry("Finding artist with ID: {}", ID);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStnt = con.prepareStatement("SELECT * FROM Programmers WHERE id = ?")) {
            preStnt.setInt(1, ID);
            ResultSet rs = preStnt.executeQuery();
            
            if (rs.next()) {
                Programmer programmer = new Programmer(rs.getInt("id"), rs.getString("name"), rs.getString("username"), rs.getString("password"), rs.getString("email"));
                //logger.traceExit();
                return programmer;
            } else {
                //logger.traceExit();
                return null;
            }
        } catch (SQLException e) {
            //logger.error(e);
            System.err.println("Error DB" + e);
            //logger.traceExit();
            return null;
        }
    }

    @Override
    public Programmer getByCredentials(String Programmername, String password){
        //logger.traceEntry("Finding Programmer with credentials: {}",Programmername, password);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStnt = con.prepareStatement("SELECT * FROM Programmers WHERE username like ? AND password like ?")) {
            preStnt.setString(1, Programmername);
            preStnt.setString(2, password);
            ResultSet rs = preStnt.executeQuery();
            
            if (rs.next()) {
                Programmer programmer = new Programmer(rs.getInt("id"), rs.getString("name"), rs.getString("username"), rs.getString("password"), rs.getString("email"));
//                logger.traceExit();
                return programmer;
            } else {
//                logger.traceExit();
                return null;
            }
        } catch (SQLException e) {
//            logger.error(e);
            System.err.println("Error DB" + e);
//            logger.traceExit();
            return null;
        }
    }

}
