package step;

import view.ProductsView;

import org.openqa.selenium.TimeoutException;

public class ProductsStep {

    ProductsView productsView = new ProductsView();

    public boolean isAppOpened() {
        try {
            productsView.waitForApp();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void tapDesiredProduct(String productName) {
        productsView.tapDesiredProduct(productName);
    }
}
