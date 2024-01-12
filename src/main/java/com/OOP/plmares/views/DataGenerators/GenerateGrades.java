package com.OOP.plmares.views.DataGenerators;

public class GenerateGrades {
    public static void main(String[] args) {

        int stdNoStart, stdNoEnd;
        CS_GradeGenerator cs = new CS_GradeGenerator();
        IT_GradeGenerator it = new IT_GradeGenerator();

        /* REGULARS */

        /*
        for (int year = 2; year <= 4; year++) {
            System.out.println("\n-- --------------------------------- BSCS YEAR LEVEL " + year + "---------------------------------");
            for (int block = 1; block <= 2; block++) {
                System.out.println("\n-- --------------------------------- BLOCK " + block + " ---------------------------------");

                if(block == 1){
                    stdNoStart = 1;
                    stdNoEnd = 15;
                } else {
                    stdNoStart = 16;
                    stdNoEnd = 30;
                }

                cs.csGenerateGrades(2023, 2024, year, block, stdNoStart, stdNoEnd);
            }
        }

        for (int year = 2; year <= 4; year++) {
            System.out.println("\n-- --------------------------------- BSIT YEAR LEVEL " + year + "---------------------------------");
            for (int block = 1; block <= 2; block++) {
                System.out.println("\n-- --------------------------------- BLOCK " + block + " ---------------------------------");

                if(block == 1){
                    stdNoStart = 31;
                    stdNoEnd = 45;
                } else {
                    stdNoStart = 46;
                    stdNoEnd = 60;
                }

                it.itGenerateGrades(2023, 2024, year, block, stdNoStart, stdNoEnd);
            }
        }

        */

        /* IRREGULARS */

        // CS IRREGS
        for (int year = 2; year <= 4; year++) {
            System.out.println("\n-- --------------------------------- BSCS YEAR LEVEL " + year + " BLOCK 1 ---------------------------------");
            cs.csGenerateGrades(2023, 2024, year, 1, 61, 63);

            System.out.println("\n-- --------------------------------- BSCS YEAR LEVEL " + year + " BLOCK 2 ---------------------------------");
            cs.csGenerateGrades(2023, 2024, year, 2, 64, 65);
        }


        // IT IRREGS
        for (int year = 2; year <= 4; year++) {
            System.out.println("\n-- --------------------------------- BSIT YEAR LEVEL " + year + " BLOCK 1 ---------------------------------");
            it.itGenerateGrades(2023, 2024, year, 1, 66, 68);

            System.out.println("\n-- --------------------------------- BSIT YEAR LEVEL " + year + " BLOCK 2 ---------------------------------");
            it.itGenerateGrades(2023, 2024, year, 2, 69, 70);
        }
    }
}
