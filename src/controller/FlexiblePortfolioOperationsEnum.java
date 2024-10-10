package controller;

/**
 * The enum represents the flexible portfolio menu options and its respective choice.
 */
public enum FlexiblePortfolioOperationsEnum {
  VIEW_ALL_STOCKS("1"),
  BUY("2"),
  SELL("3"),
  TOTAL_VALUE("4"),
  TOTAL_COMPOSITION("5"),
  TOTAL_INVESTMENT("6"),
  SAVE("7"),
  INVEST("8"),
  STRATEGY("9"),
  EXIT("10");
  private final String choice;

  /**
   * The constructor initializes the choice of the flexible portfolio menu options.
   *
   * @param choice String choice the user chooses.
   */
  FlexiblePortfolioOperationsEnum(String choice) {
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
