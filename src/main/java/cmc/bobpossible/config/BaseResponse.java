package cmc.bobpossible.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.LinkedList;

import static cmc.bobpossible.config.BaseResponseStatus.SUCCESS;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message","errorMessage", "result"})
public class BaseResponse<T> {
//    @JsonProperty("isSuccess")
//    private final Boolean isSuccess;
//    private final String message;
//    private final int code;
//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    private T result;

    @JsonProperty("isSuccess")
    private final Boolean isSuccess;
    private final int code;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;
    @ApiModelProperty(example = "메세지")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LinkedList<T> message;


    // validation 실패
    public BaseResponse(LinkedList<LinkedHashMap<String,String>> errorList){
        this.isSuccess = false;
        this.code = 2090;
        this.message = (LinkedList<T>) errorList;
    }

    // 요청에 실패한 경우
    public BaseResponse(BaseResponseStatus status) {
        LinkedList<String> list = new LinkedList();
        list.add(status.getMessage());
        this.isSuccess = status.isSuccess();
        this.message = (LinkedList<T>) list;
        this.code = status.getCode();
    }

    // 요청에 성공한 경우
    public BaseResponse(T result) {
        LinkedList<String> list = new LinkedList();
        list.add(SUCCESS.getMessage());
        this.isSuccess = SUCCESS.isSuccess();
        this.message = (LinkedList<T>) list;
        this.code = SUCCESS.getCode();
        this.result = result;
    }
}

