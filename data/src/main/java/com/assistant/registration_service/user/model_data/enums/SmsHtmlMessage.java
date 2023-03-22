package com.assistant.registration_service.user.model_data.enums;

public class SmsHtmlMessage {

    public static String sms(String code) {
        return "<html>\n" +
                "<head>\n" +
                "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                "  <style>\n" +
                "    body {\n" +
                "      font-family: \"Times New Roman\";\n" +
                "    }\n" +
                "  </style>\n" +
                "</head>\n" +
                "<body class=\"bg-light\">\n" +
                "<div class=\"container\">\n" +
                "  <div class=\"card my-10\">\n" +
                "    <div class=\"card-body\">\n" +
                "      <p  class=\"text-gray-700\" style=\"font-size:48px; color:#7B346E; text-align: center;\">Emmo Techie</p>\n" +
                "      <p  class=\"text-gray-700\" style=\"margin-top:25px; text-align:center; font-size:28px; color:#9D77E7;\">Код підтвердження</p>\n" +
                "      <div  class=\"space-y-2\" style=\"text-align:center\">\n" +
                "        <a href=\"https://www.freepik.com/free-vector/engineer-developer-with-laptop-tablet-code-cross-platform-development-cross-platform-operating-systems-software-environments-concept-bright-vibrant-violet-isolated-illustration_10780377.htm#from_view=detail_author\"><img src=\"https://img.freepik.com/free-vector/engineer-developer-with-laptop-tablet-code-cross-platform-development-cross-platform-operating-systems-software-environments-concept-bright-vibrant-violet-isolated-illustration_335657-312.jpg?w=996&t=st=1678460958~exp=1678461558~hmac=259d9af08ab2a4b0a4972733e8810c25de6cb3410504b93f5aee937358bfa401\" width=\"350\" height=\"240\" alt=\"picture2\"></a>\n" +
                "      </div>\n" +
                "      <div class=\"space-y-3\">\n" +
                "        <p class=\"text-gray-700\" style=\"text-align: justify; font-size:18px; color:#000000;\">Ваш код для перевірки дійсної електронної адреси: <strong><span style=\"color: #7B346E; font-size:18px;\">" + code + "</span></strong>.</p>\n" +
                "        <p class=\"text-gray-700\" style=\"text-align: justify; font-size:18px; color:#000000;\">Код дійсний 5 хвилин. Якщо, це не Ви відправляли - проігноруйте це повідомлення.</p>\n" +
                "        <p class=\"text-gray-700\" style=\"text-align: justify; font-size:18px; color:#000000;\">Якщо необхідна допомога звертайтеся за електронною адресою emmotechie@gmail.com</p>\n" +
                "      </div>\n" +
                "    </div>\n" +
                "  </div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";
    }
}
