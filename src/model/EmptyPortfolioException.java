package model;

/**
 * The class extends the IllegalStateException class and is thrown whenever an
 * operation is performed without the portfolio being initialized.
 */
public class EmptyPortfolioException extends IllegalStateException {
  /**
   * The constructor adds the custom message to the super class.
   */
  public EmptyPortfolioException() {
    super("Create a portfolio inorder to perform any operation");
  }
}
