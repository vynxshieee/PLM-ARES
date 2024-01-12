package com.OOP.plmares.views.DataGenerators;

public class IT_ZeroGradeGenerator {
    public static String syYearLvlBasis(int currStartYear, int currEndYear,  int subtrahend){
        return (currStartYear - subtrahend) + "-" + (currEndYear - subtrahend);
    }

    public void itGenerateGrades(int currStartYear, int currEndYear, int  choiceYear, int choiceBlock, int StdNoStart, int StdNoEnd) {

        int intCTR, stdNum = 20000;
        String sy_std_num = "",
                block_no_4thYr = "", block_no_3rdYr = "", block_no_2ndYr = "", block_no_1stYr = "",
                SY_YrBasis = "";


        // change sections based on block number
        switch (choiceBlock){
            case 1:
                block_no_1stYr = "IT11";
                block_no_2ndYr = "IT21";
                block_no_3rdYr = "IT31";
                block_no_4thYr = "IT41";
                break;
            case 2:
                block_no_1stYr = "IT12";
                block_no_2ndYr = "IT22";
                block_no_3rdYr = "IT32";
                block_no_4thYr = "IT42";
                break;
        }

        // change sy enrollment start based on year level
        switch (choiceYear){
            case 4:
                SY_YrBasis = syYearLvlBasis(currStartYear, currEndYear, 0);
                sy_std_num = Integer.toString(currEndYear - 4);
                break;

            case 3:
                SY_YrBasis = syYearLvlBasis(currStartYear, currEndYear, 0);
                sy_std_num = Integer.toString(currEndYear - 3);
                break;

            case 2:
                SY_YrBasis = syYearLvlBasis(currStartYear, currEndYear, 0);
                sy_std_num = Integer.toString(currEndYear - 2);
                break;

            case 1:
                SY_YrBasis = syYearLvlBasis(currStartYear, currEndYear, 0);
                sy_std_num = Integer.toString(currEndYear - 1);
                break;
        }


        int blkStdNo = StdNoStart;
        int total = StdNoEnd - StdNoStart + 1;

        for (intCTR = 0; intCTR < total; blkStdNo++, intCTR++) {

            System.out.println("\n-- STUDENT " + blkStdNo  + "  BLOCK " + choiceBlock);

            System.out.print("INSERT INTO grade VALUES\n");

            if(choiceYear == 1) {  // give 0 grades to current enrollment


                // 1ST YEAR    1ST SEM
                System.out.print("\n-- >>>>>>>>> 1ST YEAR 1ST SEM    IT GRADES\n");

                System.out.printf("\t('%s', '1', '%s-%s', 'STS 0002', '%s', 0),\n", SY_YrBasis, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                System.out.printf("\t('%s', '1', '%s-%s', 'AAP 0007', '%s', 0),\n", SY_YrBasis, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                System.out.printf("\t('%s', '1', '%s-%s', 'PCM 0006', '%s', 0),\n", SY_YrBasis, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                System.out.printf("\t('%s', '1', '%s-%s', 'MMW 0001', '%s', 0),\n", SY_YrBasis, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                System.out.printf("\t('%s', '1', '%s-%s', 'IPP 0010', '%s', 0),\n", SY_YrBasis, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                System.out.printf("\t('%s', '1', '%s-%s', 'ICC 0101', '%s', 0),\n", SY_YrBasis, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                System.out.printf("\t('%s', '1', '%s-%s', 'ICC 0101.1', '%s', 0),\n", SY_YrBasis, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                System.out.printf("\t('%s', '1', '%s-%s', 'ICC 0102', '%s', 0),\n", SY_YrBasis, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                System.out.printf("\t('%s', '1', '%s-%s', 'ICC 0102.1', '%s', 0),\n", SY_YrBasis, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                System.out.printf("\t('%s', '1', '%s-%s', 'NSTP 3', '%s', 0),\n", SY_YrBasis, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                System.out.printf("\t('%s', '1', '%s-%s', 'PATHFIT 1', '%s', 0);\n", SY_YrBasis, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
            }

            if(choiceYear == 2) {  // give 0 grades to current enrollment

                // 2ND YEAR    1ST SEM
                System.out.print("\n-- >>>>>>>>> 2ND YEAR 1ST SEM    IT GRADES\n");
                System.out.printf("\t('%s', '1', '%s-%s', 'EIT-ELEC1', '%s', 0),\n", SY_YrBasis, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                System.out.printf("\t('%s', '1', '%s-%s', 'ICC 0104', '%s', 0),\n", SY_YrBasis, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                System.out.printf("\t('%s', '1', '%s-%s', 'ICC 0104.1', '%s', 0),\n", SY_YrBasis, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                System.out.printf("\t('%s', '1', '%s-%s', 'EIT 0211', '%s', 0),\n", SY_YrBasis, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                System.out.printf("\t('%s', '1', '%s-%s', 'EIT 0211.1', '%s', 0),\n", SY_YrBasis, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                System.out.printf("\t('%s', '1', '%s-%s', 'TCW 0005', '%s', 0),\n", SY_YrBasis, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                System.out.printf("\t('%s', '1', '%s-%s', 'PPC 121', '%s', 0),\n", SY_YrBasis, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                System.out.printf("\t('%s', '1', '%s-%s', 'CET 0225', '%s', 0),\n", SY_YrBasis, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                System.out.printf("\t('%s', '1', '%s-%s', 'CET 0225.1', '%s', 0),\n", SY_YrBasis, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                System.out.printf("\t('%s', '1', '%s-%s', 'CET 0121', '%s', 0);\n", SY_YrBasis, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);

            }

            if(choiceYear == 3) {    // give 0 grades to current enrollment

                // 3RD YEAR    1ST SEM
                System.out.print("\n-- >>>>>>>>> 3RD YEAR 1ST SEM    IT GRADES\n");
                System.out.printf("\t('%s', '1', '%s-%s', 'ICC 0335', '%s', 0),\n", SY_YrBasis, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr);
                System.out.printf("\t('%s', '1', '%s-%s', 'ICC 0335.1', '%s', 0),\n", SY_YrBasis, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr);
                System.out.printf("\t('%s', '1', '%s-%s', 'EIT 0311', '%s', 0),\n", SY_YrBasis, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr);
                System.out.printf("\t('%s', '1', '%s-%s', 'EIT 0311.1', '%s', 0),\n", SY_YrBasis, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr);
                System.out.printf("\t('%s', '1', '%s-%s', 'EIT 0312', '%s', 0),\n", SY_YrBasis, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr);
                System.out.printf("\t('%s', '1', '%s-%s', 'EIT 0312.1', '%s', 0),\n", SY_YrBasis, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr);
                System.out.printf("\t('%s', '1', '%s-%s', 'EIT-ELEC3', '%s', 0),\n", SY_YrBasis, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr);
                System.out.printf("\t('%s', '1', '%s-%s', 'LWR 0009', '%s', 0);\n", SY_YrBasis, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr);

            }

            if(choiceYear == 4) {    // give 0 grades to current enrollment

                // 4TH YEAR    1ST SEM
                System.out.print("\n-- >>>>>>>>> 4TH YEAR 1ST SEM    IT GRADES\n");
                System.out.printf("\t('%s', '2', '%s-%s', 'CAP 0102', '%s', 0),\n", SY_YrBasis, sy_std_num, (stdNum + blkStdNo), block_no_4thYr);
                System.out.printf("\t('%s', '2', '%s-%s', 'EIT-ELEC6', '%s', 0),\n", SY_YrBasis, sy_std_num, (stdNum + blkStdNo), block_no_4thYr);
                System.out.printf("\t('%s', '2', '%s-%s', 'EIT-ELEC5', '%s', 0),\n", SY_YrBasis, sy_std_num, (stdNum + blkStdNo), block_no_4thYr);
                System.out.printf("\t('%s', '2', '%s-%s', 'EIT-ELEC4', '%s', 0);\n", SY_YrBasis, sy_std_num, (stdNum + blkStdNo), block_no_4thYr);
            }
        }

    }

}
