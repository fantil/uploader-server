package org.fanti.uploader.server.controller.base;

import org.fanti.uploader.server.dto.ResultDTO;

import static org.fanti.uploader.server.constant.ControllerConstants.STATUS_CODE_200;
import static org.fanti.uploader.server.constant.ControllerConstants.STATUS_CODE_500;

/**
 * Description:
 *
 * @author ftk
 * @date 2018/8/25
 */

public class BaseController {

    public ResultDTO ajaxDoneFail(Exception ex) {
        return ajaxDoneFail(STATUS_CODE_500, ex.getMessage());
    }

    public ResultDTO ajaxDoneFail(String message, Exception ex) {
        return ajaxDoneFail(STATUS_CODE_500, message + ex.getMessage());
    }

    public ResultDTO ajaxDoneFail(String msgCode) {
        return ajaxDoneFail(msgCode, msgCode);
    }

    private ResultDTO ajaxDoneFail(String msgCode, String message) {
        return ajaxDone(false, msgCode, message, "");
    }

    public ResultDTO ajaxDoneSuccess() {
        return ajaxDoneSuccess("");
    }

    public ResultDTO ajaxDoneSuccess(Object data) {
        return ajaxDoneSuccess(data, STATUS_CODE_200);
    }

    public ResultDTO ajaxDoneSuccess(Object data, String messageCode) {
        return ajaxDoneSuccess(data, messageCode, messageCode);
    }

    public ResultDTO ajaxDoneSuccess(Object data, String msgCode, String message) {
        return ajaxDone(true, msgCode, message, data);
    }

    private ResultDTO ajaxDone(boolean success, String msgCode, String message, Object data) {
        ResultDTO messageDto = new ResultDTO();
        messageDto.setSuccess(success);
        messageDto.setMessageCode(msgCode);
        messageDto.setMessage(message);
        messageDto.setData(data);
        return messageDto;
    }
}