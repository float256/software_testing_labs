import com.google.gson.Gson;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class TestProductApi {
    public final String BASE_URL = "http://91.210.252.240:9010";
    public final String GET_ALL_PRODUCTS_ENDPOINT_URL = BASE_URL + "/api/products";
    public final String DELETE_PRODUCT_ENDPOINT_URL = BASE_URL + "/api/deleteproduct?id=";
    public final String ADD_PRODUCT_ENDPOINT_URL = BASE_URL + "/api/addproduct";
    public final String EDIT_PRODUCT_ENDPOINT_URL = BASE_URL + "/api/editproduct";
    public final String RESOURCES_FOLDER = "./src/test/resources";
    public final String VALID_PRODUCT_FOLDER = RESOURCES_FOLDER + "/valid";
    public final String INVALID_PRODUCT_FOLDER = RESOURCES_FOLDER + "/invalid";
    public final String INVALID_THROWABLE_PRODUCT_FOLDER = INVALID_PRODUCT_FOLDER + "/throwable";
    public final String INVALID_AUTOFIXED_PRODUCT_FOLDER = INVALID_PRODUCT_FOLDER + "/autofixed";
    public final String UPDATE_TEST_PRODUCT = VALID_PRODUCT_FOLDER + "/updateTestingValidProduct.json";
    public final String ALIAS_TESTING_PRODUCT = VALID_PRODUCT_FOLDER + "/aliasTestingValidProduct.json";

    private final List<Product> needsToBeDeleted = new ArrayList<>();
    private OkHttpClient client;

    @BeforeMethod
    public void beforeEach() {
        client = new OkHttpClient();
    }

    @Test(description = "Receiving all products and checking that they are all in the correct format", priority = 1)
    public void testGetAll() {
        Request request = new Request.Builder()
                .url(GET_ALL_PRODUCTS_ENDPOINT_URL)
                .get()
                .build();

        wrapConnection(request, responseBody -> {
            Product[] products = new Gson().fromJson(responseBody, Product[].class);
            ProductFieldValidator.validate(products);
        });
    }

    @Test(
            description = "Test invalid auto fixed data throws exception due to validation",
            priority = 1,
            dataProvider = "invalidAutoFixedDataProvider"
    )
    public void testInvalidAutoFixed(String requestFilename) throws IOException {
        String fileContent = Files.readString(Path.of(requestFilename));
        Product product = new Gson().fromJson(fileContent, Product.class);
        Assert.assertThrows(() -> ProductFieldValidator.validate(product));
    }

    @Test(
            dataProvider = "addValidProductDataProvider",
            description = "Add valid product to database and check that it can be obtained from getAll response",
            priority = 1
    )
    public void testAddValidProduct(String requestFilename) throws IOException {
        System.out.println("Processed file is: " + requestFilename);

        String fileContent = Files.readString(Path.of(requestFilename));
        RequestBody requestBody = RequestBody.create(fileContent.getBytes());
        Request request = new Request.Builder()
                .url(ADD_PRODUCT_ENDPOINT_URL)
                .post(requestBody)
                .build();
        wrapConnection(request, responseBody -> {
            Gson gson = new Gson();
            ProductAddResponse deserializedResponse = gson.fromJson(responseBody, ProductAddResponse.class);
            Optional<Product> product = getById(deserializedResponse.getId());
            Assert.assertTrue(product.isPresent(), "Added value can't be found.");
            ProductFieldValidator.validate(product.get());

            this.needsToBeDeleted.add(product.get());
        });
    }

    @Test(
            description = "Test that alias is changed if there are value with this alias",
            priority = 1
    )
    public void testAliasSuffixAddition() throws IOException {
        String fileContent = Files.readString(Path.of(ALIAS_TESTING_PRODUCT));
        RequestBody requestBody = RequestBody.create(fileContent.getBytes());
        Request request = new Request.Builder()
                .url(ADD_PRODUCT_ENDPOINT_URL)
                .post(requestBody)
                .build();

        wrapConnection(request, firstResponseBody -> {
            Gson gson = new Gson();
            ProductAddResponse firstDeserializedResponse = gson.fromJson(firstResponseBody, ProductAddResponse.class);
            Optional<Product> firstAddedProduct = getById(firstDeserializedResponse.getId());
            firstAddedProduct.ifPresent(needsToBeDeleted::add);
            ProductFieldValidator.validate(firstAddedProduct.get());

            wrapConnection(request, secondResponseBody -> {
                ProductAddResponse secondDeserializedResponse = gson.fromJson(secondResponseBody, ProductAddResponse.class);
                Optional<Product> secondAddedProduct = getById(secondDeserializedResponse.getId());
                secondAddedProduct.ifPresent(needsToBeDeleted::add);
                ProductFieldValidator.validate(secondAddedProduct.get());

                Assert.assertNotEquals(
                        getById(firstDeserializedResponse.getId()).get().getAlias(),
                        getById(secondDeserializedResponse.getId()).get().getAlias()
                );
            });
        });
    }

    @Test(
            description = "Test that product with incorrect field which can't be fixed automatically can't be saved",
            dataProvider = "invalidThrowableProductDataProvider",
            priority = 1
    )
    public void testInvalidThrowable(String requestFilename) throws IOException {
        System.out.println("Processed file is: " + requestFilename);

        String fileContent = Files.readString(Path.of(requestFilename));
        RequestBody requestBody = RequestBody.create(fileContent.getBytes());
        Request request = new Request.Builder()
                .url(ADD_PRODUCT_ENDPOINT_URL)
                .post(requestBody)
                .build();
        wrapConnection(request, responseBody -> {
            Gson gson = new Gson();
            ProductAddResponse deserializedResponse = gson.fromJson(responseBody, ProductAddResponse.class);
            Assert.assertTrue(getById(deserializedResponse.getId()).isEmpty(),
                    "Product with this id mustn't have been created.");
        });
    }

    @Test(
            description = "Test that after updating changed product can be receiving from getAll endpoint",
            priority = 2
    )
    public void testProductUpdating() throws IOException {
        Optional<Integer> updatedProductId = needsToBeDeleted.stream()
                .map(Product::getId)
                .findFirst();
        Gson gson = new Gson();

        if (updatedProductId.isPresent()) {
            Product updatedProduct = gson.fromJson(
                    Files.readString(Path.of(UPDATE_TEST_PRODUCT)),
                    Product.class
            );
            updatedProduct.setId(updatedProductId.get());

            RequestBody requestBody = RequestBody.create(gson.toJson(updatedProduct).getBytes());
            Request request = new Request.Builder()
                    .url(EDIT_PRODUCT_ENDPOINT_URL)
                    .post(requestBody)
                    .build();

            wrapConnection(request, responseBody ->
                    Assert.assertEquals(updatedProduct, getById(updatedProductId.get()).get()));
        }
    }

    @Test(description = "Test that deleted products can't be obtained from getAll response", priority = 3)
    public void testDeleteProduct() {
        System.out.println("Delete is called");
        List<Integer> productIdsForDeleting = needsToBeDeleted.stream()
                .distinct()
                .map(Product::getId)
                .collect(Collectors.toList());
        for (Integer id : productIdsForDeleting) {
            Request request = new Request.Builder()
                    .url(DELETE_PRODUCT_ENDPOINT_URL + id.toString())
                    .build();
            wrapConnection(request, (requestBody) -> {});
        }

        Set<Integer> notDeletedByError = getAll().stream().map(Product::getId).collect(Collectors.toSet());
        notDeletedByError.retainAll(productIdsForDeleting);

        Assert.assertTrue(notDeletedByError.isEmpty(), "Some values have not been removed");
    }

    @DataProvider
    private Object[][] addValidProductDataProvider() {
        return new Object[][]{
                {VALID_PRODUCT_FOLDER + "/firstAdditionTestingValid.json"},
                {VALID_PRODUCT_FOLDER + "/secondAdditionTestingValid.json"},
                {VALID_PRODUCT_FOLDER + "/thirdAdditionTestingValid.json"}
        };
    }

    @DataProvider
    private Object[][] invalidAutoFixedDataProvider() {
        return new Object[][]{
                {INVALID_AUTOFIXED_PRODUCT_FOLDER + "/invalidProductWithHitEquals3.json"},
                {INVALID_AUTOFIXED_PRODUCT_FOLDER + "/invalidProductWithHitEquals-1.json"},
                {INVALID_AUTOFIXED_PRODUCT_FOLDER + "/invalidProductWithStatusEquals2.json"},
                {INVALID_AUTOFIXED_PRODUCT_FOLDER + "/invalidProductWithStatusEquals-1.json"}
        };
    }

    @DataProvider
    private Object[][] invalidThrowableProductDataProvider() {
        return new Object[][]{
                {INVALID_THROWABLE_PRODUCT_FOLDER + "/invalidProductWithCategoryId0.json"},
                {INVALID_THROWABLE_PRODUCT_FOLDER + "/invalidProductWithCategoryId16.json"}
        };
    }

    private void wrapConnection(Request request, Consumer<String> func) {
        try (Response response = client.newCall(request).execute()) {
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                func.accept(responseBody.string());
            } else {
                throw new AssertionError("Body is empty");
            }
        } catch (IOException e) {
            throw new AssertionError("Can't connect to host");
        }
    }

    private Optional<Product> getById(int id) {
        return getAll().stream().filter(elem -> elem.getId() == id).findFirst();
    }

    private List<Product> getAll() {
        Request request = new Request.Builder()
                .url(GET_ALL_PRODUCTS_ENDPOINT_URL)
                .get()
                .build();
        AtomicReference<List<Product>> products = new AtomicReference<>();
        wrapConnection(request, responseBody ->
                products.set(Arrays.stream(new Gson().fromJson(responseBody, Product[].class))
                        .collect(Collectors.toList())));
        return products.get();
    }
}
