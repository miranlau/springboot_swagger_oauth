package com.tieto.springbootdemo.util;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *
 * @ClassName:  DataFormatorWithValidator
 * @Description:  Defined all kinds of validator for input data.
 *
 * @Copyright:  2017 www.tieto.com Inc. All rights reserved.
 */
public class DataFormatorWithValidator {
//
//    /**
//     * get next date of the input date
//     * @param inDate
//     * @return next day of input date
//     */
//    public static String getNextDate(Date inDate){
//        Calendar cal = Calendar.getInstance();
//        cal.setTimeInMillis(inDate.getTime());
//        cal.add(Calendar.DATE, 1);
//        return  WebifConstant.DATE_FORMATER.format(cal.getTime());
//    }
//
//    /**
//     * get start date by end date and period type
//     * @param endDate The input end date.
//     * @param period The input period type.
//     * @return String Start date format string.
//     */
//    public static String getStartDateByEndDate(Date endDate, PeriodTypeEnum period){
//        Calendar cal = Calendar.getInstance();
//        cal.setTimeInMillis(endDate.getTime());
//        if(period.equals(PeriodTypeEnum.DAY)){
//            cal.add(Calendar.DATE, -1);
//        }else if(period.equals(PeriodTypeEnum.WEEK)){
//            cal.add(Calendar.DATE, -7);
//        }else if(period.equals(PeriodTypeEnum.MONTH)){
//            cal.add(Calendar.MONTH, -1);
//        }else if(period.equals(PeriodTypeEnum.QUARTER)){
//            cal.add(Calendar.MONTH, -3);
//        }else if(period.equals(PeriodTypeEnum.YEAR)){
//            cal.add(Calendar.YEAR, -1);
//        }
//        return WebifConstant.DATE_FORMATER.format(cal.getTime());
//    }
//
//    /**
//     *
//     * get start date by end date and schedule type
//     * @param endDate The input end date.
//     * @param scheduleType The input schedule type.
//     * @return String Start date format string.
//     */
//    public static String getStartDateByEndDate(Date endDate, ScheduleTypeEnum scheduleType){
//        Calendar cal = Calendar.getInstance();
//        cal.setTimeInMillis(endDate.getTime());
//        if(scheduleType.equals(ScheduleTypeEnum.HOURLY)){
//            cal.add(Calendar.HOUR_OF_DAY, -1);
//        }else if(scheduleType.equals(ScheduleTypeEnum.DAILY)){
//            cal.add(Calendar.DATE, -1);
//        }else if(scheduleType.equals(ScheduleTypeEnum.WEEKDAYS)){
//            if(cal.get(Calendar.DAY_OF_WEEK)==2){
//                cal.add(Calendar.DATE, -3);
//            }else{
//                cal.add(Calendar.DATE, -1);
//            }
//        }else if(scheduleType.equals(ScheduleTypeEnum.WEEKLY)){
//            cal.add(Calendar.DATE, -7);
//        }else if(scheduleType.equals(ScheduleTypeEnum.MONTHLY)){
//            cal.add(Calendar.MONTH, -1);
//        }else if(scheduleType.equals(ScheduleTypeEnum.QUARTERLY)){
//            cal.add(Calendar.MONTH, -3);
//        }else if(scheduleType.equals(ScheduleTypeEnum.YEARLY)){
//            cal.add(Calendar.YEAR, -1);
//        }
//        return WebifConstant.DATE_FORMATER.format(cal.getTime());
//    }
//
//    /**
//     * Check does the input string is a blank string or only contained some spaces.
//     * @param str   The string to be check.
//     * @return boolean True stand for the inpurt string is blank. The false means the input string is not blank.
//     */
//    public static boolean isBlank(String str) {
//        if (str != null && str.trim().length() > 0) {
//            return false;
//        }
//        return true;
//    }
//
//    /**
//     * Check does the length of input string over the limit or not.
//     * @param str   The string to be check.
//     * @param length    The length limit use to check the input string.
//     * @return  True means the string is valid. Flase means the input string is over length.
//     */
//    public static boolean strLenghtValidator(String str, int length) {
//        boolean isValid = false;
//        if (str != null && str.trim().length() > 0 && str.trim().length() <= length
//                && !str.trim().equalsIgnoreCase("NULL")) {
//            isValid = true;
//        }
//        return isValid;
//    }
//
//    /**
//     * Check does the input number is in a range or not.
//     * @param value The input number to be check.
//     * @param min   The min value of the range.
//     * @param max   The max valie of the range.
//     * @return  boolean True means the input number value is in the range. False means the input number value is out of the range.
//     */
//    public static boolean intValueValidator(int value, int min, int max) {
//        boolean isValid = false;
//        if (value >= min && value <= max) {
//            isValid = true;
//        }
//        return isValid;
//    }
//
//    /**
//     * Check does the input string number is in a range or not.
//     * @param value The input string number to be check.
//     * @param min   The min value of the range.
//     * @param max   The max valie of the range.
//     * @return  boolean True means the input string number is in the range. False means the input string number is out of the range.
//     */
//    public static boolean strIntValueValidator(String value, int min, int max) {
//        boolean isValid = false;
//        Pattern pattern = Pattern.compile("[0-9]*");
//        Matcher isNum = pattern.matcher(value);
//        if (isNum.matches()) {
//            isValid = intValueValidator(Integer.valueOf(value), min, max);
//        }
//        return isValid;
//    }

}
