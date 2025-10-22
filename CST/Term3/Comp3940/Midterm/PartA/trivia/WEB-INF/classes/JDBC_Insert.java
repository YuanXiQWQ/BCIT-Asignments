import java.sql.*;


public class JDBC_Insert {
    public static void main(String args[])
    {
        Connection con = null;
        try {
            // 加载驱动
            Class.forName("oracle.jdbc.OracleDriver");
        } catch(Exception ex) {/*忽略*/}
        try {
            // 建立连接（连接位置，用户名，密码）
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
                    "system", "oracle1");
            con.setAutoCommit(false);
            // 使用 SQL 语句插入数据（ PreparedStatement 相比 Statement 防止了 SQL 注入
            PreparedStatement preparedStatement = con.prepareStatement(
                    "INSERT INTO staff (name, address) VALUES (?,?)");


            // 设置参数（放入SQL语句的“?”位置）并执行三次
            preparedStatement.setString(1, "AAA");
            preparedStatement.setString(2, "AAA");
            int row = preparedStatement.executeUpdate();
            preparedStatement.setString(1, "BBB");
            preparedStatement.setString(2, "BBB");
            row = preparedStatement.executeUpdate();
            preparedStatement.setString(1, "CCC");
            preparedStatement.setString(2, "CCC");
            row = preparedStatement.executeUpdate();


            // 提交
            con.commit();
            con.setAutoCommit(true);


            // 查询结果 rs
            Statement stmt2 = con.createStatement();
            ResultSet rs = stmt2.executeQuery("SELECT * FROM staff");


            // 获取rs的列名
            ResultSetMetaData rsmd = rs.getMetaData();
            int numberOfColumns = rsmd.getColumnCount();
            for(int i = 1; i <= numberOfColumns; i++) {
                // 循环打印每一列列名
                System.out.println("   " + rsmd.getColumnName(i));
            }


            // 接下来是行2
            System.out.println("\n\n");


            // 打印每一行的 name和 address，直到遍历表的所有行
            while(rs.next()) {
                String sname = rs.getString("name");
                String saddress = rs.getString("address");
                System.out.println("   " + sname + "  " + saddress);
            }
            stmt2.close();
            System.out.println("\n\n");


            // 使用数据库元数据打印所有表的名称
            DatabaseMetaData md = con.getMetaData();
            rs = md.getTables(null, null, null, null);
            while (rs.next()) {
                String tname = rs.getString("TABLE_NAME");
                System.out.println(" " + tname);
            }
            System.out.println("\n\n");

            // 使用数据库元数据打印 staff 表的所有列名
            rs = md.getColumns(null, null, "staff", null);
            while(rs.next())
            {
                String name = rs.getString("COLUMN_NAME");
                String type = rs.getString("TYPE_NAME");
                int size = rs.getInt("COLUMN_SIZE");
                System.out.println(
                        "Column name: [" + name + "]; type: [" + type + "]; size: [" +
                                size + "]");
            }


            con.close();
        } catch(SQLException ex)
        {
            SQLException throwables = ex;
            try {
                con.rollback();
                con.close();
            } catch(SQLException e) {
                System.out.println("\n回滚时发生错误\n");
            }
            System.out.println("\n--- 捕获到 SQLException ---\n");
            while(throwables != null) {
                System.out.println("Message: " + throwables.getMessage());
                System.out.println("SQLState: " + throwables.getSQLState());
                System.out.println("ErrorCode: " + throwables.getErrorCode());
                throwables = throwables.getNextException();
                System.out.println();
            }
        }
    }
}
