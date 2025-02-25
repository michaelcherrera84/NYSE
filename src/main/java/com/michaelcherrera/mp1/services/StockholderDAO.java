package com.michaelcherrera.mp1.services;

import com.michaelcherrera.mp1.models.Stockholder;
import com.michaelcherrera.mp1.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This DAO defines database CRUD functionality for the stock_holder table.
 *
 * @see DAO
 */
public class StockholderDAO implements DAO<Stockholder> {

    /**
     * Set the fields of a {@link Stockholder} using specified {@link ResultSet}.
     *
     * @param stockholder a {@link Stockholder}
     * @param rs          a {@link ResultSet}
     * @throws SQLException if a database access error occurs
     */
    private void setStockHolder(Stockholder stockholder, ResultSet rs) throws SQLException {

        stockholder.setStockholderID(rs.getInt("stock_holder_id"));
        stockholder.setName(rs.getString("name"));
    }

    /**
     * Insert a tuple.
     *
     * @param stockHolder a tuple
     * @return row count of executed query
     * @throws UnsupportedOperationException not supported for this application
     */
    @Override
    public int create(Stockholder stockHolder) throws SQLException {

        throw new UnsupportedOperationException("Not supported for this application.");
    }

    /**
     * Return a tuple.
     *
     * @param id unique identifier of the requested tuple
     * @return the requested tuple or {@code null} if the requested tuple does not exist
     * @throws SQLException if a database access error occurs
     */
    @Override
    public Stockholder read(Object id) throws SQLException {

        String sql = "SELECT * FROM stock_holder WHERE stock_holder_id = ?";

        try (Connection connection = Database.connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, (Integer) id);
            try (ResultSet rs = statement.executeQuery()) {

                if (rs.next()) {
                    Stockholder stockHolder = new Stockholder();
                    setStockHolder(stockHolder, rs);
                    return stockHolder;
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
    public List<Stockholder> readAll() throws SQLException {

        List<Stockholder> stockholders = new ArrayList<>();
        String sql = "SELECT * FROM stock_holder";

        try (Connection connection = Database.connection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Stockholder stockHolder = new Stockholder();
                setStockHolder(stockHolder, rs);
                stockholders.add(stockHolder);
            }
        }
        return stockholders;
    }

    /**
     * Update a tuple.
     *
     * @param id          unique identifier of the requested tuple
     * @param stockHolder an updated tuple
     * @return row count of executed query
     * @throws UnsupportedOperationException not supported for this application
     */
    @Override
    public int update(Object id, Stockholder stockHolder) throws SQLException {

        throw new UnsupportedOperationException("Not supported for this application.");
    }

    /**
     * Delete a tuple.
     *
     * @param stockholder a tuple
     * @return row count of executed query
     * @throws UnsupportedOperationException not supported for this application
     */
    @Override
    public int delete(Stockholder stockholder) throws SQLException {

        throw new UnsupportedOperationException("Not supported for this application.");
    }
}
