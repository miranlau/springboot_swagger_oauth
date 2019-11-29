package com.tieto.springbootdemo.entity.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tieto.springbootdemo.exception.BusinessException;
import com.tieto.springbootdemo.exception.InputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ErrorInformation {
	private static Logger logger = LoggerFactory.getLogger(ErrorInformation.class);
	
	private Timestamp timestamp;
	private int errorcode;
    private String description;
	

    public ErrorInformation() {

	}

	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp time) {
		this.timestamp = time;
	}
	
	public int getErrorcode() {
		return errorcode;
	}
	public void setErrorcode(int error) {
		this.errorcode = error;
	}
	
	public String getDescription() {
        return description;
	}
	public void setDescription(String errorInfo) {
        this.description = errorInfo;
	}

    @JsonIgnore
    public static ErrorInformation invalidParamTypeError(String errorInfo) {
        return new ErrorInformation(1, errorInfo);
    }

    @JsonIgnore
    public static ErrorInformation invalidValueError(String errorInfo) {
        return new ErrorInformation(2, errorInfo);
    }

    @JsonIgnore
    public static ErrorInformation generalError(String errorInfo) {
        return new ErrorInformation(3, errorInfo);
    }

	@JsonIgnore
	public static ErrorInformation generalError(Throwable t) {
    	String errorInfo = "";
    	if (t instanceof SQLException) {
			errorInfo = "DB error";
		} else if (t instanceof BusinessException) {
    		errorInfo = "Business error";
		} else if (t instanceof InputException){
    		errorInfo = "Input is invalid";
		} else {
    		errorInfo = "Unknown error";
		}
		return new ErrorInformation(3, errorInfo);
	}

	@JsonIgnore
    private ErrorInformation(int error, String errorInfo) {
		timestamp = new Timestamp(System.currentTimeMillis());
		errorcode = error;

		if (errorcode == 1) {//invalid parameter type and name
            description = reconstructError(errorInfo);
        }
        else if (errorcode == 2) {//invalid value range, e.g. /ss7/localSSN/hlr: 400.0 is not lower or equal to 254
            description = reconstructErrorForInvalidValueException(errorInfo);
        }
        else if (errorcode == 3) {//errors that do not need translation
            description = errorInfo;
		}
	}
	
	@JsonIgnore
	private String reconstructError(String errorMsg) {
		logger.trace("Receive error msg is {}", errorMsg);
		String firstLineInfo = errorMsg.split("\n")[0];
		
		//Below is try to extract the simplified info
		//Like the error msg looks like "Can not deserialize value of type int from String "a": not a valid Integer value
		//at [Source: org.glassfish.jersey.message.internal.ReaderInterceptorExecutor$UnCloseableInputStream@50d3593b; line: 1, column: 139] (through reference chain: 
	    //com.tieto.tni.rest.resources.JsonContainer["data"]-> com.tieto.tni.configuration.feature.sms.home.HomeConfiguration["smscValidation"]->
		//com.tieto.tni.configuration.feature.sms.home.SmscValidationConfiguration["significantDigits"])"
		//Extract the content included in [], and constructed it like [data.smscValidation.significantDigits] in this example
		/* String regex = "(?<=\\[)(\\S+)(?=\\])"*/
		String reconstructedInfo = firstLineInfo;
		String regex = "(?<=\\[\").*?(?=\"\\])";
		Pattern pattern = Pattern.compile(regex);
		Matcher usefulContent = pattern.matcher(errorMsg);
    	boolean isFirst = true;
    	while(usefulContent.find()) {
    		logger.trace("Found match content is {}", usefulContent.group());
    		if(isFirst) {
    			reconstructedInfo += ": [" + usefulContent.group() ;
    		}else {
    			reconstructedInfo +=  "." + usefulContent.group();
    		}
    		isFirst = false;
    	}
    	reconstructedInfo += "]";
    	
    	logger.trace("Constructed error info is {}", reconstructedInfo);
    	
    	return reconstructedInfo;
	}
	
	@JsonIgnore
	private String reconstructErrorForInvalidValueException(String error) {
		//Used to validate the invalid value when doing the configuration via web,the first line of the whole exception may looks like below:
		//#/ss7/localSSN/hlr: 400.0 is not lower or equal to 254. Reconstruct the info to 
		//#/ss7/localSSN/hlr: 400.0 is not lower or equal to 254: [data.ss7.localSSN.hlr], then front end can focus on the error filed to ss7.localSSN.hlr 
		String regex = "(?<=\\/).*?(?=:)";
    	Pattern pattern = Pattern.compile(regex);
		Matcher usefulContent = pattern.matcher(error);
		String originalError = error;
		String reconstructedInfo = error;
		boolean isFirst = true;
		while(usefulContent.find()) {
    		if(isFirst) {
    			reconstructedInfo = ": [data." + usefulContent.group() ;
    		}else {
    			reconstructedInfo +=  "." + usefulContent.group();
    		}
    		isFirst = false;
    	}
		reconstructedInfo += "]";
    	
    	//Replace the / to .
		reconstructedInfo = originalError + reconstructedInfo.replace('/', '.');
    	
    	logger.trace("Constructed error info is {}", reconstructedInfo);
    	
    	return reconstructedInfo;
	}
	
	@JsonIgnore
	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" timestamp=" + timestamp);
        sb.append(" errorcode=" + errorcode);
        sb.append(" description=" + description);
        return sb.toString();
    }
	
}
