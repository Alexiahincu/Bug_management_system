package org.example.Repository;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import org.example.Domain.Bug;
import org.example.Domain.BugStatus;
import org.example.Domain.Bug;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import static org.example.Domain.BugStatus.PENDING;
import static org.example.Domain.BugStatus.SOLVED;

public class BugDBRepository implements BugRepository {

    private static JdbcUtils dbUtils;


//    private static final Logger logger = LogManager.getLogger();

    public BugDBRepository(Properties props) {
//        logger.info("Initializing BugsDBRepository with properties: {} ", props);
        dbUtils = new JdbcUtils(props);
    }


    @Override
    public void add(Bug elem) {
//        logger.traceEntry("saving task {} ", elem);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStnt = con.prepareStatement("insert into Bugs values (?,?,?,?)")) {
            preStnt.setInt(1, elem.getId());
            preStnt.setString(2, elem.getName());
            preStnt.setString(3, elem.getDescription());
            preStnt.setString(4, "PENDING");

            int result = preStnt.executeUpdate();
//            logger.trace("Saved {} instances", result);


        } catch (Exception e) {
//            logger.error(e);
            System.err.println("Error DB" + e);
        }
//        logger.traceExit();
    }

    @Override
    public void update(Integer id, Bug elem) {
//        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStnt = con.prepareStatement("update Bugs set status = ? where id like ?")) {

            if(find(id) == null)
                throw new Exception("Bug inexistent");

            preStnt.setString(1, "SOLVED");
            preStnt.setInt(2, id);

            int result = preStnt.executeUpdate();
//            logger.trace("Saved {} instances", result);
//            logger.trace("Saved {} instances", result2);

        } catch (Exception e) {
//            logger.error(e);
            System.err.println("Error DB" + e);
        }
//        logger.traceExit();
    }

    @Override
    public ArrayList<Bug> getAll() {
//        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        ArrayList<Bug> data = new ArrayList<>();

        try(PreparedStatement preStmt = con.prepareStatement("select * from Bugs")){
            try(ResultSet rs = preStmt.executeQuery()) {
                while (rs.next()){
                    BugStatus status;
                    if(rs.getString("status").equals("PENDING"))
                        status = PENDING;
                    else status = SOLVED;
                    Bug a = new Bug(rs.getInt("id"), rs.getString("name"), rs.getString("description"), status);
                    data.add(a);
                }
            }
        } catch (Exception e){
//            logger.error(e);
            System.err.println("Error DB" + e);
        }
//        logger.traceExit(data);
        return data;
    }

    public static Bug find(Integer ID){
        //logger.traceEntry("Finding artist with ID: {}", ID);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStnt = con.prepareStatement("SELECT * FROM Bugs WHERE id = ?")) {
            preStnt.setInt(1, ID);
            ResultSet rs = preStnt.executeQuery();

            if (rs.next()) {
                BugStatus status;
                if(rs.getString("status").equals("PENDING"))
                    status = PENDING;
                else status = SOLVED;
                Bug a = new Bug(rs.getInt("id"), rs.getString("name"), rs.getString("description"), status);
                //logger.traceExit();
                return a;
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
}