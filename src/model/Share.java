package model;

import java.time.LocalDate;

/**
 * The Share class that create the share object and is used to store the values
 * of a share of the portfolio.
 */
public class Share {
  private String ticker;
  private LocalDate timestamp;
  private float open;
  private float close;
  private long volume;
  private float shareQuantity;
  private float buyValue;

  /**
   * The constructor initializes some default values if the share is not provided with values.
   */
  public Share() {
    this.ticker = "";
    this.timestamp = LocalDate.now();
    this.open = 0;
    this.close = 0;
    this.volume = 0;
    this.shareQuantity = 1;
    this.buyValue = 0;
  }

  /**
   * The method formats the object to string.
   *
   * @return String representation of the object.
   */
  @Override
  public String toString() {
    return this.ticker + "," + this.timestamp + "," + this.open + ","
            + this.close + "," + this.volume + "," + this.shareQuantity + "," + this.buyValue;
  }

  /**
   * The method returns the share quantity.
   *
   * @return integer share quantity.
   */
  protected float getShareQuantity() {
    return this.shareQuantity;
  }

  /**
   * The method sets the share quantity.
   *
   * @param shareQuantity quantity given that needs to be set.
   * @return updated Share object.
   */
  protected Share setShareQuantity(float shareQuantity) {
    this.shareQuantity = shareQuantity;
    return this;
  }

  /**
   * The method returns the share ticker.
   *
   * @return share ticker in String.
   */
  protected String getTicker() {
    return this.ticker;
  }

  /**
   * The method sets the share ticker.
   *
   * @param ticker ticker that needs to be set.
   * @return updated Share object
   */
  protected Share setTicker(String ticker) {
    this.ticker = ticker;
    return this;
  }

  /**
   * The method sets the share's opening value.
   *
   * @param open open value that needs to be set.
   * @return updated Share object
   */
  protected Share setOpen(float open) {
    this.open = open;
    return this;
  }

  /**
   * The method sets the share's closing value.
   *
   * @param close close value that needs to be set.
   * @return updated Share object
   */
  protected Share setClose(float close) {
    this.close = close;
    return this;
  }

  /**
   * The method sets the share timestamp.
   *
   * @param timestamp timestamp that needs to be set.
   * @return updated Share object
   */
  protected Share setTimestamp(LocalDate timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  /**
   * The method returns the share volume.
   *
   * @return share volume in long.
   */
  protected long getVolume() {
    return this.volume;
  }

  /**
   * The method sets the share volume.
   *
   * @param volume volume that needs to be set.
   * @return updated Share object
   */
  protected Share setVolume(long volume) {
    this.volume = volume;
    return this;
  }

  /**
   * The method returns the timestamp of the stock.
   *
   * @return date of the stock price.
   */
  protected LocalDate getTimestamp() {
    return this.timestamp;
  }

  /**
   * Setting the value on which the stock was bought.
   *
   * @param buyValue buy value of the stock.
   * @return updated Share object.
   */
  protected Share setBuyValue(float buyValue) {
    this.buyValue = buyValue;
    return this;
  }

  /**
   * Getting the buy value of the stock.
   *
   * @return bought value of the stock.
   */
  protected float getBuyValue() {
    return buyValue;
  }

}

