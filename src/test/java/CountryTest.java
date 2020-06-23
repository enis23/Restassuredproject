import io.restassured.http.ContentType;
import model.City;
import model.Country;
import org.testng.annotations.Test;
import utility.BaseTest;

import static io.restassured.RestAssured.given;

public class CountryTest extends BaseTest {

    private String CountryId;

    @Test
    public void getBasePath() {
        given()
                .when()
                .log().body()
                .get()
                .then()
                .log().body()
                .statusCode( 200 )
        ;
    }

    @Test
    public void getCountries() {
        given()
                .cookies( cookies )
                .when()
                .log().body()
                .get( "/school-service/api/countries" )
                .then()
                .log().body()
                .statusCode( 200 )
        ;
    }

    @Test
    public void CreateCountry() {
        Country country = new Country();
        country.setName(name);
        country.setCode(code);

        CountryId = given()
                .cookies(cookies)
                .body(country)
                .contentType(ContentType.JSON)
                .when()
                .post("/school-service/api/countries")
                .then()
                .statusCode(201)
                .log().body()
                .extract().jsonPath().getString("id");
//        given()
//                .cookies(cookies)
//                .contentType(ContentType.JSON)
//                .when()
//                .delete("/school-service/api/countries/" + countryId)
//                .then()
//                .statusCode(200)
//        ;

//        // deleting country
//        given()
//                .cookies( cookies )
//                .when()
//                .log().body()
//                .delete( "/school-service/api/countries/" + countryId )
//                .then()
//                .log().body()
//                .statusCode( 200 )
//        ;
    }

    @Test (dependsOnMethods = {"CreateCountry"})
    private void CreateCity(){
        City city = new City();
        city.setName(name);

        Country country = new Country();
        country.setId( CountryId );
        city.setCountry( country );

        given()
                .cookies(cookies)
                .body(city)
                .log().body()
                .contentType(ContentType.JSON)
                .when()
                .log().body()
                .post("/school-service/api/cities")
                .then()
                .log().body()
                .statusCode(201);
//                .extract().jsonPath().getString("id");
    }










}