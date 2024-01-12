package com.OOP.plmares.views.DataGenerators;

public class CS_IrregScheduleGenerator {

    public static String syYearLvlBasis(int currStartYear, int currEndYear,  int subtrahend){
        return (currStartYear - subtrahend) + "-" + (currEndYear - subtrahend);
    }

    public void csIrregGenerateSched(int currStartYear, int currEndYear, int  choiceYear, int choiceBlock, int StdNoStart, int StdNoEnd) {

        /* START: SETUP

        int currStartYear = 2023, currEndYear = 2024,    // change to what current school year you want to set
                choiceYear = 3, choiceBlock = 2,         // change to what year lvl and block u want to generate
                stdNum = 20000,                          // change to std format after sch yr

                StdNoEnd = 5,      // end student number generation
                StdNoStart,     // start student number generation


                intCTR;

        END: SETUP */

        int intCTR, stdNum = 20000;
        String sy_std_num = "",
                block_no_4thYr = "", block_no_3rdYr = "", block_no_2ndYr = "", block_no_1stYr = "",
                SY_4thYr = "", SY_3rdYr = "", SY_2ndYr = "", SY_1stYr = "",
                status = "";



        // change sections based on block number
        switch (choiceBlock){
            case 1:
                block_no_1stYr = "CS11";
                block_no_2ndYr = "CS21";
                block_no_3rdYr = "CS31";
                block_no_4thYr = "CS41";
                break;
            case 2:
                block_no_1stYr = "CS12";
                block_no_2ndYr = "CS22";
                block_no_3rdYr = "CS32";
                block_no_4thYr = "CS42";
                break;
        }

        // change sy enrollment start based on year level
        switch (choiceYear){
            case 4:
                SY_1stYr = syYearLvlBasis(currStartYear, currEndYear, 3);
                SY_2ndYr = syYearLvlBasis(currStartYear, currEndYear, 2);
                SY_3rdYr = syYearLvlBasis(currStartYear, currEndYear, 1);
                SY_4thYr = syYearLvlBasis(currStartYear, currEndYear, 0);
                sy_std_num = Integer.toString(currEndYear - 4);
                break;

            case 3:
                SY_1stYr = syYearLvlBasis(currStartYear, currEndYear, 2);
                SY_2ndYr = syYearLvlBasis(currStartYear, currEndYear, 1);
                SY_3rdYr = syYearLvlBasis(currStartYear, currEndYear, 0);
                sy_std_num = Integer.toString(currEndYear - 3);
                break;

            case 2:
                SY_1stYr = syYearLvlBasis(currStartYear, currEndYear, 1);
                SY_2ndYr = syYearLvlBasis(currStartYear, currEndYear, 0);
                sy_std_num = Integer.toString(currEndYear - 2);
                break;

            case 1:
                SY_1stYr = syYearLvlBasis(currStartYear, currEndYear, 0);
                sy_std_num = Integer.toString(currEndYear - 1);
                break;
        }


        if(choiceBlock == 1) {
            int blkStdNo = StdNoStart;
            int total = StdNoEnd - StdNoStart + 1;
            for (intCTR = 0; intCTR < total; blkStdNo++, intCTR++) {

                if(choiceYear == 1)
                    status = "pending";
                else
                    status = "approved";


                    // 1ST YEAR
                    System.out.print("\n-- >>>>>>>>> 1ST YEAR | 1ST SEM | BLOCK 1");
                    System.out.print("\nINSERT INTO subject_schedule VALUES\n");
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CSC 0102', 'M', '14:30-16:00', 'MSTeams', 'OL', '1', 'E055', '%s'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CSC 0102', 'Th', '14:30-16:00', 'MSTeams', 'OL', '2', 'E055', '%s'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'ICC 0101', 'F', '8:00-10:00', 'MSTeams', 'OL', '1', 'E056', '%s'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'ICC 0101.1', 'T', '13:00-16:00', 'COMP LAB 3', 'FTF', '1', 'E056', '%s'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'ICC 0102', 'M', '16:00-18:00', 'MSTeams', 'OL', '1', 'E057', '%s'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'ICC 0102.1', 'Th', '16:00-19:00', 'COMP LAB 3', 'FTF', '1', 'E057', '%s'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'IPP 0010', 'M', '8:30-10:00', 'MSTeams', 'OL', '1', 'E058', '%s'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'IPP 0010', 'Th', '8:30-10:00', 'MSTeams', 'OL', '2', 'E058', '%s'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'MMW 0001', 'T', '10:00-11:30', 'MSTeams', 'OL', '1', 'E026', '%s'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'MMW 0001', 'F', '10:00-11:30', 'MSTeams', 'OL', '2', 'E026', '%s'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'PCM 0006', 'M', '7:00-8:30', 'MSTeams', 'OL', '1', 'E059', '%s'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'PCM 0006', 'Th', '7:00-8:30', 'MSTeams', 'OL', '2', 'E059', '%s'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'STS 0002', 'W', '10:00-11:30', 'MSTeams', 'OL', '1', 'E060', '%s'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'STS 0002', 'W', '11:30-13:00', 'MSTeams', 'OL', '2', 'E060', '%s'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'PATHFIT 1', 'T', '7:00-9:00', 'UAC', 'FTF', '1', 'E094', '%s'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'NSTP 3', 'Su', '8:30-10:00', 'MSTeams', 'OL', '1', 'E090', '%s'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'NSTP 3', 'Su', '14:30-15:00', 'MSTeams', 'OL', '2', 'E090', '%s');\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);


                    if(choiceYear != 1) {  // first years can't enroll for 2nd sem of current sem yet or further years
                        System.out.print("\n-- >>>>>>>>> 1ST YEAR | 2ND SEM | BLOCK 1");
                        System.out.print("\nINSERT INTO subject_schedule VALUES\n");
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CSC 0211', 'T', '9:00-10:30', 'GCA 306', 'FTF', '1', 'E055', '%s'),\n",
                                SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CSC 0211', 'F', '9:00-10:30', 'MSTeams', 'OL', '2', 'E055', '%s'),\n",
                                SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'ICC 0103', 'M', '7:30-8:30', 'COMP LAB 3', 'FTF', '1', 'E056', '%s'),\n",
                                SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'ICC 0103.1', 'M', '12:00-15:00', 'COMP LAB 4', 'FTF', '1', 'E056', '%s'),\n",
                                SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'ICC 0104', 'T', '16:00-18:00', 'COMP LAB 4', 'FTF', '1', 'E057', '%s'),\n",
                                SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'ICC 0104.1', 'F', '16:00-19:00', 'COMP LAB 4', 'FTF', '1', 'E057', '%s'),\n",
                                SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CSC 0223', 'T', '10:30-12:00', 'GCA 306', 'FTF', '1', 'E061', '%s'),\n",
                                SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CSC 0223', 'F', '10:30-12:00', 'MSTeams', 'OL', '2', 'E061', '%s'),\n",
                                SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'RPH 0004', 'T', '14:30-16:30', 'GCA 306', 'FTF', '1', 'E062', '%s'),\n",
                                SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'RPH 0004', 'F', '14:30-16:30', 'MSTeams', 'OL', '2', 'E062', '%s'),\n",
                                SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'TCW 0005', 'T', '13:00-14:30', 'GCA 306', 'FTF', '1', 'E063', '%s'),\n",
                                SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'TCW 0005', 'F', '13:00-14:30', 'MSTeams', 'OL', '2', 'E063', '%s'),\n",
                                SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'LWR 0009', 'M', '15:00-16:30', 'GCA 306', 'FTF', '1', 'E064', '%s'),\n",
                                SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'LWR 0009', 'Th', '15:00-16:30', 'MSTeams', 'OL', '2', 'E064', '%s'),\n",
                                SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'PATHFIT 2', 'M', '9:00-11:00', 'UAC', 'FTF', '1', 'E095', '%s'),\n",
                                SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'NSTP 4', 'Su', '13:00-16:00', 'GV 306', 'FTF', '1', 'E096', '%s');\n",
                                SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);


                        if(choiceYear == 2)
                            status = "pending";
                        else
                            status = "approved";
                        
                        // 2ND YEAR
                        System.out.print("\n--  >>>>>>>>> 2ND YEAR | 1ST SEM | BLOCK 1");
                        System.out.print("\nINSERT INTO subject_schedule VALUES\n");
                        System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CSC 0212', 'S', '8:00-10:00', 'GCA 306', 'F2F', '1', 'E068', '%s'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, status);
                        System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CSC 0212.1', 'W', '18:00-21:00', 'COMP LAB 3', 'F2F', '2', 'E068', '%s'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, status);
                        System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'ETH 0008', 'T', '8:30-10:00', 'MSTeams', 'OL', '1', 'E069', '%s'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, status);
                        System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'ETH 0008', 'F', '8:30-10:00', 'GEE 206', 'F2F', '2', 'E069', '%s'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, status);
                        System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'UTS 0003', 'T', '10:00-11:30', 'MSTeams', 'OL', '1', 'E070', '%s'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, status);
                        System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'UTS 0003', 'F', '10:00-11:30', 'GK 301', 'F2F', '2', 'E070', '%s'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, status);
                        System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CSC 0224', 'W', '15:00-18:00', 'GL PHYLAB', 'F2F', '1', 'E071', '%s'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, status);
                        System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CSC 0213', 'W', '7:00-9:00', 'GV 306', 'F2F', '1', 'E072', '%s'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, status);
                        System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CSC 0213.1', 'S', '13:00-16:00', 'GVENGLAB1', 'F2F', '1', 'E072', '%s'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, status);
                        System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'ICC 0105', 'T', '18:00-21:00', 'DRAWINGRM1', 'F2F', '1', 'E073', '%s'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, status);
                        System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'ICC 0105.1', 'W', '10:00-11:00', 'MSTeams', 'OL', '1', 'E073', '%s'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, status);
                        System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'ICC 0105', 'S', '10:00-11:00', 'COMP LAB 3', 'F2F', '2', 'E073', '%s'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, status);
                        System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'ITE 0001', 'T', '7:00-8:30', 'MSTeams', 'OL', '1', 'E061', '%s'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, status);
                        System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'ITE 0001', 'F', '7:00-8:30', 'GCA 306', 'F2F', '2', 'E061', '%s');\n",
                                SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, status);

                        if(choiceYear != 2) {    // second years can't enroll for 2nd sem of current sem yet or further years
                            System.out.print("\n--  >>>>>>>>> 2ND YEAR | 2ND SEM | BLOCK 1");
                            System.out.print("\nINSERT INTO subject_schedule VALUES\n");

                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'ICC 0106', 'W', '12:00-13:00', 'GCA 306', 'F2F', '1', 'E061', '%s'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, status);
                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'ICC 0106', 'S', '12:00-13:00', 'MSTeams', 'OL', '2', 'E061', '%s'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, status);
                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'ICC 0106.1', 'W', '7:00-8:30', 'GV 304', 'F2F', '1', 'E061', '%s'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, status);
                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'ICC 0106.1', 'W', '8:30-10:00', 'COMP LAB 3', 'F2F', '2', 'E061', '%s'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, status);
                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CSC 0222', 'F', '7:00-9:00', 'GCA 306', 'F2F', '1', 'E072', '%s'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, status);
                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CSC 0222.1', 'Th', '9:00-12:00', 'COMP LAB 3', 'F2F', '1', 'E072', '%s'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, status);
                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CSC 0221', 'W', '19:00-20:30', 'GCA 306', 'F2F', '1', 'E068', '%s'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, status);
                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CSC 0221', 'S', '19:00-20:30', 'MSTeams', 'OL', '2', 'E068', '%s'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, status);
                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CSC 0316', 'T', '9:00-10:30', 'MSTeams', 'OL', '1', 'E074', '%s'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, status);
                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CSC 0316', 'F', '9:00-10:30', 'COMP LAB 3', 'F2F', '2', 'E074', '%s'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, status);
                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'AAP 0007', 'T', '19:00-20:30', 'DRAWINGRM1', 'F2F', '1', 'E075', '%s'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, status);
                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'AAP 0007', 'F', '19:00-20:30', 'MSTeams', 'OL', '2', 'E075', '%s'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, status);
                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'GES 0013', 'W', '16:00-19:00', 'GEE 205', 'F2F', '1', 'E076', '%s'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, status);
                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CBM 0016', 'Th', '13:00-16:00', 'MSTeams', 'OL', '1', 'E077', '%s');\n",
                                    SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, status);


                            if(choiceYear == 3)
                                status = "pending";
                            else
                                status = "approved";

                            // 3RD YEAR
                            System.out.print("\n--  >>>>>>>>> 3RD YEAR | 1ST SEM | BLOCK 1");
                            System.out.print("\nINSERT INTO subject_schedule VALUES\n");

                            System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CSC 0311', 'M', '16:00-21:00', 'GV 309', 'F2F', '1', 'E082', '%s'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, status);
                            System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CSC 0312.1', 'W', '9:00-12:00', 'COMP LAB 4', 'F2F', '1', 'E083', '%s'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, status);
                            System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CSC 0312', 'W', '19:00-21:00', 'COMP LAB 3', 'F2F', '1', 'E083', '%s'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, status);
                            System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CSC 0313', 'F', '7:00-9:00', 'COMP LAB 2', 'F2F', '1', 'E085', '%s'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, status);
                            System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CSC 0313.1', 'F', '9:00-12:00', 'COMP LAB 4', 'F2F', '1', 'E085', '%s'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, status);
                            System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CSC 0314', 'Th', '9:00-11:00', 'GCA 306', 'F2F', '1', 'E055', '%s'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, status);
                            System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CSC 0314.1', 'Th', '13:00-16:00', 'GV 306', 'F2F', '1', 'E055', '%s'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, status);
                            System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CSC 0315', 'M', '7:00-9:00', 'COMP LAB 4', 'F2F', '1', 'E084', '%s'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, status);
                            System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CSC 0315.1', 'M', '9:00-12:00', 'COMP LAB 3', 'F2F', '1', 'E082', '%s');\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, status);

                            if(choiceYear != 3) { // third years can't enroll for 2nd sem of current sem yet or further years
                                System.out.print("\n--  >>>>>>>>> 3RD YEAR | 2ND SEM | BLOCK 1");
                                System.out.print("\nINSERT INTO subject_schedule VALUES\n");

                                System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CSC 0321', 'T', '12:00-13:00', 'MSTeams', 'OL', '1', 'E085', '%s'),\n",
                                SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, status);
                                System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CSC 0321', 'F', '12:00-13:00', 'COMP LAB 3', 'F2F', '2', 'E085', '%s'),\n",
                                SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, status);
                                System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CSC 0321.1', 'F', '7:00-10:00', 'COMP LAB 4', 'F2F', '1', 'E085', '%s'),\n",
                                SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, status);
                                System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CSC 0322', 'T', '16:00-17:00', 'MSTeams', 'OL', '1', 'E086', '%s'),\n",
                                SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, status);
                                System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CSC 0322', 'F', '16:00-17:00', 'GCA DL 1', 'F2F', '2', 'E086', '%s'),\n",
                                SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, status);
                                System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CSC 0322.1', 'Th', '16:30-18:00', 'COMP LAB 3', 'F2F', '2', 'E086', '%s'),\n",
                                SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, status);
                                System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CSC 0322.1', 'Th', '15:00-16:30', 'COMP LAB 3', 'F2F', '1', 'E086', '%s'),\n",
                                SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, status);
                                System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CSC 0323', 'T', '19:30-20:30', 'MSTeams', 'OL', '1', 'E068', '%s'),\n",
                                SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, status);
                                System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CSC 0323', 'F', '19:30-20:30', 'GCA 306', 'F2F', '2', 'E068', '%s'),\n",
                                SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, status);
                                System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CSC 0323.1', 'S', '9:30-12:30', 'COMP LAB 3', 'F2F', '1', 'E068', '%s'),\n",
                                SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, status);
                                System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CSC 0324', 'T', '18:00-19:00', 'MSTeams', 'OL', '1', 'E072', '%s'),\n",
                                SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, status);
                                System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CSC 0324', 'F', '18:00-19:00', 'DRAWINGRM1', 'F2F', '2', 'E072', '%s'),\n",
                                SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, status);
                                System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CSC 0324.1', 'S', '17:00-20:00', 'COMP LAB 3', 'F2F', '1', 'E072', '%s'),\n",
                                SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, status);
                                System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CSC 0325', 'M', '13:30-15:00', 'MSTeams', 'OL', '1', 'E087', '%s'),\n",
                                SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, status);
                                System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CSC 0325', 'Th', '13:30-15:00', 'GCA 306', 'F2F', '2', 'E087', '%s');\n",
                                SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, status);


                                if(choiceYear == 4)
                                    status = "pending";
                                else
                                    status = "approved";

                                // 4TH YEAR 1ST SEM
                                System.out.print("\n--  >>>>>>>>> 4TH YEAR | 1ST SEM | BLOCK 1");
                                System.out.print("\nINSERT INTO subject_schedule VALUES\n");

                                System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CSC 0411', 'M', '7:00-8:30', 'GCA 306', 'F2F', '1', 'E085', '%s'),\n",
                                SY_4thYr, sy_std_num, (stdNum + blkStdNo), block_no_4thYr, status);
                                System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CSC 0411', 'Th', '7:00-8:30', 'GCA 306', 'F2F', '2', 'E085', '%s'),\n",
                                SY_4thYr, sy_std_num, (stdNum + blkStdNo), block_no_4thYr, status);
                                System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CSC 0413', 'F', '7:00-9:00', 'FIELD', 'F2F', '1', 'E023', '%s'),\n",
                                SY_4thYr, sy_std_num, (stdNum + blkStdNo), block_no_4thYr, status);
                                System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CSC 0413.1', 'M', '18:00-21:00', 'COMP LAB 3', 'F2F', '1', 'E023', '%s'),\n",
                                SY_4thYr, sy_std_num, (stdNum + blkStdNo), block_no_4thYr, status);
                                System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CSC 0414', 'W', '7:00-8:30', 'GCA 306', 'F2F', '1', 'E045', '%s'),\n",
                                SY_4thYr, sy_std_num, (stdNum + blkStdNo), block_no_4thYr, status);
                                System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CSC 0414', 'S', '7:00-8:30', 'GCA 306', 'F2F', '2', 'E045', '%s'),\n",
                                SY_4thYr, sy_std_num, (stdNum + blkStdNo), block_no_4thYr, status);
                                System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CSC 0414.1', 'F', '18:00-21:00', 'FIELD', 'F2F', '1', 'E088', '%s'),\n",
                                SY_4thYr, sy_std_num, (stdNum + blkStdNo), block_no_4thYr, status);
                                System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CSC 0412', 'F', '9:30-11:30', 'FIELD', 'F2F', '1', 'E074', '%s'),\n",
                                SY_4thYr, sy_std_num, (stdNum + blkStdNo), block_no_4thYr, status);
                                System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CSC 0412.1', 'M', '10:00-13:00', 'GCA 306', 'F2F', '1', 'E074', '%s');\n",
                                SY_4thYr, sy_std_num, (stdNum + blkStdNo), block_no_4thYr, status);
                            }
                        }
                    }
                }
            }

        if(choiceBlock == 2) {
            int blkStdNo = StdNoStart;
            int total = StdNoEnd - StdNoStart + 1;
            for (intCTR = 0; intCTR < total; blkStdNo++, intCTR++) {
                System.out.print("\n-- [STUDENT " + blkStdNo +" - BLOCK " + choiceBlock + "]");

                if(choiceYear == 1)
                    status = "pending";
                else
                    status = "approved";

                // 1ST YEAR
                System.out.print("\n-- >>>>>>>>> 1ST YEAR | 1ST SEM | BLOCK 2");
                System.out.print("\nINSERT INTO subject_schedule VALUES\n");
                System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CSC 0102', 'M', '16:00-17:30', 'MSTeams', 'OL', '1', 'E055', '%s'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CSC 0102', 'Th', '16:00-17:30', 'MSTeams', 'OL', '2', 'E055', '%s'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'ICC 0101', 'M', '17:00-18:30', 'MSTeams', 'OL', '1', 'E056', '%s'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'ICC 0101.1', 'T', '18:00-21:00', 'COMP LAB 3', 'FTF', '1', 'E056', '%s'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'ICC 0102', 'W', '18:00-20:00', 'MSTeams', 'OL', '1', 'E057', '%s'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'ICC 0102.1', 'W', '13:00-16:00', 'COMP LAB 3', 'FTF', '1', 'E057', '%s'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'IPP 0010', 'M', '14:30-16:00', 'MSTeams', 'OL', '1', 'E058', '%s'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'IPP 0010', 'Th', '14:30-16:00', 'MSTeams', 'OL', '2', 'E058', '%s'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'MMW 0001', 'T', '10:00-11:30', 'MSTeams', 'OL', '1', 'E026', '%s'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'MMW 0001', 'F', '10:00-11:30', 'MSTeams', 'OL', '2', 'E026', '%s'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'PCM 0006', 'M', '13:00-14:30', 'MSTeams', 'OL', '1', 'E059', '%s'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'PCM 0006', 'Th', '13:00-14:30', 'MSTeams', 'OL', '2', 'E059', '%s'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'STS 0002', 'M', '18:30-20:00', 'MSTeams', 'OL', '1', 'E060', '%s'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'STS 0002', 'Th', '18:30-20:00', 'MSTeams', 'OL', '2', 'E060', '%s'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'PATHFIT 1', 'M', '9:00-11:00', 'UAC', 'FTF', '1', 'E094', '%s'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'NSTP 3', 'Su', '10:00-11:30', 'MSTeams', 'OL', '1', 'E090', '%s'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'NSTP 3', 'Su', '13:00-15:00', 'MSTeams', 'OL', '2', 'E090', '%s');\n",
                        SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);


                if(choiceYear != 1) {  // first years can't enroll for 2nd sem of current sem yet or further years
                    System.out.print("\n-- >>>>>>>>> 1ST YEAR | 2ND SEM | BLOCK 2");
                    System.out.print("\nINSERT INTO subject_schedule VALUES\n");
                    System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CSC 0211', 'W', '9:00-12:00', 'GCA 306', 'FTF', '1', 'E055', '%s'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                    System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'ICC 0103', 'T', '7:30-8:30', 'COMP LAB 3', 'FTF', '1', 'E056', '%s'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                    System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'ICC 0103.1', 'M', '9:00-12:00', 'COMP LAB 3', 'FTF', '1', 'E056', '%s'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                    System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'ICC 0104', 'W', '18:00-20:00', 'COMP LAB 4', 'FTF', '1', 'E057', '%s'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                    System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'ICC 0104.1', 'W', '13:00-15:00', 'COMP LAB 4', 'FTF', '1', 'E057', '%s'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                    System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CSC 0223', 'Th', '18:00-21:00', 'GCA 306', 'FTF', '1', 'E061', '%s'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                    System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'RPH 0004', 'M', '13:00-15:30', 'GCA 306', 'FTF', '1', 'E062', '%s'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                    System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'RPH 0004', 'Th', '13:00-15:30', 'MSTeams', 'OL', '2', 'E062', '%s'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                    System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'TCW 0005', 'M', '12:00-13:30', 'GCA 306', 'FTF', '1', 'E063', '%s'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                    System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'TCW 0005', 'Th', '12:00-14:30', 'MSTeams', 'OL', '2', 'E063', '%s'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                    System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'LWR 0009', 'M', '7:00-8:30', 'GCCA 306', 'FTF', '1', 'E064', '%s'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                    System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'LWR 0009', 'Th', '7:00-8:30', 'MSTeams', 'OL', '2', 'E064', '%s'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                    System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'PATHFIT 2', 'T', '9:00-11:00', 'UAC', 'FTF', '1', 'E095', '%s'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);
                    System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'NSTP 4', 'Su', '13:00-16:00', 'GV 306', 'FTF', '1', 'E096', '%s');\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr, status);


                    if(choiceYear == 2)
                        status = "pending";
                    else
                        status = "approved";

                    // 2ND YEAR
                    System.out.print("\n--  >>>>>>>>> 2ND YEAR | 1ST SEM | BLOCK 2");
                    System.out.print("\nINSERT INTO subject_schedule VALUES\n");
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CSC 0212', 'S', '10:00-12:00', 'GCA 306', 'F2F', '1', 'E068', '%s'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, status);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CSC 0212.1', 'Th', '18:00-21:00', 'COMP LAB 3', 'F2F', '1', 'E068', '%s'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, status);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'ETH 0008', 'T', '18:00-19:30', 'MSTeams', 'OL', '1', 'E078', '%s'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, status);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'ETH 0008', 'F', '19:30-21:00', 'GV 203', 'F2F', '2', 'E078', '%s'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, status);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'UTS 0003', 'T', '14:30-16:00', 'MSTeams', 'OL', '1', 'E079', '%s'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, status);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'UTS 0003', 'F', '13:00-14:30', 'GV 203', 'F2F', '2', 'E079', '%s'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, status);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CSC 0224', 'Th', '10:00-13:00', 'COMP LAB 2', 'F2F', '1', 'E080', '%s'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, status);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CSC 0213', 'F', '10:00-12:00', 'COMP LAB 3', 'F2F', '1', 'E072', '%s'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, status);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CSC 0213.1', 'S', '7:00-10:00', 'GV 306', 'F2F', '1', 'E072', '%s'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, status);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'ICC 0105', 'W', '13:00-14:00', 'MSTeams', 'OL', '1', 'E073', '%s'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, status);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'ICC 0105.1', 'W', '18:00-21:00', 'MSTeams', 'OL', '1', 'E073', '%s'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, status);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'ICC 0105', 'S', '13:00-14:00', 'COMP LAB 4', 'F2F', '2', 'E073', '%s'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, status);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'ITE 0001', 'T', '8:30-10:00', 'MSTeams', 'OL', '1', 'E061', '%s'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, status);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'ITE 0001', 'F', '8:30-10:00', 'GCA 306', 'F2F', '2', 'E061', '%s');\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, status);

                    if(choiceYear != 2) {    // second years can't enroll for 2nd sem of current sem yet or further years
                        System.out.print("\n--  >>>>>>>>> 2ND YEAR | 2ND SEM | BLOCK 2");
                        System.out.print("\nINSERT INTO subject_schedule VALUES\n");

                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'ICC 0106', 'M', '12:00-13:00', 'GCA 306', 'F2F', '1', 'E061', '%s'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, status);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'ICC 0106', 'W', '12:00-13:00', 'MSTeams', 'OL', '2', 'E061', '%s'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, status);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'ICC 0106.1', 'Th', '7:00-8:30', 'GV 304', 'F2F', '1', 'E061', '%s'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, status);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'ICC 0106.1', 'Th', '8:30-10:00', 'COMP LAB 3', 'F2F', '2', 'E061', '%s'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, status);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CSC 0222', 'F', '9:00-11:00', 'GCA 306', 'F2F', '1', 'E072', '%s'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, status);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CSC 0222.1', 'Th', '9:00-12:00', 'COMP LAB 3', 'F2F', '2', 'E072', '%s'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, status);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CSC 0221', 'T', '19:00-20:30', 'GCA 306', 'F2F', '1', 'E068', '%s'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, status);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CSC 0221', 'S', '16:30-18:00', 'MSTeams', 'OL', '2', 'E068', '%s'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, status);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CSC 0316', 'T', '7:00-8:30', 'MSTeams', 'OL', '1', 'E074', '%s'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, status);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CSC 0316', 'F', '7:00-8:30', 'COMP LAB 3', 'F2F', '2', 'E074', '%s'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, status);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'AAP 0007', 'T', '9:00-10:30', 'DRAWINGRM1', 'F2F', '1', 'E075', '%s'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, status);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'AAP 0007', 'F', '9:00-10:30', 'MSTeams', 'OL', '2', 'E075', '%s'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, status);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'GES 0013', 'Th', '16:00-19:00', 'GEE 205', 'F2F', '1', 'E076', '%s'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, status);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CBM 0016', 'Th', '13:00-16:00', 'MSTeams', 'OL', '1', 'E077', '%s');\n",
                                SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr, status);


                        if(choiceYear == 3)
                            status = "pending";
                        else
                            status = "approved";

                        // 3RD YEAR
                        System.out.print("\n--  >>>>>>>>> 3RD YEAR | 1ST SEM | BLOCK 2");
                        System.out.print("\nINSERT INTO subject_schedule VALUES\n");

                        System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CSC 0311', 'T', '18:00-21:00', 'GV 307', 'F2F', '1', 'E082', '%s'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, status);
                        System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CSC 0312.1', 'W', '14:00-17:00', 'COMP LAB 2', 'F2F', '1', 'E083', '%s'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, status);
                        System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CSC 0312', 'F', '13:00-15:00', 'COMP LAB 3', 'F2F', '1', 'E083', '%s'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, status);
                        System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CSC 0313', 'Th', '16:00-18:00', 'COMP LAB 4', 'F2F', '1', 'E085', '%s'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, status);
                        System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CSC 0313.1', 'Th', '18:00-21:00', 'COMP LAB 3', 'F2F', '1', 'E085', '%s'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, status);
                        System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CSC 0314', 'Th', '13:00-15:00', 'GCA 309', 'F2F', '1', 'E055', '%s'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, status);
                        System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CSC 0314.1', 'M', '9:00-12:00', 'GV 306', 'F2F', '1', 'E055', '%s'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, status);
                        System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CSC 0315', 'W', '12:00-14:00', 'COMP LAB 2', 'F2F', '1', 'E084', '%s'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, status);
                        System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CSC 0315.1', 'M', '13:00-16:00', 'COMP LAB 4', 'F2F', '1', 'E082', '%s');\n",
                                SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, status);

                        if(choiceYear != 3) { // third years can't enroll for 2nd sem of current sem yet or further years
                            System.out.print("\n--  >>>>>>>>> 3RD YEAR | 2ND SEM | BLOCK 2");
                            System.out.print("\nINSERT INTO subject_schedule VALUES\n");

                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CSC 0321', 'M', '12:00-13:00', 'MSTeams', 'OL', '1', 'E085', '%s'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, status);
                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CSC 0321', 'W', '12:00-13:00', 'COMP LAB 3', 'F2F', '2', 'E085', '%s'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, status);
                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CSC 0321.1', 'W', '7:00-10:00', 'COMP LAB 4', 'F2F', '1', 'E085', '%s'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, status);
                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CSC 0322', 'M', '16:00-17:00', 'MSTeams', 'OL', '1', 'E086', '%s'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, status);
                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CSC 0322', 'Th', '16:00-17:00', 'GCA DL 1', 'F2F', '2', 'E086', '%s'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, status);
                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CSC 0322.1', 'T', '16:30-18:00', 'COMP LAB 3', 'F2F', '1', 'E086', '%s'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, status);
                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CSC 0322.1', 'T', '15:00-16:30', 'COMP LAB 3', 'F2F', '2', 'E086', '%s'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, status);
                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CSC 0323', 'M', '19:30-20:30', 'MSTeams', 'OL', '1', 'E068', '%s'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, status);
                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CSC 0323', 'F', '20:30-21:30', 'GCA 306', 'F2F', '2', 'E068', '%s'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, status);
                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CSC 0323.1', 'S', '12:30-13:30', 'COMP LAB 3', 'F2F', '1', 'E068', '%s'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, status);
                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CSC 0324', 'T', '19:00-20:00', 'MSTeams', 'OL', '1', 'E072', '%s'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, status);
                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CSC 0324', 'F', '19:00-20:00', 'DRAWINGRM1', 'F2F', '2', 'E072', '%s'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, status);
                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CSC 0324.1', 'S', '14:00-17:00', 'COMP LAB 3', 'F2F', '1', 'E072', '%s'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, status);
                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CSC 0325', 'M', '9:30-11:00', 'MSTeams', 'OL', '1', 'E087', '%s'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, status);
                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CSC 0325', 'Th', '9:30-11:00', 'GCA 306', 'F2F', '2', 'E087', '%s');\n",
                                    SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr, status);


                            if(choiceYear == 4)
                                status = "pending";
                            else
                                status = "approved";

                            // 4TH YEAR 1ST SEM
                            System.out.print("\n--  >>>>>>>>> 4TH YEAR | 1ST SEM | BLOCK 2");
                            System.out.print("\nINSERT INTO subject_schedule VALUES\n");

                            System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CSC 0411', 'T', '7:00-8:30', 'GCA 306', 'F2F', '1', 'E085', '%s'),\n",
                            SY_4thYr, sy_std_num, (stdNum + blkStdNo), block_no_4thYr, status);
                            System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CSC 0411', 'F', '7:00-8:30', 'GCA 306', 'F2F', '2', 'E085', '%s'),\n",
                            SY_4thYr, sy_std_num, (stdNum + blkStdNo), block_no_4thYr, status);
                            System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CSC 0413', 'W', '18:00-20:00', 'GCA 306', 'F2F', '1', 'E023', '%s'),\n",
                            SY_4thYr, sy_std_num, (stdNum + blkStdNo), block_no_4thYr, status);
                            System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CSC 0413.1', 'T', '18:00-21:00', 'COMP LAB 3', 'F2F', '1', 'E023', '%s'),\n",
                            SY_4thYr, sy_std_num, (stdNum + blkStdNo), block_no_4thYr, status);
                            System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CSC 0414', 'M', '7:00-8:30', 'COMP LAB 3', 'F2F', '1', 'E045', '%s'),\n",
                            SY_4thYr, sy_std_num, (stdNum + blkStdNo), block_no_4thYr, status);
                            System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CSC 0414', 'Th', '7:00-8:30', 'COMP LAB 3', 'F2F', '2', 'E045', '%s'),\n",
                            SY_4thYr, sy_std_num, (stdNum + blkStdNo), block_no_4thYr, status);
                            System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CSC 0414.1', 'Th', '18:00-21:00', 'FIELD', 'F2F', '1', 'E088', '%s'),\n",
                            SY_4thYr, sy_std_num, (stdNum + blkStdNo), block_no_4thYr, status);
                            System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CSC 0412', 'W', '9:00-11:00', 'GCA 307', 'F2F', '1', 'E074', '%s'),\n",
                            SY_4thYr, sy_std_num, (stdNum + blkStdNo), block_no_4thYr, status);
                            System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CSC 0412.1', 'T', '10:00-13:00', 'COMP LAB 4', 'F2F', '1', 'E074', '%s');\n",
                                    SY_4thYr, sy_std_num, (stdNum + blkStdNo), block_no_4thYr, status);
                        }
                    }
                }
            }
        }
    }
}
