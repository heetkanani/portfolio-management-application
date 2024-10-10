package model;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The class extends the PortfolioModelImpl to support their features as well as implements the
 * methods of FlexiblePortfolio to support the new features.
 */
public class FlexiblePortfolioModelImpl extends
        PortfolioModelImpl implements FlexiblePortfolioModel {

  private List<String> transaction;
  private List<String> strategyList;

  /**
   * Constructs the flexible portfolio where shares, strategy and amount to be invested in the
   * flexible portfolio can be added.
   *
   * @param shares   shares that are invested in the portfolio.
   * @param strategy strategy that is created by the investor.
   * @param amount   amount invested by the investor.
   * @throws ParseException file I/O exception.
   */
  public FlexiblePortfolioModelImpl(List<String> shares,
                                    List<Map<String, String>> strategy, float amount)
          throws ParseException {
    super();
    formatDataToShares(shares);
    strategyList = new ArrayList<>();
    investFixedAmount(amount, strategy);
  }

  /**
   * The constructor initializes the portfolio name and its shares. It is used whenever a
   * portfolio is loaded.
   *
   * @param name   name of the portfolio.
   * @param shares shares of the portfolio to be loaded.
   * @throws ParseException If the shares aren't parsed properly.
   */
  public FlexiblePortfolioModelImpl(String name, List<String> shares) throws ParseException {
    super();
    transaction = new ArrayList<>();
    strategyList = new ArrayList<>();
    formatDataToShares(shares);
  }

  /**
   * The constructor creates a new flexible portfolio and initializes the name, an empty shares
   * array and empty history hashmap.
   *
   * @param name name of the flexible portfolio.
   */
  public FlexiblePortfolioModelImpl(String name) {
    super();
    transaction = new ArrayList<>();
    strategyList = new ArrayList<>();
  }

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
  @Override
  public void add(float shareQuantity, Map<String, String> data)
          throws IllegalArgumentException, ParseException {
    if (shareQuantity <= 0) {
      throw new IllegalArgumentException("Please enter a valid quantity");
    }
    Share share = new Share().
            setTicker(data.get("ticker")).
            setTimestamp(parseDate(data.get("timestamp"))).
            setOpen(Float.parseFloat(data.get("open"))).
            setClose(Float.parseFloat(data.get("close"))).
            setVolume(Long.parseLong(data.get("volume")));
    if (shareQuantity > share.getVolume()) {
      throw new IllegalArgumentException("Quantity cannot be greater than the volume");
    }
    for (Share existingShare : this.shares) {
      if (existingShare.getTicker().equals(share.getTicker())) {
        if (existingShare.getShareQuantity() + shareQuantity > existingShare.getVolume()) {
          throw new IllegalArgumentException("Quantity cannot be greater than the volume");
        }
        this.checkShareQuantity(shareQuantity, existingShare);
        if (existingShare.getTimestamp().isEqual(share.getTimestamp())) {
          float existingShareQuantity = existingShare.getShareQuantity();
          existingShare.setShareQuantity(shareQuantity);
          transaction.add(existingShare.toString() + ",BUY");
          existingShare.setShareQuantity(existingShare.getShareQuantity() + existingShareQuantity);
          existingShare.setBuyValue(existingShare.getBuyValue() +
                  shareQuantity * Float.parseFloat(data.get("close")));
          return;
        }
      }
    }
    share.setShareQuantity(shareQuantity);
    share.setBuyValue(shareQuantity * Float.parseFloat(data.get("close")));
    transaction.add(share.toString() + ",BUY");
    this.shares.add(share);
  }

  @Override
  public void investFixedAmount(float amount, List<Map<String, String>> data)
          throws ParseException, IllegalArgumentException {
    if (amount <= 0) {
      throw new IllegalArgumentException("Please enter a valid amount");
    }
    for (Map<String, String> map : data) {
      if (Float.parseFloat(map.get("percentage")) <= 0) {
        throw new IllegalArgumentException("Please enter a valid percentage");
      }
      float quantity = ((Float.parseFloat(map.get("percentage")) * amount) / 100) /
              Float.parseFloat(map.get("close"));
      this.add(quantity, map);
    }
  }

  /**
   * The method allows the user to sell a specific quantity of share from their portfolio on a
   * particular date.
   *
   * @param shareQuantity quantity of share to be sold.
   * @param shareName     name of the share to be sold.
   * @param date          date on which the share has to be sold.
   * @throws ParseException whenever the method fails to parse the given data.
   */
  @Override
  public void sell(int shareQuantity, String shareName, LocalDate date) throws ParseException,
          IllegalArgumentException {
    if (shareQuantity <= 0) {
      throw new IllegalArgumentException("Please enter a valid quantity");
    }
    for (Share existingShare : this.shares) {
      if (existingShare.getTicker().equals(shareName)) {
        if (existingShare.getTimestamp().isEqual(date) ||
                existingShare.getTimestamp().isBefore(date)) {
          if (shareQuantity > existingShare.getShareQuantity()) {
            throw new IllegalArgumentException("You only have " +
                    existingShare.getShareQuantity());
          }
          if (existingShare.getShareQuantity() - shareQuantity == 0) {
            this.shares.remove(existingShare);
            transaction.add(existingShare.toString() + ",SELL");
          } else {
            float existingShareQuantity = existingShare.getShareQuantity();
            existingShare.setShareQuantity(shareQuantity);
            transaction.add(existingShare.toString() + ",SELL");
            existingShare.setShareQuantity(existingShareQuantity - shareQuantity);
          }
          return;
        }
      }
    }
    throw new IllegalArgumentException("You don't have this " + shareName + " in your portfolio.");
  }

  /**
   * The method returns the total amount invested upto a particular date in the portfolio by the
   * user.
   *
   * @param date date upto which the total invested amount has to be shown.
   * @return amount invested on a date.
   */
  @Override
  public float getTotalInvestment(LocalDate date) throws ParseException {
    float investmentValue = 0;
    for (String s : transaction) {
      String[] row = s.split(",");
      if (parseDate(row[1]).isEqual(date) || parseDate(row[1]).isBefore(date)) {
        if (row[7].equals("BUY")) {
          investmentValue += Float.parseFloat(row[6]);
        }
      }
    }
    return investmentValue;
  }

  @Override
  public List<String> getTransactionHistory() {
    return transaction;
  }

  @Override
  public void dollarCostStrategy(float amount,
                                 List<Map<String, String>> data) throws ParseException {
    this.investFixedAmount(amount, data);
  }

  @Override
  public void addStrategyToList(String strategy) {
    strategyList.add(strategy);
  }

  @Override
  public List<String> getStrategy() {
    return strategyList;
  }

  private void formatDataToShares(List<String> sharesList) throws ParseException {
    transaction = sharesList;
    for (String transact : sharesList) {
      String[] row = transact.split(",");
      boolean flag = false;
      for (Share existingShare : shares) {
        if (existingShare.getTicker().equals(row[0]) &&
                existingShare.getTimestamp().isEqual(parseDate(row[1]))
        ) {
          if (row[7].equals("BUY")) {
            existingShare.setShareQuantity(existingShare
                    .getShareQuantity() + Float.parseFloat(row[5]));
            existingShare.setBuyValue(existingShare
                    .getBuyValue() + Float.parseFloat(row[6]));
          } else {
            existingShare.setShareQuantity(existingShare
                    .getShareQuantity() - Float.parseFloat(row[5]));
          }
          flag = true;
        } else if (existingShare.getTicker().equals(row[0]) &&
                existingShare.getTimestamp().isBefore(parseDate(row[1])) &&
                row[7].equals("SELL")
        ) {
          existingShare.setShareQuantity(existingShare
                  .getShareQuantity() - Float.parseFloat(row[5]));
          flag = true;
        }
      }
      if (!flag) {
        Share shareObj = new Share().
                setTicker(row[0]).
                setTimestamp(parseDate(row[1])).
                setOpen(Float.parseFloat(row[2])).
                setClose(Float.parseFloat(row[3])).
                setVolume(Long.parseLong(row[4])).
                setShareQuantity(Float.parseFloat(row[5])).
                setBuyValue(Float.parseFloat(row[6]));
        shares.add(shareObj);
      }
    }
    shares.removeIf(share -> share.getShareQuantity() == 0);
  }
}

