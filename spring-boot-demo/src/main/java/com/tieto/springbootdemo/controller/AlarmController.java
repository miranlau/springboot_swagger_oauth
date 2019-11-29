package com.tieto.springbootdemo.controller;

import com.tieto.springbootdemo.entity.enums.AlarmStatus;
import com.tieto.springbootdemo.entity.vo.ErrorInformation;
import com.tieto.springbootdemo.entity.vo.Response;
import com.tieto.springbootdemo.model.Alarm;
import com.tieto.springbootdemo.service.AlarmService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/api/v2/alarms")
@Api(value = "Alarm Rest API", tags = "Alarm", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class AlarmController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AlarmService alarmService;

    @ApiOperation(value="Create an alarm", notes="Create an alarm by input body",
            authorizations = {
                    @Authorization(value = "oauth2schema", scopes = {
                            @AuthorizationScope(scope = "create", description = "create alarms"),
                            @AuthorizationScope(scope = "trust", description = "create alarms")
                    })
            }
    )
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created"),
    })
    @RequestMapping(method = RequestMethod.POST)
    Response createAlarm(
            @ApiParam(name = "alarm", required = true)
            @NotNull(message = "Alarm should be not null")
            @RequestBody Alarm alarm) {
        logger.info("create an alarm: {}", alarm);
        try {
            alarmService.insertAlarm(alarm);
            return createResponse(HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("create an alarm exception", e);
            return createResponse(HttpStatus.INTERNAL_SERVER_ERROR, ErrorInformation.generalError(e));
        }
    }

    @ApiOperation(value="Update an alarm", notes="Update an alarm by input body",
            authorizations = {
                    @Authorization(value = "oauth2schema", scopes = {
                            @AuthorizationScope(scope = "update", description = "update alarms"),
                            @AuthorizationScope(scope = "trust", description = "create alarms")
                    })
            }
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
    })
    @RequestMapping(method = RequestMethod.PUT)
    Response updateAlarm(@ApiParam(name = "alarm", required = true) @RequestBody Alarm alarm) {
        logger.info("update an alarm: {}", alarm);
        try {
            alarmService.updateAlarm(alarm);
            return createResponse(HttpStatus.OK);
        } catch (Exception e) {
            logger.error("update an alarm exception", e);
            return createResponse(HttpStatus.INTERNAL_SERVER_ERROR, ErrorInformation.generalError(e));
        }
    }

    @ApiOperation(value="Query alarms conditionally", notes="Query alarms conditionally and in pagenation",
            authorizations = {
                    @Authorization(value = "oauth2schema", scopes = {
                            @AuthorizationScope(scope = "query", description = "query alarms"),
                            @AuthorizationScope(scope = "trust", description = "create alarms")
                    })
            }
    )
    @RequestMapping(method = RequestMethod.GET)
    Response queryAlarms(String status, String searchName, String fields, String moduleName, @NotNull int perPage, int page) {
        logger.info("query alarms by status={}, searchName={}, fields={}, moduleName={}, perPage={}, page={}", status, searchName, fields, moduleName, perPage, page);
        try {
            List<Alarm> alarms = alarmService.queryAlarms();
            return createResponse(HttpStatus.OK, alarms);
        } catch (Exception e) {
            logger.error("query alarms exception", e);
            return createResponse(HttpStatus.INTERNAL_SERVER_ERROR, ErrorInformation.generalError(e));
        }
    }

    @ApiOperation(value="Query all alarms", notes="Query all alarms both active and inactive",
            authorizations = {
                    @Authorization(value = "oauth2schema", scopes = {
                            @AuthorizationScope(scope = "query", description = "query alarms"),
                            @AuthorizationScope(scope = "trust", description = "create alarms")
                    })
            }
    )
    @RequestMapping(value="/all", method = RequestMethod.GET)
    ResponseEntity queryAllAlarms() {
        logger.info("query all alarms: {}");
        try {
            List<Alarm> alarms = alarmService.queryAlarms();
            return ResponseEntity.ok(alarms);
        } catch (Exception e) {
            logger.error("query all alarms exception", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorInformation.generalError(e));
        }
    }



    @ApiOperation(value="Delete alarms conditionally", notes="Delete alarms by status, interval and timestamp",
            authorizations = {
                    @Authorization(value = "oauth2schema", scopes = {
                            @AuthorizationScope(scope = "delete", description = "delete alarms"),
                            @AuthorizationScope(scope = "trust", description = "create alarms")
                    })
            }
    )
    @RequestMapping(method = RequestMethod.DELETE)
    Response delete(int interval, TimeUnit timeUnit) {
        logger.info("delete alarams by interval:{}, timeUnit:{}", interval, timeUnit);
        try {
            int deletedNum = alarmService.deleteAlarms(interval, timeUnit);
            return createResponse(HttpStatus.OK, deletedNum);
        } catch (Exception e) {
            logger.error("delete alarams exception", e);
            return createResponse(HttpStatus.INTERNAL_SERVER_ERROR, ErrorInformation.generalError(e));
        }
    }
}
