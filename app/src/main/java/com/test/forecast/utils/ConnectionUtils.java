package com.test.forecast.utils;

import java.net.UnknownHostException;

public class ConnectionUtils {

    public static boolean noInternetAvailable(Throwable throwable) {
        return throwable instanceof UnknownHostException;
    }

}
