package glue;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import org.junit.Assert;
import step.SingleProductStep;
import step.ProductsStep;
import view.CartView;
import view.SingleProductView;
import view.ProductsView;

public class AddProductStepDefinition {

    ProductsView productsView = new ProductsView();
    ProductsStep productsStep = new ProductsStep();
    SingleProductView singleProductView = new SingleProductView();
    SingleProductStep singleProductStep = new SingleProductStep();
    CartView cartView = new CartView();

    private int initialQuantity = 0;
    private int unitsAdded;
    private String productName;

    @Given("estoy en la aplicación de SauceLabs")
    public void estoy_en_la_aplicacion_de_SauceLabs() {
        // Validamos que haya cargado el app correctamente validando la existencia del botón del carrito
        Assert.assertTrue("LA APP NO SE ENCUENTRA ABIERTA",
                this.productsStep.isAppOpened());
    }

    @And("valido que carguen correctamente los productos en la galeria")
    public void valido_que_carguen_correctamente_los_productos_en_la_galeria() {
        // Validamos que se carguen correctamente los productos
        Assert.assertTrue("LA LISTA DE PRODUCTOS NO SE ENCUENTRA CARGADA",
                this.productsView.isProductListOnScreen());
    }

    @When("agrego {int} del siguiente producto {string}")
    public void agrego_unidades_del_siguiente_producto(int units, String productName) {
        // Seteamos las variables para tener acceso en otras funciones
        this.unitsAdded = units;
        this.productName = productName;

        // Hacemos clic sobre el producto esperado
        this.productsStep.tapDesiredProduct(this.productName);

        // Esperamos a que cargue la página del producto
        this.singleProductView.waitForPage();

        // Asignamos como valor iniciar a la cantidad de productos en el carrito actual
        this.initialQuantity = this.singleProductView.getProductQuantity();

        // Hacemos clic en el botón de "+" la cantidad de veces esperada
        this.singleProductStep.addUnits(units);

        // Hacemos clic en el botón de añadir al carrito
        this.singleProductStep.tapAddToCartButton();
    }

    @Then("valido el carrito de compra actualice correctamente")
    public void valido_el_carrito_de_compra_actualice_correctamente() throws Exception {
        // Validamos que en la pestaña del producto se hayan añadido al carrito la cantidad esperada
        Assert.assertEquals("La cantidad de productos no coincide con la que debería", this.initialQuantity + this.unitsAdded, this.singleProductView.getProductQuantity());

        // Vamos al carrito de compras
        this.singleProductStep.tapCartButton();

        // Esperamos a que la página del carrito cargue correctamente
        this.cartView.waitForPage();

        // Validamos que el número de unidades sea el mismo que el esperado y que el producto también sea el mismo que el que se añadió
        Assert.assertEquals("La cantidad de productos no coincide con la que debería", this.initialQuantity + this.unitsAdded, this.cartView.getProductQuantity());
        Assert.assertEquals("El nombre del producto que se tiene en el carrito no coincide con el producto que se añadió", this.productName, this.cartView.getProductName());
    }
}
