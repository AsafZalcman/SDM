package utils;

import com.google.gson.Gson;

public class ResponseUtils {

    public static class JsonResponse {
        int status;
        String errorMessage;

        public JsonResponse(int status, String message) {
            this.status = status;
            this.errorMessage = message;
        }

        public int getStatus() {
            return this.status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getErrorMessage() {
            return this.errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }
    }

    public static String getJsonResponseString(int status, String message)
    {
        return new Gson().toJson(new JsonResponse(status,message));
    }
}
