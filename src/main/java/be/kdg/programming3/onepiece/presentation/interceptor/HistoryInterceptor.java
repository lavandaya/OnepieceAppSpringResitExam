package be.kdg.programming3.onepiece.presentation.interceptor;

import be.kdg.programming3.onepiece.presentation.session.SessionHistory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class HistoryInterceptor implements HandlerInterceptor {

    private final SessionHistory sessionHistory;

    public HistoryInterceptor(SessionHistory sessionHistory) {
        this.sessionHistory = sessionHistory;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if ("GET".equalsIgnoreCase(request.getMethod()) && isPageRequest(request.getRequestURI())) {
            sessionHistory.addVisit(request.getRequestURI());
        }
        return true;
    }

    private boolean isPageRequest(String uri) {
        return !uri.startsWith("/webjars")
                && !uri.startsWith("/css")
                && !uri.startsWith("/js")
                && !uri.equals("/favicon.ico");
    }
}
