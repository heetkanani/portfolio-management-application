package view;

import java.io.IOException;

/**
 * The interface represents the operations a view can perform.
 */
public interface PortfolioView {
  /**
   * The method displays the operations that can be performed after creating the portfolio.
   * It doesn't implement the operations.
   *
   * @throws IOException If an I/O error occurs.
   */
  void stockMenuOptions() throws IOException;

  /**
   * The method displays the operations that the user is given when loading the application.
   * It doesn't implement the operations.
   *
   * @throws IOException If an I/0 error occurs.
   */
  void portfolioMenuOptions() throws IOException;

  /**
   * The method calls the scanner to take an integer input value.
   *
   * @return the integer input value entered by the user.
   */
  int setInputValue();

  /**
   * The method calls the scanner to take a string input value.
   *
   * @return the string input value entered by the user.
   */
  String setInputString();

  /**
   * The method displays the message given.
   *
   * @param inputMessage the message to be displayed.
   * @throws IOException If an I/O error occurs.
   */
  void setInputMessage(String inputMessage) throws IOException;

  /**
   * The methods move to the next line.
   */
  void setNextLineMessage();

  /**
   * The method displays the error message given.
   *
   * @param errorMessage the error message to be displayed.
   * @throws IOException If an I/O error occurs.
   */
  void setErrorMessage(String errorMessage) throws IOException;


}
