package com.OOP.plmares.views.DataGenerators;

public class GenerateIrregularSched {
    public static void main(String[] args) {
        CS_IrregScheduleGenerator cs = new CS_IrregScheduleGenerator();
        IT_IrregScheduleGenerator it = new IT_IrregScheduleGenerator();

        for (int year = 1; year <= 4; year++) {
            System.out.println("\n-- --------------------------------- BSCS IRREG YEAR LEVEL " + year + "---------------------------------");
            System.out.println("\n-- --------------------------------- BLOCK 1 ---------------------------------");
            cs.csIrregGenerateSched(2023, 2024, year, 1, 61, 63);
        }

        for (int year = 1; year <= 4; year++) {
            System.out.println("\n-- --------------------------------- BSCS IRREG YEAR LEVEL " + year + "---------------------------------");
            System.out.println("\n-- --------------------------------- BLOCK 2 ---------------------------------");
            cs.csIrregGenerateSched(2023, 2024, year, 2, 64, 65);
        }

        for (int year = 1; year <= 4; year++) {
            System.out.println("\n-- --------------------------------- BSIT IRREG YEAR LEVEL " + year + "---------------------------------");
            System.out.println("\n-- --------------------------------- BLOCK 1 ---------------------------------");
            it.itIrregGenerateSched(2023, 2024, year, 1, 66, 68);
        }

        for (int year = 1; year <= 4; year++) {
            System.out.println("\n-- --------------------------------- BSIT IRREG YEAR LEVEL " + year + "---------------------------------");
            System.out.println("\n-- --------------------------------- BLOCK 2 ---------------------------------");
            it.itIrregGenerateSched(2023, 2024, year, 2, 69, 70);
        }

    }
}
