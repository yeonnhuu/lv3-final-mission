package finalmission.helper;

import finalmission.dto.request.MemberLoginRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AuthExtractor {

    public static String extractMemberLoginToken(MemberLoginRequest request) {
        ExtractableResponse<Response> response = sendMemberLoginRequest(request);
        String token = response.cookie("token");

        if (token == null) {
            throw new IllegalStateException("로그인 응답에 토큰이 없습니다.");
        }
        return token;
    }

    private static ExtractableResponse<Response> sendMemberLoginRequest(MemberLoginRequest request) {
        return RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/auth/login")
                .then().statusCode(200)
                .extract();
    }
}
