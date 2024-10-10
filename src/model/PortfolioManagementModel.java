package model;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * The interface represents the portfolio management model that has
 * different operations such as create, getting total composition, total value
 * and adding new shares.
 */
public interface PortfolioManagementModel {
  /**
   * The method initializes the portfolio object for a given name and shares data. The existing
   * method was changed to incorporate the sharesMap as one of the parameters. The change was
   * done to follow the requirement that the user can only create a normal portfolio after adding
   * the stocks which we failed to follow previously.
   *
   * @param portfolioName name of the portfolio.
   * @param sharesMap     share data of the portfolio.
   * @throws ParseException if the given data isn't parsed properly.
   */
  void createPortfolio(String portfolioName, List<Map<String, String>> sharesMap)
          throws ParseException;

  /**
   * The method returns list of shares present inside the portfolio.
   *
   * @return The list of shares in string format.
   * @throws EmptyPortfolioException whenever the method is called without creating the portfolio.
   */
  List<String> totalComposition() throws EmptyPortfolioException;

  /**
   * The method returns total computed value for the given data.
   * The computed value taken is either share's closing or opening value based on date.
   *
   * @param values This is list of the stocks that contains values of individual shares
   *               required to compute the total value.
   * @return the total computed value.
   * @throws EmptyPortfolioException whenever the method is called without creating the portfolio.
   * @throws ParseException          whenever the method fails to parse the given data.
   */
  float totalValue(List<Map<String, String>> values) throws
          EmptyPortfolioException, ParseException;


}