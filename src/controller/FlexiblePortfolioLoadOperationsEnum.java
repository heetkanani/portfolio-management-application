package controller;

/**
 * The enum represents the flexible portfolio load menu options and its respective choice.
 */
public enum FlexiblePortfolioLoadOperationsEnum {
  VIEW_ALL_STOCKS("1"),
  BUY("2"),
  SELL("3"),
  TOTAL_VALUE("4"),
  TOTAL_COMPOSITION("5"),
  TOTAL_INVESTMENT("6"),
  PERFORMANCE("7"),
  SAVE("8"),
  INVEST("9"),
  STRATEGY("10"),
  EXIT("11");

  private final String choice;

  /**
   * The constructor initializes the choice of the flexible load menu options.
   *
   * @param choice String choice the user chooses.
   */
  FlexiblePortfolioLoadOperationsEnum(String choice) {
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
