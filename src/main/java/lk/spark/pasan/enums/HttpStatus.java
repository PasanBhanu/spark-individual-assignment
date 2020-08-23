package lk.spark.pasan.enums;

public enum HttpStatus {
    SUCCESS("success"),

    ERROR("error");


    private String status;

    HttpStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
