import java.sql.*;

public class Dbfunction {

    // this function use for connecting with data base
    public Connection connect_to_db(String dbname, String user, String pass){
        Connection conn = null;
        try{
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+dbname, user, pass);
            if(conn != null){
                System.out.println("connection stablished");
            }else{
                System.out.println("connection not stablished");
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return conn;
    }

    // this function use for creating vertices tabel
    public void createTableForVertice(Connection conn, String table_name){
        Statement statement;
        try{
            String query = "create table " + table_name + "(vertice integer , primary key(vertice));";
            statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("vertices table created");
        }catch (Exception e){
            System.out.println(e);
        }
    }

    // this table use for inserting vertices in table
    public void insertVertices(Connection conn, String table_name, int verice){
        Statement statement;
        try{
            String query = String.format("insert into %s(vertice) values(%d)",table_name, verice);
            statement = conn.createStatement();
            statement.executeUpdate(query);
        }catch(Exception e){
            System.out.println(e);
        }
    }

    // this table use for read vertices form database
    public ResultSet read_vertices(Connection conn, String table_name){
        Statement statement;
        ResultSet rs= null;
        try{
            String query = String.format("select * from %s", table_name);
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            return rs;
        }catch(Exception e){
            System.out.println(e);
            return null;
        }
    }

    // this table is for creating edges table and there "from" and "to" is foreign key of vertices table
    public void createTableForEdges(Connection conn, String table_name){
        Statement statement;
        try{
            String query = "CREATE TABLE " + table_name + " (f integer, t integer, foreign key (f)  references vertices(vertice), foreign key (t)  references vertices(vertice));";
            statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("edges table created");
        } catch (Exception e){
            System.out.println(e);
        }
    }


    // this table is for inserting edges
    public void insertEdges(Connection conn , String table_name, int f, int t){
        Statement statement;
        try{
            String query = String .format("insert into %s(f, t) values(%d, %d)", table_name, f, t);
            statement = conn.createStatement();
            statement.executeUpdate(query);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    // this table is for reading edges from database
    public ResultSet read_edges(Connection conn, String table_name){
        Statement statement;
        ResultSet rs = null;
        try{
            String query = String.format("select * from %s", table_name);
            statement = conn.createStatement();
            rs  = statement.executeQuery(query);
            return rs;
        }catch(Exception e){
            System.out.println(e);
            return rs;
        }
    }
    public ResultSet read_edgesForVertex(Connection conn, String table_name, int vertex) {
        Statement statement;
        ResultSet rs = null;
        try {
            String query = String.format("SELECT * FROM %s WHERE f = %d", table_name, vertex);
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            return rs;
        } catch (Exception e) {
            System.out.println(e);
            return rs;
        }
    }

}
