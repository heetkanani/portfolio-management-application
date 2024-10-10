package controller;

/**
 * The enum represents the inflexible portfolio load menu options and its respective choice.
 */
public enum PortfolioLoadOperationsEnum {
  TOTAL_COMPOSITION("1"),
  TOTAL_VALUE("2"),
  PERFORMANCE("3"),
  EXIT("4");
  private final String choice;

  /**
   * The constructor initializes the choice of the inflexible portfolio menu options.
   *
   * @param choice String choice the user chooses.
   */
  PortfolioLoadOperationsEnum(String choice) {
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
