package com.michaelcherrera.mp1.services;

import com.michaelcherrera.mp1.models.Stock;
import com.michaelcherrera.mp1.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This DAO defines database CRUD functionality for the stock table.
 *
 * @see DAO
 */
public class StockDAO implements DAO<Stock> {

    /**
     * Returns the current price of a specified stock from the stock table.
     *
     * @param stockId ID of a stock
     * @return the current price of a specified stock from the stock table.
     * @throws SQLException if a database access error occurs
     */
    public double currentPrice(String stockId) throws SQLException {

        String sql = "SELECT price_current FROM stock WHERE stock_id = ?";
        try (Connection connection = Database.connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, stockId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("price_current");
                }
            }
        }

        return 0;
    }

    /**
     * Set the fields of a {@link Stock} using specified {@link ResultSet}.
     *
     * @param stock a {@link Stock}
     * @param rs    a {@link ResultSet}
     * @throws SQLException if a database access error occurs
     */
    private void setStock(Stock stock, ResultSet rs) throws SQLException {

        stock.setStockId(rs.getString("stock_id"));
        stock.setCompanyName(rs.getString("company_name"));
        stock.setPriceCurrent(rs.getDouble("price_current"));
        stock.setPriceClosing(rs.getDouble("price_closing"));
        stock.setNumberOfSharesAvailable(rs.getLong("number_Of_shares_available"));
        stock.setNumberOfSharesSold(rs.getLong("number_Of_shares_sold"));
    }

    /**
     * Insert a tuple.
     *
     * @param o a tuple
     * @return row count of executed query
     * @throws UnsupportedOperationException not supported for this application
     */
    @Override
    public int create(Stock o) throws SQLException {

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
    public Stock read(Object id) throws SQLException {

        String sql = "SELECT * FROM stock WHERE stock_id = ?";

        try (Connection connection = Database.connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, (String) id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Stock stock = new Stock();
                    setStock(stock, resultSet);
                    return stock;
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
    public List<Stock> readAll() throws SQLException {

        List<Stock> list = new ArrayList<>();
        String sql = "SELECT * FROM stock";

        try (Connection connection = Database.connection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Stock stock = new Stock();
                setStock(stock, resultSet);
                list.add(stock);
            }
        }

        return list;
    }

    /**
     * Update a tuple.
     *
     * @param id    unique identifier of the requested tuple
     * @param stock an updated tuple
     * @return row count of executed query
     * @throws SQLException if a database access error occurs
     */
    @Override
    public int update(Object id, Stock stock) throws SQLException {

        String sql = "UPDATE stock SET stock_id = ?, company_name = ?, price_current = ?, price_closing = ?, " +
                "number_of_shares_available = ?, number_of_shares_sold = ? WHERE stock_id = ?";

        try (Connection connection = Database.connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, stock.getStockId());
            statement.setString(2, stock.getCompanyName());
            statement.setDouble(3, stock.getPriceCurrent());
            statement.setDouble(4, stock.getPriceClosing());
            statement.setLong(5, stock.getNumberOfSharesAvailable());
            statement.setLong(6, stock.getNumberOfSharesSold());
            statement.setString(7, stock.getStockId());
            return statement.executeUpdate();
        }
    }

    /**
     * Delete a tuple.
     *
     * @param stock a tuple
     * @return row count of executed query
     * @throws UnsupportedOperationException not supported for this application
     */
    @Override
    public int delete(Stock stock) throws SQLException {

        throw new UnsupportedOperationException("Not supported for this application.");
    }
}
