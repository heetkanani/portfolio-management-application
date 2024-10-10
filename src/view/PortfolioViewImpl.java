package view;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import enums.CommandLineEnum;

/**
 * The class implements the PortfolioView interface.
 * It initializes the input stream and appendable output
 * to capture the input and output in the commandline.
 */
public class PortfolioViewImpl implements PortfolioView {

  /**
   * Changed the appendable access modifier from private to protected inorder to use it in
   * extended class.
   */
  protected final Appendable out;
  Scanner scanner;

  /**
   * The constructor initializes the InputStream and OutputStream.
   *
   * @param in  The InputStream input.
   * @param out The Appendable output.
   */
  public PortfolioViewImpl(InputStream in, Appendable out) {
    this.out = out;
    scanner = new Scanner(in);
  }

  /**
   * The method displays the operations that can be performed after creating the portfolio.
   * It doesn't implement the operations.
   *
   * @throws IOException If an I/O error occurs.
   */
  @Override
  public void stockMenuOptions() throws IOException {
    this.out.append("1. View all stocks.").append("\n")
            .append("2. Add a particular stock.").append("\n")
            .append("3. Save the portfolio.").append("\n")
            .append("4. Back to main menu.").append("\n")
            .append("Choose an option: ");
  }

  /**
   * The method displays the operations that the user is given when loading the application.
   * It doesn't implement the operations.
   *
   * @throws IOException If an I/0 error occurs.
   */
  @Override
  public void portfolioMenuOptions() throws IOException {
    this.out.append("1. Create a portfolio.").append("\n")
            .append("2. View a portfolio.").append("\n")
            .append("3. Import your file.").append("\n")
            .append("4. Stock Trend Analysis.").append("\n")
            .append("5. List all portfolios.").append("\n")
            .append("6. Exit.").append("\n")
            .append("Choose an option: ");
  }

  /**
   * The method calls the scanner to take an integer input value.
   *
   * @return the integer input value entered by the user.
   */
  @Override
  public int setInputValue() {
    return scanner.nextInt();
  }

  /**
   * The methods move to the next line.
   */
  @Override
  public void setNextLineMessage() {
    scanner.nextLine();
  }

  /**
   * The method calls the scanner to take a string input value.
   *
   * @return the string input value entered by the user.
   */
  @Override
  public String setInputString() {
    return scanner.next();
  }

  /**
   * The method displays the message given.
   *
   * @param inputMessage the message to be displayed.
   * @throws IOException If an I/O error occurs.
   */
  @Override
  public void setInputMessage(String inputMessage) throws IOException {
    this.out.append(inputMessage).append("\n");
  }

  /**
   * The method displays the error message given.
   *
   * @param errorMessage the error message to be displayed.
   * @throws IOException If an I/O error occurs.
   */
  @Override
  public void setErrorMessage(String errorMessage) throws IOException {
    this.out.append(CommandLineEnum.ERROR.getStyle()).append(errorMessage)
            .append("\n").append(CommandLineEnum.RESET.getStyle());
  }


}
