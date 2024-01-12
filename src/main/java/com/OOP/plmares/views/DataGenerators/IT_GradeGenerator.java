package com.OOP.plmares.views.DataGenerators;

import java.util.Random;

public class IT_GradeGenerator {

    public static String syYearLvlBasis(int currStartYear, int currEndYear,  int subtrahend){
        return (currStartYear - subtrahend) + "-" + (currEndYear - subtrahend);
    }

    public void itGenerateGrades(int currStartYear, int currEndYear, int  choiceYear, int choiceBlock, int StdNoStart, int StdNoEnd) {

        int intCTR, stdNum = 20000;
        String sy_std_num = "",
                block_no_3rdYr = "", block_no_2ndYr = "", block_no_1stYr = "",
                SY_3rdYr = "", SY_2ndYr = "", SY_1stYr = "";

        Random random = new Random();
        double[] grades = {1.00, 1.25, 1.50, 1.75, 2.00, 2.25};


        // change sections based on block number
        switch (choiceBlock){
            case 1:
                block_no_1stYr = "IT11";
                block_no_2ndYr = "IT21";
                block_no_3rdYr = "IT31";
                break;
            case 2:
                block_no_1stYr = "IT12";
                block_no_2ndYr = "IT22";
                block_no_3rdYr = "IT32";
                break;
        }

        // change sy enrollment start based on year level
        switch (choiceYear){
            case 4:
                SY_1stYr = syYearLvlBasis(currStartYear, currEndYear, 3);
                SY_2ndYr = syYearLvlBasis(currStartYear, currEndYear, 2);
                SY_3rdYr = syYearLvlBasis(currStartYear, currEndYear, 1);
                sy_std_num = Integer.toString(currEndYear - 4);
                break;

            case 3:
                SY_1stYr = syYearLvlBasis(currStartYear, currEndYear, 2);
                SY_2ndYr = syYearLvlBasis(currStartYear, currEndYear, 1);
                sy_std_num = Integer.toString(currEndYear - 3);
                break;

            case 2:
                SY_1stYr = syYearLvlBasis(currStartYear, currEndYear, 1);
                sy_std_num = Integer.toString(currEndYear - 2);
                break;
        }



        int blkStdNo = StdNoStart;
        int total = StdNoEnd - StdNoStart + 1;

        for (intCTR = 0; intCTR < total; blkStdNo++, intCTR++) {

            System.out.println("\n-- STUDENT " + blkStdNo  + "  BLOCK " + choiceBlock);
            System.out.print("INSERT INTO grade VALUES\n");

            // 1ST YEAR    1ST SEM
            System.out.print("\n-- >>>>>>>>> 1ST YEAR 1ST SEM    IT GRADES\n");
            System.out.printf("\t('%s', '1', '%s-%s', 'STS 0002', '%s', %.2f),\n", SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, grades[random.nextInt(grades.length)]);
            System.out.printf("\t('%s', '1', '%s-%s', 'AAP 0007', '%s', %.2f),\n", SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, grades[random.nextInt(grades.length)]);
            System.out.printf("\t('%s', '1', '%s-%s', 'PCM 0006', '%s', %.2f),\n", SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, grades[random.nextInt(grades.length)]);
            System.out.printf("\t('%s', '1', '%s-%s', 'MMW 0001', '%s', %.2f),\n", SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, grades[random.nextInt(grades.length)]);
            System.out.printf("\t('%s', '1', '%s-%s', 'IPP 0010', '%s', %.2f),\n", SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, grades[random.nextInt(grades.length)]);
            System.out.printf("\t('%s', '1', '%s-%s', 'ICC 0101', '%s', %.2f),\n", SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, grades[random.nextInt(grades.length)]);
            System.out.printf("\t('%s', '1', '%s-%s', 'ICC 0101.1', '%s', %.2f),\n", SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, grades[random.nextInt(grades.length)]);
            System.out.printf("\t('%s', '1', '%s-%s', 'ICC 0102', '%s', %.2f),\n", SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, grades[random.nextInt(grades.length)]);
            System.out.printf("\t('%s', '1', '%s-%s', 'ICC 0102.1', '%s', %.2f),\n", SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, grades[random.nextInt(grades.length)]);
            System.out.printf("\t('%s', '1', '%s-%s', 'NSTP 3', '%s', %.2f),\n", SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, grades[random.nextInt(grades.length)]);
            System.out.printf("\t('%s', '1', '%s-%s', 'PATHFIT 1', '%s', %.2f),\n", SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, grades[random.nextInt(grades.length)]);

            System.out.print("\n-- >>>>>>>>> 1ST YEAR 2nd SEM    IT GRADES\n");
            System.out.printf("\t('%s', '2', '%s-%s', 'CET 0111', '%s', %.2f),\n", SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, grades[random.nextInt(grades.length)]);
            System.out.printf("\t('%s', '2', '%s-%s', 'CET 0114', '%s', %.2f),\n", SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, grades[random.nextInt(grades.length)]);
            System.out.printf("\t('%s', '2', '%s-%s', 'CET 0114.1', '%s', %.2f),\n", SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, grades[random.nextInt(grades.length)]);
            System.out.printf("\t('%s', '2', '%s-%s', 'EIT 0121', '%s', %.2f),\n", SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, grades[random.nextInt(grades.length)]);
            System.out.printf("\t('%s', '2', '%s-%s', 'EIT 0121.1', '%s', %.2f),\n", SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, grades[random.nextInt(grades.length)]);
            System.out.printf("\t('%s', '2', '%s-%s', 'EIT 0122', '%s', %.2f),\n", SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, grades[random.nextInt(grades.length)]);
            System.out.printf("\t('%s', '2', '%s-%s', 'EIT 0123', '%s', %.2f),\n", SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, grades[random.nextInt(grades.length)]);
            System.out.printf("\t('%s', '2', '%s-%s', 'EIT 0123.1', '%s', %.2f),\n", SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, grades[random.nextInt(grades.length)]);
            System.out.printf("\t('%s', '2', '%s-%s', 'ICC 0103', '%s', %.2f),\n", SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, grades[random.nextInt(grades.length)]);
            System.out.printf("\t('%s', '2', '%s-%s', 'ICC 0103.1', '%s', %.2f),\n", SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, grades[random.nextInt(grades.length)]);
            System.out.printf("\t('%s', '2', '%s-%s', 'GTB121', '%s', %.2f),\n", SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, grades[random.nextInt(grades.length)]);
            System.out.printf("\t('%s', '2', '%s-%s', 'NSTP 4', '%s', %.2f),\n", SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, grades[random.nextInt(grades.length)]);
            System.out.printf("\t('%s', '2', '%s-%s', 'PATHFIT 2', '%s', %.2f);\n", SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, grades[random.nextInt(grades.length)]);


            if(choiceYear != 2) {  // current second years cannot have second year grades

                System.out.print("\nINSERT INTO grade VALUES\n");

                // 2ND YEAR    1ST SEM
                System.out.print("\n-- >>>>>>>>> 2ND YEAR 1ST SEM    IT GRADES\n");
                System.out.printf("\t('%s', '1', '%s-%s', 'EIT-ELEC1', '%s', %.2f),\n", SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, grades[random.nextInt(grades.length)]);
                System.out.printf("\t('%s', '1', '%s-%s', 'ICC 0104', '%s', %.2f),\n", SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, grades[random.nextInt(grades.length)]);
                System.out.printf("\t('%s', '1', '%s-%s', 'ICC 0104.1', '%s', %.2f),\n", SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, grades[random.nextInt(grades.length)]);
                System.out.printf("\t('%s', '1', '%s-%s', 'EIT 0211', '%s', %.2f),\n", SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, grades[random.nextInt(grades.length)]);
                System.out.printf("\t('%s', '1', '%s-%s', 'EIT 0211.1', '%s', %.2f),\n", SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, grades[random.nextInt(grades.length)]);
                System.out.printf("\t('%s', '1', '%s-%s', 'TCW 0005', '%s', %.2f),\n", SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, grades[random.nextInt(grades.length)]);
                System.out.printf("\t('%s', '1', '%s-%s', 'PPC 121', '%s', %.2f),\n", SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, grades[random.nextInt(grades.length)]);
                System.out.printf("\t('%s', '1', '%s-%s', 'CET 0225', '%s', %.2f),\n", SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, grades[random.nextInt(grades.length)]);
                System.out.printf("\t('%s', '1', '%s-%s', 'CET 0225.1', '%s', %.2f),\n", SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, grades[random.nextInt(grades.length)]);
                System.out.printf("\t('%s', '1', '%s-%s', 'CET 0121', '%s', %.2f),\n", SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, grades[random.nextInt(grades.length)]);

                // 2ND YEAR    2ND SEM
                System.out.print("\n-- >>>>>>>>> 2ND YEAR 2ND SEM    IT GRADES\n");
                System.out.printf("\t('%s', '2', '%s-%s', 'EIT 0212', '%s', %.2f),\n", SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, grades[random.nextInt(grades.length)]);
                System.out.printf("\t('%s', '2', '%s-%s', 'EIT-ELEC2', '%s', %.2f),\n", SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, grades[random.nextInt(grades.length)]);
                System.out.printf("\t('%s', '2', '%s-%s', 'EIT 0221', '%s', %.2f),\n", SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, grades[random.nextInt(grades.length)]);
                System.out.printf("\t('%s', '2', '%s-%s', 'ICC 0105', '%s', %.2f),\n", SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, grades[random.nextInt(grades.length)]);
                System.out.printf("\t('%s', '2', '%s-%s', 'ICC 0105.1', '%s', %.2f),\n", SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, grades[random.nextInt(grades.length)]);
                System.out.printf("\t('%s', '2', '%s-%s', 'EIT 0222', '%s', %.2f),\n", SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, grades[random.nextInt(grades.length)]);
                System.out.printf("\t('%s', '2', '%s-%s', 'EIT 0222.1', '%s', %.2f),\n", SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, grades[random.nextInt(grades.length)]);
                System.out.printf("\t('%s', '2', '%s-%s', 'RPH 0004', '%s', %.2f),\n", SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, grades[random.nextInt(grades.length)]);
                System.out.printf("\t('%s', '2', '%s-%s', 'GES 0013', '%s', %.2f),\n", SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, grades[random.nextInt(grades.length)]);
                System.out.printf("\t('%s', '2', '%s-%s', 'UTS 0003', '%s', %.2f);\n", SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, grades[random.nextInt(grades.length)]);

                if(choiceYear != 3) {    // current third years cannot have third year grades

                    System.out.print("\nINSERT INTO grade VALUES\n");

                    // 3RD YEAR    1ST SEM
                    System.out.print("\n-- >>>>>>>>> 3RD YEAR 1ST SEM    IT GRADES\n");
                    System.out.printf("\t('%s', '1', '%s-%s', 'ICC 0335', '%s', %.2f),\n", SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, grades[random.nextInt(grades.length)]);
                    System.out.printf("\t('%s', '1', '%s-%s', 'ICC 0335.1', '%s', %.2f),\n", SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, grades[random.nextInt(grades.length)]);
                    System.out.printf("\t('%s', '1', '%s-%s', 'EIT 0311', '%s', %.2f),\n", SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, grades[random.nextInt(grades.length)]);
                    System.out.printf("\t('%s', '1', '%s-%s', 'EIT 0311.1', '%s', %.2f),\n", SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, grades[random.nextInt(grades.length)]);
                    System.out.printf("\t('%s', '1', '%s-%s', 'EIT 0312', '%s', %.2f),\n", SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, grades[random.nextInt(grades.length)]);
                    System.out.printf("\t('%s', '1', '%s-%s', 'EIT 0312.1', '%s', %.2f),\n", SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, grades[random.nextInt(grades.length)]);
                    System.out.printf("\t('%s', '1', '%s-%s', 'EIT-ELEC3', '%s', %.2f),\n", SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, grades[random.nextInt(grades.length)]);
                    System.out.printf("\t('%s', '1', '%s-%s', 'LWR 0009', '%s', %.2f),\n", SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, grades[random.nextInt(grades.length)]);

                    // 3RD YEAR     2ND SEM
                    System.out.print("\n-- >>>>>>>>> 3RD YEAR 2ND SEM    IT GRADES\n");
                    System.out.printf("\t('%s', '2', '%s-%s', 'EIT 0321', '%s', %.2f),\n", SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, grades[random.nextInt(grades.length)]);
                    System.out.printf("\t('%s', '2', '%s-%s', 'EIT 0321.1', '%s', %.2f),\n", SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, grades[random.nextInt(grades.length)]);
                    System.out.printf("\t('%s', '2', '%s-%s', 'EIT 0323', '%s', %.2f),\n", SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, grades[random.nextInt(grades.length)]);
                    System.out.printf("\t('%s', '2', '%s-%s', 'EIT 0323.1', '%s', %.2f),\n", SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, grades[random.nextInt(grades.length)]);
                    System.out.printf("\t('%s', '2', '%s-%s', 'EIT 0322', '%s', %.2f),\n", SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, grades[random.nextInt(grades.length)]);
                    System.out.printf("\t('%s', '2', '%s-%s', 'EIT 0322.1', '%s', %.2f),\n", SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, grades[random.nextInt(grades.length)]);
                    System.out.printf("\t('%s', '2', '%s-%s', 'ETH 0008', '%s', %.2f);\n", SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, grades[random.nextInt(grades.length)]);

                }
            }
        }

    }

}
