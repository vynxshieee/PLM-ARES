package com.OOP.plmares.views.DataGenerators;

public class GenerateRegularSched {
    public static void main(String[] args) {
        CS_RegScheduleGenerator cs = new CS_RegScheduleGenerator();
        IT_RegScheduleGenerator it = new IT_RegScheduleGenerator();

/*
        for (int year = 1; year <= 4; year++) {
            System.out.println("\n-- --------------------------------- BSCS YEAR LEVEL " + year + "---------------------------------");
            for (int block = 1; block <= 2; block++) {
                System.out.println("\n-- --------------------------------- BLOCK " + block + " ---------------------------------");
                cs.csGenerateSched(2023, 2024, year, block, 15);
            }
        }*/

        for (int year = 1; year <= 4; year++) {
            System.out.println("\n-- --------------------------------- BSIT YEAR LEVEL " + year + "---------------------------------");
            for (int block = 1; block <= 2; block++) {
                System.out.println("\n-- --------------------------------- BLOCK " + block + " ---------------------------------");
                it.itGenerateSched(2023, 2024, year, block, 15);
            }
        }
    }
}
