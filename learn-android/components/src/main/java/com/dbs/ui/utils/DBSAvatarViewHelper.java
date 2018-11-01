package com.dbs.ui.utils;

public class DBSAvatarViewHelper {

    private DBSAvatarViewHelper() {
    }


    public static char[] getChars(String name) {
        if (name == null) return new char[0];
        String[] splits = name.trim().split("(?<=[\\S])[\\S]*\\s*");
        if (splits.length > 0) {
            if (splits[0].length() == 0) return new char[0];
            char[] chars = new char[2];
            chars[0] = Character.toUpperCase(splits[0].charAt(0));
            if (splits.length > 1 && splits[splits.length - 1].length() > 0) {
                chars[1] = Character.toUpperCase(splits[splits.length - 1].charAt(0));
            }
            return chars;
        }
        return new char[0];
    }

    private static int getDigitColorInt(char c) {
        switch (c) {
            case '0':
                return 0xffFF88BF;
            case '1':
                return 0xffAFB1FF;
            case '2':
                return 0xff5AC1FF;
            case '3':
                return 0xffFF9A61;
            case '4':
                return 0xffFF8384;
            case '5':
                return 0xffFF7E82;
            case '6':
                return 0xffBBAAFF;
            case '7':
                return 0xff7DBDFF;
            case '8':
                return 0xffFFA964;
            case '9':
                return 0xffFF918A;
            default:
                return 0xFF000000;
        }
    }

    private static int getLetterColorInt(char c) {
        switch (c) {
            case 'A':
                return 0xffFF9DA2;
            case 'B':
                return 0xffFFA480;
            case 'C':
                return 0xffFFC72B;
            case 'D':
                return 0xffA8B5FF;
            case 'E':
                return 0xffFF92DD;
            case 'F':
                return 0xffFF9CA2;
            case 'G':
                return 0xffFFA289;
            case 'H':
                return 0xffFFB23D;
            case 'I':
                return 0xffA3B8FF;
            case 'J':
                return 0xffF085FF;
            case 'K':
                return 0xffFF8F9B;
            case 'L':
                return 0xffFF9482;
            case 'M':
                return 0xffFFC471;
            case 'N':
                return 0xff97BEFF;
            case 'O':
                return 0xffD0A2FF;
            case 'P':
                return 0xffFF8DA2;
            case 'Q':
                return 0xffFF918A;
            case 'R':
                return 0xffFFA964;
            case 'S':
                return 0xff7DBDFF;
            case 'T':
                return 0xffBBAAFF;
            case 'U':
                return 0xffFF7EA2;
            case 'V':
                return 0xffFF8384;
            case 'W':
                return 0xffFF9A61;
            case 'X':
                return 0xff5AC1FF;
            case 'Y':
                return 0xffAFB1FF;
            case 'Z':
                return 0xffFF88BF;
            default:
                return 0xff000000;
        }
    }

    /**
     * should be changed.
     */
    public static int get(char c) {

        if (Character.isLetter(c)) return getLetterColorInt(c);
        if (Character.isDigit(c)) return getDigitColorInt(c);
        return 0xff000000;


    }
}
