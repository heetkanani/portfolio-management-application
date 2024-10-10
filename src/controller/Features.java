package controller;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;

import view.ManagementGUIView;

/**
 * This class implements the different features that the controller uses in order to perform
 * the operations on portfolios and stocks.
 */
public interface Features {
  /**
   * Creates a portfolio as per the name specified by the user, where a user can perform different
   * operations like adding and selling the stocks.
   *
   * @param portfolioName  name of the portfolio.
   * @param portfolioLabel label of the portfolio.
   */
  void createPortfolio(String portfolioName, JLabel portfolioLabel);

  /**
   * A Validation method that checks if the portfolio exists with the same
   * name as specified or not.
   *
   * @param portfolioName name of the portfolio.
   * @return true if exists, false otherwise.
   */
  boolean checkPortfolio(String portfolioName);

  /**
   * Loads an existing portfolio as per the name specified by the user, where a user can
   * perform different operations like adding and selling the stocks.
   *
   * @param portfolioName  name of the portfolio.
   * @param portfolioLabel label of the portfolio.
   */
  void loadPortfolio(String portfolioName, JLabel portfolioLabel);

  /**
   * Displays different stocks based on the name specified, that are available in the
   * stock market listed.
   *
   * @param stockName  name of the stock that is to be searched.
   * @param stockLabel label of the stock.
   * @return map specifying the ticker and the company name associated with it.
   */
  Map<String, String> viewAllStocks(String stockName, JLabel stockLabel);

  /**
   * Checks if a particular stock exists or not based on the name entered and the listed
   * stocks in the stock market.
   *
   * @param stockName name of the stock.
   * @return true if stock exists, flase otherwise.
   * @throws IOException    file I/O exception.
   * @throws ParseException file incorrectly parse exception.
   */
  boolean checkStock(String stockName) throws ParseException, IOException;

  /**
   * Checks if the provided date is valid or not.
   *
   * @param year  year of the calendar.
   * @param month month of the calendar year.
   * @param day   day of the calendar year.
   * @return true if date is valid, false otherwise.
   */
  boolean isValidDate(String year, String month, String day);

  /**
   * Checks if the stock data is present on the specified data.
   *
   * @param year      year of the calendar.
   * @param month     month of the calendar year.
   * @param day       day of the calendar year.
   * @param stockName name of the stock.
   * @return true if share exists on that date, false otherwise.
   */
  boolean checkShareOnDate(String year, String month, String day, String stockName);

  /**
   * Formats the date based on the year, month and day provided as the arguments.
   *
   * @param year  year of the calendar.
   * @param month month of the calendar year.
   * @param day   day of the calendar year.
   * @return formatted date.
   */
  LocalDate formatDates(String year, String month, String day);

  /**
   * Calculates the daily stock price change on the specified date and the name of the stock of
   * which the change is to be viewed for.
   *
   * @param stockName name of the stock.
   * @param date      date on which the data is to be viewed for.
   * @param dateLabel label of the date.
   * @return price changed value.
   */
  float dailyStockPriceChange(String stockName, LocalDate date, JLabel dateLabel);

  /**
   * Calculates the stock price changed over a period of time of the particular stock whether
   * its has gained it value or lost the same.
   *
   * @param stockName name of the stock.
   * @param startDate start date of the period to view the price change for.
   * @param endDate   end date of the period to view the price change for.
   * @param label     label of the price change.
   * @return price changed value over the specified period.
   */
  float periodStockPriceChange(String stockName, LocalDate startDate, LocalDate endDate,
                               JLabel label);

  /**
   * Calculates the X day moving average of the particular stock on the particular date.
   *
   * @param stockName name of the stock.
   * @param xDay      X day moving average .
   * @param date      date from which the moving average is to be viewed.
   * @return x day moving average on the given date.
   */
  float xDayMovingAverage(String stockName, Integer xDay, LocalDate date);

  /**
   * Calculates the crossover of a particular stock over a period of time to view if the
   * dates in the given period are of positive crossover or negative crossover.
   *
   * @param stockName name of the stock.
   * @param startDate start date of the stock analysis period.
   * @param endDate   end date of the stock analysis period.
   * @return crossover dates whether positives or negatives.
   */
  Map<String, List<String>> crossovers(String stockName, LocalDate startDate, LocalDate endDate);

  /**
   * Calculates the moving crossover of a particular stock over a period of time to view if the
   * dates in the given period are of positive moving crossover or negative moving crossover.
   *
   * @param stockName name of the stock.
   * @param xDay      x day dates from the start date.
   * @param yDay      y day dates from the end date.
   * @param startDate start date of the period.
   * @param endDate   end date of the period.
   * @return moving crossover dates whether positives or negatives.
   */
  Map<String, List<String>> movingCrossovers(String stockName, int xDay, int yDay
          , LocalDate startDate, LocalDate endDate);

  /**
   * Fetches the total value of the stocks that is particularly invested in the giving portfolio.
   *
   * @param date date on which the value is to be viewed for.
   * @return total value of the current portfolio.
   */
  float getValueOfPortfolio(LocalDate date);

  /**
   * Fetches the total amount invested of the stocks in a particular portfolio.
   *
   * @param date date on which the investment is to be viewed.
   * @return total investment of the portfolio.
   */
  float getTotalInvestmentOfPortfolio(LocalDate date);

  /**
   * Fetches the total portfolios that are present under a particular investor name.
   *
   * @return list of all portfolios present.
   */
  List<String> getFlexiblePortfolios();

  /**
   * Fetches the total strategies that are present under a particular investor name.
   *
   * @return list of all strategies present.
   */
  List<String> getAllStrategies();

  /**
   * Checks if the start date specified is before the end date specified.
   *
   * @param startYear  start year of the calendar.
   * @param startMonth start month of the calendar.
   * @param startDay   start day of the calendar year.
   * @param endYear    end year of the calendar.
   * @param endMonth   end month of the calendar.
   * @param endDay     end day of the calendar year.
   * @return true if date is before, false if its after.
   */
  boolean isStartDateBeforeEndDate(String startYear, String startMonth,
                                   String startDay, String endYear, String endMonth,
                                   String endDay);

  /**
   * A stock can be bought on a specific date and of any quantity specified.
   *
   * @param companyName name of the company.
   * @param quantity    quantity of the stock.
   * @param localDate   date on which the stock is to be bought.
   * @param label       label of the stock.
   */
  void buyStock(String companyName, String quantity, LocalDate localDate, JLabel label);

  /**
   * Checks if the particular quantity is invalid or not.
   *
   * @param quantity quantity of the stock.
   * @return true if the quantity is invalid, false otherwise.
   */
  boolean checkQuantity(float quantity);

  /**
   * A stock can be sold on a specific date and of any quantity specified.
   *
   * @param companyName name of the company.
   * @param quantity    quantity of the stock.
   * @param date        date on which the stock is to be bought.
   * @param label       label of the stock.
   */
  void sellStock(String companyName, String quantity, LocalDate date, JLabel label);

  /**
   * Saves a particular portfolio onto the file with the number of stocks added by the investor.
   *
   * @param portfolioName name of the portfolio.
   * @param stockLabel    label of the stock.
   * @return true if portfolio is saved successfully, false otherwise.
   */
  boolean savePortfolio(String portfolioName, JLabel stockLabel);

  /**
   * Displays the total composition of the stocks that are present in the portfolio.
   *
   * @param label label of the stock.
   */
  void totalComposition(JLabel label);


  /**
   * Adds the data to the list as in the required format.
   *
   * @param amount          amount to be invested.
   * @param list            data list.
   * @param percentageLabel label of the percetnage.
   * @param errorLabel      label of the error.
   * @param data            data of the share.
   */
  void addToList(float amount, List<Map<String, String>> list, JLabel percentageLabel,
                 JLabel errorLabel, Map<String, String> data);

  /**
   * A fixed percentage of shares can be invested by the user on the amount specified.
   *
   * @param list   list of shares that are invested.
   * @param amount amount invested by the investor.
   * @param label  label of the amount.
   */
  void investFixedAmount(List<Map<String, String>> list, Float amount, JLabel label);

  /**
   * Adds the data to the table that can be used to display on the user interface.
   *
   * @param tableModel      table model used to display the data
   * @param company         name of the company of which the data is.
   * @param timestamp       timestamp of the stock data.
   * @param percentage      percentage entered by the investor of the specific stock.
   * @param percentageLabel label of the percentage.
   * @param errorLabel      label to display the error for the view.
   * @return map data of the stocks.
   */
  Map<String, String> addDataToTable(DefaultTableModel tableModel, String company, String timestamp,
                                     String percentage, JLabel percentageLabel, JLabel errorLabel);

  /**
   * Adds the row to the stocks data table on which stocks are to be added.
   *
   * @param model      table model used to display the data.
   * @param row        row to be added.
   * @param name       name of the row that is to be added.
   * @param rowCount   count of the row.
   * @param percentage percentage specified by the investor.
   */
  void addRow(DefaultTableModel model, String[] row, String name, int rowCount,
              String percentage);

  /**
   * Fetches the strategy data which the investor has created on his portfolio by adding
   * different percentage of stocks.
   *
   * @param currentDate current date to view the strategy on.
   * @param companyName name of the company to create the strategy.
   * @param shareData   stocks data added while creating the strategy.
   * @param label       label of the strategy.
   * @return map of strategy data.
   */
  Map<String, String> getDataForStrategy(LocalDate currentDate, String companyName,
                                         List<Map<String, String>> shareData,
                                         JLabel label);

  /**
   * Fetches the share data of the company specified.
   *
   * @param companyName name of the company to fetch the data.
   * @param label       label of the stock.
   * @return list of stocks data.
   */
  List<Map<String, String>> getShareData(String companyName, JLabel label);

  /**
   * Adds the shares to the shares percentage list specified by the investor.
   *
   * @param sharesByPercentage percentage of different share added.
   * @param shares             string of shares added in the portfolio.
   */
  void addShareToSharesList(List<String> sharesByPercentage, String shares);

  /**
   * Creates the dollar cost averaging of the particular portfolio based on the strategy created
   * by the investor, where the shares value gets updated each time based on the period specified by
   * the investor.
   *
   * @param sharesByPercentage percentage of different share added.
   * @param amount             amount invested while creating the strategy.
   * @param startDate          start date from which date the strategy is to be created.
   * @param endDate            end date on which the strategy should be until.
   * @param currentDate        current date to view the strategy on.
   * @param period             period of days on which the strategy should be updated.
   * @param sharesList         shares data added in the portfolio while creating the strategy.
   * @param label              label for the shares.
   */
  void createStrategy(List<String> sharesByPercentage, float amount, LocalDate startDate,
                      LocalDate endDate, LocalDate currentDate, int period,
                      List<Map<String, String>> sharesList, JLabel label);

  /**
   * Sets the basic layout for the Graphical User Interface.
   *
   * @param view GUI view of the application
   */
  void setView(ManagementGUIView view);
}
