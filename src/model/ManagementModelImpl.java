package model;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The class extends the existing portfolio management model to support the portfolio management
 * features as well as implement the new features of management model. The class delegates the
 * operations to the respective flexible portfolio and statistics classes.
 */
public class ManagementModelImpl extends
        PortfolioManagementModelImpl implements ManagementModel {

  protected ShareStatistics statistics;
  protected FlexiblePortfolioModel flexiblePortfolio;

  private List<String> strategy;

  /**
   * The constructor initializes statistics class.
   */
  public ManagementModelImpl() {
    this.statistics = new ShareStatisticsImpl();
  }

  @Override
  public void loadFlexiblePortfolio(String name, List<String> shares, List<Map<String,
          String>> strategy, float amount)
          throws ParseException {
    if (strategy == null || amount == -1) {
      flexiblePortfolio = new FlexiblePortfolioModelImpl(name, shares);
    } else {
      System.out.println("STRATEGY: " + strategy.toString());
      flexiblePortfolio = new FlexiblePortfolioModelImpl(shares, strategy, amount);
    }
  }

  /**
   * The method creates a new flexible portfolio for a given name.
   *
   * @param name name of flexible portfolio to be created.
   */
  @Override
  public void createFlexiblePortfolio(String name) {
    flexiblePortfolio = new FlexiblePortfolioModelImpl(name);
  }

  /**
   * The method allows the user to sell a specific quantity of share from their portfolio on a
   * particular date.
   *
   * @param shareQuantity quantity of share to be sold.
   * @param shareName     name of share to be sold.
   * @param date          date on which the share has to be sold.
   * @throws ParseException whenever the method fails to parse the given data.
   */
  @Override
  public void sell(int shareQuantity, String shareName, LocalDate date) throws ParseException {
    super.stockValidation(shareQuantity);
    flexiblePortfolio.sell(shareQuantity, shareName, date);
  }

  /**
   * The method returns the total amount invested upto a particular date in the flexible
   * portfolio by the user.
   *
   * @param date date upto which the total invested amount has to be shown.
   * @return amount invested on a date.
   */
  @Override
  public float getTotalInvestment(LocalDate date) throws ParseException {
    return flexiblePortfolio.getTotalInvestment(date);
  }

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
  @Override
  public AbstractMap.SimpleEntry<Map<LocalDate,
          String>, Integer> getPerformanceOfFlexiblePortfolio(LocalDate
                                                                      startDate,
                                                              LocalDate endDate,
                                                              Map<LocalDate,
                                                                      List<Map<String,
                                                                              String>>> values)
          throws ParseException {
    return flexiblePortfolio.getPerformanceOfPortfolio(startDate, endDate, values);
  }

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
  @Override
  public AbstractMap.SimpleEntry<Map<LocalDate,
          String>, Integer> getPerformanceOfInFlexiblePortfolio(LocalDate startDate,
                                                                LocalDate endDate,
                                                                Map<LocalDate,
                                                                        List<Map<String,
                                                                                String>>> values)
          throws ParseException {
    return portfolio.getPerformanceOfPortfolio(startDate, endDate, values);
  }

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
  public AbstractMap.SimpleEntry<Map<LocalDate,
          String>, Integer> getPerformanceOfStock(LocalDate startDate,
                                                  LocalDate endDate,
                                                  List<Map<String, String>> values)
          throws ParseException {
    return statistics.getPerformanceOfStock(startDate, endDate, values);
  }

  /**
   * The method returns the total composition of the flexible portfolio.
   *
   * @return list of shares in string format.
   * @throws EmptyPortfolioException whenever the method is called without creating or loading the
   *                                 portfolio.
   */
  @Override
  public List<String> totalCompositionOfFlexiblePortfolio() throws EmptyPortfolioException {
    List<String> shares = new ArrayList<>();
    for (Share share : flexiblePortfolio.totalComposition()) {
      shares.add(share.toString());
    }
    return shares;
  }

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
  @Override
  public float totalValueOfFlexiblePortfolio(List<Map<String, String>> values)
          throws EmptyPortfolioException, ParseException {
    return flexiblePortfolio.totalValue(values);
  }

  /**
   * The method buys a specific quantity of share to the flexible portfolio.
   *
   * @param shareQuantity quantity of share to be added.
   * @param data          The data of the share to be added.
   * @throws EmptyPortfolioException whenever the method is called without creating the portfolio.
   * @throws ParseException          whenever the method fails to parse the given data.
   */
  @Override
  public void buy(int shareQuantity, Map<String, String> data)
          throws EmptyPortfolioException, ParseException {
    stockValidation(shareQuantity);
    flexiblePortfolio.add(shareQuantity, data);
  }

  @Override
  public void investFixedAmount(float amount, List<Map<String, String>> data)
          throws ParseException {
    flexiblePortfolio.investFixedAmount(amount, data);
  }

  @Override
  public List<String> getTransactionOfFlexiblePortfolio() {
    return flexiblePortfolio.getTransactionHistory();
  }


  @Override
  public float stockAnalysisDay(Map<String, String> shareData) {
    return statistics.stockTrendForDay(shareData);
  }

  @Override
  public float stockAnalysisPeriod(Map<String, String> shareData) {
    return statistics.stockTrendForPeriod(shareData);
  }

  @Override
  public float xDayMovingAverage(List<Map<String, String>> shareData) {
    return statistics.xDayMovingAverage(shareData);
  }

  @Override
  public Map<String, List<String>> crossOvers(List<Map<String, String>> shareData)
          throws IllegalArgumentException {
    return statistics.crossOvers(shareData);
  }

  @Override
  public Map<String, List<String>> movingCrossovers(List<Map<String, String>> shareData) {
    return statistics.movingCrossovers(shareData);
  }

  @Override
  public void dollarCostStrategy(float amount, List<Map<String,
          String>> data) throws ParseException {
    flexiblePortfolio.dollarCostStrategy(amount, data);
  }

  @Override
  public List<String> getStrategy() {
    return flexiblePortfolio.getStrategy();
  }

  @Override
  public void addStrategyToList(String strategy) {
    flexiblePortfolio.addStrategyToList(strategy);
  }

}
