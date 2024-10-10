package controller;

/**
 * The enum represents the stock trend menu options and its respective choice.
 */
enum StockTrendEnum {
  GAIN_LOSE_DAY("1"),
  GAIN_LOSE_PERIOD("2"),
  X_DAY_MOVING_AVERAGE("3"),
  CROSSOVER("4"),
  MOVING_CROSSOVER("5"),
  PERFORMANCE("6"),
  EXIT("7");

  private final String choice;

  /**
   * The constructor initializes the choice for the stock trend menu options.
   *
   * @param choice String choice the user chooses.
   */
  StockTrendEnum(String choice) {
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
