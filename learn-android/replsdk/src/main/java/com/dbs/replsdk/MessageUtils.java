package com.dbs.replsdk;

import com.dbs.replsdk.persistence.Message;

public class MessageUtils {

    public static final String messageContent = "{\"type\": \"TEXT\",\"payload\": {\"text\": \"dbdn\"}}";
    public static final String card = "{\"type\": \"CARD\",\n" +
            "\"payload\": {\n" +
            "\t\"title\": \"Spending Patterns\",\n" +
            "\t\"subtitle\": \"Ask me for your transactions by acco\",\n" +
            "\t\"buttons\": [{\n" +
            "\t\t\"label\": \"LEARN MORE\",\n" +
            "\t\t\"type\": \"HYPERLINK\",\n" +
            "\t\t\"payload\": \"http://www.xbank.com/learn_sp\"\n" +
            "\t}],\n" +
            "\t\"medium\": {\n" +
            "\t\t\"medium_url\": \"spend_ptn.jpg\"\n" +
            "\t}\n" +
            "}}";


    public static final String carousel = "{\n" +
            "        \t\"type\": \"CONTAINER\",\n" +
            "        \t\"payload\": {\n" +
            "        \t\t\"mode\": \"LIST\",\n" +
            "        \t\t\"contents\": [{\n" +
            "        \t\t\t\t\"type\": \"CARD\",\n" +
            "        \t\t\t\t\"payload\": {\n" +
            "        \t\t\t\t\t\"title\": \"How do I open an account with DBS?\",\n" +
            "        \t\t\t\t\t\"buttons\": [{\n" +
            "        \t\t\t\t\t\t\"payload\": \"How do I open an account with DBS?\",\n" +
            "        \t\t\t\t\t\t\"label\": \"Select\",\n" +
            "        \t\t\t\t\t\t\"type\": \"POSTBACK\"\n" +
            "        \t\t\t\t\t}]\n" +
            "        \t\t\t\t}\n" +
            "        \t\t\t},\n" +
            "        \t\t\t{\n" +
            "        \t\t\t\t\"type\": \"CARD\",\n" +
            "        \t\t\t\t\"payload\": {\n" +
            "        \t\t\t\t\t\"title\": \"What documents are required for account opening?\",\n" +
            "        \t\t\t\t\t\"buttons\": [{\n" +
            "        \t\t\t\t\t\t\"payload\": \"What documents are required for account opening?\",\n" +
            "        \t\t\t\t\t\t\"label\": \"Select\",\n" +
            "        \t\t\t\t\t\t\"type\": \"POSTBACK\"\n" +
            "        \t\t\t\t\t}]\n" +
            "        \t\t\t\t}\n" +
            "        \t\t\t},\n" +
            "        \t\t\t{\n" +
            "        \t\t\t\t\"type\": \"CARD\",\n" +
            "        \t\t\t\t\"payload\": {\n" +
            "        \t\t\t\t\t\"title\": \"Tell me more about your credit cards\",\n" +
            "        \t\t\t\t\t\"buttons\": [{\n" +
            "        \t\t\t\t\t\t\"payload\": \"Tell me more about your credit cards\",\n" +
            "        \t\t\t\t\t\t\"label\": \"Select\",\n" +
            "        \t\t\t\t\t\t\"type\": \"POSTBACK\"\n" +
            "        \t\t\t\t\t}]\n" +
            "        \t\t\t\t}\n" +
            "        \t\t\t},\n" +
            "        \t\t\t{\n" +
            "        \t\t\t\t\"type\": \"CARD\",\n" +
            "        \t\t\t\t\"payload\": {\n" +
            "        \t\t\t\t\t\"title\": \"Fee Waiver for my Card / Cashline\",\n" +
            "        \t\t\t\t\t\"buttons\": [{\n" +
            "        \t\t\t\t\t\t\"payload\": \"Fee Waiver for my Card / Cashline\",\n" +
            "        \t\t\t\t\t\t\"label\": \"Select\",\n" +
            "        \t\t\t\t\t\t\"type\": \"POSTBACK\"\n" +
            "        \t\t\t\t\t}]\n" +
            "        \t\t\t\t}\n" +
            "        \t\t\t},\n" +
            "        \t\t\t{\n" +
            "        \t\t\t\t\"type\": \"CARD\",\n" +
            "        \t\t\t\t\"payload\": {\n" +
            "        \t\t\t\t\t\"title\": \"I have some transactions in my account that I do not recognize.\",\n" +
            "        \t\t\t\t\t\"buttons\": [{\n" +
            "        \t\t\t\t\t\t\"payload\": \"I have some transactions in my account that I do not recognize.\",\n" +
            "        \t\t\t\t\t\t\"label\": \"Select\",\n" +
            "        \t\t\t\t\t\t\"type\": \"POSTBACK\"\n" +
            "        \t\t\t\t\t}]\n" +
            "        \t\t\t\t}\n" +
            "        \t\t\t}\n" +
            "        \t\t]\n" +
            "        \t}\n" +
            "        }";


    public static final String test = "[\n" +
            "  {\n" +
            "    \"type\": \"TEXT\",\n" +
            "    \"payload\": {\n" +
            "      \"text\": \"Hi, I'm digibank Virtual Assistant. I make banking easy. Ask me about your accounts, cards, or transactions or to pay your credit card. If ever you're stuck, type 'start over'. To see examples of what I can do at any time, type 'get started'.\"\n" +
            "    }\n" +
            "  }\n" +
            "]";

    public static final String array = "[{\n" +
            "\t\t\"type\": \"TEXT\",\n" +
            "\t\t\"payload\": {\n" +
            "\t\t\t\"text\": \"dbdn\"\n" +
            "\t\t}\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"type\": \"CARD\",\n" +
            "\t\t\"payload\": {\n" +
            "\t\t\t\"title\": \"Spending Patterns\",\n" +
            "\t\t\t\"subtitle\": \"Ask me for your transactions by acco\",\n" +
            "\t\t\t\"buttons\": [{\n" +
            "\t\t\t\t\"label\": \"LEARN MORE\",\n" +
            "\t\t\t\t\"type\": \"HYPERLINK\",\n" +
            "\t\t\t\t\"payload\": \"http://www.xbank.com/learn_sp\"\n" +
            "\t\t\t}],\n" +
            "\t\t\t\"medium\": {\n" +
            "\t\t\t\t\"medium_url\": \"spend_ptn.jpg\"\n" +
            "\t\t\t}\n" +
            "\t\t}\n" +
            "\t}\n" +
            "]";

    public static Message generate() {
        Message message = new Message();
        message.setMe(true);
        message.setQuickReplies("");
        message.setAlternativeQuestions("");
        message.setCurrentIntent("");
        message.setMessageContents(array);
        return message;
    }
}
