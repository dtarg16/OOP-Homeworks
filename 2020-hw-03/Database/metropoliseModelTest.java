
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;


public class metropoliseModelTest extends TestCase {
    private metropoliseModel model;


    public void setUp() throws SQLException {
        model = new metropoliseModel();
        model.createTablefromScratch();
    }
    @Test
    public void testsimple() throws SQLException {
        model.search("","","","Population Larger Than","Exact Match");
        assertEquals(3,model.getColumnCount());
        assertEquals(8,model.getRowCount());
        assertEquals( "North America",model.getValueAt(2,1));
        assertEquals( "Metropolis", (String) model.getColumnName(0));
    }


    @Test
    public void testAdd() throws SQLException {


        model.add("Cairo", "Africa", "9500000");
        model.add("Cairo", "Africa", "9500000");
        String query = "SELECT * FROM metropolises WHERE metropolis = \"Cairo\" AND continent = \"Africa\" AND population = 9500000";
        Statement testStatement = model.getStatement();
        ResultSet result = testStatement.executeQuery(query);
        result.last();
        assertEquals(2, result.getRow());
        String deleteQuery = "DELETE FROM metropolises WHERE metropolis = \"Cairo\" AND continent = \"Africa\" AND population = 9500000";
        int status = testStatement.executeUpdate(deleteQuery);
        ResultSet result2 = testStatement.executeQuery(query);
        result2.last();
        assertEquals(0,result2.getRow());
    }
    @Test
    public  void testSearchAllField() throws SQLException{

        model.search("New York","North America","900000","Population Larger Than","Exact Match");
        assertEquals(1,model.getRowCount());
        model.search("New","North","90000000","Population Fewer Than","Partial Match");
        assertEquals(1,model.getRowCount());
    }

    public  void testfirstField() throws SQLException{
        model.search("Mumbai", "" ,"" ,"Population Larger Than","Partial Match");
        assertEquals(1,model.getRowCount());
        model.search("Mumbai", "" ,"" ,"Population Larger Than","Exact Match");
        assertEquals(1,model.getRowCount());
    }

    public  void testsecondField() throws SQLException{
        model.search("", "Europe" ,"" ,"Population Larger Than","Partial Match");
        assertEquals(3,model.getRowCount());
        assertEquals("Rome",model.getValueAt(1,0));
        model.search("", "Asia" ,"" ,"Population Larger Than","Exact Match");
        assertEquals(1,model.getRowCount());
        assertEquals("Mumbai",model.getValueAt(0,0));
    }
    public  void testthirdField() throws SQLException{
        model.search("", "" ,"100000000" ,"Population Fewer Than","Partial Match");
        assertEquals(8,model.getRowCount());
        model.search("", "" ,"1000" ,"Population Larger Than","Partial Match");
        assertEquals(8,model.getRowCount());
    }
    public  void testseocondandthirdField() throws SQLException{
        model.search("", "North" ,"10000" ,"Population Greater Than","Partial Match");
        assertEquals(3,model.getRowCount());
        model.search("", "North" ,"10000" ,"Population Greater Than","Exact Match");
        assertEquals(0,model.getRowCount());
    }

    public  void testfirstandthirdField() throws SQLException{
        model.search("San", "" ,"10000" ,"Population Greater Than","Partial Match");
        assertEquals(2,model.getRowCount());

    }

    public  void testvalueAtException() throws SQLException{
        model.search("", "" ,"" ,"Population Greater Than","Partial Match");
        String res = (String) model.getValueAt(100,10);
        assertNull(res);
        model.search("Tbilisi", "" ,"!@#!@$" ,"Population Greater Than","Partial Match");
    }
    public  void testException() throws SQLException{
        model.add("", "" ,"" );
    }


}
