package com.michaelcherrera.mp1.services;

import com.michaelcherrera.mp1.models.Transaction;
import com.michaelcherrera.mp1.util.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This DAO defines database CRUD functionality for the transactions table.
 *
 * @see DAO
 */
public class TransactionsDAO implements DAO<Transaction> {

    /**
     * Set the fields of a {@link Transaction} using specified {@link ResultSet}.
     *
     * @param transaction a {@link Transaction}
     * @param rs          a {@link ResultSet}
     * @throws SQLException if a database access error occurs
     */
    private void setTransaction(Transaction transaction, ResultSet rs) throws SQLException {

        transaction.setTransactionId(rs.getInt("transaction_id"));
        transaction.setStockHolderID(rs.getInt("stock_holder_id"));
        transaction.setStockID(rs.getString("stock_id"));
        transaction.setQty(rs.getInt("qty"));
    }

    /**
     * Returns the total number of shares currently owned by a stockholder.
     *
     * @param stockID       stock symbol
     * @param stockHolderID stockholder's ID
     * @return the total number of shares currently owned by a stockholder
     * @throws SQLException if a database access error occurs
     */
    public long shareTotal(String stockID, Integer stockHolderID) throws SQLException {

        String sql = "SELECT SUM(qty) FROM transactions WHERE stock_holder_id = ? AND stock_id = ?";

        try (Connection connection = Database.connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, stockHolderID);
            statement.setString(2, stockID);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        }
        return 0;
    }

    /**
     * Returns the last transaction of a stockholder for a stock.
     *
     * @param stockID stock symbol
     * @param stockHolderID stockholder's ID
     * @return the last transaction from a stockholder
     * @throws SQLException if a database access error occurs
     */
    public Transaction getLastTransaction(String stockID, Integer stockHolderID) throws SQLException {

        String sql = "SELECT * FROM transactions WHERE stock_holder_id = ? AND stock_id = ? ORDER BY transaction_id " +
                "DESC LIMIT 1";

        try (Connection connection = Database.connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, stockHolderID);
            statement.setString(2, stockID);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Transaction transaction = new Transaction();
                    setTransaction(transaction, rs);
                    return transaction;
                }
            }
        }
        return null;
    }

    /**
     * Insert a tuple.
     *
     * @param transaction a tuple
     * @return row count of executed query
     * @throws SQLException if a database access error occurs
     */
    @Override
    public int create(Transaction transaction) throws SQLException {

        String sql =
                "INSERT INTO transactions (transaction_id, stock_holder_id, stock_id, qty) VALUES (DEFAULT, ?, ?, ?)";

        try (Connection connection = Database.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, transaction.getStockHolderID());
            preparedStatement.setString(2, transaction.getStockID());
            preparedStatement.setInt(3, transaction.getQty());
            return preparedStatement.executeUpdate();
        }
    }

    /**
     * Return a tuple.
     *
     * @param id unique identifier of the requested tuple
     * @return the requested tuple or {@code null} if the requested tuple does not exist
     * @throws SQLException if a database access error occurs
     */
    @Override
    public Transaction read(Object id) throws SQLException {

        String sql = "SELECT * FROM transactions WHERE transaction_id = ?";

        try (Connection connection = Database.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, (Integer) id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {
                    Transaction transaction = new Transaction();
                    setTransaction(transaction, resultSet);
                    return transaction;
                }
            }
        }
        return null;
    }

    /**
     * Return a {@linkplain List} of all tuples.
     *
     * @return a {@linkplain List} of all tuples.
     * @throws SQLException if a database access error occurs
     */
    @Override
    public List<Transaction> readAll() throws SQLException {

        List<Transaction> list = new ArrayList<>();
        String sql = "SELECT * FROM transactions";
        try (Connection connection = Database.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Transaction transaction = new Transaction();
                setTransaction(transaction, resultSet);
                list.add(transaction);
            }
        }
        return list;
    }

    /**
     * Update a tuple.
     *
     * @param id unique identifier of the requested tuple
     * @param transaction  an updated tuple
     * @return row count of executed query
     * @throws UnsupportedOperationException not supported for this application
     */
    @Override
    public int update(Object id, Transaction transaction) throws SQLException {

        throw new UnsupportedOperationException("Not supported for this application.");
    }

    /**
     * Delete a tuple.
     *
     * @param transaction a tuple
     * @return row count of executed query
     * @throws UnsupportedOperationException not supported for this application
     */
    @Override
    public int delete(Transaction transaction) throws SQLException {

        throw new UnsupportedOperationException("Not supported for this application.");
    }
}
