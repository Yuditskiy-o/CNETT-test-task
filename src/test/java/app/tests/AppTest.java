package app.tests;

import app.entity.Detective;
import app.specifications.Specification;
import io.qameta.allure.Step;
import org.junit.jupiter.api.*;

import java.util.List;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class AppTest {
    public static String URL = "https://run.mocky.io/v3/ebc9d9ef-a781-4914-9539-87223d8d7dc8";

    @BeforeEach
    void init() {
        Specification.installSpecification(Specification.requestSpecification(), Specification.responseSpecOK200());
    }

    @Nested
    @Tag("PositiveTest")
    class PositiveTests {

        @Test
        @DisplayName("Проверка, что если в массиве детективов есть элемент с firstName = Sherlock, то поле success " +
                "в JSON-файле установлено в true")
        void testCheckIfDetectiveNamedSherlockExists() {
            step("Проверка, что детектива из 1-го массива зовут Шерлок", () ->
                    assertEquals("Sherlock", setUpDetectives().get(0).getFirstName(), "Имя отличается!"));

            step("Проверка, что если детектива зовут Шерлок, то значение success — true", () -> {
                boolean success = false;
                for (Detective detectiveElement : setUpDetectives()) {
                    String firstName = detectiveElement.getFirstName();
                    if (firstName.equals("Sherlock")) {
                        success = true;
                        break;
                    }
                }
                assertTrue(success, "Значение ключа success не true!");
            });
        }

        @Test
        @DisplayName("Проверка количества элементов в массиве детективов")
        public void testCheckIfDetectivesArrayNotEmpty() {
            step("Проверка, что в массиве детективов объектов больше 0", () ->
                    assertTrue(setUpDetectives().size() > 0, "Массив детективов не должен быть пуст " +
                            "и должен иметь минимум 1 элемент"));
        }

        @Test
        @DisplayName("Проверка соответствия количества детективов определенному диапазону")
        public void testDetectivesCountInRange() {
            step("Проверка, что количество детективов должно быть в диапазоне от 1 до 3", () -> {
                int detectivesCount = setUpDetectives().size();
                assertTrue(detectivesCount >= 1 && detectivesCount <= 3, "Количество детективов должно быть " +
                        "в диапазоне от 1 до 3");
            });
        }

        @Test
        @DisplayName("Проверка, что для CategoryID = 1 массив extraArray в JSON-файле не пустой")
        public void testCheckIfExtraArrayNotEmptyForCategory1() {
            step("Проверка, что массив extraArray не пуст для CategoryID = 1", () -> {
                int categoryID = setUpDetectives().get(0).getCategories().get(0).getCategoryID();
                int extraArray = setUpDetectives().get(0).getCategories().get(0).getExtra().getExtraArray().size();

                if (categoryID == 1)
                    Assertions.assertEquals(2, extraArray, "Массив extraArray пуст!");
            });
        }

        @Test
        @DisplayName("Проверка, что для CategoryID = 1 поле extra в JSON-файле не равно null")
        public void testCheckIfExtraNotNullForCategory1() {
            step("Проверка, массив extra не должен быть null, если CategoryID = 1", () -> {
                int categoryID = setUpDetectives().get(0).getCategories().get(0).getCategoryID();
                Object extra = setUpDetectives().get(0).getCategories().get(0).getExtra();

                if (categoryID == 1) {
                    assertNotNull(extra, "Массив extra не должен быть null, если CategoryID = 1");
                }
            });
        }

        @Test
        @DisplayName("Проверка, что для CategoryID = 2 массив extraArray в JSON-файле пустой (null)")
        public void testCheckIfExtraArrayIsNullForCategory2() {
            step("Проверка, что массив extraArray null для CategoryID = 2", () -> {
                int categoryID = setUpDetectives().get(1).getCategories().get(0).getCategoryID();
                Object extra = setUpDetectives().get(1).getCategories().get(0).getExtra();

                if (categoryID == 2)
                    assertNull(extra, "Массив extraArray не null!");
            });
        }


        @Test
        @DisplayName("Проверка, что значение поля mainId в JSON-файле находится в допустимом диапазоне от 0 до 10")
        public void testCheckIfMainIdInRange() {
            step("Проверка, что MainId вне допустимого диапазона", () -> {
                int idOne = setUpDetectives().get(0).getMainId();
                int idTwo = setUpDetectives().get(1).getMainId();

                assertTrue(idOne >= 0 && idTwo <= 10, "MainId вне допустимого диапазона");
            });
        }

        @Test
        @DisplayName("Проверка, что значение поля categoryId в JSON-файле является допустимым (1 или 2)")
        public void testCheckIfCategoryIdGotValidValue() {
            step("Проверка, что CategoryID должен иметь значения 1 или 2", () -> {
                int categoryIDOne = setUpDetectives().get(0).getCategories().get(0).getCategoryID();
                int categoryIDTwo = setUpDetectives().get(1).getCategories().get(0).getCategoryID();

                assertTrue(categoryIDOne == 1 || categoryIDTwo == 2, "CategoryID должен иметь значения 1 или 2");
            });
        }
    }

    @Nested
    @Tag("NegativeTest")
    class NegativeTests {

        @Test
        @DisplayName("Проверка полученного ответа зная, что 1-го детектива зовут Шерлок")
        void testCheckIfDetectiveNamedSherlockNotExists() {
            assertFalse(setUpDetectives().get(0).getFirstName().contains("James"), "В JSON появился 3-й массив детективов");
        }

        @Test
        @DisplayName("Проверка соответствия CategoryID для первого детектива")
        public void testExtraArrayNotEmptyForCategory2() {
            assertNotEquals(4, setUpDetectives().get(0).getCategories().get(0).getCategoryID(), "CategoryID ");
        }

    }

    @Step("Получение и обработка тестовых данных из JSON")
    private List<Detective> setUpDetectives() {
        return given()
                .when()
                .get(URL)
                .then().log().all()
                .extract().body().jsonPath().getList("detectives", Detective.class);
    }
}



