package model;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

/**
 * The management model interface represents the operations that can be performed on flexible
 * portfolio, normal portfolio and share analysis. It extends the existing portfolio management
 * model to support the operations on normal portfolio. The model is responsible for delegating
 * the operations to their respective classes.
 */
public interface ManagementModel extends PortfolioManagementModel {

  /**
   * The method loads a flexible portfolio of given name and array of shares provided.
   *
   * @param name     name of flexible portfolio to be loaded.
   * @param shares   shares of flexible portfolio to be loaded.
   * @param strategy strategy to be added to the portfolio.
   * @param amount   amount invested in the portfolio.
   * @throws ParseException If the shares aren't parsed properly.
   */
  void loadFlexiblePortfolio(String name, List<String> shares, List<Map<String,
          String>> strategy, float amount)
          throws ParseException;

  /**
   * The method creates a new flexible portfolio for a given name.
   *
   * @param name name of flexible portfolio to be created.
   */
  void createFlexiblePortfolio(String name);

  /**
   * The method allows the user to sell a specific quantity of share from their portfolio on a
   * particular date.
   *
   * @param shareQuantity quantity of share to be sold.
   * @param shareName     name of share to be sold.
   * @param date          date on which the share has to be sold.
   * @throws ParseException whenever the method fails to parse the given data.
   */
  void sell(int shareQuantity, String shareName, LocalDate date) throws ParseException;

  /**
   * The method returns the total amount invested upto a particular date in the flexible
   * portfolio by the user.
   *
   * @param date date upto which the total invested amount has to be shown.
   * @return amount invested on a date.
   * @throws ParseException if date provided isn't valid
   */
  float getTotalInvestment(LocalDate date) throws ParseException;

  /**
   * Calculates the daily stock price change on the specified date and the name of the stock of
   * which the change is to be viewed for.
   *
   * @param shareData shares data to view the analysis of.
   * @return price changed value.
   */
  float stockAnalysisDay(Map<String, String> shareData);

  /**
   * Calculates the stock price changed over a period of time of the particular stock whether
   * its has gained it value or lost the same.
   *
   * @param shareData shares data to view the analysis of.
   * @return price changed value over the specified period.
   */
  float stockAnalysisPeriod(Map<String, String> shareData);

  /**
   * Calculates the X day moving average of the particular stock on the particular date.
   *
   * @param shareData shares data to view the analysis of.
   * @return x day moving average on the given date.
   */
  float xDayMovingAverage(List<Map<String, String>> shareData);

  /**
   * Calculates the crossover of a particular stock over a period of time to view if the
   * dates in the given period are of positive crossover or negative crossover.
   *
   * @param shareData shares data to view the analysis of.
   * @return crossover dates whether positives or negatives.
   * @throws IllegalArgumentException if any error occurs.
   */
  Map<String, List<String>> crossOvers(List<Map<String, String>> shareData)
          throws IllegalArgumentException;

  /**
   * Calculates the moving crossover of a particular stock over a period of time to view if the
   * dates in the given period are of positive moving crossover or negative moving crossover.
   *
   * @param shareData shares data to view the analysis of.
   * @return moving crossover dates whether positives or negatives.
   */
  Map<String, List<String>> movingCrossovers(List<Map<String, String>> shareData);

  /**
   * The method calculates the values of flexible portfolio for a given time range inorder to
   * plot it. It also calculates the scale and type of period that needs to be plotted on the
   * y-axis (day, year, month).
   *
   * @param startDate The start date from where the portfolio values need to be calculated.
   * @param endDate   The end date upto which the portfolio values need to be calculated.
   * @param values    map of date and its respective values on that date for each
   *                  stock in the portfolio.
   * @return A pair of hashmap of date and its respective values to be displayed.
   * @throws ParseException If the dates passed isn't parsed properly.
   */
  AbstractMap.SimpleEntry<Map<LocalDate,
          String>, Integer> getPerformanceOfFlexiblePortfolio(LocalDate startDate,
                                                              LocalDate endDate,
                                                              Map<LocalDate,
                                                                      List<Map<String,
                                                                              String>>> values)
          throws ParseException;

  /**
   * The method calculates the values of inflexible/normal portfolio for a given time range
   * inorder to plot it. It also calculates the scale and
   * type of period that needs to be plotted on the y-axis (day, year, month).
   *
   * @param startDate The start date from where the portfolio values need to be calculated.
   * @param endDate   The end date upto which the portfolio values need to be calculated.
   * @param values    map of date and its respective values on that date for each
   *                  stock in the portfolio.
   * @return A pair of hashmap of date and its respective values to be displayed.
   * @throws ParseException If the dates passed isn't parsed properly.
   */
  AbstractMap.SimpleEntry<Map<LocalDate,
          String>, Integer> getPerformanceOfInFlexiblePortfolio(LocalDate
                                                                        startDate,
                                                                LocalDate endDate,
                                                                Map<LocalDate,
                                                                        List<Map<String,
                                                                                String>>> values)
          throws ParseException;

  /**
   * The method calculates the values of a particular stock for a given time range
   * inorder to plot it. It also calculates the scale and
   * type of period that needs to be plotted on the y-axis (day, year, month).
   *
   * @param startDate The start date from where the stock values need to be calculated.
   * @param endDate   The end date upto which the stock values need to be calculated.
   * @param values    map of date and its respective values on that date for the stock.
   * @return A pair of hashmap of date and its respective values to be displayed.
   * @throws ParseException If the dates passed isn't parsed properly.
   */
  AbstractMap.SimpleEntry<Map<LocalDate, String>,
          Integer> getPerformanceOfStock(LocalDate startDate,
                                         LocalDate endDate,
                                         List<Map<String, String>> values) throws ParseException;


  /**
   * The method returns the total composition of the flexible portfolio.
   *
   * @return list of shares in string format.
   * @throws EmptyPortfolioException whenever the method is called without creating or loading the
   *                                 portfolio.
   */
  List<String> totalCompositionOfFlexiblePortfolio() throws EmptyPortfolioException;

  /**
   * The method returns total computed value for the given data for flexible portfolio.
   * The computed value taken is either share's closing or opening value based on date.
   *
   * @param values This is list of the stocks that contains values of individual shares
   *               required to compute the total value.
   * @return the total computed value.
   * @throws EmptyPortfolioException whenever the method is called without creating the portfolio.
   * @throws ParseException          whenever the method fails to parse the given data.
   */
  float totalValueOfFlexiblePortfolio(List<Map<String, String>> values) throws
          EmptyPortfolioException, ParseException;

  /**
   * The method buys a specific quantity of share to the flexible portfolio.
   *
   * @param shareQuantity quantity of share to be added.
   * @param data          The data of the share to be added.
   * @throws EmptyPortfolioException whenever the method is called without creating the portfolio.
   * @throws ParseException          whenever the method fails to parse the given data.
   */
  void buy(int shareQuantity, Map<String, String> data) throws EmptyPortfolioException,
          ParseException;

  /**
   * Calculates the fixed amount that is invested in the portfolio over a given period of time
   * by the investor.
   *
   * @param amount amount invested in the portfolio.
   * @param data   data of the shares that are invested in the portfolio.
   * @throws ParseException file exception.
   */
  void investFixedAmount(float amount, List<Map<String, String>> data) throws ParseException;

  /**
   * Fetches the transaction history of the transaction done by the user in order to invest
   * different stocks in his portfolio.
   *
   * @return list of transaction history.
   */
  List<String> getTransactionOfFlexiblePortfolio();


  /**
   * Calculates the dollar cost averaging of the particular portfolio based on the strategy created
   * by the user, where the shares value gets updated each time based on the period specified by
   * the user.
   *
   * @param amount amount invested in the portfolio.
   * @param data   data of the shares that are invested in the portfolio.
   * @throws ParseException file exception.
   */
  void dollarCostStrategy(float amount, List<Map<String,
          String>> data) throws ParseException;

  /**
   * Fetches all the strategies that an investor has created till now.
   *
   * @return the list of all strategies created.
   */
  List<String> getStrategy();

  /**
   * Adds the strategy to list of strategies which is created by the investor.
   *
   * @param strategy strategy of the portfolio the investor has created.
   */
  void addStrategyToList(String strategy);
}
