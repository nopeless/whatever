package finalProject.src;

// Looked up some syntax related to java.sql
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import io.github.cdimascio.dotenv.Dotenv;
import java.nio.file.Paths;

public class Database {

    private String url;
    private String username;
    private String password;
    private Connection connection;

    public Database() {
        Dotenv dotenv = Dotenv.configure()
                .directory(Paths.get("finalProject/private").toString())
                .filename("cred.env")
                .load();

        this.url = dotenv.get("DB_URL");
        this.username = dotenv.get("DB_USER");
        this.password = dotenv.get("DB_PASSWORD");

        try {
            Class.forName("org.sqlite.JDBC");
            // Initialize the connection
            connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(false); // Disable auto-commit
            System.out.println("Database connection established.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("JDBC Driver not found.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to establish database connection.");
        }
    }

    private void rollbackTransaction() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.rollback();
                System.out.println("Transaction rolled back.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to rollback transaction.");
        }
    }

    public boolean doesTablesExist() {
        String[] tables = { "Users", "Games", "Scores" };
        for (String table : tables) {
            String sql = "SELECT name FROM sqlite_master WHERE type='table' AND name=?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, table);
                ResultSet rs = pstmt.executeQuery();
                if (!rs.next()) {
                    System.out.println("Tables don't exist.");
                    return false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error checking table existence: " + e.getMessage());
                return false;
            }
        }
        System.out.println("Tables exist.");
        return true;
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to close database connection.");
        }
    }

    public void recreateScoresTable() {
        String dropTable = "DROP TABLE IF EXISTS Scores";
        String createTable = "CREATE TABLE Scores (\n"
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " userId INTEGER NOT NULL,\n"
                + " gameId INTEGER NOT NULL,\n"
                + " score INTEGER NOT NULL,\n"
                + " timeStamp TEXT NOT NULL,\n"
                + " FOREIGN KEY (userId) REFERENCES Users(id),\n"
                + " FOREIGN KEY (gameId) REFERENCES Games(id)\n"
                + ");";

        try (Statement stmt = connection.createStatement()) {
            connection.setAutoCommit(false); // Start transaction
            stmt.execute(dropTable);
            stmt.execute(createTable);
            connection.commit(); // Commit transaction
            System.out.println("Scores table has been recreated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error recreating Scores table: " + e.getMessage());
        }
    }

    public void dropAllTables() {
        String dropUsersTable = "DROP TABLE IF EXISTS Users";
        String dropGamesTable = "DROP TABLE IF EXISTS Games";
        String dropScoresTable = "DROP TABLE IF EXISTS Scores";

        try (Statement stmt = connection.createStatement()) {
            connection.setAutoCommit(false); // Start transaction
            stmt.execute(dropUsersTable);
            stmt.execute(dropGamesTable);
            stmt.execute(dropScoresTable);
            connection.commit(); // Commit transaction
            System.out.println("All tables have been dropped successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error dropping tables: " + e.getMessage());
            rollbackTransaction();
        }
    }

    public void deleteAllDataFromTables() {
        String deleteUsersData = "DELETE FROM Users";
        String deleteGamesData = "DELETE FROM Games";
        String deleteScoresData = "DELETE FROM Scores";

        try (Statement stmt = connection.createStatement()) {
            connection.setAutoCommit(false); // Start transaction
            stmt.execute(deleteUsersData);
            stmt.execute(deleteGamesData);
            stmt.execute(deleteScoresData);
            connection.commit(); // Commit transaction
            System.out.println("All data has been deleted from tables successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error deleting data from tables: " + e.getMessage());
            rollbackTransaction();
        }
    }

    public void createTables() {
        String sqlTable1 = "CREATE TABLE IF NOT EXISTS Users (\n"
                + " id INT PRIMARY KEY AUTO_INCREMENT,\n"
                + " username VARCHAR(25) NOT NULL UNIQUE\n"
                + ");";

        String sqlTable2 = "CREATE TABLE IF NOT EXISTS Games (\n"
                + " id INT PRIMARY KEY AUTO_INCREMENT,\n"
                + " gameType VARCHAR(10) NOT NULL UNIQUE\n"
                + ");";

        String sqlTable3 = "CREATE TABLE IF NOT EXISTS Scores (\n"
                + " id INT PRIMARY KEY AUTO_INCREMENT,\n"
                + " userId INT NOT NULL,\n"
                + " gameId INT NOT NULL,\n"
                + " score INT NOT NULL,\n"
                + " timeStamp VARCHAR(50) NOT NULL,\n"
                + " FOREIGN KEY (userId) REFERENCES Users(id),\n"
                + " FOREIGN KEY (gameId) REFERENCES Games(id)\n"
                + ");";

        try (Statement stmt = connection.createStatement()) {
            connection.setAutoCommit(false); // Start transaction
            stmt.execute(sqlTable1);
            stmt.execute(sqlTable2);
            stmt.execute(sqlTable3);
            connection.commit(); // Commit transaction
            System.out.println("Tables have been created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error creating tables: " + e.getMessage());
            rollbackTransaction();
        }
    }

    public void insertDataIntoUsers(Data data) {
        String sql = "INSERT IGNORE INTO Users(username) VALUES(?)";

        try (PreparedStatement sqlStatement = connection.prepareStatement(sql)) {
            sqlStatement.setString(1, data.getName());
            sqlStatement.executeUpdate();
            connection.commit(); // Commit transaction
            System.out.println("User inserted.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error inserting user: " + e.getMessage());
        }
    }

    public void insertDataIntoGames() {
        String[] gameTypes = { "Easy", "Medium", "Hard" };
        String sql = "INSERT IGNORE INTO Games(gameType) VALUES(?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (String gameType : gameTypes) {
                pstmt.setString(1, gameType);
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            System.out.println("Games inserted.");
        } catch (SQLException e) {
            System.out.println("Error inserting gameTypes: " + e.getMessage());
        }
    }

    public void insertDataIntoScores(Data data) {
        String sql = "INSERT INTO Scores(userId, gameId, score, timeStamp) VALUES(?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, getId(data.getName(), "Users", "username"));
            pstmt.setInt(2, getId(data.getGameType(), "Games", "gameType"));
            pstmt.setInt(3, data.getScore());
            pstmt.setTimestamp(4, data.getTime());
            pstmt.executeUpdate();
            connection.commit();
            System.out.println("Score inserted.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error inserting data into Scores: " + e.getMessage());
        }
    }

    private int getId(String query, String table, String column) {
        String sql = "SELECT id FROM " + table + " WHERE " + column + " = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, query);
            ResultSet result = pstmt.executeQuery();
            if (result.next()) {
                return result.getInt("id");
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            System.out.println("Error selecting Id from table: " + e.getMessage());
        }
        return -1;
    }

    public ArrayList<Data> selectAllData(int numOfRows) {
        String sql = "SELECT u.username, g.gameType, s.score, s.timeStamp FROM Scores AS s JOIN Users AS u ON s.userId = u.id JOIN Games AS g ON s.gameId = g.id LIMIT ?";
        ArrayList<Data> dataList = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, numOfRows);
            ResultSet rs = pstmt.executeQuery();
            connection.commit(); 

            while (rs.next()) {
                Data data = new Data(rs.getString("username"), rs.getString("gameType"), rs.getInt("score"),
                        rs.getTimestamp("timeStamp").getTime());

                dataList.add(data);
            }
        } catch (SQLException e) {
          //  e.printStackTrace();
            System.out.println("Error selecting data: " + e.getMessage());
        }
        for (Data data : dataList) {// TODO: testing to show data in terminal/ remove later
            System.out.println(data.getName() + " : " + data.getScore());
        }
        return dataList;
    }

}
