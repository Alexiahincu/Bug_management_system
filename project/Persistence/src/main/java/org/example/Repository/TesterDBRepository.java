package org.example.Repository;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import org.example.Domain.Tester;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TesterDBRepository implements TesterRepository {

    private static JdbcUtils dbUtils;



    //private static final Logger logger= LogManager.getLogger();

    public TesterDBRepository(Properties props) {
        //logger.info("Initializing TesteriDBRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }


    @Override
    public void add(Tester elem) {
        //logger.traceEntry("saving task {} ", elem);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStnt = con.prepareStatement("insert into Testers values (?,?,?,?,?)")) {
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
    public void update(Integer integer, Tester elem) {}

    @Override
    public List<Tester> getAll(){
        //logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Tester> data = new ArrayList<>();
        try(PreparedStatement preStmt = con.prepareStatement("select * from Testers")){
            try(ResultSet rs = preStmt.executeQuery()) {
                while (rs.next()){
                    Tester a = new Tester(rs.getInt("id"), rs.getString("name"), rs.getString("username"), rs.getString("password"), rs.getString("email"));
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

    public static Tester find(Integer ID){
        //logger.traceEntry("Finding artist with ID: {}", ID);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStnt = con.prepareStatement("SELECT * FROM Testers WHERE id = ?")) {
            preStnt.setInt(1, ID);
            ResultSet rs = preStnt.executeQuery();
            
            if (rs.next()) {
                Tester tester = new Tester(rs.getInt("id"), rs.getString("name"), rs.getString("username"), rs.getString("password"), rs.getString("email"));
                //logger.traceExit();
                return tester;
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
    public Tester getByCredentials(String Testername, String password){
        //logger.traceEntry("Finding Tester with credentials: {}",Testername, password);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStnt = con.prepareStatement("SELECT * FROM Testers WHERE username like ? AND password like ?")) {
            preStnt.setString(1, Testername);
            preStnt.setString(2, password);
            ResultSet rs = preStnt.executeQuery();
            
            if (rs.next()) {
                Tester tester = new Tester(rs.getInt("id"), rs.getString("name"), rs.getString("username"), rs.getString("password"), rs.getString("email"));
//                logger.traceExit();
                return tester;
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
