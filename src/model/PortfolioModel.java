package model;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

/**
 * The interface represents the method to be done on the portfolio object.
 * The methods are called in the PortfolioManagementClass.
 */
public interface PortfolioModel {

  /**
   * The method returns the list of shares in the portfolio.
   *
   * @return List of Share object.
   * @throws IllegalStateException if the shares is empty.
   */
  List<Share> totalComposition() throws IllegalStateException;

  /**
   * The method returns total computed value for the given data.
   * The computed value taken is either share's closing or opening value based on date. The
   * method was implemented here and the logic was moved from PortfolioManagementModel to here.
   * The change was made to make sure that all the operations of portfolio should happen inside
   * the portfolio interface and not in the management class who responsibility is to delegate
   * the methods.
   *
   * @param values This is list of the stocks that contains values of individual shares
   *               required to compute the total value.
   * @return the total computed value.
   * @throws ParseException whenever the method fails to parse the given data.
   */
  float totalValue(List<Map<String, String>> values) throws ParseException;

  /**
   * The method calculates the values of inflexible/normal portfolio for a given time range
   * inorder to plot it. It also calculates the scale and
   * type of period that needs to be plotted on the y-axis (day, year, month). The method was
   * added in this interface to implement a new requirement to view the performance of portfolio.
   *
   * @param startDate The start date from where the portfolio values need to be calculated.
   * @param endDate   The end date upto which the portfolio values need to be calculated.
   * @param values    map of date and its respective values on that date for each
   *                  stock in the portfolio.
   * @return A pair of hashmap of date and its respective values to be displayed.
   * @throws ParseException If the dates passed isn't parsed properly.
   */
  AbstractMap.SimpleEntry<Map<LocalDate,
          String>, Integer> getPerformanceOfPortfolio(LocalDate startDate,
                                                      LocalDate endDate,
                                                      Map<LocalDate, List<Map<String,
                                                              String>>> values)
          throws ParseException;

}
