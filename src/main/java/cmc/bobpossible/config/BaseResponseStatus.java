package cmc.bobpossible.config;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),

    /**
     * 2000 : Request 오류
     */

    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_REFRESH_TOKEN(false, 2002, "Refresh Token이 유효하지 않습니다."),
    CHECK_REFRESH_TOKEN(false, 2004, "토큰 정보가 불일치합니다"),
    INVALID_USER_JWT(false, 2003, "권한이 없는 유저의 접근입니다."),

    // users
    USERS_EMPTY_USER_ID(false, 2010, "유저 아이디 값을 확인해주세요."),
    DOUBLE_CHECK_ID(false, 2011, "유저 아이디가 이미 존재합니다."),
    MEMBER_LOGOUT(false, 2012, "로그아웃 된 사용자입니다."),
    CHECK_QUIT_USER(false, 2022, "이미 탈퇴한 사용자입니다."),

    // [POST] /users
    POST_USERS_EMPTY_EMAIL(false, 2015, "이메일을 입력해주세요."),
    POST_USERS_INVALID_EMAIL(false, 2016, "이메일 형식을 확인해주세요."),
    POST_USERS_EXISTS_EMAIL(false, 2017, "중복된 이메일입니다."),

    // favorites
    FAVORITES_ID_NOT_EXIST(false, 2101, "해당 선호 음식 종류 ID가 존재하지 않습니다."),

    // aws
    IMAGE_UPLOAD_FAIL(false, 2111, "이미지 업로드에 실패하였습니다."),

    //mission
    INVALID_MISSION_ID(false, 2121, "해당 missionId값과 일치하는 값이 없습니다."),
    NO_AVAILABLE_MISSION(false, 2122, "현재 배포가능한 미션이 없습니다."),

    // Category
    INVALID_CATEGORY_ID(false, 2301, "해당 categoryId값과 일치하는 값이 없습니다."),

    // store
    INVALID_STORE_ID(false, 2030, "해당 storeId값과 일치하는 값이 없습니다."),

    // Review
    INVALID_REVIEW_ID(false, 2040, "해당 reviewId값과 일치하는 값이 없습니다."),

    //Like
    POST_LIKE_EXISTS(false, 2050, "이미 찜이 되어있습니다."),
    DELETE_LIKE_EXISTS(false, 2050, "이미 찜이 삭제 되어있습니다."),

    //auth
    CHECK_INVALID_PHONE(false, 2060, "휴대폰 형식을 확인해주세요."),

    //searchKeyword
    CHECK_INVALID_SEARCH_KEYWORD(false,2070,"searchIdx에 해당하는 값이 없습니다"),
    CHECK_NULL_SEARCH_KEYWORD(false,2071,"키워드가 비어있습니다"),

    //question
    CHECK_INVALID_PRODUCT_ID(false,2080,"productIdx에 해당하는 값이 없습니다"),
    CHECK_INVALID_QUESTION_OR_PRODUCT_ID(false,2081,"questionIdx 또는 productIdx를 확인해 주세요"),


    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),

    // [POST] /users
    DUPLICATED_EMAIL(false, 3013, "중복된 이메일입니다."),
    FAILED_TO_LOGIN(false, 3014, "없는 아이디거나 비밀번호가 틀렸습니다."),

    //[POST] / coupons
    DUPLICATED_COUPON(false, 3020, "이미 발급된 쿠폰입니다."),


    FAILED_TO_GET_CART_IDX(false, 3030, "cartIdx가 존재하지 않습니다."),
    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),

    // [PATCH] /users/{userIdx}
    MODIFY_FAIL_USERNAME(false, 4014, "유저정보 수정 실패"),
    MODIFY_FAIL_DELIVERYINFO(false, 4015, "배송지 수정 실패"),

    PASSWORD_ENCRYPTION_ERROR(false, 4011, "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, 4012, "비밀번호 복호화에 실패하였습니다."),

    // Order
    DELETION_FAIL_ORDER(false, 4020, "주문취소 실패"),

    DELETION_FAIL_Like(false,4030, "찜취소 실패");

    // 5000 : 필요시 만들어서 쓰세요
    // 6000 : 필요시 만들어서 쓰세요

    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
