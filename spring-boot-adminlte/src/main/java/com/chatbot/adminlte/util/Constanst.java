package com.chatbot.adminlte.util;

public interface Constanst {
    public final static String PREFIX_LINK_UPLOAD = "/link/";
    enum STATUS {
        ACTIVE(1, "Đang hoạt động"),
        DELETE(-1, "Đã xóa"),
        INACTIVE(2, "Không hoạt động");

        private final int code;
        private final String description;

        public int getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public String toString() {
            return "code=" + code + ", description=" + description;
        }

        STATUS(int value, String description) {
            this.code = value;
            this.description = description;
        }
    }

    enum RESPONSE {

        SUCCESS("00","Thanh cong"),
        FAIL("01","That bai");
        private final String code;
        private final String description;

        public String getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public String toString() {
            return "code=" + code + ", description=" + description;
        }

        RESPONSE(String value, String description) {
            this.code = value;
            this.description = description;
        }
    }
}
