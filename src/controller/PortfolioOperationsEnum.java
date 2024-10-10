package controller;

/**
 * The enum represents the stock menu options and its respective choice.
 */
enum PortfolioOperationsEnum {
  VIEW_ALL_STOCKS("1"),
  ADD("2"),
  SAVE("3"),
  EXIT("4");
  private final String choice;

  /**
   * The constructor initializes the choice of the stock menu options.
   *
   * @param choice String choice the user chooses.
   */
  PortfolioOperationsEnum(String choice) {
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
