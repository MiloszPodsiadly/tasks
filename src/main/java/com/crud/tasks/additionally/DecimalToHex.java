package com.crud.tasks.additionally;

public class DecimalToHex {

    public static String decimalToHex(int number) {
        return Integer.toHexString(number).toUpperCase();
    }

    public static void main(String[] args) {
        int decimal = 255;
        String hex = decimalToHex(decimal);

        System.out.println("Decimal: " + decimal);
        System.out.println("Hex: " + hex);
    }
}

