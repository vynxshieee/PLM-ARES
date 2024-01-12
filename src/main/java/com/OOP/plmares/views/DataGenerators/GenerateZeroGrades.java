package com.OOP.plmares.views.DataGenerators;

public class GenerateZeroGrades {
    public static void main(String[] args) {
        CS_ZeroGradeGenerator cs = new CS_ZeroGradeGenerator();
        IT_ZeroGradeGenerator it = new IT_ZeroGradeGenerator();

        int stdNoStart;
        int stdNoEnd;

        // generate CS ZERO GRADES      REGULAR
        for (int year = 1; year <= 4; year++) {
            System.out.println("\n-- --------------------------------- BSCS YEAR LEVEL " + year + "---------------------------------");
            for (int block = 1; block <= 2; block++) {

                if(block == 1){
                    stdNoStart = 1;
                    stdNoEnd = 15;
                } else {
                    stdNoStart = 16;
                    stdNoEnd = 30;
                }

                System.out.println("\n-- --------------------------------- BLOCK " + block + " ---------------------------------");
                cs.csGenerateGrades(2023, 2024, year, block, stdNoStart, stdNoEnd);
            }
        }

        // generate IT ZERO GRADES     REGULAR
        for (int year = 1; year <= 4; year++) {
            System.out.println("\n-- --------------------------------- BSIT YEAR LEVEL " + year + "---------------------------------");
            for (int block = 1; block <= 2; block++) {

                if(block == 1){
                    stdNoStart = 31;
                    stdNoEnd = 45;
                } else {
                    stdNoStart = 46;
                    stdNoEnd = 60;
                }

                System.out.println("\n-- --------------------------------- BLOCK " + block + " ---------------------------------");
                it.itGenerateGrades(2023, 2024, year, block, stdNoStart, stdNoEnd);
            }
        }

        // Generate zero grades for irregulars

        for (int year = 1; year <= 4; year++) {
            System.out.println("\n-- --------------------------------- BSCS IRREG YEAR LEVEL " + year + "---------------------------------");
            System.out.println("\n-- --------------------------------- BLOCK 1 ---------------------------------");
            cs.csGenerateGrades(2023, 2024, year, 1, 61, 63);
        }

        for (int year = 1; year <= 4; year++) {
            System.out.println("\n-- --------------------------------- BSCS IRREG YEAR LEVEL " + year + "---------------------------------");
            System.out.println("\n-- --------------------------------- BLOCK 2 ---------------------------------");
            cs.csGenerateGrades(2023, 2024, year, 2, 64, 65);
        }

        for (int year = 1; year <= 4; year++) {
            System.out.println("\n-- --------------------------------- BSIT IRREG YEAR LEVEL " + year + "---------------------------------");
            System.out.println("\n-- --------------------------------- BLOCK 1 ---------------------------------");
            it.itGenerateGrades(2023, 2024, year, 1, 66, 68);
        }

        for (int year = 1; year <= 4; year++) {
            System.out.println("\n-- --------------------------------- BSIT IRREG YEAR LEVEL " + year + "---------------------------------");
            System.out.println("\n-- --------------------------------- BLOCK 2 ---------------------------------");
            it.itGenerateGrades(2023, 2024, year, 2, 69, 70);
        }

    }
}
