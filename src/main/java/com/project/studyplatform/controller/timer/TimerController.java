package com.project.studyplatform.controller.timer;

import com.project.studyplatform.controller.timer.dto.request.EndTimeReqDto;
import com.project.studyplatform.controller.timer.dto.request.StartTimeReqDto;
import com.project.studyplatform.controller.timer.dto.response.EndTimeRespDto;
import com.project.studyplatform.controller.timer.dto.response.StartTimeRespDto;
import com.project.studyplatform.domain.user.User;
import com.project.studyplatform.security.entity.UserDetailsImpl;
import com.project.studyplatform.service.TimerService;
import com.project.studyplatform.util.ApiResult;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/timers")
public class TimerController {

    private final TimerService timerService;

    public TimerController(TimerService timerService) {
        this.timerService = timerService;
    }

    @PostMapping("/{subjectId}/start-timer")
    public ResponseEntity<ApiResult<StartTimeRespDto>> startTimer(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable("subjectId") Long subjectId, @Valid @RequestBody StartTimeReqDto reqDto) {

        User user = userDetails.getUser();
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResult.success(timerService.startTimer(user.getId(), subjectId, reqDto)));
    }

    @PostMapping("/{subjectId}/end-timer")
    public ResponseEntity<ApiResult<EndTimeRespDto>> endTimer(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable("subjectId") Long subjectId, @Valid @RequestBody EndTimeReqDto reqDto) {
        User user = userDetails.getUser();
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResult.success(timerService.endTimer(user.getId(), subjectId, reqDto)));
    }

}
