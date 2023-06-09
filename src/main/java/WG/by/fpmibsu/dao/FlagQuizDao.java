package WG.by.fpmibsu.dao;

import WG.by.fpmibsu.entity.Country;
import WG.by.fpmibsu.entity.FlagQuiz;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FlagQuizDao extends ConnectionInit implements ModeDao<Integer, FlagQuiz> {
    final private String create = "INSERT INTO FlagQuiz (Country_ID, Variant1," +
            " Variant2, Variant3, 4) VALUES (?, ?, ?, ?, ?)";
    final private String read = "SELECT * FROM FlagQuiz WHERE FlagQuiz_ID = ?";
    final private String update = "UPDATE FlagQuiz SET Country_ID = ?," +
            " Variant1 = ?, Variant2 = ?, Variant3 = ?, Variant4 = ? WHERE FlagQuiz_ID = ?";
    final private String delete = "DELETE FROM FlagQuiz WHERE FlagQuiz_ID = ?";
    final private String readAll = "SELECT * FROM FlagQuiz";
    final private String count = "SELECT COUNT(*) AS quantity FROM FlagQuiz;";
     Connection connection;
    public FlagQuizDao() throws DaoException {
        super();
    }

    @Override
    public boolean create(FlagQuiz flagQuiz) throws DaoException {
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(create)) {
            statement.setInt(1, flagQuiz.getAnswerCountry().getID());
            statement.setInt(2, flagQuiz.getFirstVariant().getID());
            statement.setInt(3, flagQuiz.getSecondVariant().getID());
            statement.setInt(4, flagQuiz.getThirdVariant().getID());
            statement.setInt(5, flagQuiz.getFourthVariant().getID());
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            throw new DaoException("Error creating FlagQuiz", e);
        }
    }

    public int returnCount() throws DaoException {
        try {
            connection = connectionPool.getConnection();
            String query = count;
            int quantity = -1;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                String temp = resultSet.getString("quantity");
                quantity = Integer.parseInt(temp);

            }
            statement.close();
            connection.close();
            return quantity;
        } catch (SQLException e) {
            throw new DaoException("Error returning count", e);
        }
    }

    @Override
    public FlagQuiz read(Integer id) throws DaoException {
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(read)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int flagQuizID = resultSet.getInt("FlagQuiz_ID");
                int answerCountryID = resultSet.getInt("Country_ID");
                int variant1ID = resultSet.getInt("Variant1");
                int variant2ID = resultSet.getInt("Variant2");
                int variant3ID = resultSet.getInt("Variant3");
                int variant4ID = resultSet.getInt("Variant4");
                Country answerCountry = new CountryDao().read(answerCountryID);
                Country variant1 = new CountryDao().read(variant1ID);
                Country variant2 = new CountryDao().read(variant2ID);
                Country variant3 = new CountryDao().read(variant3ID);
                Country variant4 = new CountryDao().read(variant4ID);
                return new FlagQuiz(flagQuizID, answerCountry, variant1, variant2, variant3, variant4);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DaoException("Error reading FlagQuiz", e);
        }
    }

    @Override
    public FlagQuiz update(FlagQuiz flagQuiz) throws DaoException {
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(update)) {
            statement.setInt(1, flagQuiz.getAnswerCountry().getID());
            statement.setInt(2, flagQuiz.getFirstVariant().getID());
            statement.setInt(3, flagQuiz.getSecondVariant().getID());
            statement.setInt(4, flagQuiz.getThirdVariant().getID());
            statement.setInt(5, flagQuiz.getFourthVariant().getID());
            statement.setInt(6, flagQuiz.getFlagQuizID());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                return flagQuiz;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DaoException("Error updating FlagQuiz", e);
        }
    }

    @Override
    public boolean delete(Integer id) throws DaoException {
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(delete)) {
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            throw new DaoException("Error deleting FlagQuiz", e);
        }
    }

    @Override
    public List<FlagQuiz> readAll() throws DaoException {
        connection = connectionPool.getConnection();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(readAll);
            List<FlagQuiz> flagQuizzes = new ArrayList<>();
            while (resultSet.next()) {
                int flagQuizID = resultSet.getInt("FlagQuiz_ID");
                int answerCountryID = resultSet.getInt("Country_ID");
                int variant1ID = resultSet.getInt("Variant1");
                int variant2ID = resultSet.getInt("Variant2");
                int variant3ID = resultSet.getInt("Variant3");
                int variant4ID = resultSet.getInt("Variant4");
                Country answerCountry = new CountryDao().read(answerCountryID);
                Country variant1 = new CountryDao().read(variant1ID);
                Country variant2 = new CountryDao().read(variant2ID);
                Country variant3 = new CountryDao().read(variant3ID);
                Country variant4 = new CountryDao().read(variant4ID);
                flagQuizzes.add(new FlagQuiz(flagQuizID, answerCountry, variant1, variant2, variant3, variant4));
            }
            return flagQuizzes;
        } catch (SQLException e) {
            throw new DaoException("Error reading FlagQuizzes", e);
        }
    }
}
