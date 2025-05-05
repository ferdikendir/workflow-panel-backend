package com.ferdi.workflow_panel_backend.constant;

public class ResultMessage {

    public static final ResultMessageBase createdSuccess = new ResultMessageBase(
            "Created Success",
            2001
    );

    public final static  ResultMessageBase  sameEmailRegistered = new ResultMessageBase(
            "Same email address already registered.",
            1001
    );

    public final static  ResultMessageBase  userNotFound = new ResultMessageBase(
            "User not found",
            1002
    );

    public final static  ResultMessageBase  loginSuccess = new ResultMessageBase(
            "Login Success",
            1003
    );

    public static final ResultMessageBase none = new ResultMessageBase(
            null,
            0
    );


    public static class ResultMessageBase {

        public String message;

        public int code;

        public  ResultMessageBase (String message, int code) {
            this.message = message;
            this.code = code;
        }
    }

}