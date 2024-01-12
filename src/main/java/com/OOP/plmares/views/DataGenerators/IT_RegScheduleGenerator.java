package com.OOP.plmares.views.DataGenerators;

public class IT_RegScheduleGenerator {

    public static String syYearLvlBasis(int currStartYear, int currEndYear,  int subtrahend){
        return (currStartYear - subtrahend) + "-" + (currEndYear - subtrahend);
    }

    public void itGenerateSched(int currStartYear, int currEndYear, int  choiceYear, int choiceBlock, int blkLimit) {

        /* START: SETUP

        int currStartYear = 2023, currEndYear = 2024,    // change to what current school year you want to set
                choiceYear = 3, choiceBlock = 2,         // change to what year lvl and block u want to generate
                stdNum = 20000,                          // change to std format after sch yr

                blkLimit = 5,      // change number of students per block (make sure this corresponds to you already existing student data)
                blkStdNoStart,     //this should be adjusted on the course division


                intCTR;

        END: SETUP */

        int intCTR, stdNum = 20000, blkStdNoStart = 0;
        String sy_std_num = "",
                block_no_4thYr = "", block_no_3rdYr = "", block_no_2ndYr = "", block_no_1stYr = "",
                SY_4thYr = "", SY_3rdYr = "", SY_2ndYr = "", SY_1stYr = "";



        // change sections based on block number
        switch (choiceBlock){
            case 1:
                block_no_1stYr = "IT11";
                block_no_2ndYr = "IT21";
                block_no_3rdYr = "IT31";
                block_no_4thYr = "IT41";
                blkStdNoStart = 31;
                break;
            case 2:
                block_no_1stYr = "IT12";
                block_no_2ndYr = "IT22";
                block_no_3rdYr = "IT32";
                block_no_4thYr = "IT42";
                blkStdNoStart = 46;
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
            int blkStdNo = blkStdNoStart;
            for (intCTR = 0; intCTR < blkLimit; blkStdNo++, intCTR++) {
                    System.out.print("\n-- [STUDENT " + blkStdNo +" - BLOCK " + choiceBlock + "]");

                    // 1ST YEAR
                    System.out.print("\n-- >>>>>>>>> 1ST YEAR | 1ST SEM | BLOCK 1");
                    System.out.print("\nINSERT INTO subject_schedule VALUES\n");

                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'STS 0002', 'M', '9:00-10:30', 'MSTeams', 'OL', '1', 'E001', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'STS 0002', 'M', '10:30-12:00', 'MSTeams', 'OL', '2', 'E001', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'AAP 0007', 'T', '9:00-10:00', 'MSTeams', 'OL', '1', 'E002', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'AAP 0007', 'T', '10:00-12:00', 'MSTeams', 'OL', '2', 'E002', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'PCM 0006', 'M', '13:00-16:00', 'MSTeams', 'OL', '1', 'E003', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'MMW 0001', 'W', '15:00-16:00', 'MSTeams', 'OL', '1', 'E004', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'MMW 0001', 'W', '16:00-18:00', 'MSTeams', 'OL', '2', 'E004', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'IPP 0010', 'T', '12:00-15:00', 'MSTeams', 'OL', '1', 'E005', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'ICC 0101', 'W', '18:30-19:30', 'MSTeams', 'OL', '1', 'E006', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'ICC 0101', 'W', '19:30-20:30', 'MSTeams', 'OL', '2', 'E006', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'ICC 0101.1', 'F', '17:30-19:00', 'MSTeams', 'OL', '1', 'E007', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'ICC 0101.1', 'S', '17:30-19:00', 'MSTeams', 'OL', '2', 'E007', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'ICC 0102', 'T', '15:00-16:00', 'MSTeams', 'OL', '1', 'E008', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'ICC 0102', 'T', '16:00-17:00', 'MSTeams', 'OL', '2', 'E008', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'ICC 0102.1', 'T', '18:00-19:30', 'MSTeams', 'OL', '1', 'E008', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'ICC 0102.1', 'T', '19:30-21:00', 'MSTeams', 'OL', '2', 'E008', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'NSTP 3', 'Su', '8:30-10:00', 'MSTeams', 'OL', '1', 'E090', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'PATHFIT 1', 'M', '7:00-9:00', 'MSTeams', 'OL', '1', 'E094', 'approved');\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);


                    if(choiceYear != 1) {  // first years can't enroll for 2nd sem of current sem yet or further years
                        System.out.print("\n-- >>>>>>>>> 1ST YEAR | 2ND SEM | BLOCK 1");
                        System.out.print("\nINSERT INTO subject_schedule VALUES\n");

                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CET 0111', 'M', '17:00-18:30', 'GEE 304', 'FTF', '1', 'E017', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CET 0111', 'Th', '17:00-18:30', 'MSTeams', 'OL', '2', 'E017', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CET 0114', 'M', '11:30-13:00', 'MSTeams', 'OL', '1', 'E018', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CET 0114', 'Th', '11:30-13:00', 'GCA 307', 'FTF', '2', 'E018', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CET 0114.1', 'Th', '13:00-16:00', 'GEE 307', 'FTF', '1', 'E019', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'EIT 0121', 'W', '7:00-8:00', 'MSTeams', 'OL', '1', 'E020', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'EIT 0121', 'S', '7:00-8:00', 'COMP LAB 2', 'FTF', '2', 'E020', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'EIT 0121.1', 'S', '8:00-11:00', 'COMP LAB 2', 'FTF', '1', 'E020', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'EIT 0122', 'W', '13:00-14:30', 'GCA 307', 'FTF', '1', 'E021', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'EIT 0122', 'S', '13:00-14:30', 'MSTeams', 'OL', '2', 'E021', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'EIT 0123', 'W', '17:00-18:00', 'GCA 305', 'FTF', '1', 'E054', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'EIT 0123', 'S', '17:00-18:00', 'MSTeams', 'OL', '2', 'E054', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'EIT 0123.1', 'W', '18:00-21:00', 'COMP LAB 3', 'FTF', '1', 'E023', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'ICC 0103', 'M', '7:00-8:00', 'COMP LAB 4', 'FTF', '1', 'E024', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'ICC 0103', 'Th', '7:00-8:00', 'MSTeams', 'OL', '2', 'E024', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'ICC 0103.1', 'M', '8:00-11:00', 'COMP LAB 2', 'FTF', '1', 'E023', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'GTB121', 'W', '11:30-13:00', 'GCA 307', 'FTF', '1', 'E025', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'GTB121', 'S', '11:30-13:00', 'MSTeams', 'OL', '2', 'E025', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'NSTP 4', 'Su', '9:30-12:00', 'MSTeams', 'OL', '1', 'E090', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'PATHFIT 2', 'M', '13:00-15:00', 'MSTeams', 'OL', '1', 'E094', 'approved');\n",
                                SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);



                        // 2ND YEAR
                        System.out.print("\n--  >>>>>>>>> 2ND YEAR | 1ST SEM | BLOCK 1");
                        System.out.print("\nINSERT INTO subject_schedule VALUES\n");

                        System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'EIT-ELEC1', 'W', '13:00-14:30', 'GV 309', 'F2F', '1', 'E008', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                        System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'EIT-ELEC1', 'S', '13:00-14:30', 'MSTeams', 'OL', '2', 'E008', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                        System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'ICC 0104', 'W', '16:00-17:00', 'COMP LAB 2', 'F2F', '1', 'E054', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                        System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'ICC 0104', 'S', '16:00-17:00', 'MSTeams', 'OL', '2', 'E054', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                        System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'ICC 0104.1', 'W', '17:00-20:00', 'COMP LAB 2', 'F2F', '1', 'E054', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                        System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'EIT 0211', 'M', '10:30-11:30', 'COMP LAB 2', 'F2F', '1', 'E033', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                        System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'EIT 0211', 'Th', '10:30-11:30', 'MSTeams', 'OL', '2', 'E033', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                        System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'EIT 0211.1', 'M', '11:30-14:30', 'COMP LAB 2', 'F2F', '1', 'E033', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                        System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'TCW 0005', 'M', '17:30-19:00', 'GK 303', 'F2F', '1', 'E034', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                        System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'TCW 0005', 'Th', '17:30-19:00', 'MSTeams', 'OL', '2', 'E034', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                        System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'PPC 121', 'M', '16:00-17:30', 'GCA 307', 'F2F', '1', 'E035', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                        System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'PPC 121', 'Th', '16:00-17:30', 'MSTeams', 'OL', '2', 'E035', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                        System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CET 0225', 'M', '14:30-16:00', 'GL BIOLAB', 'F2F', '1', 'E036', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                        System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CET 0225', 'Th', '14:30-16:00', 'MSTeams', 'OL', '2', 'E036', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                        System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CET 0225.1', 'M', '7:00-10:00', 'GEE 204', 'F2F', '1', 'E036', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                        System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CET 0121', 'W', '7:00-8:30', 'GCA 307', 'F2F', '1', 'E012', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                        System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CET 0121', 'S', '7:00-8:30', 'MSTeams', 'OL', '2', 'E012', 'approved');\n",
                                SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);

                        if(choiceYear != 2) {    // second years can't enroll for 2nd sem of current sem yet or further years
                            System.out.print("\n--  >>>>>>>>> 2ND YEAR | 2ND SEM | BLOCK 1");
                            System.out.print("\nINSERT INTO subject_schedule VALUES\n");

                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'EIT 0212', 'M', '10:00-11:30', 'MSTeams', 'OL', '1', 'E028', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'EIT 0212', 'W', '10:00-11:30', 'GCA 307', 'F2F', '2', 'E028', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'EIT-ELEC2', 'T', '17:00-18:30', 'MSTeams', 'OL', '1', 'E041', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'EIT-ELEC2', 'F', '17:00-18:30', 'GCA 307', 'F2F', '2', 'E041', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'EIT 0221', 'M', '12:00-13:30', 'MSTeams', 'OL', '1', 'E042', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'EIT 0221', 'W', '12:00-13:30', 'GCA 305', 'F2F', '2', 'E042', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'ICC 0105', 'Th', '16:00-17:00', 'GCA 305', 'F2F', '1', 'E054', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'ICC 0105', 'S', '16:00-17:00', 'MSTeams', 'OL', '2', 'E054', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'ICC 0105.1', 'Th', '18:00-21:00', 'COMP LAB 2', 'F2F', '1', 'E054', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'EIT 0222', 'M', '16:00-17:00', 'GCA 307', 'F2F', '1', 'E043', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'EIT 0222', 'W', '16:00-17:00', 'MSTeams', 'OL', '2', 'E043', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'EIT 0222.1', 'M', '17:00-20:00', 'COMP LAB 2', 'F2F', '1', 'E045', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'RPH 0004', 'Th', '8:30-10:00', 'GCA 307', 'F2F', '1', 'E044', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'RPH 0004', 'S', '8:30-10:00', 'MSTeams', 'OL', '2', 'E044', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'GES 0013', 'Th', '12:00-13:30', 'GCA 307', 'F2F', '1', 'E046', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'GES 0013', 'S', '12:00-13:30', 'MSTeams', 'OL', '2', 'E046', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'UTS 0003', 'Th', '10:00-11:30', 'GCA 307', 'F2F', '1', 'E047', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'UTS 0003', 'S', '10:00-11:30', 'MSTeams', 'OL', '2', 'E047', 'approved');\n",
                                    SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);


                            // 3RD YEAR
                            System.out.print("\n--  >>>>>>>>> 3RD YEAR | 1ST SEM | BLOCK 1");
                            System.out.print("\nINSERT INTO subject_schedule VALUES\n");

                            System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'ICC 0335', 'T', '4:30-5:30', 'MSTeams', 'OL', '1', 'E024', 'approved'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr);
                            System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'ICC 0335', 'W', '4:30-5:30', 'MSTeams', 'OL', '2', 'E024', 'approved'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr);
                            System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'ICC 0335.1', 'Th', '18:00-21:00', 'COMP LAB', 'FTF', '1', 'E048', 'approved'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr);
                            System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'EIT 0311', 'T', '18:00-19:00', 'MSTeams', 'OL', '1', 'E024', 'approved'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr);
                            System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'EIT 0311.1', 'Th', '11:00-14:00', 'COMP LAB 2', 'FTF', '1', 'E045', 'approved'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr);
                            System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'EIT 0312', 'T', '11:00-12:00', 'MSTeams', 'OL', '1', 'E049', 'approved'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr);
                            System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'EIT 0312', 'T', '12:00-13:00', 'MSTeams', 'OL', '2', 'E049', 'approved'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr);
                            System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'EIT 0312.1', 'M', '18:00-21:00', 'COMP LAB 2', 'FTF', '1', 'E049', 'approved'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr);
                            System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'EIT-ELEC3', 'T', '13:00-14:30', 'MSTeams', 'OL', '1', 'E021', 'approved'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr);
                            System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'EIT-ELEC3', 'T', '14:30-16:30', 'MSTeams', 'OL', '2', 'E021', 'approved'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr);
                            System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'LWR 0009', 'T', '7:00-8:30', 'MSTeams', 'OL', '1', 'E050', 'approved');\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr);


                            if(choiceYear != 3) { // third years can't enroll for 2nd sem of current sem yet or further years
                                System.out.print("\n--  >>>>>>>>> 3RD YEAR | 2ND SEM | BLOCK 1");
                                System.out.print("\nINSERT INTO subject_schedule VALUES\n");

                                System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'EIT 0321', 'T', '7:00-8:00', 'GCA 306', 'FTF', '1', 'E037', 'approved'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr);
                                System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'EIT 0321', 'F', '7:00-8:00', 'MSTeams', 'OL', '2', 'E037', 'approved'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr);
                                System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'EIT 0321.1', 'T', '8:00-11:00', 'GVENLAB 2', 'FTF', '1', 'E037', 'approved'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr);
                                System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'EIT 0323', 'T', '13:00-14:00', 'COMP LAB 2', 'FTF', '1', 'E049', 'approved'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr);
                                System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'EIT 0323', 'F', '13:00-14:00', 'MSTeams', 'OL', '2', 'E049', 'approved'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr);
                                System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'EIT 0323.1', 'T', '14:00-17:00', 'COMP LAB 2', 'OL', '2', 'E049', 'approved'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr);
                                System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'EIT 0322', 'W', '13:00-14:00', 'MSTeams', 'OL', '1', 'E028', 'approved'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr);
                                System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'EIT 0322', 'S', '13:00-14:00', 'COMP LAB 2', 'FTF', '2', 'E028', 'approved'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr);
                                System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'EIT 0322.1', 'S', '14:00-17:00', 'COMP LAB 2', 'FTF', '1', 'E028', 'approved'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr);
                                System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'ETH 0008', 'M', '12:30-14:00', 'MSTeams', 'OL', '1', 'E051', 'approved'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr);
                                System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'ETH 0008', 'Th', '12:30-14:00', 'GCA 307', 'FTF', '2', 'E051', 'approved');\n",
                                        SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr);


                                // 4TH YEAR 1ST SEM
                                System.out.print("\n--  >>>>>>>>> 4TH YEAR | 1ST SEM | BLOCK 1");
                                System.out.print("\nINSERT INTO subject_schedule VALUES\n");

                                System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CAP 0102', 'W', '10:00-11:30', 'FIELD', 'F2F', '1', 'E049', 'approved'),\n",
                            SY_4thYr, sy_std_num, (stdNum + blkStdNo), block_no_4thYr);
                                System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CAP 0102', 'S', '10:00-11:30', 'MSTeams', 'OL', '2', 'E049', 'approved'),\n",
                            SY_4thYr, sy_std_num, (stdNum + blkStdNo), block_no_4thYr);
                                System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'EIT-ELEC6', 'W', '14:30-16:00', 'FIELD', 'F2F', '1', 'E028', 'approved'),\n",
                            SY_4thYr, sy_std_num, (stdNum + blkStdNo), block_no_4thYr);
                                System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'EIT-ELEC6', 'S', '14:30-16:00', 'MSTeams', 'OL', '2', 'E028', 'approved'),\n",
                            SY_4thYr, sy_std_num, (stdNum + blkStdNo), block_no_4thYr);
                                System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'EIT-ELEC5', 'W', '16:00-17:00', 'FIELD', 'F2F', '1', 'E013', 'approved'),\n",
                            SY_4thYr, sy_std_num, (stdNum + blkStdNo), block_no_4thYr);
                                System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'EIT-ELEC5', 'S', '16:00-17:00', 'MSTeams', 'OL', '2', 'E013', 'approved'),\n",
                            SY_4thYr, sy_std_num, (stdNum + blkStdNo), block_no_4thYr);
                                System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'EIT-ELEC4', 'S', '17:00-20:30', 'FIELD', 'F2F', '1', 'E022', 'approved');\n",
                                        SY_4thYr, sy_std_num, (stdNum + blkStdNo), block_no_4thYr);
                            }
                        }
                    }
                }
            }

        if(choiceBlock == 2) {
            int blkStdNo = blkStdNoStart;
            for (intCTR = 0; intCTR < blkLimit; blkStdNo++, intCTR++) {
                System.out.print("\n-- [STUDENT " + blkStdNo +" - BLOCK " + choiceBlock + "]");

                // 1ST YEAR
                System.out.print("\n-- >>>>>>>>> 1ST YEAR | 1ST SEM | BLOCK 2");
                System.out.print("\nINSERT INTO subject_schedule VALUES\n");

                System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'STS 0002', 'W', '7:00-8:30', 'MSTeams', 'OL', '1', 'E009', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'STS 0002', 'W', '8:30-10:00', 'MSTeams', 'OL', '2', 'E009', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'AAP 0007', 'T', '12:30-14:00', 'MSTeams', 'OL', '1', 'E010', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'AAP 0007', 'T', '14:00-15:30', 'MSTeams', 'OL', '2', 'E010', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'PCM 0006', 'W', '12:30-14:00', 'MSTeams', 'OL', '1', 'E011', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'PCM 0006', 'W', '14:00-15:30', 'MSTeams', 'OL', '2', 'E011', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'MMW 0001', 'Th', '8:30-10:00', 'MSTeams', 'OL', '1', 'E012', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'MMW 0001', 'Th', '10:00-11:30', 'MSTeams', 'OL', '2', 'E012', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'IPP 0010', 'W', '17:00-20:00', 'MSTeams', 'OL', '1', 'E013', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'ICC 0101', 'M', '7:00-8:00', 'MSTeams', 'OL', '1', 'E014', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'ICC 0101', 'M', '8:00-9:00', 'MSTeams', 'OL', '2', 'E014', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'ICC 0101.1', 'F', '19:00-20:30', 'MSTeams', 'OL', '1', 'E007', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'ICC 0101.1', 'S', '19:00-20:30', 'MSTeams', 'OL', '2', 'E007', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'ICC 0102', 'M', '17:00-18:00', 'MSTeams', 'OL', '1', 'E015', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'ICC 0102', 'T', '17:00-18:00', 'MSTeams', 'OL', '2', 'E015', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'ICC 0102.1', 'T', '18:00-19:30', 'MSTeams', 'OL', '1', 'E016', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'ICC 0102.1', 'T', '19:30-21:00', 'MSTeams', 'OL', '2', 'E016', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'NSTP 3', 'Su', '9:30-12:00', 'MSTeams', 'OL', '1', 'E090', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'PATHFIT 1', 'M', '13:00-15:00', 'MSTeams', 'OL', '1', 'E094', 'approved');\n",
                        SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);


                if(choiceYear != 1) {  // first years can't enroll for 2nd sem of current sem yet or further years
                    System.out.print("\n-- >>>>>>>>> 1ST YEAR | 2ND SEM | BLOCK 2");
                    System.out.print("\nINSERT INTO subject_schedule VALUES\n");

                    System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CET 0111', 'M', '16:00-17:30', 'GEE 305', 'FTF', '1', 'E026', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                    System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CET 0111', 'Th', '16:00-17:30', 'MSTeams', 'OL', '2', 'E026', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                    System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CET 0114', 'W', '8:30-10:00', 'MSTeams', 'OL', '1', 'E019', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                    System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CET 0114', 'S', '8:30-10:00', 'GCA 307', 'FTF', '2', 'E019', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                    System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'CET 0114.1', 'S', '10:0-13:00', 'GCA 307', 'FTF', '1', 'E027', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                    System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'EIT 0121', 'T', '12:00-13:00', 'MSTeams', 'OL', '1', 'E028', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                    System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'EIT 0121', 'F', '12:00-13:00', 'COMP LAB 2', 'FTF', '2', 'E028', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                    System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'EIT 0121.1', 'F', '13:00-16:00', 'COMP LAB 2', 'FTF', '1', 'E020', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                    System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'EIT 0122', 'W', '15:00-16:30', 'GCA 307', 'FTF', '1', 'E007', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                    System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'EIT 0122', 'S', '15:00-16:30', 'MSTeams', 'OL', '2', 'E007', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                    System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'EIT 0123', 'T', '17:00-18:00', 'GCA 305', 'FTF', '1', 'E008', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                    System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'EIT 0123', 'F', '17:00-18:00', 'MSTeams', 'OL', '2', 'E008', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                    System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'EIT 0123.1', 'T', '18:00-21:00', 'COMP LAB 4', 'FTF', '1', 'E023', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                    System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'ICC 0103', 'M', '11:00-12:00', 'COMP LAB 4', 'FTF', '1', 'E024', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                    System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'ICC 0103', 'Th', '11:00-12:00', 'MSTeams', 'OL', '2', 'E024', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                    System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'ICC 0103.1', 'M', '12:00-15:00', 'COMP LAB 4', 'FTF', '1', 'E029', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                    System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'GTB121', 'T', '7:00-8:30', 'GCA 307', 'FTF', '1', 'E029', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                    System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'NSTP 4', 'Su', '9:30-12:00', 'MSTeams', 'OL', '1', 'E090', 'approved'),\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);
                    System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'PATHFIT 2', 'M', '13:00-15:00', 'MSTeams', 'OL', '1', 'E094', 'approved');\n",
                            SY_1stYr, sy_std_num, (stdNum + blkStdNo), block_no_1stYr);



                    // 2ND YEAR
                    System.out.print("\n--  >>>>>>>>> 2ND YEAR | 1ST SEM | BLOCK 2");
                    System.out.print("\nINSERT INTO subject_schedule VALUES\n");

                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'EIT-ELEC1', 'W', '11:30-13:00', 'GV 309', 'F2F', '1', 'E037', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'EIT-ELEC1', 'S', '11:30-13:00', 'MSTeams', 'OL', '2', 'E037', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'ICC 0104', 'W', '7:00-8:00', 'COMP LAB 2', 'F2F', '1', 'E015', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'ICC 0104', 'S', '7:00-8:00', 'MSTeams', 'OL', '2', 'E015', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'ICC 0104.1', 'W', '8:00-11:00', 'DRAWINGRM2', 'F2F', '1', 'E015', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'EIT 0211', 'M', '14:30-15:30', 'COMP LAB 2', 'F2F', '1', 'E033', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'EIT 0211', 'Th', '14:30-15:30', 'MSTeams', 'OL', '2', 'E033', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'EIT 0211.1', 'M', '15:30-18:30', 'COMP LAB 2', 'F2F', '1', 'E033', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'TCW 0005', 'M', '11:30-13:00', 'GK 303', 'F2F', '1', 'E038', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'TCW 0005', 'Th', '11:30-13:00', 'MSTeams', 'OL', '2', 'E038', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'PPC 121', 'T', '17:30-19:00', 'GCA 307', 'F2F', '1', 'E039', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'PPC 121', 'F', '17:30-19:00', 'MSTeams', 'OL', '2', 'E039', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CET 0225', 'T', '10:00-11:30', 'GL 302', 'F2F', '1', 'E036', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CET 0225', 'F', '10:00-11:30', 'MSTeams', 'OL', '2', 'E036', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CET 0225.1', 'T', '13:00-16:00', 'GL PHYLAB', 'F2F', '1', 'E036', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CET 0121', 'M', '7:00-8:30', 'GCA 307', 'F2F', '1', 'E040', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                    System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CET 0121', 'Th', '7:00-8:30', 'MSTeams', 'OL', '2', 'E040', 'approved');\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);



                    if(choiceYear != 2) {    // second years can't enroll for 2nd sem of current sem yet or further years
                        System.out.print("\n--  >>>>>>>>> 2ND YEAR | 2ND SEM | BLOCK 2");
                        System.out.print("\nINSERT INTO subject_schedule VALUES\n");

                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'EIT 0212', 'T', '10:00-11:30', 'MSTeams', 'OL', '1', 'E028', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'EIT 0212', 'F', '10:00-11:30', 'GCA 307', 'F2F', '2', 'E028', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'EIT-ELEC2', 'M', '17:00-18:30', 'MSTeams', 'OL', '1', 'E041', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'EIT-ELEC2', 'Th', '17:00-18:30', 'GCA 307', 'F2F', '2', 'E041', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'EIT 0221', 'T', '12:00-13:30', 'MSTeams', 'OL', '1', 'E042', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'EIT 0221', 'F', '12:00-13:30', 'GCA 305', 'F2F', '2', 'E042', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'ICC 0105', 'W', '16:00-17:00', 'GCA 305', 'F2F', '1', 'E054', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'ICC 0105', 'S', '16:00-17:00', 'MSTeams', 'OL', '2', 'E054', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'ICC 0105.1', 'W', '18:00-21:00', 'COMP LAB 2', 'F2F', '1', 'E054', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'EIT 0222', 'T', '16:00-17:00', 'GCA 307', 'F2F', '1', 'E043', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'EIT 0222', 'F', '16:00-17:00', 'MSTeams', 'OL', '2', 'E043', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'EIT 0222.1', 'T', '17:00-20:00', 'COMP LAB 2', 'F2F', '1', 'E045', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'RPH 0004', 'W', '8:30-10:00', 'GCA 307', 'F2F', '1', 'E044', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'RPH 0004', 'S', '8:30-10:00', 'MSTeams', 'OL', '2', 'E044', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'GES 0013', 'W', '12:00-13:30', 'GCA 307', 'F2F', '1', 'E046', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'GES 0013', 'S', '12:00-13:30', 'MSTeams', 'OL', '2', 'E046', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'UTS 0003', 'W', '10:00-11:30', 'GCA 307', 'F2F', '1', 'E047', 'approved'),\n",
                            SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);
                        System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'UTS 0003', 'S', '10:00-11:30', 'MSTeams', 'OL', '2', 'E047', 'approved');\n",
                                SY_2ndYr, sy_std_num, (stdNum + blkStdNo), block_no_2ndYr);


                        // 3RD YEAR
                        System.out.print("\n--  >>>>>>>>> 3RD YEAR | 1ST SEM | BLOCK 2");
                        System.out.print("\nINSERT INTO subject_schedule VALUES\n");

                        System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'ICC 0335', 'T', '9:00-10:00', 'MSTeams', 'OL', '1', 'E024', 'approved'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr);
                        System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'ICC 0335', 'T', '10:00-11:00', 'MSTeams', 'OL', '2', 'E024', 'approved'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr);
                        System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'ICC 0335.1', 'S', '7:00-10:00', 'COMP LAB 2', 'FTF', '1', 'E048', 'approved'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr);
                        System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'EIT 0311', 'T', '11:00-12:00', 'MSTeams', 'OL', '1', 'E045', 'approved'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr);
                        System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'EIT 0311', 'T', '12:00-13:00', 'MSTeams', 'OL', '2', 'E045', 'approved'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr);
                        System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'EIT 0311.1', 'S', '7:00-10:00', 'COMP LAB 2', 'FTF', '1', 'E048', 'approved'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr);
                        System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'EIT 0312', 'T', '7:00-8:00', 'MSTeams', 'OL', '1', 'E049', 'approved'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr);
                        System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'EIT 0312', 'T', '8:00-9:00', 'MSTeams', 'OL', '2', 'E049', 'approved'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr);
                        System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'EIT 0312.1', 'M', '7:00-10:00', 'COMP LAB 2', 'FTF', '1', 'E049', 'approved'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr);
                        System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'EIT-ELEC3', 'F', '16:30-19:00', 'MSTeams', 'OL', '1', 'E021', 'approved'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr);
                        System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'EIT-ELEC3', 'S', '16:30-21:00', 'MSTeams', 'OL', '2', 'E021', 'approved'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr);
                        System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'LWR 0009', 'W', '16:00-17:30', 'MSTeams', 'OL', '1', 'E050', 'approved'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr);
                        System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'LWR 0009', 'S', '16:00-17:30', 'MSTeams', 'OL', '2', 'E050', 'approved');\n",
                                SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr);

                        if(choiceYear != 3) { // third years can't enroll for 2nd sem of current sem yet or further years
                            System.out.print("\n--  >>>>>>>>> 3RD YEAR | 2ND SEM | BLOCK 2");
                            System.out.print("\nINSERT INTO subject_schedule VALUES\n");

                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'EIT 0321', 'T', '13:00-14:00', 'GCA 307', 'FTF', '1', 'E037', 'approved'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr);
                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'EIT 0321', 'F', '13:00-14:00', 'MSTeams', 'OL', '2', 'E037', 'approved'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr);
                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'EIT 0321.1', 'W', '14:00-17:00', 'COMP LAB', 'FTF', '1', 'E049', 'approved'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr);
                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'EIT 0323', 'W', '13:00-14:00', 'COMP LAB 2', 'FTF', '1', 'E049', 'approved'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr);
                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'EIT 0323', 'S', '13:00-14:00', 'MSTeams', 'OL', '2', 'E049', 'approved'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr);
                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'EIT 0323.1', 'T', '14:00-17:00', 'COMP LAB 2', 'OL', '2', 'E049', 'approved'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr);
                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'EIT 0322', 'M', '17:00-18:00', 'MSTeams', 'OL', '1', 'E028', 'approved'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr);
                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'EIT 0322', 'Th', '17:00-18:00', 'MSTeams', 'OL', '2', 'E028', 'approved'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr);
                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'EIT 0322.1', 'Th', '18:00-21:00', 'GV311', 'FTF', '1', 'E028', 'approved'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr);
                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'ETH 0008', 'M', '14:30-15:30', 'MSTeams', 'OL', '1', 'E051', 'approved'),\n",
                            SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr);
                            System.out.printf("\t('%s', '2', 'CET', '%s-%s', '%s', 'ETH 0008', 'Th', '14:30-15:30', 'GCA 307', 'FTF', '2', 'E051', 'approved');\n",
                                    SY_3rdYr, sy_std_num, (stdNum + blkStdNo), block_no_3rdYr);


                            // 4TH YEAR 1ST SEM
                            System.out.print("\n--  >>>>>>>>> 4TH YEAR | 1ST SEM | BLOCK 2");
                            System.out.print("\nINSERT INTO subject_schedule VALUES\n");

                            System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CAP 0102', 'W', '14:30-16:00', 'FIELD', 'F2F', '1', 'E052', 'approved'),\n",
                            SY_4thYr, sy_std_num, (stdNum + blkStdNo), block_no_4thYr);
                            System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'CAP 0102', 'S', '14:30-16:00', 'MSTeams', 'OL', '2', 'E052', 'approved'),\n",
                            SY_4thYr, sy_std_num, (stdNum + blkStdNo), block_no_4thYr);
                            System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'EIT-ELEC6', 'M', '14:30-16:00', 'FIELD', 'F2F', '1', 'E054', 'approved'),\n",
                            SY_4thYr, sy_std_num, (stdNum + blkStdNo), block_no_4thYr);
                            System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'EIT-ELEC6', 'Th', '14:30-16:00', 'MSTeams', 'OL', '2', 'E054', 'approved'),\n",
                            SY_4thYr, sy_std_num, (stdNum + blkStdNo), block_no_4thYr);
                            System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'EIT-ELEC5', 'W', '13:00-14:30', 'FIELD', 'F2F', '1', 'E053', 'approved'),\n",
                            SY_4thYr, sy_std_num, (stdNum + blkStdNo), block_no_4thYr);
                            System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'EIT-ELEC5', 'S', '13:00-14:30', 'MSTeams', 'OL', '2', 'E053', 'approved'),\n",
                            SY_4thYr, sy_std_num, (stdNum + blkStdNo), block_no_4thYr);
                            System.out.printf("\t('%s', '1', 'CET', '%s-%s', '%s', 'EIT-ELEC4', 'S', '10:00-13:00', 'FIELD', 'F2F', '1', 'E052', 'approved');\n",
                                    SY_4thYr, sy_std_num, (stdNum + blkStdNo), block_no_4thYr);
                        }
                    }
                }
            }
        }
    }
}
