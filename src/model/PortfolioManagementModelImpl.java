package model;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * The class implements the PortfolioManagementModel and its methods. The class also uses
 * facade design pattern by delegating methods to another PortfolioModel class.
 */

public class PortfolioManagementModelImpl implements PortfolioManagementModel {

  protected PortfolioModel portfolio;

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
  @Override
  public void createPortfolio(String portfolioName, List<Map<String, String>> sharesMap)
          throws ParseException {
    portfolio = new PortfolioModelImpl(sharesMap);
  }


  /**
   * The method returns list of shares present inside the portfolio.
   *
   * @return The list of shares in string format.
   * @throws EmptyPortfolioException whenever the method is called without creating the portfolio.
   */
  @Override
  public List<String> totalComposition() throws EmptyPortfolioException {
    List<String> shares = new ArrayList<>();
    for (Share share : portfolio.totalComposition()) {
      shares.add(share.toString());
    }
    return shares;
  }

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
  @Override
  public float totalValue(List<Map<String, String>> values) throws
          EmptyPortfolioException, ParseException {
    return portfolio.totalValue(values);
  }


  /**
   * The method checks if the given quantity is non-negative whole number.
   *
   * @param shareQuantity quantity of the share.
   */
  protected void stockValidation(int shareQuantity) {
    if (shareQuantity <= 0) {
      throw new IllegalArgumentException("Provide valid share quantity.");
    }
  }


}
 