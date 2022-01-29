import javax.swing.table.AbstractTableModel;
import java.sql.*;


public class metropoliseModel extends AbstractTableModel {

    static String url = "jdbc:mysql://localhost/sys"; // replace with your account
    static String user = "root";
    static String password = "4159"; // replace with your password
    //static String database = "sys";
    private String[] columns = {"Metropolis", "Continent", "Population"};
    private ResultSet rs;
    Statement statement;

    /// change if using this model for the first time
    private static final boolean firstTime = false;

    public metropoliseModel() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            if (firstTime) {
                createTablefromScratch();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getRowCount() {
        try {
            if (rs != null) {
                rs.last();
                return rs.getRow();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int i, int i1) {
        try {
            if (rs != null) {
                rs.absolute(i + 1);
                return rs.getObject(i1 + 1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    public void add(String metropolisS, String continentS, String populationS) {
        try {
            String insert = "INSERT INTO metropolises values ('" + metropolisS + "','" + continentS + "','" + populationS + "')";
            String select = "SELECT * FROM metropolises;";
            statement.executeUpdate(insert);
            rs = statement.executeQuery(select);
            fireTableStructureChanged();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Statement getStatement() {
        return statement;
    }

    public void createTablefromScratch() throws SQLException {
        statement.execute("DROP table if exists metropolises");

        statement.execute("CREATE TABLE metropolises (\r\n" +
                "    metropolis CHAR(64),\r\n" +
                "    continent CHAR(64),\r\n" +
                "    population BIGINT\r\n" +
                ");");
        statement.execute("INSERT INTO metropolises VALUES\n" +
                "\t(\"Mumbai\",\"Asia\",20400000),\n" +
                "        (\"New York\",\"North America\",21295000),\n" +
                "\t(\"San Francisco\",\"North America\",5780000),\n" +
                "\t(\"London\",\"Europe\",8580000),\n" +
                "\t(\"Rome\",\"Europe\",2715000),\n" +
                "\t(\"Melbourne\",\"Australia\",3900000),\n" +
                "\t(\"San Jose\",\"North America\",7354555),\n" +
                "\t(\"Rostov-on-Don\",\"Europe\",1052000);");
    }

    public void search(String metropolisS, String continentS, String populationS, String populationSearch, String matchSearch) {
        try {
            boolean exactMatch = false;
            String matchOperation = " LIKE ";
            String populationOperation = " >= ";
            if (populationSearch.equals("Population Fewer Than")) {
                populationOperation = " <  ";
            }
            if (matchSearch.equals("Exact Match")) {
                matchOperation = " = ";
                exactMatch = true;
            }

            String query;
            if (metropolisS.isEmpty() && continentS.isEmpty() && populationS.isEmpty()) {
                query = "Select * From metropolises ;";
            } else {
                query = "Select * From metropolises WHERE ";
                if (!metropolisS.isEmpty()) {
                    if (!exactMatch)
                        query += "metropolis" + matchOperation + "\"" + "%" + metropolisS + "%" + "\"";
                    else
                        query += "metropolis" + matchOperation + "\"" + metropolisS + "\"";
                    if (!continentS.isEmpty()) {
                        query += " AND ";
                        if (!exactMatch)
                            query += "continent" + matchOperation + "\"" + "%" + continentS + "%" + "\"";
                        else
                            query += "continent" + matchOperation + "\"" + continentS + "\"";
                        if (!populationS.isEmpty()) {
                            query += " AND ";
                            query += "population " + populationOperation + populationS;
                        }
                    } else {
                        if (!populationS.isEmpty()) {
                            query += " AND ";
                            query += "population " + populationOperation + populationS;
                        }
                    }
                } else {
                    if (!continentS.isEmpty()) {
                        if (!exactMatch)
                            query += "continent" + matchOperation + "\"" + "%" + continentS + "%" + "\"";
                        else
                            query += "continent" + matchOperation + "\"" + continentS + "\"";
                        if (!populationS.isEmpty()) {
                            query += " AND ";
                            query += "population " + populationOperation + populationS;
                        }
                    } else {
                        query += "population " + populationOperation + populationS;
                    }
                }
                query += ";";
            }
            rs = statement.executeQuery(query);
            fireTableStructureChanged();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}