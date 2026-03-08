package step;

import io.appium.java_client.AppiumBy;
import driver.MobileDriverManager;
import view.SingleProductView;

public class SingleProductStep {

    SingleProductView singleProductView = new SingleProductView();

    public void tapAddToCartButton() {
        singleProductView.tapAddToCart();
    }

    public void addUnits(int units) {
        if (units <= 0) {
            throw new IllegalArgumentException("La cantidad de unidades debe ser mayor a 0. Valor recibido: " + units);
        }
        for (int i = 0; i < units - 1; i++) {
            singleProductView.tapIncreaseProduct();
        }
    }

    public void tapCartButton() throws Exception {
        try {
            singleProductView.openCart();
        } catch (Exception e) {
            throw new Exception("Falló la aplicación al abrir el carrito: " + e);
        }
    }
}
