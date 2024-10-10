package enums;


/**
 * The enum represents the styles for the view to be displayed on the console.
 */
public enum CommandLineEnum {
  BOLD("\u001B[1m"),
  ITALIC("\u001B[3m"),
  RESET("\033[0m"),

  ERROR("\u001B[31m"),
  SUCCESS("\u001B[32m");
  private final String style;

  /**
   * The constructor initializes the style for the view to be displayed on the console.
   *
   * @param style required style for the view.
   */
  CommandLineEnum(String style) {
    this.style = style;
  }

  /**
   * The method returns current style.
   *
   * @return String style ascii code.
   */
  public String getStyle() {
    return this.style;
  }

}
