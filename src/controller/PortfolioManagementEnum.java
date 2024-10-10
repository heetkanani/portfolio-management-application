package controller;

/**
 * The enum represents the main menu options and its respective choice.
 */
enum PortfolioManagementEnum {
  CREATE("1"),
  LOAD("2"),
  IMPORT("3"),
  STOCK_TREND("4"),
  LIST("5"),
  EXIT("6");

  private final String choice;

  /**
   * The constructor initializes the choice.
   *
   * @param choice String choice the user chooses.
   */
  PortfolioManagementEnum(String choice) {
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
