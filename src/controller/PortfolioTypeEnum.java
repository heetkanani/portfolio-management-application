package controller;

/**
 * The enum represents types of portfolio used by the application.
 */
public enum PortfolioTypeEnum {
  NORMAL("1"),
  FLEXIBLE("2");
  private final String choice;

  /**
   * The constructor initializes the choice of the types of the portfolio.
   *
   * @param choice String choice the user chooses.
   */
  PortfolioTypeEnum(String choice) {
    this.choice = choice;
  }

  /**
   * The method returns the choice.
   *
   * @return String choice.
   */
  String getChoice() {
    return this.choice;
  }
}
