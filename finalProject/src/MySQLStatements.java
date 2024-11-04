package finalProject.src;

public interface MySQLStatements {
    static final String SELECT_DATA_MYSQL_STATEMENT = "SELECT u.username, g.gameType, s.score, s.timeStamp FROM Scores AS s JOIN Users AS u ON s.userId = u.id JOIN Games AS g ON s.gameId = g.id LIMIT ?";
    static final String INSERT_SCORES_MYSQL_STATEMENT = "INSERT INTO Scores(userId, gameId, score, timeStamp) VALUES(?, ?, ?, ?)";
    static final String INSERT_USERS_MYSQL_STATEMENT = "INSERT IGNORE INTO Users(username) VALUES(?)";
}
