package model;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;


/**
 * The method represents the flexible portfolio model which allows the user to buy, sell and view
 * the total investment on a specific date. The flexible portfolio extends the existing portfolio
 * model to support their methods too.
 */
public interface FlexiblePortfolioModel extends PortfolioModel {

  /**
   * The method allows the user to sell a specific quantity of share from their portfolio on a
   * particular date.
   *
   * @param shareQuantity quantity of share to be sold.
   * @param shareName     name of share to be sold.
   * @param date          date on which the share has to be sold.
   * @throws ParseException whenever the method fails to parse the given data.
   */
  void sell(int shareQuantity, String shareName, LocalDate date) throws ParseException,
          IllegalArgumentException;

  /**
   * The method returns the total amount invested upto a particular date in the portfolio by the
   * user.
   *
   * @param date date upto which the total invested amount has to be shown.
   * @return amount invested on a date.
   * @throws ParseException if date isn't parsed properly.
   */
  float getTotalInvestment(LocalDate date) throws ParseException;

  /**
   * The method allows the user to buy a specific quantity of share to the portfolio on a
   * particular date. The method has been transferred from the PortfolioModel from the existing
   * design. The reason for the change was only the FlexiblePortfolioModel can buy the shares
   * even after saving and loading, which is not the case in the PortfolioModel.
   *
   * @param shareQuantity quantity of share to be bought.
   * @param data          The data of the share to be bought.
   * @throws ParseException whenever the method fails to parse the given data.
   */

  void add(float shareQuantity, Map<String, String> data)
          throws IllegalArgumentException, ParseException;


  /**
   * Calculates the fixed amount that is invested in the portfolio over a given period of time
   * by the investor.
   *
   * @param amount amount invested in the portfolio.
   * @param data   data of the shares that are invested in the portfolio.
   * @throws ParseException file exception.
   */
  void investFixedAmount(float amount, List<Map<String, String>> data) throws ParseException,
          IllegalArgumentException;

  /**
   * Fetches the transaction history of the transaction done by the user in order to invest
   * different stocks in his portfolio.
   *
   * @return list of transaction history.
   */
  List<String> getTransactionHistory();

  /**
   * Calculates the dollar cost averaging of the particular portfolio based on the strategy created
   * by the user, where the shares value gets updated each time based on the period specified by
   * the user.
   *
   * @param amount amount invested in the portfolio.
   * @param data   data of the shares that are invested in the portfolio.
   * @throws ParseException file exception.
   */
  void dollarCostStrategy(float amount, List<Map<String, String>> data) throws ParseException;

  /**
   * Adds the strategy to list of strategies which is created by the investor.
   *
   * @param strategy strategy of the portfolio the investor has created.
   */
  void addStrategyToList(String strategy);

  /**
   * Fetches all the strategies that an investor has created till now.
   *
   * @return the list of all strategies created.
   */
  List<String> getStrategy();
}
