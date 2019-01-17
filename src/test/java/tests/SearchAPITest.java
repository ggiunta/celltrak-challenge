package tests;

import org.junit.Test;
import io.restassured.RestAssured;
import static org.hamcrest.Matchers.*;

public class SearchAPITest {
    private String endpoint = "https://api.github.com/search/repositories";
    
    @Test public void
    search_by_author() {
        RestAssured.given().
                param("q","user:ggiunta").
            when().
                get(endpoint).
            then().
                statusCode(200).body("total_count", equalTo(9));
    }
    
    @Test public void
    search_by_repository_name() {
        RestAssured.given().
                param("q","celltrak-challenge in:name user:ggiunta").
            when().
                get(endpoint).
            then().
                statusCode(200).body("total_count", equalTo(1));
    }
    
    @Test public void
    search_by_language() {
        RestAssured.given().
                param("q","language:javascript user:ggiunta").
            when().
                get(endpoint).
            then().
                statusCode(200).body("total_count", equalTo(3));
    }
    
    
    @Test public void
    pagination() {
        RestAssured.given().
                param("q","tetris").param("per_page", "100").
            when().
                get(endpoint).
            then().
                statusCode(200).body("items", hasSize(100));
    }
    
    @Test public void
    sorting() {
        RestAssured.given().
                param("q","user:ggiunta").param("sort", "updated").param("order", "desc").
            when().
                get(endpoint).
            then().
                statusCode(200).body("items[0].name", equalTo("celltrak-challenge"));
    }
}