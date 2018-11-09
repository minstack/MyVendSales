/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.smyoon.myvendsales;

/**
 * A POJO representing a retailer's outlet to be able to easily map JSON key values.
 *
 * @author Sung Min Yoon
 */
public class VendOutlet {

    private String id;
    private String name;
    private String default_tax_id;
    private String currency;
    private String currency_symbol;
    private String display_prices;
    private String time_zone;
    private String email;
    private double totalSales;
    private int numSales;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefault_tax_id() {
        return default_tax_id;
    }

    public void setDefault_tax_id(String default_tax_id) {
        this.default_tax_id = default_tax_id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrency_symbol() {
        return currency_symbol;
    }

    public void setCurrency_symbol(String currency_symbol) {
        this.currency_symbol = currency_symbol;
    }

    public String getDisplay_prices() {
        return display_prices;
    }

    public void setDisplay_prices(String display_prices) {
        this.display_prices = display_prices;
    }

    public String getTime_zone() {
        return time_zone;
    }

    public void setTime_zone(String time_zone) {
        this.time_zone = time_zone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(double totalSales) {
        this.totalSales = totalSales;
    }

    public void addSaleTotal(double saleTotal) {
        this.totalSales += saleTotal;
    }

    public int getNumSales() {
        return numSales;
    }

    public void incrementNumSales() {
        this.numSales++;
    }

}
