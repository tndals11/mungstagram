package com.example.mungstragram.notification;

import com.example.mungstragram._common.dto.Response;
import com.example.mungstragram.user.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NotificationApiController {

    private final NotificationService notificationService;

    @GetMapping("/api/notifications/{id}")
    ResponseEntity<Response<NotificationResponse.DetailDTO>> detailNotification(
            @PathVariable Long id,
            HttpSession session
    ) {

        User user = (User) session.getAttribute("sessionUser");

        NotificationResponse.DetailDTO detailDTO = notificationService.detailNotification(id, user.getId());

        return ResponseEntity.ok(Response.ok(detailDTO));
    }

    @GetMapping("/api/notifications")
    ResponseEntity<Response<List<NotificationResponse.ListDTO>>> listNotification(
            HttpSession session
    ) {
        User user = (User) session.getAttribute("sessionUser");

        List<NotificationResponse.ListDTO> listDTO = notificationService.listNotification(user.getId());

        return ResponseEntity.ok(Response.ok(listDTO));
    }

}
